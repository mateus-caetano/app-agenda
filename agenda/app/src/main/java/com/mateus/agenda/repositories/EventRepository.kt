package com.mateus.agenda.repositories

import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import model.Task

class EventRepository {

    private var dataset: MutableLiveData<List<Task>> = MutableLiveData()
    private var taskList: MutableList<Task> = mutableListOf()

    private val cloudbase: FirebaseFirestore = Firebase.firestore

    companion object {
        fun instance(): EventRepository = EventRepository()
    }
    init {
        cloudbase.collection("Events")
            .get()
            .addOnSuccessListener { value ->
                if (value != null) {
                    for (dc in value) {
                        var dataobj = dc.toObject<Teste>()
                        var location = Location(LocationManager.NETWORK_PROVIDER)
                        location.latitude = dataobj.location["latitude"] as Double
                        location.longitude = dataobj.location["longitude"] as Double

                        taskList
                            .add(
                                Task(
                                    dc.id,
                                    dc.data.getValue("title").toString(),
                                    dc.data.getValue("details").toString(),
                                    dc.data.getValue("dateTime").toString(),
                                    location,
                                    dc.data.getValue("link").toString()
                                )
                            )
                    }
                    dataset.value = taskList
                }

            }
    }


    /*Teste de implementação*/

    private fun insertEvent(item: Task){
        val event = hashMapOf(
            "title" to item.title,
            "details" to item.details,
            "dateTime" to item.dateTime,
            "location" to item.location,
            "link" to item.link)
        cloudbase.collection("Events").document(item.id)
            .set(event)
            .addOnSuccessListener {
                Log.w("AddEvent", "Added successfully: Id ${item.id}")

            }
        taskList.add(0,item)
    }
    fun remove(id: String){

        cloudbase.collection("Events").document(id)
            .delete()
            .addOnSuccessListener { Log.w("DelEvent", "DocumentSnapshot successfully deleted!: Id $id") }
            .addOnFailureListener { e -> Log.w("DelEvent", "Error deleting document", e) }
    }

    private fun initializeDataSet(): MutableLiveData<List<Task>> {
        dataset.value = taskList
        return dataset
    }

    fun getEventsList(): MutableLiveData<List<Task>> {
        if(taskList.isEmpty()){
            return initializeDataSet()
        }
        return dataset
    }

    fun saveNewEvent(event: Task): Boolean {
        insertEvent(event)
        dataset.value = taskList
        if (dataset.value == taskList){
            return true
        }
        return false
    }
    fun editEvent(id: String, event: Task): Boolean {
        deleteEvent(id)
        return saveNewEvent(event)
    }

    fun getEventById(id: String): LiveData<Task> {
        val event = dataset.value?.first{ it.id == id }
        return MutableLiveData(event)
    }

    fun deleteEvent(id: String): Boolean {
        val event = taskList.first { task -> task.id == id }
        remove(event.id)
        val result = taskList.remove(event)
        dataset.value = taskList
        return result
    }

}

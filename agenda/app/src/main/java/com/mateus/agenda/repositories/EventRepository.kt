package com.mateus.agenda.repositories

import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import model.Task

class EventRepository {

    private var dataset: MutableLiveData<List<Task>> = MutableLiveData()
    private var taskList: MutableList<Task> = mutableListOf()

    private val cloudbase: FirebaseFirestore = Firebase.firestore

    companion object {
        fun instance(): EventRepository = EventRepository()
        private val TAG = "DocSnippets"
    }
    init {
        cloudbase.collection("Events")
            .get()
            .addOnSuccessListener { value ->
                if (value != null) {
                    for (dc in value) {
                        taskList
                            .add(
                                Task(
                                    dc.id,
                                    dc.data.getValue("title").toString(),
                                    dc.data.getValue("details").toString(),
                                    dc.data.getValue("dateTime").toString(),
                                    Location(LocationManager.NETWORK_PROVIDER),
                                    dc.data.getValue("link").toString()
                                )
                            )
                        Log.w(TAG, dc.id.toString())
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
        cloudbase.collection("Events")
            .add(event)
            .addOnSuccessListener {
                item.id = it.id
                taskList.add(0,item)
            }

       /* taskList.add(0,item)
        var id2 = item.id
        Log.w(TAG, "Added successfully: Id $id2")*/
    }
    fun remove(id: String){

        cloudbase.collection("Events").document(id)
            .delete()
            .addOnSuccessListener { Log.w(TAG, "DocumentSnapshot successfully deleted!: Id $id") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }

    private fun initializeDataSet(): MutableLiveData<List<Task>> {
        Log.w(TAG, taskList.toString())
        dataset.value = taskList
        return dataset
    }
/*id = document.id
                        title = document.data.getValue("title").toString()
                        details = document.data.getValue("details").toString()
                        dateTime = document.data.getValue("dateTime").toString()
                        link = document.data.getValue("link").toString()
                        taskList
                            .add(
                                Task(
                                    id,
                                    title,
                                    details,
                                    dateTime,
                                    Location(LocationManager.NETWORK_PROVIDER),
                                    link
                                )
                            )*/
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

    fun getEventById(id: String): LiveData<Task> {
        val event = dataset.value?.first{ it.id == id }
        return MutableLiveData(event)
    }

    fun deleteEvent(id: String): Boolean {
        val event = taskList.first { task -> task.id == id }
        val result = taskList.remove(event)
        dataset.value = taskList
        return result
    }

}

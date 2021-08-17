package com.mateus.agenda.repositories

import android.location.Location
import android.location.LocationManager
import android.text.format.DateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.Task
import java.util.*

class EventRepository {
    private val dataset: MutableLiveData<List<Task>> = MutableLiveData()
    private val taskList: MutableList<Task> = mutableListOf()

    companion object {
        fun instance(): EventRepository = EventRepository()
    }

    fun initializeDataSet(): LiveData<List<Task>> {
        for (i in 1..20) {
            taskList.add(0,
                Task(
                    UUID.randomUUID().toString(),
                    "task title " + i.toString(),
                    "this task is a mock task",
                    DateFormat.format("dd-MM-yyyy hh:mm a", Date()).toString(),
                    Location(LocationManager.NETWORK_PROVIDER),
                    "https://meet.google.com/")
            )
        }
        dataset.value = taskList
        return dataset
    }

    fun getEventsList(): LiveData<List<Task>> {
        if (taskList == mutableListOf<Task>()) {
            return initializeDataSet()
        } else {
            return dataset
        }
    }

    fun saveNewEvent(event: Task): Boolean {
        taskList.add(0, event)
        dataset.value = taskList
        if (dataset.value == taskList) return true
        else return false
    }

    fun getEventById(id: String): LiveData<Task> {
        val event = dataset.value?.first{ it.id == id }
        return MutableLiveData(event)
    }

    fun deleteEvent(id: String): Boolean {
        val event = taskList.first { task -> task.id == id }
        return taskList.remove(event)
    }
}
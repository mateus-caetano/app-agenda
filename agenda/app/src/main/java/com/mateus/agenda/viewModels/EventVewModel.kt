package com.mateus.agenda.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateus.agenda.repositories.EventRepository
import model.Task

class EventVewModel(private val repository: EventRepository): ViewModel() {
    private lateinit var dataset: LiveData<List<Task>>

    fun getEventsList(): LiveData<List<Task>> {
        dataset = repository.getEventsList()
        return dataset
    }

    fun getEventById(id: String): LiveData<Task> {
        val event: LiveData<Task> = repository.getEventById(id)
        return event
    }

    fun saveNewEvent(event: Task): Boolean {
        val state: Boolean = repository.saveNewEvent(event)
        getEventsList()
        return state
    }

    fun editEvent(id: String, event: Task): Boolean {
        return repository.editEvent(id, event)
    }

    fun deleteEvent(id: String): Boolean {
        return repository.deleteEvent(id)
    }
}
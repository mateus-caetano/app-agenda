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
        return repository.getEventById(id)
    }

    fun saveNewEvent(event: Task): Boolean {
        val state: Boolean = repository.saveNewEvent(event)
        getEventsList()
        return state
    }

    fun deleteEvent(id: String): Boolean {
        return repository.deleteEvent(id)
    }
}
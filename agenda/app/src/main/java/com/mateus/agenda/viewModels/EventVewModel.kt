package com.mateus.agenda.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mateus.agenda.repositories.EventRepository
import model.Task

class EventVewModel(private val repository: EventRepository): ViewModel() {
    lateinit var dataset: LiveData<List<Task>>

    fun getEventsList(): LiveData<List<Task>> {
        dataset = repository.getEventsList()
        return dataset
    }

    fun saveNewEvent(event: Task): Boolean {
        val state: Boolean = repository.saveNewEvent(event)
        getEventsList()
        return state
    }
}
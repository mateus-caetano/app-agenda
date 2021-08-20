package com.mateus.agenda.viewModels.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mateus.agenda.repositories.EventRepository
import com.mateus.agenda.viewModels.EventVewModel

class EventsListViewModelFactory(private val repository: EventRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventVewModel(repository) as T
    }

}
package com.mateus.agenda

import android.app.AlertDialog
import androidx.lifecycle.*
import com.mateus.agenda.database.itemDatabase
import kotlinx.coroutines.launch
import model.Task
import java.lang.IllegalArgumentException

class AgendaViewModel(private val agendaitem: itemDatabase ): ViewModel() {
    private var _allitems = MutableLiveData<List<Task>>()
    val allitems: LiveData<List<Task>>
    get() {
        agendaitem.getItems { _allitems.value = it }
        return _allitems
    }
    private fun insertItem(item: Task){
        agendaitem.insert(item)
    }
    private var _selectItem = MutableLiveData<Task>()
    private fun getNewItemEntry(itemTitle:String, itemLocation:String, itemDescription:String, itemLink:String):Task{
        return Task(
            title = itemTitle,
        location = itemLocation,
        details = itemDescription,
        link = itemLink)
    }
    private fun getUpdateItemEntry(
        itemId: String,
        itemTitle: String,
        itemLocation: String,
        itemDescription: String,
        itemLink: String): Task{
        return Task(
            id = itemId,
            title = itemTitle,
            location = itemLocation,
            details = itemDescription,
            link = itemLink
        )
    }
    fun addNewItem(itemTitle:String, itemLocation:String, itemDescription:String, itemLink:String){
        val newItem = getNewItemEntry(itemTitle, itemLocation, itemDescription, itemLink)
        insertItem(newItem)
    }

    fun isEntryValid(itemTitle:String, itemLocation:String, itemDescription:String, itemLink:String): Boolean{
        return !(itemTitle.isBlank() || itemDescription.isBlank() || itemLink.isBlank() || itemLocation.isBlank())
    }
    fun retriveItem(id: String): LiveData<Task>{
        agendaitem.getItem(id){ _selectItem.value = it}
        return _selectItem
    }
    private fun updateItem(item: Task){
        agendaitem.update(item)
    }

    fun deleteItem(item: Task){
       agendaitem.delete(item)
    }
}
class AgendaModelFactory(private val item:itemDatabase): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AgendaViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AgendaViewModel(item) as T
        }
        throw IllegalArgumentException("Unknow viewmodel class")
    }
}
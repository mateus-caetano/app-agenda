package com.mateus.agenda.firebase

import android.location.Location
import android.location.LocationManager
import com.google.firebase.database.*
import model.Task

class Database(private val db: DatabaseReference) {

    private val listenerTracker = HashMap<DatabaseReference, ValueEventListener>()

    fun getItems(callback: (List<Task>) -> Unit)/*: MutableList<Task> */{
        val items = mutableListOf<Task>()
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(child in snapshot.children){
                    items.add(child.getValue(Task::class.java)!!)
                }
                val oldListener = listenerTracker.put(snapshot.ref,this)
                if(oldListener != null){
                    db.removeEventListener(oldListener)
                }else{
                    print("lol")
                }
                callback(items)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        //return items
    }

    fun getItem(id: String, callback:(Task)->Unit){
        db.child(id).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val newItem = snapshot.getValue(Task::class.java)!!
                val oldListener = listenerTracker.put(snapshot.ref,this)
                if(oldListener != null){
                    db.child(id).removeEventListener(oldListener)
                }
                callback(newItem)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun insert(item: Task){
        val key = db.push().key
        if(key != null) {
            db.child(key).setValue(item.copy(id = key))
        }
    }
    fun update(item: Task,map: Map<String, Any> = mapOf()){
        if(map.isEmpty()) {
            db.child(item.id).setValue(item)
        }else{
            db.child(item.id).updateChildren(map)
        }
    }
    fun delete(item: Task){
        db.child(item.id).removeValue()
    }
    fun removeAllListeners(){
        for(listener in listenerTracker.values){
            db.removeEventListener(listener)
        }
        listenerTracker.clear()
    }

    fun getId(){

    }

}
package com.mateus.agenda.firebase

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InstanceDatabase {
    val database: DatabaseReference by lazy { Firebase.database.reference }

    fun itemFirebaseRepository() = Database(database.child("Eventos"))

}
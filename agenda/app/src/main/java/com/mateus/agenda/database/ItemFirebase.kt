package com.mateus.agenda.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ItemFirebase {
    val database: DatabaseReference by lazy { Firebase.database.reference }

    fun itemFirebaseRepository() = itemDatabase(database.child("items"))
}
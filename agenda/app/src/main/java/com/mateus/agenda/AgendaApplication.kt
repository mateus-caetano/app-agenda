package com.mateus.agenda

import android.app.Application
import com.mateus.agenda.database.ItemFirebase

class AgendaApplication: Application() {
    val database: ItemFirebase by lazy { ItemFirebase() }
}
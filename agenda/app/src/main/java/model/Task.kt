package model

import android.location.Location

data class Task(val title: String, val details: String, val dateTime: String, val location: Location, val link: String)
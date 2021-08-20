package model

import android.location.Location

data class Task(val id: String = "",
                var title: String = ""
                , var details: String = ""
                , var dateTime: String = ""
                , val location: Location
                , var link: String = ""){

}
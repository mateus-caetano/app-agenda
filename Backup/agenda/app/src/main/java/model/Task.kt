package model

import android.location.Location
import android.location.LocationManager

data class Task(var id: String = ""
                , var title: String? = null
                , var details: String? = null
                , var dateTime: String? = null
                , var location: Location
                , var link: String? = null )
val teste = Task("teste", "title_teste", "details_teste",
"dateTime_test", Location(LocationManager.NETWORK_PROVIDER), "link_test")

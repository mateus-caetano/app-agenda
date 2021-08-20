package com.mateus.agenda.repositories

import android.location.Location
import android.location.LocationManager

class Teste(): java.io.Serializable {
    public var title:String=""
    public var details:String=""
    public  var dateTime:String=""
    public var link: String = ""
    public lateinit var location: HashMap<String, Any>


    constructor(title: String, details: String, dateTime: String, link: String, location: HashMap<String, Any>) : this() {
        this.title = title
        this.details = details
        this.dateTime = dateTime
        this.link = link
        this.location = location

    }
}
package com.mateus.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigateToDetails(view: View) {
        view.findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
    }

    fun navigateToMap(view: View) {
        view.findNavController().navigate(R.id.action_detailsFragment_to_mapsFragment)
    }

    fun eventDialog(view: View) {
        val dialog = NewEventDialog()
        dialog.show(supportFragmentManager, "newEvent")
    }
}
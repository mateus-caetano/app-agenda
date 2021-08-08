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

    fun navigateToDestails(view: View) {
        view.findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
    }

    fun eventDialog(view: View) {
        val dialog = NewEventDialog()
        dialog.show(supportFragmentManager, "newEvent")
    }
}
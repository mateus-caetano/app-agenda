package com.mateus.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun navigateToDetails(view: View) {
        view.findNavController().navigate(R.id.action_listFragment_to_detailsFragment)
    }

    fun navigateToMap(view: View) {
        view.findNavController().navigate(R.id.action_detailsFragment_to_mapFragment)
    }

    fun eventDialog(view: View) {
        val dialog = NewEventDialog()
        dialog.show(supportFragmentManager, "newEvent")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(-3.4373045,-39.1439613))
                .title("Marker")
        )
    }

}
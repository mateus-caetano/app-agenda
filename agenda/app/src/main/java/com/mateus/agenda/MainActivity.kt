package com.mateus.agenda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        //setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()

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
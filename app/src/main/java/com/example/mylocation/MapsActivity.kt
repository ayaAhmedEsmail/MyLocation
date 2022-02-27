package com.example.mylocation

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation:Location
    private lateinit var  fusedLocationProviderClient: FusedLocationProviderClient

    companion object{
        private const val LOCATION_REQUEST_CODE =1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled =true
        mMap.setOnMarkerClickListener(this)
        setUpMap()

        // Add a marker in Sydney and move the camera
        val grandKadriHotel= LatLng(33.85148430277257,35.895525763213946)
        val germanosPastry= LatLng(33.85217073479985, 35.89477838111461)
        val malakelTawook= LatLng(33.85334017189446, 35.894389460938246)
        val zBurgerHouse= LatLng (33.85454300475094, 35.894561122304474)
        val collègeOriental= LatLng(33.85129821373707, 35.89446263654391)
        val vEROMODA= LatLng(33.85048738635312, 35.89664059012788)

        val bitmapIcon= AppCompatResources.getDrawable(this, R.drawable.ic_trip)?.toBitmap()

        val gkMarker = mMap.addMarker(
            MarkerOptions()
                .position(grandKadriHotel)
                .icon(bitmapIcon?.let { BitmapDescriptorFactory.fromBitmap(it) })

        )
        val gpMarker = mMap.addMarker(
            MarkerOptions()
                .position(germanosPastry)
                .icon(bitmapIcon?.let { BitmapDescriptorFactory.fromBitmap(it) })

        )
        val mMarker = mMap.addMarker(
            MarkerOptions()
                .position(malakelTawook)
                .icon(bitmapIcon?.let { BitmapDescriptorFactory.fromBitmap(it) })

        )
        val zMarker = mMap.addMarker(
            MarkerOptions()
                .position(zBurgerHouse)
                .icon(bitmapIcon?.let { BitmapDescriptorFactory.fromBitmap(it) })

        )
        val cMarker = mMap.addMarker(
            MarkerOptions()
                .position(collègeOriental)
                .icon(bitmapIcon?.let { BitmapDescriptorFactory.fromBitmap(it) })

        )
        val vMarker = mMap.addMarker(
            MarkerOptions()
                .position(vEROMODA)
                .icon(bitmapIcon?.let { BitmapDescriptorFactory.fromBitmap(it) })

        )

    }

    private fun setUpMap(){
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),LOCATION_REQUEST_CODE)

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled  = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(this) {
            location -> if (location != null){
                lastLocation=location
            val  currentLocation =LatLng(location.latitude,location.longitude)
            placeMarkerOnMap(currentLocation)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,12f))
            }
        }

    }

    private fun placeMarkerOnMap(currentLocation: LatLng) {
        val markerOptions = MarkerOptions().position(currentLocation)
        markerOptions.title("$currentLocation")
        mMap.addMarker(markerOptions)


    }

    override fun onMarkerClick(p0: Marker): Boolean =false

}
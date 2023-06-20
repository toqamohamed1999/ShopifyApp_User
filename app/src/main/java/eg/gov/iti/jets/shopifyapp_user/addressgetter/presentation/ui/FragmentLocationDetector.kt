package eg.gov.iti.jets.shopifyapp_user.addressgetter.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.MatrixCursor
import android.location.Address
import android.location.Geocoder
import android.location.Geocoder.GeocodeListener
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.BaseColumns
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.Task
import eg.gov.iti.jets.shopifyapp_user.databinding.FragmentLocationDetectorBinding
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.util.Dialogs
import java.util.*

class FragmentLocationDetector() : Fragment(), GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener {
    val egyptBounds = LatLngBounds(
        LatLng(22.000083, 25.000021),  // SW bounds
        LatLng(31.324762, 34.217809) // NE bounds
    )

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mGoogleApiClient: GoogleApiClient
    lateinit var mGoogleMap: GoogleMap
    private var pinSelected:Boolean=false
    private var myLocation=false
    private var latitude:String=""
    private var longitude:String=""
    private  val PermissionID: Int= 10
    private var binding:FragmentLocationDetectorBinding?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationDetectorBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding?.loadingAnim?.visibility = View.INVISIBLE
            binding?.imageButtonMyLocation?.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                     "Loading Your Location",
                    Toast.LENGTH_SHORT
                ).show()
                binding?.imageButtonMyLocation?.visibility = View.INVISIBLE
                binding?.loadingAnim?.visibility = View.VISIBLE
                binding?.loadingAnim?.playAnimation()
                myLocation = true
                getLastLocation()
            }
            binding?.searchViewPlace?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                @RequiresApi(Build.VERSION_CODES.TIRAMISU)
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val latlng = query?.let {
                        getLatLonForAddress(
                            it
                        )
                    }
                    if (latlng != null) {
                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, 10F)
                            latitude = latlng.latitude.toString()
                            longitude = latlng.longitude.toString()
                        pinSelected = true
                        mGoogleMap.clear()
                        mGoogleMap.addMarker(MarkerOptions().position(latlng))
                        mGoogleMap.animateCamera(cameraUpdate)
                    }
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {

                    if(!newText.isNullOrEmpty()) {
                        val cursor = MatrixCursor(
                            arrayOf(
                                BaseColumns._ID,
                                SearchManager.SUGGEST_COLUMN_TEXT_1
                            )
                        )
                    }
                    return true
                }
            })
            binding?.imageButtonMapConfirm?.setOnClickListener {
                if (pinSelected) {
                    val la=latitude.toDouble()
                    val lo=longitude.toDouble()
                    if(la> egyptBounds.southwest.latitude&&la<egyptBounds.northeast.latitude&&lo>egyptBounds.southwest.longitude&&lo<egyptBounds.northeast.longitude)
                        getAddress()
                    else{
                        Dialogs.SnakeToast(requireView(),"Please Select Place In Egypt !!")
                    }
                } else {
                    Dialogs.SnakeToast(it, "Please Select Location First")
                }
            }
            binding?.mapView?.onCreate(savedInstanceState)
            binding?.mapView?.onResume()
            mGoogleApiClient = GoogleApiClient.Builder(requireContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
            setUpMap()
    }
    override fun onResume() {
        super.onResume()
        if(myLocation) {
            binding?.loadingAnim?.playAnimation()
            getLastLocation()
        }
    }
   private fun getAddress()
    {
        UserSettings.isSelected =true
        var address:MutableList<Address>?=null
        val geocoder= Geocoder(requireContext(),Locale("en"))
        if(latitude.isNotEmpty())
            address =  geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(),1)

        if(!address.isNullOrEmpty())
          UserSettings.selectedAddress = address[0]
        binding?.mapView?.onDestroy()
        binding?.root?.findNavController()?.popBackStack()
    }
    fun getLatLonForAddress(location: String): LatLng {
        val geocoder = Geocoder(requireContext(), Locale("en"))
        val addressList = geocoder.getFromLocationName(location, 1)
        var address: Address?=null
        if(addressList?.isNotEmpty() == true)
        {
            address = addressList[0]
        }

        return if(address!=null)LatLng(address.latitude, address.longitude) else LatLng(0.0, 0.0)
    }
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(request: CurrentLocationRequest, token: CancellationToken?): Task<Location> {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this.requireActivity())

        return fusedLocationProviderClient.getCurrentLocation(request,token)
    }

    private fun getLastLocation(){
        if(checkPermissions())
        {
            if(isLocationEnabled())
            {
                requestNewLoaction()
            }else{
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
            binding?.loadingAnim?.pauseAnimation()
            requestPermissions()
        }
    }

    private fun requestNewLoaction(){
        val currentLocationRequest= CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
        getCurrentLocation(currentLocationRequest,null).addOnSuccessListener {
            try {
                 latitude = it.latitude.toString()
                 longitude = it.longitude.toString()
                fusedLocationProviderClient.removeLocationUpdates(this)
                binding?.loadingAnim?.cancelAnimation()
                binding?.loadingAnim?.visibility = View.GONE

                if (myLocation) {
                        myLocation=false
                        binding?.imageButtonMyLocation?.visibility = View.VISIBLE
                        val cameraUpdate =
                            CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 10F)
                        pinSelected = true
                        mGoogleMap.clear()
                        mGoogleMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)))
                        mGoogleMap.animateCamera(cameraUpdate)
                    }
            }catch (e:java.lang.IllegalStateException)
            {
                Log.e("error",e.toString())
            }
        }
    }

    private fun checkPermissions(): Boolean{
        var result = false
        if ((ContextCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)   &&(ContextCompat.checkSelfPermission(this.requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)) {
            result  = true
        }
        return result
    }
    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager =this.requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
    private fun requestPermissions():Unit{
        ActivityCompat.requestPermissions(this.requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),PermissionID
        )
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PermissionID)
        {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                binding?.loadingAnim?.playAnimation()
                getLastLocation()
            }
        }
    }
    private fun setUpMap() {
        binding?.mapView?.getMapAsync { googleMap ->
            mGoogleMap = googleMap
            mGoogleMap.setLatLngBoundsForCameraTarget(egyptBounds)
            val cameraUpdate =
                CameraUpdateFactory.zoomBy(10F)
            mGoogleMap.animateCamera(cameraUpdate)
            mGoogleMap.setOnMapLongClickListener{
                 latitude=it.latitude.toString()
                 longitude=it.longitude.toString()
                pinSelected=true
                mGoogleMap.clear()
                mGoogleMap.addMarker(MarkerOptions().position(it))
            }
        }

    }
    override fun onConnected(p0: Bundle?) {
        if(checkPermissions())
            getLastLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e("","Suspended Connection")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e("","Failed Connection")
    }

    override fun onLocationChanged(p0: Location) {
        Log.e("","Location Changed")
    }
}
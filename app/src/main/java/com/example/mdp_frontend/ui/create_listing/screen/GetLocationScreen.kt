package com.example.mdp_frontend.ui.create_listing.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

@Composable
fun GetLocationScreen(
    latitude: Double,
    longitude: Double,
    updateLatitude: (Double) -> Unit,
    updateLongitude: (Double) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    val context = LocalContext.current
    var latInput: String by remember { mutableStateOf(latitude.toString()) }
    var lonInput: String by remember { mutableStateOf(longitude.toString()) }

    // Location hell -- needs to be refactored, but not now
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (lo in p0.locations) {
                // Update UI with location data
                updateLatitude(lo.latitude)
                latInput = lo.latitude.toString()
                updateLongitude(lo.longitude)
                lonInput = lo.longitude.toString()
            }
        }
    }
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallback.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                launcherMultiplePermissions.launch(permissions)
                return
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }

    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = latInput,
                onValueChange = {
                    latInput = it.replace(',', '.')
                    updateLatitude(latInput.toDoubleOrNull() ?: 0.0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Latitude") }
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                value = lonInput,
                onValueChange = {
                    lonInput = it.replace(',', '.')
                    updateLongitude(lonInput.toDoubleOrNull() ?: 0.0)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Longitude") }
            )
            Button(onClick = {
                if (permissions.all {
                        ContextCompat.checkSelfPermission(
                            context,
                            it
                        ) == PackageManager.PERMISSION_GRANTED
                    }) {
                    // Get the location
                    startLocationUpdates()
                } else {
                    launcherMultiplePermissions.launch(permissions)
                }
            }) {
                Text(text = "Get current location")
            }
        }
    }
}

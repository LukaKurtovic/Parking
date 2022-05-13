package com.example.parking.helpers

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultCallback
import com.example.parking.utils.hasPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionHelper @Inject constructor(@ApplicationContext private val context: Context) {

    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    val smsPermission = Manifest.permission.SEND_SMS

    fun withPermission(
        permission: String,
        onPermissionGranted: () -> Unit,
        onPermissionsDenied: () -> Unit
    ) {
        if (context.hasPermission(permission)) onPermissionGranted() else onPermissionsDenied()
    }
}

class ActivityResultCallback(
    private val onPermissionGranted: () -> Unit,
    private val onPermissionsDenied: () -> Unit
) : ActivityResultCallback<MutableMap<String, Boolean>> {

    override fun onActivityResult(result: MutableMap<String, Boolean>?) {
        val granted = result?.entries?.all { it.value } ?: false
        if (granted) onPermissionGranted() else onPermissionsDenied()
    }
}
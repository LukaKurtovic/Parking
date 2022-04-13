package com.example.parking.Utils

import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

fun Fragment.hasPermission (permission: String): Boolean{
    return ActivityCompat.checkSelfPermission(requireActivity(), permission) == PackageManager.PERMISSION_GRANTED
}


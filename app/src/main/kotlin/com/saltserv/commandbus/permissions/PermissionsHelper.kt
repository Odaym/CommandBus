package com.saltserv.commandbus.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun allPermissionsGranted(requiredPermissions: Array<String>, context: Context) =
    requiredPermissions.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
    }

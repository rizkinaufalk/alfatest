package com.rizki.alfatest.ext.delegate

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

interface PermissionCheck {
    fun permissionGranted(context: Fragment, permissions: Array<String>, resLauncher: ActivityResultLauncher<Array<String>>)  : Boolean
}

class PermissionCheckImpl : PermissionCheck {

    override fun permissionGranted(
        context: Fragment,
        permissions: Array<String>,
        resLauncher: ActivityResultLauncher<Array<String>>
    ) :Boolean {
        var allPermitted = false
        for (permission in permissions) {
            allPermitted = (ContextCompat.checkSelfPermission(context.requireContext(), permission)
                    == PackageManager.PERMISSION_GRANTED)
            if (!allPermitted) break
        }
        if (allPermitted) return true
        resLauncher.launch(permissions)
        return false

    }
}
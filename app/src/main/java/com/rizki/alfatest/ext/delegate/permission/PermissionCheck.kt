package com.rizki.alfatest.ext.delegate.permission

import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment

interface PermissionCheck {

    fun permissionGranted(context: Fragment, permissions: Array<String>, resLauncher: ActivityResultLauncher<Array<String>>)  : Boolean

}
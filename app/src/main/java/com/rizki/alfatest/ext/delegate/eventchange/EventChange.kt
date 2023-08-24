package com.rizki.alfatest.ext.delegate.eventchange

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

interface EventChange {
    fun registerLifecycleOwner(owner: LifecycleOwner, fragment: Fragment)
}
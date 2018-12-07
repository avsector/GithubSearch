package com.samsaz.githubsearch.util

import android.content.Context
import com.google.android.gms.security.ProviderInstaller
import java.lang.Exception
import javax.inject.Inject

/**
 * Copyright 2018
 * Created and maintained by Hamid Moazzami
 */

class TlsProviderInstaller @Inject constructor(val context: Context) {
    var alreadyInstalled = false

    fun install(): Boolean {
        if (alreadyInstalled)
            return false

        return try {
            ProviderInstaller.installIfNeeded(context)
            alreadyInstalled = true
            true
        } catch (e: Exception) {
            false
        }
    }
}
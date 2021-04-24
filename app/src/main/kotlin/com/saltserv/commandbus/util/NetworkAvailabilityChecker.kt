package com.saltserv.commandbus.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

interface NetworkAvailabilityChecker {
    val isConnectedToInternet: Boolean
}

class NetworkAvailabilityCheckerImpl(context: Context) :
    NetworkAvailabilityChecker {

    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isConnectedToInternet: Boolean
        get() {

            val activeNetwork = cm.activeNetwork

            if (activeNetwork != null) {
                val nc = cm.getNetworkCapabilities(activeNetwork)
                if (nc != null) {
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ))
                }
            }

            return false
        }
}
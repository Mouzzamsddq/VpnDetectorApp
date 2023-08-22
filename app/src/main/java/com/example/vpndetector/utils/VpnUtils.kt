package com.example.vpndetector.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object VpnUtils {
    fun isVpnActive(context: Context) =
        callbackFlow {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            if (connectivityManager == null) {
                channel.close(IllegalStateException("connectivity manager is null"))
                return@callbackFlow
            } else {
                val callback = object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        channel.trySend(true)
                    }

                    override fun onLost(network: Network) {
                        channel.trySend(false)
                    }
                }
                connectivityManager.registerNetworkCallback(
                    NetworkRequest.Builder()
                        .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
                        .removeCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN)
                        .build(),
                    callback,
                )
                awaitClose {
                    connectivityManager.unregisterNetworkCallback(callback)
                }
            }
        }
}

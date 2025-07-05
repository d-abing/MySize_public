package com.aube.mysize.app

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object NetworkMonitor {

    private val _networkAvailable = MutableStateFlow(true)
    val networkAvailable: StateFlow<Boolean> get() = _networkAvailable

    private var callback: ConnectivityManager.NetworkCallback? = null

    fun register(context: Context) {
        val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        _networkAvailable.value = isNetworkAvailable(context)

        callback?.let {
            try {
                cm.unregisterNetworkCallback(it)
            } catch (_: Exception) {}
        }

        val builder = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        val newCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _networkAvailable.value = true
            }

            override fun onLost(network: Network) {
                _networkAvailable.value = false
            }

            override fun onUnavailable() {
                _networkAvailable.value = false
            }
        }

        callback = newCallback
        cm.registerNetworkCallback(builder.build(), newCallback)
    }

    fun unregister(context: Context) {
        val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        callback?.let {
            try {
                cm.unregisterNetworkCallback(it)
            } catch (_: Exception) {}
        }
        callback = null
    }
}

// 초기 네트워크 상태 확인 함수
fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val capabilities = cm.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

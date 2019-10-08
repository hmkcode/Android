package com.hmkcode.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var tvConnectivity: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvConnectivity = findViewById<TextView>(R.id.tvConnectivity)

        // get ConnectivityManager
        val cm:ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val builder: NetworkRequest.Builder = NetworkRequest.Builder()
            cm.registerNetworkCallback(

                builder.build(),
                object : ConnectivityManager.NetworkCallback() {

                    override fun onAvailable(network: Network) {
                        lifecycleScope.launch {
                            Log.i("MainActivity", "onAvailable!")

                            // check if NetworkCapabilities has TRANSPORT_WIFI
                            val isWifi:Boolean = cm.getNetworkCapabilities(network).hasTransport(
                                NetworkCapabilities.TRANSPORT_WIFI)

                            doSomething(true, isWifi)
                        }
                    }

                    override fun onLost(network: Network) {
                        lifecycleScope.launch {
                            Log.i("MainActivity", "onLost!")
                            doSomething(false)
                        }
                    }
                }
            )
        }

    }

    private suspend fun doSomething(isConnected:Boolean, isWifi:Boolean= false){
        withContext(Dispatchers.Main){
            if(isConnected) {
                tvConnectivity.text = "Connected "+(if(isWifi)"WIFI" else "MOBILE")
                tvConnectivity.setBackgroundColor(-0x8333da)
            }else {
                tvConnectivity.text = "Not Connected"
                tvConnectivity.setBackgroundColor(-0x10000)
            }
        }
    }
}

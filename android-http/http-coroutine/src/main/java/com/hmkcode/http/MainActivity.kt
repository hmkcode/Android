package com.hmkcode.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.NumberFormatException
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var tvIsConnected: TextView;
    lateinit var tvResult: TextView;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvIsConnected = findViewById<TextView>(R.id.tvIsConnected)
        tvResult = findViewById<TextView>(R.id.tvResult)

        if(checkNetworkConnection()) {
            lifecycleScope.launch {
                val result = httpGet("http://hmkcode-api.appspot.com/rest/api/hello/Android")
                tvResult.setText(result)
            }
        }

    }


    private fun checkNetworkConnection(): Boolean {

            val cm:ConnectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



        val networkInfo:NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = if(networkInfo != null) networkInfo.isConnected() else false

        if(isConnected){
            tvIsConnected.setText("Connected "+networkInfo?.typeName)
            tvIsConnected.setBackgroundColor(0xFF7CCC26.toInt())

        }else{
            tvIsConnected.setText("Not Connected!")
            tvIsConnected.setBackgroundColor(0xFFFF0000.toInt())
        }
        return isConnected;
    }



    private suspend fun httpGet(myURL: String?): String? {

        val result = withContext(Dispatchers.IO) {
            val inputStream: InputStream


            // create URL
            val url: URL = URL(myURL)

            // create HttpURLConnection
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

            // make GET request to the given URL
            conn.connect()

            // receive response as inputStream
            inputStream = conn.inputStream

            // convert inputstream to string
            if (inputStream != null)
                convertInputStreamToString(inputStream)
            else
                "Did not work!"


        }
        return result
    }

    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader:BufferedReader? = BufferedReader(InputStreamReader(inputStream))

        var line:String? = bufferedReader?.readLine()
        var result:String = ""

        while (line != null) {
            result += line
            line = bufferedReader?.readLine()
        }

        inputStream.close()
        return result
    }

}

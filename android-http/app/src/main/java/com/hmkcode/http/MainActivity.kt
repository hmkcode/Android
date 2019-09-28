package com.hmkcode.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
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

        if(checkNetworkConnection())
            HTTPAsyncTask().execute("http://hmkcode-api.appspot.com/rest/api/hello/Android")
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

    inner class HTTPAsyncTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg urls: String?): String {
            return HttpGet(urls[0])
        }
        override fun onPostExecute(result: String?) {
            tvResult.setText(result)
        }
    }

    private fun HttpGet(myURL: String?): String {

        val inputStream:InputStream
        val result:String

        // create URL
        val url:URL = URL(myURL)

        // create HttpURLConnection
        val conn:HttpURLConnection = url.openConnection() as HttpURLConnection

        // make GET request to the given URL
        conn.connect()

        // receive response as inputStream
        inputStream = conn.inputStream

        // convert inputstream to string
        if(inputStream != null)
            result = convertInputStreamToString(inputStream)
        else
            result = "Did not work!"
        
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

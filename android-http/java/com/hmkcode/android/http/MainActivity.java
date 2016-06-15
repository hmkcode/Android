  package com.hmkcode.android.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

  public class MainActivity extends AppCompatActivity {

    TextView tvIsConnected;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        tvResult = (TextView) findViewById(R.id.tvResult);
        if(checkNetworkConnection())
            // perform HTTP GET request
            new HTTPAsyncTask().execute("http://hmkcode.com/examples/index.php");
    }


    // check network connection
    public boolean checkNetworkConnection() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        boolean isConnected = false;
        if (networkInfo != null && (isConnected = networkInfo.isConnected())) {
            // show "Connected" & type of network "WIFI or MOBILE"
            tvIsConnected.setText("Connected "+networkInfo.getTypeName());
            // change background color to red
            tvIsConnected.setBackgroundColor(0xFF7CCC26);


        } else {
            // show "Not Connected"
            tvIsConnected.setText("Not Connected");
            // change background color to green
            tvIsConnected.setBackgroundColor(0xFFFF0000);
        }

        return isConnected;
    }

  private class HTTPAsyncTask extends AsyncTask<String, Void, String> {
      @Override
      protected String doInBackground(String... urls) {

          // params comes from the execute() call: params[0] is the url.
          try {
              return HttpGet(urls[0]);
          } catch (IOException e) {
              return "Unable to retrieve web page. URL may be invalid.";
          }
      }
      // onPostExecute displays the results of the AsyncTask.
      @Override
      protected void onPostExecute(String result) {
          tvResult.setText(result);
      }
  }

  private String HttpGet(String myUrl) throws IOException {
      InputStream inputStream = null;
      String result = "";

          URL url = new URL(myUrl);

          // create HttpURLConnection
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();

          // make GET request to the given URL
          conn.connect();

          // receive response as inputStream
          inputStream = conn.getInputStream();

          // convert inputstream to string
          if(inputStream != null)
              result = convertInputStreamToString(inputStream);
          else
              result = "Did not work!";

      return result;
  }

      private static String convertInputStreamToString(InputStream inputStream) throws IOException{
          BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
          String line = "";
          String result = "";
          while((line = bufferedReader.readLine()) != null)
              result += line;

          inputStream.close();
          return result;

      }


  }

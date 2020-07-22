package simple.musicgenie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Ankit on 8/26/2016.
 */
public class MusicStreamer {

    private static final String TAG = "MusicStreamer";
    private static Context context;
    private static MusicStreamer mInstance;
    private String vid;
    private String file;
    private OnStreamUriFetchedListener onStreamUriFetchedListener;


    public MusicStreamer(Context context) {
        this.context = context;
    }

    public static MusicStreamer getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MusicStreamer(context);
        }
        return mInstance;
    }

    public MusicStreamer setOnStreamUriFetchedListener(OnStreamUriFetchedListener listener){
        this.onStreamUriFetchedListener = listener;
        return this;
    }

    public MusicStreamer setData(String v_id,String file_name){
       this.vid = v_id;
        this.file = file_name;
        return this;
    }

    public void initProcess(){
        new StreamThread(this.vid,this.file).start();
        log("started fetching uri");
    }

    private void broadcastURI(String t_url,String file) {

            Intent intent = new Intent(Constants.ACTION_STREAM_URL_FETCHED);
            intent.putExtra(Constants.EXTRAA_URI, t_url);
        intent.putExtra(Constants.EXTRAA_STREAM_FILE, file);
            context.sendBroadcast(intent);
        }

    private void log(String s) {
        Log.d(TAG, "log " + s);
    }

    public interface OnStreamUriFetchedListener{
        void onUriAvailable(String uri);
    }

    private class StreamThread extends Thread{

        private String v_id;
        private String file;
        public StreamThread(String v_id, String file) {
            this.v_id = v_id;
            this.file = file;
        }

        @Override
        public void run() {

            final String t_v_id = this.v_id;
            String streaming_url_pref = Constants.SERVER_URL;

                try {
    // getting download url
                    //=http://ymp3.aavi.me/api/v1/stream?url=fSR3dG4kPCIkanZ2cnU8MTF0NC8vL3VwL3I3c251cHV7MGlxcWluZ3hrZmdxMGVxbzF4a2ZncXJuY3tkY2VtQXdycD9IaktLZWhvVDZFZShrdmNpPzM2Mih1cmN0Y291P2VuZ3AnNEVmd3QnNEVnayc0RWlldCc0RWlrdCc0RWtmJzRFa3BrdmV5cGZkcnUnNEVrcic0RWtyZGt2dSc0RWt2Y2knNEVtZ2dyY25reGcnNEVub3YnNEVva29nJzRFb28nNEVvcCc0RW91JzRFb3gnNEVwaic0RXJuJzRFdGdzd2t0Z3V1bic0RXVxd3RlZyc0RXdycCc0RWd6cmt0Zyhvdj8zNjk0ODU0Nzs0KHV4Z3Q_NShpZXQ_d3UoZ3pya3RnPzM2OTQ4NzY3OjgodGdzd2t0Z3V1bj97Z3Uoa3BrdmV5cGZkcnU_OTg6ODQ3MihtZ3s_e3Y4KGlrdD97Z3Uob2tvZz9jd2ZrcSc0SG9yNihtZ2dyY25reGc_e2d1KHBqP0tpcnllbEN6Tm9ualxGSzRNaW16T2xld09FNnlObEcoZW5ncD80Ozc5Ozo2KGZ3dD8zOjgwNDIyKG5vdj8zNjc6NDI0NDU6ODg3Mzc1KHVxd3RlZz97cXd2d2RnKGtyPzc2MDo2MDIwMzo7KG91P2N3KGdrP29ybElYOHhZT1tjezppVlt7cXBDREMob3g_byhybj8zNyhrcmRrdnU_MihrZj9xL0NDUmZ1UlhpXHVMTG9ONEN4dlRNVHFudDROOHV1e3o3a2tWdGlaNllMenBVKG9wP3VwL3I3c251cHV7KG9vPzUzKHVraXBjdnd0Zz86MkU7NDg0RUdGMzVEMjQ1OzZEOTUyQzI4OUY7RUY3NDNFMjNDQzg3MDpDSDYyNUhHOEg7RkZGNjY7OTpDREg2M0c7OzU7OTcyNUY0NzlDNjsodGN2Z2R7cmN1dT97Z3UkLiIkbmdwaXZqJDwiJDU8MjkkLiIka2YkPCIkbVpba1dhTEVbdlckLiIkdmt2bmckPCIkUHdvZCIqUWhoa2VrY24iWGtmZ3ErIi8iTmtwbWtwIlJjdG0kfw%3D%3D

                    String _url = Constants.SERVER_URL + "/api/v1/stream?url=" + t_v_id;
                    log("for stream url requesting on "+_url);
                    URL u = new URL(_url);
                    URLConnection dconnection = u.openConnection();
                    dconnection.setReadTimeout(20000);
                    dconnection.setConnectTimeout(20000);
                    dconnection.connect();
                    StringBuilder result = new StringBuilder();
                    InputStream in = new BufferedInputStream(dconnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    try {
                        JSONObject obj = new JSONObject(result.toString());
                        if(obj.getInt("status")==200){
                            streaming_url_pref += obj.getString("url");
                            log("stream url:" + streaming_url_pref);
                            onStreamUriFetchedListener.onUriAvailable(streaming_url_pref);
                            broadcastURI(streaming_url_pref, file);
                        }else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    log("URL exception " + e);
                } catch (IOException e) {
                    log("IO exception " + e);
                }
            }
    }


}

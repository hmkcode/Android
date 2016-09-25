package simple.musicgenie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ankit on 9/25/2016.
 */
public class DownloadedSongsAdapter extends ArrayAdapter<String> {

    private static final String TAG = "DownloadedSongsAdapter";
    private static Context context;
    private static DownloadedSongsAdapter mInstance;
    private ArrayList<String> fileList;
    //Views
    Button deleteBtn;
    Button playBtn;
    TextView title;
    TextView duration;
    TextView artist;
    TextView fileSize;
    ImageView albumArt;


    public DownloadedSongsAdapter(Context context) {
        super(context,0);
        this.context = context;
        fileList = new ArrayList<>();
    }

    public static DownloadedSongsAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DownloadedSongsAdapter(context);
        }
        return mInstance;
    }

    public void setItemList(ArrayList<String> list){
        this.fileList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View tempView = convertView;
        if(tempView == null){
            tempView = LayoutInflater.from(context).inflate(R.layout.downloaded_item,parent,false);
        }
        bind(tempView, position);
        return tempView;
    }

    private void bind(View view, int position){

        title = (TextView) view.findViewById(R.id.song_title);
        duration = (TextView) view.findViewById(R.id.content_time_length);
        //fileSize = (TextView) view.findViewById(R.id.fileSize);
        albumArt = (ImageView) view.findViewById(R.id.albumArt);
        artist = (TextView) view.findViewById(R.id.artist);
        final android.media.MediaMetadataRetriever mmr= new MediaMetadataRetriever();
        log(fileList.get(position).toString());
        mmr.setDataSource(fileList.get(position));
        byte[] data= mmr.getEmbeddedPicture();
        if(data!=null){
            Bitmap bmp= BitmapFactory.decodeByteArray(data, 0, data.length);
            albumArt.setImageBitmap(bmp);
        }
        else{
            //Todo: change default album art
            albumArt.setImageResource(android.R.drawable.presence_audio_online);
        }

        artist.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        title.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        int totalMillis = 0;
        try {
            totalMillis = Integer.parseInt((mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
        } catch (Exception e){
            log("exception while retrieving meta info");
        }
        int minute = (totalMillis/1000)/60;
        int second = (totalMillis/1000)%60;
        String min = (String.valueOf(minute).length()==1)?"0"+String.valueOf(minute):String.valueOf(minute);
        String sec = (String.valueOf(second).length()==1)?"0"+String.valueOf(second):String.valueOf(second);

        duration.setText(min + ":" + sec);
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    private void log(String msg){
        Log.d(TAG, "log " + msg);
    }
}

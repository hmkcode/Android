package simple.tierscrollview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static final int TYPE_SONG = 0;
    private static final int TYPE_SECTION_TITLE = 1;
    private static final String TAG = "TrendingRecylerAdapter";
    private static Context context;
    private static TrendingRecyclerViewAdapter mInstance;
    private ArrayList<ViewTypeModel> typeViewList;
    private ArrayList<TrendingSongModel> trendingSongList;
    private ArrayList<Song> songs;
    private int orientation;
    private int screenMode;
    private int viewToInflate;
    private TaskAddListener taskAddListener;
    private OnStreamingSourceAvailableListener streamingSourceAvailableListener;

    public TrendingRecyclerViewAdapter(Context context) {
        this.context = context;
        typeViewList = new ArrayList<>();
        songs = new ArrayList<>();
    }

    public static TrendingRecyclerViewAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TrendingRecyclerViewAdapter(context);
        }
        return mInstance;
    }

    public void appendSongs(ArrayList<Song> list, String type) {
        // add section header and loops through list and call addItem on each item
        // adding section
        addItem(null, type);
        for (Song s : list) {
            //  adding each item of type
            addItem(s, "");
        }

        notifyDataSetChanged();
    }

    public void setSongs(ArrayList<Song> list, String type) {
        if (list == null) {
            this.songs.clear();
            this.typeViewList.clear();
            notifyDataSetChanged();
            return;
        }

        this.songs.clear();
        this.typeViewList.clear();
        addItem(null, type);
        for (Song s : list) {
            //  adding each item of type
            // log("setSong() adding " + s.Title);
            addItem(s, "");
        }

        notifyDataSetChanged();

    }

    public void addItem(Song song, String section) {   //     create view list
        // if section is "" then it is song
        // else it is sectionType
        if (section.equals("")) { // means it is song
            //      log("add song:");
            int index = songs.size();
            songs.add(song);
            typeViewList.add(new ViewTypeModel(TYPE_SONG, "", index));
        } else { //means it is Section Title
            //        log("section:");
            typeViewList.add(new ViewTypeModel(TYPE_SECTION_TITLE, section, -1));
        }

        //log("now typeViewList: \n\n");
//        for(ViewTypeModel t: typeViewList){
//            log(t.viewType + " \t " + t.sectionTitle + " \t " + t.index);
//        }
//        //log("===================");
//        for(Song s: songs){
//            //log("song "+s.Title+ " ");
//        }

    }

    private void log(String s) {
        Log.d(TAG, "log>>" + s);
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
//        Log.d(TAG, "setOrientation " + orientation);
    }

    private boolean isPortrait(int orientation) {
        return orientation % 2 == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        //log("VH "+" rec = "+viewType);
        if (viewType == TYPE_SECTION_TITLE) {
            int hvti = getHeaderViewToInflate();
            view = LayoutInflater.from(context).inflate(hvti, parent, false);
            log("returning section");
            return new SectionTitleViewHolder(view);
        } else {
            int vti = getViewToInflate();   // getView depending on screen screen sizes
            view = LayoutInflater.from(context).inflate(vti, parent, false);
            log("returning song item");
            return new SongViewHolder(view);
        }
    }


}

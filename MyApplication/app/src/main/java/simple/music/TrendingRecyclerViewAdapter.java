package simple.music;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import musicgenie.com.musicgenie.interfaces.TaskAddListener;
import musicgenie.com.musicgenie.models.Song;
import musicgenie.com.musicgenie.models.TrendingSongModel;
import musicgenie.com.musicgenie.models.ViewTypeModel;
import musicgenie.com.musicgenie.utilities.MusicStreamer;

/**
 * Created by Ankit on 8/21/2016.
 */
public class TrendingRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    private int getViewToInflate() {

        if (isPortrait(orientation)) {
            // check mode
            if (this.screenMode == AppConfig.SCREEN_MODE_TABLET) {
                // means it is tablet with portrait
                //            log("inflating portrait tablet");
                viewToInflate = R.layout.song_card_sw600;
            } else {
                // mobile with portrait
                //          log("inflating portrait mobile");
                viewToInflate = R.layout.song_card_normal;
            }
        } else {
            if (this.screenMode == AppConfig.SCREEN_MODE_TABLET) {
                // means it is tablet with landscape
                //        log("inflating landscape tablet");
                viewToInflate = R.layout.song_card_land_sw600;
            } else {
                // mobile with landscape
                //      log("inflating landscape mobile");
                viewToInflate = R.layout.song_card_normal_land;
            }

        }
        return viewToInflate;
    }

    private int getHeaderViewToInflate() {

        int _temp_header_viewID = -1;

        if (isPortrait(orientation)) {
            // check mode
            if (this.screenMode == Constants.SCREEN_MODE_TABLET) {
                // means it is tablet with portrait
                //    log("[H] inflating portrait tablet");
                _temp_header_viewID = R.layout.section_header_layout_sw600;
            } else {
                // mobile with portrait
                //  log("[H] inflating portrait mobile");
                _temp_header_viewID = R.layout.section_header_layout;
            }
        } else {
            if (this.screenMode == AppConfig.SCREEN_MODE_TABLET) {
                // means it is tablet with landscape
                //log("[H] inflating landscape tablet");
                _temp_header_viewID = R.layout.section_header_layout_land_sw600;
            } else {
                // mobile with landscape
                //log("[H] inflating landscape mobile");
                _temp_header_viewID = R.layout.section_header_layout_land;
            }

        }

        return _temp_header_viewID;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Typeface fontawesome = FontManager.getInstance(context).getTypeFace(FontManager.FONT_AWESOME);
        Typeface ralewayTfRegular = FontManager.getInstance(context).getTypeFace(FontManager.FONT_RALEWAY_REGULAR);
        Typeface ralewayTfBold = FontManager.getInstance(context).getTypeFace(FontManager.FONT_RALEWAY_BOLD);

        if (holder instanceof SongViewHolder) {

            // bind section data
            //  log("binding song " + position);
            Song song = songs.get(typeViewList.get(position).index);
            ((SongViewHolder) holder).title.setText(song.Title);
            ((SongViewHolder) holder).uploader.setText(song.UploadedBy);
            ((SongViewHolder) holder).views.setText(song.UserViews);
//            ((SongViewHolder) holder).popMenuBtn.setText("\uF142");
            ((SongViewHolder) holder).content_length.setText(song.TrackDuration);
            // loads thumbnail in async fashion
            if (connected()) Picasso.with(context)
                    .load(song.Thumbnail_url)
                    .into(((SongViewHolder) holder).thumbnail);

            // setting typeface to fonta
            ((SongViewHolder) holder).downloadBtn.setTypeface(fontawesome);
            ((SongViewHolder) holder).uploader_icon.setTypeface(fontawesome);
            ((SongViewHolder) holder).views_icon.setTypeface(fontawesome);
//            ((SongViewHolder) holder).popMenuBtn.setTypeface(fontawesome);
            //setting typeface to raleway
            ((SongViewHolder) holder).title.setTypeface(ralewayTfRegular);
            ((SongViewHolder) holder).content_length.setTypeface(ralewayTfRegular);
            ((SongViewHolder) holder).uploader.setTypeface(ralewayTfRegular);
            ((SongViewHolder) holder).views.setTypeface(ralewayTfRegular);

        } else {
            // binnd song data
            //log("binding header " + position);
            StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            layoutParams.setFullSpan(true);
            String section_format = typeViewList.get(position).sectionTitle.substring(0,1).toUpperCase()+typeViewList.get(position).sectionTitle.substring(1);
            ((SectionTitleViewHolder) holder).sectionTitle.setText(section_format);
            ((SectionTitleViewHolder) holder).sectionTitle.setTypeface(ralewayTfRegular);
        }

    }

    @Override
    public int getItemCount() {
        return typeViewList.size();
    }

    @Override
    public int getItemViewType(int position) {
        //log("view type at"+position+" = "+typeViewList.get(position).viewType);
        return typeViewList.get(position).viewType;
    }

    public void setOnTaskAddListener(TaskAddListener listener) {
        this.taskAddListener = listener;
    }

    public void setOnStreamingSourceAvailable(OnStreamingSourceAvailableListener listener) {
        this.streamingSourceAvailableListener = listener;
    }

    public void addDownloadTask(final String video_id, final String file_name) {

        if (this.taskAddListener != null)
            this.taskAddListener.onTaskTapped();

        TaskHandler
                .getInstance(context)
                .addTask(file_name, video_id);

        if (this.taskAddListener != null)
            this.taskAddListener.onTaskAddedToQueue(file_name);
    }

    public void setScreenMode(int mode) {
        this.screenMode = mode;
    }

    private boolean connected() {
        return ConnectivityUtils.getInstance(context).isConnectedToNet();
    }

    public interface OnStreamingSourceAvailableListener {
        void onPrepared(String uri);
        void optioned();
    }

    public static class SectionTitleViewHolder extends RecyclerView.ViewHolder {

        TextView sectionTitle;

        public SectionTitleViewHolder(View itemView) {
            super(itemView);
            sectionTitle = (TextView) itemView.findViewById(R.id.section_title);
        }
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        ProgressDialog progressDialoge;
        TextView downloadBtn;
        TextView uploader_icon;
        TextView views_icon;
        TextView popMenuBtn;
        TextView content_length;
        TextView uploader;
        TextView streamBtn;
        TextView title;
        TextView views;
        ImageView thumbnail;
        MusicStreamer.OnStreamUriFetchedListener streamUriFetchedListener = new MusicStreamer.OnStreamUriFetchedListener() {
            @Override
            public void onUriAvailable(String uri) {
                if (TrendingRecyclerViewAdapter.getInstance(context).streamingSourceAvailableListener != null) {
                   // Log.d(TAG, "onUriAvailable : uri made available");
                    TrendingRecyclerViewAdapter.getInstance(context).streamingSourceAvailableListener.onPrepared(uri);
                }

            }
        };

        public SongViewHolder(View itemView) {
            super(itemView);
            Typeface fontawesome = FontManager.getInstance(context).getTypeFace(FontManager.FONT_AWESOME);
            Typeface ralewayTfRegular = FontManager.getInstance(context).getTypeFace(FontManager.FONT_RALEWAY_REGULAR);
            Typeface ralewayTfBold = FontManager.getInstance(context).getTypeFace(FontManager.FONT_RALEWAY_BOLD);
            // material
            downloadBtn = (TextView) itemView.findViewById(R.id.download_btn_card);
            uploader_icon = (TextView) itemView.findViewById(R.id.uploader_icon);
            views_icon = (TextView) itemView.findViewById(R.id.views_icon);
           // popMenuBtn = (TextView) itemView.findViewById(R.id.popUpMenuIcon);
            thumbnail = (ImageView) itemView.findViewById(R.id.Videothumbnail);
            streamBtn = (TextView) itemView.findViewById(R.id.stream_btn_card);

            streamBtn.setTypeface(fontawesome);
            downloadBtn.setTypeface(fontawesome);
            uploader_icon.setTypeface(fontawesome);
            views_icon.setTypeface(fontawesome);
            //popMenuBtn.setTypeface(fontawesome);
            // regular raleway
            content_length = (TextView) itemView.findViewById(R.id.song_time_length);
            uploader = (TextView) itemView.findViewById(R.id.uploader_name);
            views = (TextView) itemView.findViewById(R.id.views_text);
            title = (TextView) itemView.findViewById(R.id.song_title);
            title.setTypeface(ralewayTfBold);
            content_length.setTypeface(ralewayTfRegular);
            uploader.setTypeface(ralewayTfRegular);
            views.setTypeface(ralewayTfRegular);


            // attach listener
            downloadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TrendingRecyclerViewAdapter adapter = TrendingRecyclerViewAdapter.getInstance(context);
                    int pos = getAdapterPosition() - 1;
                    //Log.d("Ada", " pos" + pos);
                    String v_id = adapter.songs.get(pos).Video_id;
                    String file_name = adapter.songs.get(pos).Title;
                   // adapter.log("adding download task");
                    TrendingRecyclerViewAdapter.getInstance(context).addDownloadTask(v_id, file_name);
                }
            });

            streamBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TrendingRecyclerViewAdapter adapter = TrendingRecyclerViewAdapter.getInstance(context);
                    adapter.streamingSourceAvailableListener.optioned();
                    int pos = getAdapterPosition() - 1;
                   // Log.d("Ada", " pos" + pos);
                    String v_id = adapter.songs.get(pos).Video_id;
                    String file_name = adapter.songs.get(pos).Title;
                   // adapter.log("fetch for streaming");
                    // set Uri Fetched Listener to MusicStreamer


                    MusicStreamer
                            .getInstance(context)
                            .setData(v_id,file_name)
                            .setOnStreamUriFetchedListener(streamUriFetchedListener)
                            .initProcess();

                }
            });



        }


    }
}

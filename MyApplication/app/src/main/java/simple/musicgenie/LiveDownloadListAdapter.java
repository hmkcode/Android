package simple.musicgenie;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ankit on 9/25/2016.
 */
public class LiveDownloadListAdapter extends ArrayAdapter<String> {

    private static Context context;
    private static LiveDownloadListAdapter mInstance;
    private ArrayList<DownloadTaskModel> downloadingList;
    private DownloadCancelListener downloadCancelListener;
    private Typeface tfIcon;
    private Typeface raleway;

    public LiveDownloadListAdapter(Context context) {
        super(context, 0);
        this.context = context;
        downloadingList = new ArrayList<>();
        tfIcon = FontManager.getInstance(context).getTypeFace(FontManager.FONT_AWESOME);
        raleway = FontManager.getInstance(context).getTypeFace(FontManager.FONT_RALEWAY_REGULAR);

    }

    public static LiveDownloadListAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LiveDownloadListAdapter(context);
        }
        return mInstance;
    }

    public void setOnDownloadCancelListener(DownloadCancelListener listener) {
        this.downloadCancelListener = listener;
    }

    public void setDownloadingList(ArrayList<DownloadTaskModel> list) {
        this.downloadingList = list;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ProgressBar progressBar;
        TextView progressText;
        final TextView cancelBtn;
        TextView taskTitle;
        TextView contentSizeMB;
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(context).inflate(R.layout.downloading_item, parent, false);
        }

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressText = (TextView) v.findViewById(R.id.progressText);
        progressBar.setIndeterminate(false);
        cancelBtn = (TextView) v.findViewById(R.id.cancel_btn_text);
        taskTitle = (TextView) v.findViewById(R.id.download_task_title);
        contentSizeMB = (TextView) v.findViewById(R.id.content_size_MB);
        contentSizeMB.setText(downloadingList.get(position).contentSize);
        progressBar.setProgress(downloadingList.get(position).Progress);
        progressText.setText(downloadingList.get(position).Progress + " %");
        taskTitle.setText(downloadingList.get(position).Title);
        // font assign
        progressText.setTypeface(raleway);
        taskTitle.setTypeface(raleway);
        cancelBtn.setTypeface(tfIcon);
        contentSizeMB.setTypeface(raleway);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log("cancel btn click");
                cancelDownload(downloadingList.get(position).taskID);
            }
        });

        return v;
    }

    @Override
    public int getCount() {
        return downloadingList.size();
    }


    private void cancelDownload(String taskID) {
        log("cancelled task " + taskID + " li" + downloadCancelListener);
        if (this.downloadCancelListener != null) {
            downloadCancelListener.onDownloadCancel(taskID);
        }
        // remove items from list

    }

    private void log(String msg) {
        Log.d("LiveDownloadAdapter", msg);
    }

}

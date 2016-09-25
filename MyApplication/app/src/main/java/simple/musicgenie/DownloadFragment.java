package simple.musicgenie;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ankit on 9/25/2016.
 */


public class DownloadFragment extends Fragment {

    private static ListView downloadedItemListView;
    private static final String TAG = "DownloadFragment";
    private static Context context;
    public DownloadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_download, container, false);
        downloadedItemListView = (ListView) view.findViewById(R.id.dowloadedItemsList);
        setUpAdapter();
        return view;

    }

    private static void setUpAdapter(){
        ArrayList<String> files = new ArrayList<>();
        File dir = new File(AppConfig.FILES_DIR);
        File[] _files = dir.listFiles();
        for (File f:_files) {
            //Log.d(TAG, "setUpAdapter f-size"+f.getTotalSpace());
            files.add(0,f.toString());
        }
        DownloadedSongsAdapter adapter = new DownloadedSongsAdapter(context);
        adapter.setItemList(files);
        downloadedItemListView.setAdapter(adapter);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity.getApplicationContext();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}

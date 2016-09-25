package simple.musicgenie;

import android.os.Environment;

public class Constants {

    public static final String SERVER_URL = "http://ymp3.aavi.me";
    public static final String SDCARD = "sdcard";
    public static final String PHONE = "phone";
    public static final int SCREEN_MODE_TABLET = 0 ;
    public static final int SCREEN_MODE_MOBILE = 1;
    public static final String ACTION_PROGRESS_UPDATE_BROADCAST = "action_progress_update";
    public static final String EXTRA_TASK_ID = "task_id";
    public static final String EXTRA_PROGRESS = "progress";
    public static final String FILES_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Musicgenie/Audio";
    public static final String ACTION_NETWORK_CONNECTED = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final int SCREEN_ORIENTATION_PORTRAIT = 0;
    public static final int SCREEN_ORIENTATION_LANDSCAPE = 1;
    public static final String EXTRA_CONTENT_SIZE = "contentSize";
    public static final String ACTION_STREAM_URL_FETCHED = "action_uri_fetched";
    public static final String EXTRAA_URI = "uri";
    private static final String TAG = "AppConfig";
    public static final String EXTRAA_STREAM_FILE = "stream_file_name";
    public static final String EXTRAA_ACTIVITY_PRE_LOAD_FLAG = "actvity_preloaded";
    public static final String FLAG_STREAMING_CONTINUED = "streamingWillContinue";
    public static final int ACTION_TYPE_FIRST_LOAD = 0;
    public static final int ACTION_TYPE_RESUME = 1;
    public static final int ACTION_TYPE_REFRESS = 2;
    public static final int ACTION_TYPE_SEARCH = 3;
    public static final String KEY_SEARCH_TERM = "searchTerm";
    public static final String FLAG_RESET_ADAPTER_DATA = "reset_data_0x879SADF8dsfkdfjd";
}

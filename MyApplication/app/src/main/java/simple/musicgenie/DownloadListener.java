package simple.musicgenie;

/**
 * Created by Ankit on 8/9/2016.
 */
public interface DownloadListener {
     void onInterruptted(String taskID);
     void onError(String error);
     void onDownloadTaskProcessStart();
     void onDownloadFinish();
}

package simple.music;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ankit on 9/13/2016.
 */
public class TaskHandler {
    private static final int TYPE_TASK_DOWNLOAD = 0;
    private static final int TYPE_TASK_DISPATCH = 1;
    private static final String TAG = "TaskHandler";
    private static Context context;
    private static TaskHandler mInstance;
    private static Handler mHandler;
    private boolean isHandlerRunning = false;
    private int task_count = 0;
    private ProgressDialog progressDialog;
    private String dwnd_url;

    public TaskHandler(Context context) {
        this.context = context;
    }

    public static TaskHandler getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TaskHandler(context);
        }
        return mInstance;
    }

    /*
    * WID: loops over pending tasks and dipatches in one by one fashion
    * */
    public void initiate(){

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                log("callback: handleMessage");
                initiate();
            }
        };

        if(!isHandlerRunning){

            if (getDispatchTaskCount() > 0 && isConnected()) {
                log("tasks to dispatch = "+getDispatchTaskCount());
                isHandlerRunning = true;
                final ArrayList<String> taskIDs = getDispatchTaskSequence();

                for (final String taskID : taskIDs) {
                    log("try dispatch "+taskID);
                    // problem : getCurrentDownloadCount() is called too late
                    // main-threads cursor runs the loop before getCurrentdownloadCount()
                    // solution: call setCurrentDownloadCount(1) before any more loop get played
                    // call should mean it
                    // it should be called to achieve purpose not for value purpose
                    //
                    if(SharedPrefrenceUtils.getInstance(context).getCurrentDownloadsCount()<1) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                log("succ: dispatched " + taskID);
                                dispatch(taskID);
                                log("posting handler ");
                                // last round check up
                                Message message = mHandler.obtainMessage();
                                message.sendToTarget();

                            }
                        }).start();

                        removeDispatchTask(taskID);
                    }
                }
            }
        }else{
            log("Handler Already running , Task is enqued ");
        }
    }

    public void pauseHandler(){
        //TODO: check how to cancel activities on thread
    }
    /*
    * WID: get task details from s.pref. and start AsyncTask for download
    * */

    private void dispatch(final String taskID) {

        String v_id = SharedPrefrenceUtils.getInstance(context).getTaskVideoID(taskID);
        String file_name = SharedPrefrenceUtils.getInstance(context).getTaskTitle(taskID);

        DownloadListener listener = new DownloadListener() {

            @Override
            public void onInterruptted(String taskID) {
                // retrieve the file and delete it
                deleteFile(taskID);
                String flnm = SharedPrefrenceUtils.getInstance(context).getTaskTitle(taskID);
                LocalNotificationManager.getInstance(context).launchNotification("Failed To Download - "+flnm);
            }
            @Override
            public void onError(String taskID) {
                deleteFile(taskID);
                String flnm = SharedPrefrenceUtils.getInstance(context).getTaskTitle(taskID);
                LocalNotificationManager.getInstance(context).launchNotification("Failed To Download - "+flnm);
            }

            @Override
            public void onDownloadTaskProcessStart() {
                SharedPrefrenceUtils.getInstance(context).setCurrentDownloadCount(1);
                log("callback: download started");

            }

            @Override
            public void onDownloadFinish() {
                SharedPrefrenceUtils.getInstance(context).setCurrentDownloadCount(0);
            }
        };

        DownloadThread thread = new DownloadThread(taskID,v_id,file_name,listener);
        thread.start();

        try {
            log(Thread.currentThread().getId()+" waiting thread to join");
            isHandlerRunning = true;
            thread.join();
            isHandlerRunning = false;
            //  last-round check up for any residue task taken-in in beetween
            //initiate();
            log("thread joined !");

        } catch (InterruptedException e) {
            log("thread join Interrupted");
        }
    }

    private void deleteFile(String taskID) {

        SharedPrefrenceUtils.getInstance(context).setCurrentDownloadCount(0);
        log("callback: download error");

        String fn = SharedPrefrenceUtils.getInstance(context).getTaskTitle(taskID);
        File dest_file = new File(AppConfig.FILES_DIR+"/"+fn+".mp3");
        if(dest_file.exists()){
            if(dest_file.delete()){
                log("Successfully Deleted File" +dest_file.getName());
            }else{
                log("Failed To Delete File "+dest_file.getName());
            }
        }
    }

    /*
    *                  Task Helpers
    * */

    public ArrayList<String> getTaskSequence() {
        ArrayList<String> task;
        String _tasks = SharedPrefrenceUtils.getInstance(context).getTaskSequence();
        task = new Segmentor().getParts(_tasks, '#');
        return task;
    }

    private ArrayList<String> getDispatchTaskSequence() {

        ArrayList<String> task;
        String _tasks = SharedPrefrenceUtils.getInstance(context).getDispatchTaskSequence();
        //log(" dispatch pendings :: "+_tasks);
        task = new Segmentor().getParts(_tasks, '#');
        return task;

    }

    public int getTaskCount(){
        return getTaskSequence().size();
    }

    public int getDispatchTaskCount(){
        return getDispatchTaskSequence().size();
    }

    // adds task to shared preferences task queue
    public void addTask(String file_name, String v_id){

        SharedPrefrenceUtils utils = SharedPrefrenceUtils.getInstance(context);
        // create taskID
        Date d = new Date();
        String timeStamp = DateFormat.format("yyyyMMddhhmmss", d.getTime()).toString();
        // log("adding task :[audTsk" + timeStamp + "]");
        String taskID = "audTsk"+timeStamp;
        String tasks = utils.getTaskSequence();
        utils.setTasksSequence(tasks + taskID + "#");
        log("add: Task["+taskID+"]");
        tasks = utils.getDispatchTaskSequence();
        utils.setDispatchTasksSequence(tasks + taskID + "#");
        log("add: DispatchTask["+taskID+"]");
        //log("after adding " + utils.getTaskSequence());
        // save taskTitle:file_name
        utils.setTaskTitle(taskID, file_name);
        // save videoID  : v_id
        utils.setTaskVideoID(taskID, v_id);
        // notifies handler for new task arrival
        initiate();
    }

    // removes taskID from sharedPreferences string queue
    public void removeTask(String taskID){

        ArrayList<String> tids =new Segmentor().getParts(SharedPrefrenceUtils.getInstance(context).getTaskSequence(), '#');
        for (int i =0;i<tids.size();i++) {
            String tid = tids.get(i);
            if(tid.equals(taskID)){
                log("removing download task "+ taskID );
                tids.remove(i);
            }
        }
        // write back to spref
        writeToSharedPreferences(tids, TYPE_TASK_DOWNLOAD);

    }

    // remove all tasks
    public void removeAllTasks(){
        log("removing all tasks");
        SharedPrefrenceUtils.getInstance(context).setTasksSequence("");
    }

    //remove dispatch task
    public void removeDispatchTask(String taskID){

        ArrayList<String> tids =new Segmentor().getParts(SharedPrefrenceUtils.getInstance(context).getDispatchTaskSequence(), '#');
        for (int i =0;i<tids.size();i++) {
            String tid = tids.get(i);
            if(tid.equals(taskID)){
                log("removing dispatch task "+ taskID );
                tids.remove(i);
            }
        }
        // write back to spref
        writeToSharedPreferences(tids, TYPE_TASK_DISPATCH);

    }

    // write string task sequence to SF

    public void writeToSharedPreferences(ArrayList<String> taskIDs,int type){
        String currStack="";
        for (String id : taskIDs) {
            currStack +=id+"#";
        }
        currStack = currStack.substring(0,currStack.length());


        if (type == TYPE_TASK_DOWNLOAD) {
            log("writing back tasks :" + currStack);
            SharedPrefrenceUtils.getInstance(context).setTasksSequence(currStack);
        } else {
            log("writing back the dispatch tasks :" + currStack);
            SharedPrefrenceUtils.getInstance(context).setDispatchTasksSequence(currStack);
        }

    }
    // getConnectivity of Device
    private boolean isConnected(){
        return ConnectivityUtils.getInstance(context).isConnectedToNet();
    }

    // logs
    public void log(String msg) {
        Log.d(TAG, msg);
    }

    /*
    *   Download Thread
    * */

    //WID: takes taskID , file_name , url and  download it , removes task after 100% , publishes progress

    private class DownloadThread extends Thread{

        private String taskID;
        private String v_id;
        private String file_name;
        private DownloadListener downloadListener;
        private boolean isCanceled = false;
        private int currentProgress;
        private int fileLength;
        //private Context context;

        public DownloadThread(String taskID , String v_id , String file_name,DownloadListener listener) {
            this.taskID = taskID;
            this.v_id = v_id;
            this.file_name = file_name;
            this.downloadListener = listener;
            this.currentProgress = 0;
            //  this.context = context;
        }

        @Override
        public void run() {
            int count;
            final String t_v_id = this.v_id;
            final String t_file_name = this.file_name;
            String t_url = AppConfig.SERVER_URL;
            File dest_file = null;
            File dest_dir = null;
            subscribeDownloadCancelListener();
            if(!isCanceled){
                try {

                    downloadListener.onDownloadTaskProcessStart();

                    String _url = AppConfig.SERVER_URL + "/api/v1/g?url=" + t_v_id;
                    log("for dwnd url requesting on "+_url);
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

                            t_url += obj.getString("url");
                            log("download url:" + t_url);

                        }else{
                            downloadListener.onError(taskID);
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    URL url = new URL(t_url);
                    URLConnection connection = url.openConnection();
                    connection.setReadTimeout(20000);
                    connection.setConnectTimeout(20000);
                    connection.connect();
                    fileLength = connection.getContentLength();
                    log("content len "+fileLength);

                    if(fileLength==-1 || fileLength==24){
                        downloadListener.onError(taskID);
                        return;
                    }

                    // file creation
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        PermissionManager.getInstance(context).seek();
                    }

                    dest_dir = new File(AppConfig.FILES_DIR);
                    dest_file = new File(dest_dir, t_file_name.trim() + ".m4a");
                    log("writing to " + dest_file.toString());

                    InputStream inputStream = new BufferedInputStream(url.openStream());
                    OutputStream outputStream = new FileOutputStream(dest_file);
                    byte data[] = new byte[1024];
                    long total = 0;
                    while (!isCanceled && (count = inputStream.read(data)) != -1) {
                        total += count;
                        Log.d("Sending filelength",fileLength+"");
                        publishProgress((int) total * 100 / fileLength,fileLength+"");
                        outputStream.write(data, 0, count);
                    }
                    //check inturruption
                    if(total<fileLength){
                        if(downloadListener!=null){
                            downloadListener.onInterruptted(taskID);
                            log("Download Interrupted :" +taskID);
                        }
                    }

                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();

                } catch (MalformedURLException e) {
                    downloadListener.onError(taskID);
                    log("URL exception " + e);
                } catch (IOException e) {
                    downloadListener.onError(taskID);
                    log("IO exception " + e);
                }

            }
        }

        private void publishProgress(int progress,String cl) {

            if (progress == 100) {
                removeTask(taskID);
                SharedPrefrenceUtils utils = SharedPrefrenceUtils.getInstance(context);
                downloadListener.onDownloadFinish();
                log("downloaded task " + taskID);
            }
            broadcastUpdate(String.valueOf(progress),cl);
            if(currentProgress<progress) {
                LocalNotificationManager.getInstance(context).publishProgressOnNotification(progress, file_name);
            }
            this.currentProgress = progress;
        }

        public void broadcastUpdate(String progressPercentage ,String contentLen){
            Intent intent = new Intent(AppConfig.ACTION_PROGRESS_UPDATE_BROADCAST);
            intent.putExtra(AppConfig.EXTRA_TASK_ID,taskID);
            intent.putExtra(AppConfig.EXTRA_PROGRESS, progressPercentage);
            intent.putExtra(AppConfig.EXTRA_CONTENT_SIZE, contentLen);
            context.sendBroadcast(intent);
        }

        private void subscribeDownloadCancelListener(){
            LiveDownloadListAdapter.getInstance(context).setOnDownloadCancelListener(new DownloadCancelListener() {
                @Override
                public void onDownloadCancel(String tID) {
                    if (taskID.equals(tID)) { // means current downloading task is canceled
                        log("cancelling live download task");
                        isCanceled = true;
                    }
                    removeTask(taskID);     // work in both circumstances (cancel live-download or pending download)
                    log("task " + taskID + " got canceled !!");
                }
            });
        }

    }

}

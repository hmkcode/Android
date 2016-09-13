package simple.music;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ankit on 9/13/2016.
 */
public class SharedPrefrenceUtils {
    private static SharedPrefrenceUtils mInstance;
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static int MODE = 0;
    private static final String PREF_NAME = "musicgenie_tasks";


    public SharedPrefrenceUtils(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,MODE);
        editor = preferences.edit();
    }

    public static SharedPrefrenceUtils getInstance(Context context){
        if(mInstance ==null){
            mInstance = new SharedPrefrenceUtils(context);
        }
        return mInstance;
    }

    public void setFileSavingLocation(String location) {
        editor.putString("saveLocation",location);
        editor.commit();
    }

    public String getFileSavingLocation(){
        return preferences.getString("saveLocation", Constants.PHONE);
    }

    public void setTasksSequence(String sequence){
        editor.putString("task_seq",sequence);
        editor.commit();
    }

    public String getTaskSequence(){
        //Log.d("SF",preferences.getString("task_seq",""));
        return preferences.getString("task_seq","");
    }


    public void setDispatchTasksSequence(String sequence){
        editor.putString("dis_task_seq",sequence);
        editor.commit();
    }

    public String getDispatchTaskSequence(){
        //Log.d("SF-d", preferences.getString("dis_task_seq", ""));
        return preferences.getString("dis_task_seq","");
    }



    public void setTaskVideoID(String taskID ,String v_id){
        // taskID : key and download_url : value
        editor.putString(taskID+"_u",v_id);
        editor.commit();
    }

    public String getTaskVideoID(String taskID){
        return preferences.getString(taskID+"_u","");
    }

    public void setTaskTitle(String taskId, String file_name){
        editor.putString(taskId+"_t",file_name);
        editor.commit();
    }

    public String getTaskTitle(String taskID){
        return preferences.getString(taskID+"_t","");
    }

    public int getCurrentDownloadsCount(){
        return preferences.getInt("cur_dnd", 0);
    }

    public void setCurrentDownloadCount(int count){
        editor.putInt("cur_dnd",count);
        editor.commit();
    }

    public void setActiveFragmentAttachedState(boolean yesOrNo){
        editor.putBoolean("isActive", yesOrNo);
        editor.commit();
    }

    public boolean getActiveFragmentAttachedState(){
        return preferences.getBoolean("isActive",false);
    }

    public void setOptionsForTrendingAudio(boolean needTrending) {
        editor.putBoolean("needTrending",needTrending);
        editor.commit();
    }

    public boolean getOptionForTrendingAudio(){
        return preferences.getBoolean("needTrending",false);
    }

    public void setSuggestionsList(String currStack) {
        editor.putString("sugg",currStack);
        editor.commit();
    }

    public String getSuggestionList(){
        return preferences.getString("sugg","");
    }

    public void setOptionsForThumbnailLoad(boolean needLoading) {
        editor.putBoolean("needThumb",needLoading);
        editor.commit();
    }
    public boolean getOptionsForThumbnailLoad(){
        return preferences.getBoolean("needThumb",true);
    }

    public void setCurrentStreamingItem(String file_name) {
        editor.putString("streaming",file_name);
        editor.commit();
    }

    public String getCurrentStreamingItem(){
        return preferences.getString("streaming","");
    }

    public void setFlagForContinuedStreaming(boolean b) {
        editor.putBoolean(Constants.FLAG_STREAMING_CONTINUED,b);
        editor.commit();
    }

    public boolean getFlagForContinuedStreaming(){
        return preferences.getBoolean(Constants.FLAG_STREAMING_CONTINUED,false);
    }
}

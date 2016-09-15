package simple.musicgenie;


import android.content.Context;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;

public class CentralDataRepository {

    /**
     *  Flag is refrenced when activity first load
     */
    private static final int FLAG_FIRST_LOAD = 0;
    /**
     * Flag is refrenced when back navigation
     */
    private static final int FLAG_RESTORE = 1;
    /**
     * Flag is refrenced during search
     */
    private static final int FLAG_SEARCH = 2;
    /**
     * Flag is refrenced refress is triggred
     */
    private static final int FLAG_REFRESS = 3;

    /**
     *  Result type
     */
    private static final int TYPE_TRENDING = 405;

    /**
     * Result type
     */
    private static final int TYPE_RESULT = 406;

    private ActionCompletedListener mListener;
    private DataReadyToSubmitListener dataReadyToSubmitListener;
    private DbHelper dbHelper;
    private static Context context;

    private static CentralDataRepository mInstance;

    private int mLastLoadedType = TYPE_TRENDING;

    /**
     * Default constructor
     */
    private CentralDataRepository() {
    }

    /**
     * @param context Subscriber`s context
     */
    private CentralDataRepository(Context context) {
        this.context = context;
        this.dbHelper = DbHelper.getInstance(context);
    }

    /**
     * @param context Singleton Pattern method to access same instance
     * @return
     */
    public static CentralDataRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CentralDataRepository(context);
        }
        return mInstance;
    }

    /**
     * @param type Flag
     * @param callback  Callback for action completed
     */

    public void submitAction(int type , ActionCompletedListener callback) throws InvalidCallbackException{

        if(callback!=null)
            setListener(callback);
        else throw new InvalidCallbackException("Callback in Invalid");

        switch (type){

            case FLAG_FIRST_LOAD:
                                    loadTrendingOrRequestTrending();
                break;
            case FLAG_RESTORE:
                                    submitLastLoaded();
                break;
            case FLAG_SEARCH:
                                    searchAndSubmit();
                break;
            case FLAG_REFRESS:
                                    refressAndSubmit();
                break;
            default:
                break;              // do nothing
        }

    }

    /**
     * gets Last Loaded and Request
     *  save to db + callback + submit
     *  not need to set Last loaded
     */
    private void refressAndSubmit() {

    }

    /**
     *  Searches for result and submit + save to db
     *      + callback
     *      After submittion must setLastLoaded
     */
    private void searchAndSubmit() {

    }

    /**
     * Checks Last Loaded
     *      gets from DB and submits to registered adapters
     *      After submittion must setLastLoaded
     */
    private void submitLastLoaded() {

    }

    /**
     *  Checks DB for saved data
     *      if !Available
     *          Request - > save to db + action callback +  adapter callback
     *      else
     *          action callback + adapter callback
     *          After submittion must setLastLoaded
     */
    private void loadTrendingOrRequestTrending() {

        //  check for available cache
        boolean isAnyCache = dbHelper.isTrendingsCached();

        if(!isAnyCache){    // request for trending and then update

        }


    }

    public void registerForDataLoadListener(DataReadyToSubmitListener listener){
        this.dataReadyToSubmitListener = listener ;
    }

    public void setListener(ActionCompletedListener mListener) {
        this.mListener = mListener;
    }

    public int getLastLoadedType() {
        return this.mLastLoadedType;
    }

    public void setLastLoadedType(int mLastLoadedType) {
        this.mLastLoadedType = mLastLoadedType;
    }

    public interface ActionCompletedListener{
        void onActionCompleted();
    }

    public interface DataReadyToSubmitListener{
        //TODO: add params to onDataSubmit(ArrayList<SongModel> dataList)
        void onDataSubmit();
    }


    private class InvalidCallbackException extends Exception {
        public InvalidCallbackException(String detailMessage) {
            super(detailMessage);
            System.out.println(detailMessage);
            Log.e("CentralDataRepository",detailMessage);
        }

    }
}

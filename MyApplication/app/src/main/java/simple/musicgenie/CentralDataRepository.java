package simple.musicgenie;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class CentralDataRepository {

    /**
     * Flag is refrenced when activity first load
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
     * Result type
     */
    private static final int TYPE_TRENDING = 405;

    /**
     * Result type
     */
    private static final int TYPE_RESULT = 406;

    private ActionCompletedListener mActionCompletdListener;
    private DataReadyToSubmitListener dataReadyToSubmitListener;
    private DbHelper mDBHelper;
    private CloudManager mCloudManager;
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
        this.mDBHelper = DbHelper.getInstance(context);
        this.mCloudManager = CloudManager.getInstance(context);

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
     * @param type     Flag for operation type
     * @param callback Callback for action completed
     */

    public void submitAction(int type, ActionCompletedListener callback) throws InvalidCallbackException {

        if (callback != null)
            setListener(callback);
        else throw new InvalidCallbackException("Callback in Invalid");

        switch (type) {

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
     * save to db + callback + submit
     * not need to set Last loaded
     */
    private void refressAndSubmit() {

        if (mLastLoadedType == TYPE_TRENDING) {

            mDBHelper.setTrendingLoadListener(new DbHelper.TrendingLoadListener() {
                @Override
                public void onTrendingLoad(ArrayList<SectionModel> trendingList) {
                    // now result are written to database
                    // so submit them to thirsty adapters
                    dataReadyToSubmitListener.onDataSubmit(trendingList);
                    // callback confirmation to operation initiater
                    mActionCompletdListener.onActionCompleted();

                    mLastLoadedType = TYPE_TRENDING;
                }
            });

            mCloudManager.lazyRequestTrending();

        } else {
            // re-use: same symptoms
            searchAndSubmit();
        }

    }

    /**
     * Searches for result and submit + save to db
     * + callback
     * After submittion must setLastLoaded
     */
    private void searchAndSubmit() {

        mDBHelper.setResultLoadListener(new DbHelper.ResultLoadListener() {
            @Override
            public void onResultLoadListener(SectionModel result) {

                ArrayList<SectionModel> temp = new ArrayList<>();
                temp.add(new SectionModel(result.sectionTitle, result.getList()));

                //callback to thirsty adapters
                dataReadyToSubmitListener.onDataSubmit(temp);

                // callback confirmation to operation initiater
                mActionCompletdListener.onActionCompleted();

            }
        });

        SharedPrefrenceUtils utils = SharedPrefrenceUtils.getInstance(context);
        String searchTerm = utils.getLastSearchTerm();
        mCloudManager.requestSearch(searchTerm);

        mLastLoadedType = TYPE_RESULT;

    }

    /**
     * (Restore State)
     * Checks Last Loaded
     * gets from DB and submits to registered adapters
     * After submittion must setLastLoaded
     */
    private void submitLastLoaded() {

        if (mLastLoadedType == TYPE_TRENDING) {

            mDBHelper.setTrendingLoadListener(new DbHelper.TrendingLoadListener() {
                @Override
                public void onTrendingLoad(ArrayList<SectionModel> trendingList) {
                    // now result are written to database
                    // so submit them to thirsty adapters
                    dataReadyToSubmitListener.onDataSubmit(trendingList);
                    // callback confirmation to operation initiater
                    mActionCompletdListener.onActionCompleted();
                }
            });

            mDBHelper.pokeForTrending();

            mLastLoadedType = TYPE_TRENDING;

        } else {

            mDBHelper.setResultLoadListener(new DbHelper.ResultLoadListener() {
                @Override
                public void onResultLoadListener(SectionModel result) {

                    ArrayList<SectionModel> temp = new ArrayList<>();
                    temp.add(new SectionModel(result.sectionTitle, result.getList()));
                    dataReadyToSubmitListener.onDataSubmit(temp);

                    // callback confirmation to operation initiater
                    mActionCompletdListener.onActionCompleted();

                }
            });

            mDBHelper.pokeForResults();

            mLastLoadedType = TYPE_RESULT;
        }


    }

    /**
     * Checks DB for saved data
     * if !Available
     * Request - > save to db + action callback +  adapter callback
     * else
     * action callback + adapter callback
     * After submittion must setLastLoaded
     */
    private void loadTrendingOrRequestTrending() {

        //  check for available cache
        boolean isAnyCache = mDBHelper.isTrendingsCached();

        // subscribe for callback from database
        mDBHelper.setTrendingLoadListener(new DbHelper.TrendingLoadListener() {
            @Override
            public void onTrendingLoad(ArrayList<SectionModel> trendingList) {
                // now result are written to database
                // so submit them to thirsty adapters
                dataReadyToSubmitListener.onDataSubmit(trendingList);
                // callback confirmation to operation initiater
                mActionCompletdListener.onActionCompleted();
            }
        });


        if (!isAnyCache) {    // request for trending and then update
            // request
            mCloudManager.lazyRequestTrending();
        } else {
            // poke for available data
            mDBHelper.pokeForTrending();
        }

        mLastLoadedType = TYPE_TRENDING;

    }

    public void registerForDataLoadListener(DataReadyToSubmitListener listener) {
        this.dataReadyToSubmitListener = listener;
    }

    public void setListener(ActionCompletedListener mListener) {
        this.mActionCompletdListener = mListener;
    }

    public int getLastLoadedType() {
        return this.mLastLoadedType;
    }

    public void setLastLoadedType(int mLastLoadedType) {
        this.mLastLoadedType = mLastLoadedType;
    }

    public interface ActionCompletedListener {      // these listeners will be summitted by Operation Initiater
        void onActionCompleted();
    }

    public interface DataReadyToSubmitListener {    // these listeners will be summitted by Adapter who are waiting for our data

        // for Result  there will be single item
        // for Trending there will be list of items
        void onDataSubmit(ArrayList<SectionModel> items);
    }


    private class InvalidCallbackException extends Exception {
        public InvalidCallbackException(String detailMessage) {
            super(detailMessage);
            System.out.println(detailMessage);
            Log.e("CentralDataRepository", detailMessage);
        }

    }
}

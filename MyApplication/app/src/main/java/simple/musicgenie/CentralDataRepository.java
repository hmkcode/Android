package simple.musicgenie;


import android.content.Context;
import android.support.v7.app.ActionBarDrawerToggle;

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

    private static Context context;

    private static CentralDataRepository mInstance;


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
    public void submitAction(int type , ActionCompletedListener callback){



    }




    public interface ActionCompletedListener{
        void onActionCompleted();
    }
}

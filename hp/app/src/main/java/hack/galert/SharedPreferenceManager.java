package hack.galert;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ankit on 9/10/2016.
 */
public class SharedPreferenceManager {

    private static Context context;
    private static SharedPreferenceManager mInstance;
    private static final String PREF_NAME = "kMorePref";
    private static final String TAG = SharedPreferenceManager.class.getSimpleName();
    static SharedPreferences.Editor editor;
    static SharedPreferences preferences;
    int Mode = 0;
    Context _context;

    public SharedPreferenceManager(Context context) {
        this._context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Mode);
        editor = preferences.edit();
    }

    public static SharedPreferenceManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceManager(context);
        }
        return mInstance;
    }

    public void setLoginStatus(boolean status) {
        editor.putBoolean(Constants.LOGIN_PREF, status);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(Constants.LOGIN_PREF, false);
    }

    public boolean getChoiceOnImageLoad() {
        return preferences.getBoolean(Constants.IMAGE_LOAD_CHOICE, false);
    }

    public void setChoiceOnImageLoad(boolean shouldLoad) {
        editor.putBoolean(Constants.IMAGE_LOAD_CHOICE, shouldLoad);
        editor.commit();
    }

    public void setUserToken(String token) {
        editor.putString(Constants.TOKEN, token);
        editor.commit();
    }

    public String getUserToken() {
        return preferences.getString(Constants.TOKEN, "");
    }
}

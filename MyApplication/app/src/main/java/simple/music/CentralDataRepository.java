package simple.music;


import android.content.Context;

public class CentralDataRepository {

    private static Context context;
    private static CentralDataRepository mInstance;

    public CentralDataRepository(Context context) {
        this.context = context;
    }

    public static CentralDataRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new CentralDataRepository(context);
        }
        return mInstance;
    }


    interface RepositoryDataChangeListener{
        void onDataChange(String type);
    }
}

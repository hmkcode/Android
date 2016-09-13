package simple.music;


import android.content.Context;

public class CentralDataRepository {

    private CentralDataChangeListener mListener;
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

    public boolean getConnectivityState(){
        boolean connected  = false;

        connected = ConnectivityUtils.getInstance(context).isConnectedToNet();

        return connected;
    }

    public void submitData(){
        // write to data base and notify adapter
    }

    public void setListener(CentralDataChangeListener mListener) {
        this.mListener = mListener;
    }

    interface CentralDataChangeListener{
        void onDataChange();
    }

}

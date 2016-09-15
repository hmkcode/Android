package simple.musicgenie;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import simple.musicgenie.R;

public class TestAct extends AppCompatActivity {

    private static Context context;
    private static TestAct mInstance;

    public TestAct(Context context) {
        this.context = context;
    }

    public static TestAct getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TestAct(context);
        }
        return mInstance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        CloudManager manager = CloudManager.getInstance(this);

        manager.requestSupportedPlaylist();


    }

}

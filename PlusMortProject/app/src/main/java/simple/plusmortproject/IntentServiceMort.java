package simple.plusmortproject;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


public class IntentServiceMort extends IntentService {


    public static void startActionFoo(Context context, String param1, String param2) {

    }

    public static void startActionBaz(Context context, String param1, String param2) {

    }

    public IntentServiceMort() {
        super("IntentServiceMort");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        }



    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

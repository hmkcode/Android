package simple.musicgenie;

import android.content.Context;

public class Parser {

    private static Context context;
    private static Parser mInstance;

    public Parser(Context context) {
        this.context = context;
    }

    public static Parser getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Parser(context);
        }
        return mInstance;
    }

    public void parseTrending(String json){



    }



}

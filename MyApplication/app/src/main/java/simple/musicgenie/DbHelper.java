package simple.musicgenie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Create Local Cache Trending (temp)
 *
 */
public class DbHelper extends SQLiteOpenHelper {

    private static Context context;
    private static DbHelper mInstance;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "musicegenieDB";
    // create table trending ()

    private static final String CREATE_TRENDING_TABLE = "";
    private static final String CREATE_RESULTS_TABLE = "";

    private DbHelper(Context context) {
        super(context , DB_NAME , null , DB_VERSION);
        this.context = context;
    }

    public static DbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DbHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    /**
     * @param sqLiteDatabase database object
     * @param oldVersion    older version of database
     * @param newVersion    newer version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}

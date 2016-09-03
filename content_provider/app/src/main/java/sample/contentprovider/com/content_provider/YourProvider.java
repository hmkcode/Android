package sample.contentprovider.com.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Ankit on 9/3/2016.
 */
public class YourProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher =  new UriMatcher(UriMatcher.NO_MATCH);
    private static final String DBNAME = "songsDB";
    private SQLiteDatabase db;
    private LocalDatabaseHelper mOpenHelper;

    static {
        sUriMatcher.addURI("com.example.app.provider.me","songs",1);
        sUriMatcher.addURI("com.example.app.provider.me","songs/#",2);
    }

    private static Context context;
    private static YourProvider mInstance;

    public YourProvider(Context context) {
        this.context = context;
    }

    public static YourProvider getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new YourProvider(context);
        }
        return mInstance;
    }

    @Override
    public boolean onCreate() {

        mOpenHelper = new LocalDatabaseHelper(
                context,
                DBNAME,
                null,
                1);


        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        return null;
    }

    @Override
    public String getType(Uri uri) {
        // if uri matches for single row : vnd.android.cursor.item/
        // if uri matches for more than one row : vnd.android.cursor.dir/
        // Provider specific part : vnd.<name>.<type>
        // for single row of table vnd.android.cursor.item/vnd.com.example.ourapp.provider.table
        return  "";
    }

    //Please do some real coding, this is not upto the level of your standard.
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        db = mOpenHelper.getWritableDatabase();
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}

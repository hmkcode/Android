package sample.contentprovider.com.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Ankit on 9/3/2016.
 */
public class YourProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher =  new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI("com.example.app.provider.me","songs",1);
        sUriMatcher.addURI("com.example.app.provider.me","songs/#",2);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
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

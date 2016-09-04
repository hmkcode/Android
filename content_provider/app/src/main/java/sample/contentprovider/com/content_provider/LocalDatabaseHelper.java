package sample.contentprovider.com.content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ankit on 9/3/2016.
 */
public class LocalDatabaseHelper extends SQLiteOpenHelper {

    public LocalDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String SQL_CREATE_MAIN = "CREATE TABLE "+
            "main "+    // Table`s name
            " ( "+      //The columns in the table
            " _ID INTEGER PRIMARY KEY, "+
            " WORD TEXT, "+
            "FREQUENCY INTEGER, "+
            " LOCALE TEXT)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_MAIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}

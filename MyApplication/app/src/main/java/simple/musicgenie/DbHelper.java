package simple.musicgenie;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Create Local Cache Trending (temp)
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final java.lang.String SECTION_TYPE_RESULTS = "Results";
    private static final String TIME_SINCE_UPLOADED_LEFT_VACCANT = "";
    private static Context context;
    private static DbHelper mInstance;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "musicegenieDB";
    private static final String TABLE_TRENDING = "trendingTable";
    private static final String TABLE_RESULTS = "resultsTable";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_TRACK_DURATION = "TRACK_DURATION";
    private static final String COL_UPLOADED_BY = "UPLOADED_BY";
    private static final String COL_THUMB_URL = "THUMB_URL";
    private static final String COL_VIDEO_ID = "VIDEO_ID";
    private static final String COL_TIME_SINCE_UPLOADED = "TIME_SINCE_UPLOADED";
    private static final String COL_USER_VIEWS = "USER_VIEWS";
    private static final String COL_TYPE = "TYPE";

    //String title, String trackDuration, String uploadedBy,
    // String thumbnail_url, String video_id, String timeSinceUploaded, String userViews, String type
    // create table trending ()

    private static final String CREATE_TRENDING_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TRENDING + "( " +
            COL_TITLE + " TEXT , " +
            COL_UPLOADED_BY + " TEXT , " +
            COL_THUMB_URL + " TEXT , " +
            COL_VIDEO_ID + " TEXT , " +
            COL_TIME_SINCE_UPLOADED + " TEXT , " +
            COL_USER_VIEWS + " TEXT , " +
            COL_TRACK_DURATION + " TEXT , " +
            COL_TYPE + " TEXT" +
            ")";

    private static final String CREATE_RESULTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RESULTS + " ( " +
            COL_TITLE + " TEXT , " +
            COL_UPLOADED_BY + " TEXT , " +
            COL_THUMB_URL + " TEXT , " +
            COL_VIDEO_ID + " TEXT , " +
            COL_TIME_SINCE_UPLOADED + " TEXT , " +
            COL_USER_VIEWS + " TEXT , " +
            COL_TRACK_DURATION + " TEXT," +
            COL_TYPE + " TEXT" +
            ")";

    private static final String DROP_TRENDING_TABLE = "DROP TABLE " + TABLE_TRENDING;
    private static final String DROP_RESULTS_TABLE = "DROP TABLE " + TABLE_RESULTS;

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
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

        sqLiteDatabase.execSQL(CREATE_TRENDING_TABLE);
        L.m("DBHelper", "created trending table");
        sqLiteDatabase.execSQL(CREATE_RESULTS_TABLE);
        L.m("DBHelper", "created results table");

    }

    public void upgradeDB() {
        int oldVer = 1;
        int newVer = 2;
        onUpgrade(getReadableDatabase(), oldVer, newVer);
    }

    /**
     * @param sqLiteDatabase database object
     * @param oldVersion     older version of database
     * @param newVersion     newer version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL(DROP_RESULTS_TABLE);
        sqLiteDatabase.execSQL(DROP_TRENDING_TABLE);
        onCreate(sqLiteDatabase);

    }

    private ContentValues getCVObject(ItemModel data) {

        ContentValues temp = new ContentValues();
        temp.put(COL_TITLE, data.Title);
        temp.put(COL_TIME_SINCE_UPLOADED, data.TimeSinceUploaded);
        temp.put(COL_THUMB_URL, data.Thumbnail_url);
        temp.put(COL_TYPE, data.type);
        temp.put(COL_TRACK_DURATION, data.TrackDuration);
        temp.put(COL_UPLOADED_BY, data.UploadedBy);
        temp.put(COL_VIDEO_ID, data.Video_id);
        temp.put(COL_USER_VIEWS, data.UserViews);

        return temp;
    }

    public boolean isTrendingsCached() {

        boolean hasStoredTrending = false;

        String[] cols = {COL_THUMB_URL};

        String[] selectionArgs = {};

        Cursor cr = getReadableDatabase().query(TABLE_TRENDING, cols, "", selectionArgs, null, null, null);
        cr.moveToFirst();

        hasStoredTrending = cr.getCount() > 0;
        return hasStoredTrending;
    }

    public void addTrendingList(ArrayList<SectionModel> list) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values;

        for (int i = 0; i < list.size(); i++) {

            ArrayList<ItemModel> itemModels = list.get(i).getList();

            for (int j = 0; j < itemModels.size(); j++) {

                values = getCVObject(itemModels.get(j));
                values.put(COL_TYPE, list.get(i).sectionTitle);
                long id = database.insert(TABLE_TRENDING, null, values);

                if (id < 0) {
                    Log.d("DBHelper (Trending)", "Cannot Add Row");
                } else {
                    Log.d("DBHelper (Trending)", "Added Successfully ");
                }
            }
        }


    }

    /**
     * @return Read Trending From database
     */
    public ArrayList<SectionModel> getTrendingList() {

        ArrayList<SectionModel> trendingFromDbList = new ArrayList<>();

        String[] cols = {COL_THUMB_URL,
                COL_USER_VIEWS,
                COL_TYPE,
                COL_TITLE,
                COL_UPLOADED_BY,
                COL_TIME_SINCE_UPLOADED,
                COL_VIDEO_ID,
                COL_TRACK_DURATION};

        String[] selectionArgs = {};


        Cursor cr = getReadableDatabase().query(TABLE_TRENDING, cols, "", selectionArgs, null, null, null);

        cr.moveToFirst();

        //temp.put(COL_TITLE, data.Title);
//        temp.put(COL_TIME_SINCE_UPLOADED, data.TimeSinceUploaded);
//        temp.put(COL_THUMB_URL, data.Thumbnail_url);
//        temp.put(COL_TYPE, data.type);
//        temp.put(COL_TRACK_DURATION, data.TrackDuration);
//        temp.put(COL_UPLOADED_BY, data.UploadedBy);
//        temp.put(COL_VIDEO_ID, data.Video_id);
//        temp.put(COL_USER_VIEWS, data.UserViews);

        HashMap<String, ArrayList<ItemModel>> trendingMap = new HashMap<>();
        boolean hasNext = cr.getCount() > 0;
        while (hasNext) {
            // get type of song from cursor

            ItemModel itemModelObj;

            String type = cr.getString(cr.getColumnIndex(COL_TYPE));
            itemModelObj = new ItemModel(cr.getString(cr.getColumnIndex(COL_TITLE)),
                    cr.getString(cr.getColumnIndex(COL_TRACK_DURATION)),
                    cr.getString(cr.getColumnIndex(COL_UPLOADED_BY)),
                    cr.getString(cr.getColumnIndex(COL_THUMB_URL)),
                    cr.getString(cr.getColumnIndex(COL_VIDEO_ID)),
                    "",
                    cr.getString(cr.getColumnIndex(COL_USER_VIEWS)),
                    cr.getString(cr.getColumnIndex(COL_TYPE)));

            ArrayList<ItemModel> mapList = trendingMap.get(type);

            if (mapList == null) {       // accessing list from map for first time
                mapList = new ArrayList<>();
                trendingMap.put(type, mapList);
                mapList.add(itemModelObj);
            } else {
                mapList.add(itemModelObj);
            }

            hasNext = cr.moveToNext();

        }

        // get each item from map and get list and add

        Iterator iterator = trendingMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            L.m("DBHelper", " Iterator " + pair.getKey().toString());
            trendingFromDbList.add(new SectionModel(pair.getKey().toString(), trendingMap.get(pair.getKey())));
        }

        L.m("DBHelper", " collected total " + trendingFromDbList.size());
        return trendingFromDbList;
    }

    ///////////////////////////////////////////////////////////////////////////
    //                  Results Operation Section
    ///////////////////////////////////////////////////////////////////////////

    public boolean isAnyResult() {

        boolean hasStoredResult = false;

        String[] cols = {COL_THUMB_URL};

        String[] selectionArgs = {};
        Cursor cr = getReadableDatabase().query(TABLE_RESULTS, cols, "", selectionArgs, null, null, null);

        cr.moveToFirst();
        hasStoredResult = cr.getCount() > 0;
        return hasStoredResult;
    }

    private void clearResultsIfAny() {
        SQLiteDatabase db = getReadableDatabase();
        String deleteClause = "";
        String[] selectionArgs = {};
        db.delete(TABLE_RESULTS, deleteClause, selectionArgs);
        L.m("DBH", "Wiped Out Result Data");
    }

    public void addResultsList(SectionModel modelItem) {        // actually Overwrite the old data

        // clear back storage
        clearResultsIfAny();

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values;

        ArrayList<ItemModel> itemModels = modelItem.getList();

        for (int j = 0; j < itemModels.size(); j++) {

            values = getCVObject(itemModels.get(j));

            long id = database.insert(TABLE_RESULTS, null, values);

            if (id < 0) {
                Log.d("DBHelper (Results)", "Cannot Add Row");
            } else {
                Log.d("DBHelper (Results)", "Added Successfully ");
            }
        }
    }

    public SectionModel getResultList() {

        SectionModel returnSectionModel = null;

        String[] cols = {
                COL_THUMB_URL,
                COL_USER_VIEWS,
                COL_TITLE,
                COL_UPLOADED_BY,
                COL_TIME_SINCE_UPLOADED,
                COL_VIDEO_ID,
                COL_TRACK_DURATION};

        String[] selectionArgs = {};

        ItemModel itemModelObj;
        ArrayList<ItemModel> itemModelArrayList = new ArrayList<>();

        Cursor cr = getReadableDatabase().query(TABLE_RESULTS, cols, "", selectionArgs, null, null, null);

        cr.moveToFirst();
        boolean hasNext = cr.getCount() > 0;

        while (hasNext) {

            itemModelObj = new ItemModel(cr.getString(cr.getColumnIndex(COL_TITLE)),
                    cr.getString(cr.getColumnIndex(COL_TRACK_DURATION)),
                    cr.getString(cr.getColumnIndex(COL_UPLOADED_BY)),
                    cr.getString(cr.getColumnIndex(COL_THUMB_URL)),
                    cr.getString(cr.getColumnIndex(COL_VIDEO_ID)),
                    TIME_SINCE_UPLOADED_LEFT_VACCANT,
                    cr.getString(cr.getColumnIndex(COL_USER_VIEWS)),
                    SECTION_TYPE_RESULTS);

            itemModelArrayList.add(itemModelObj);
            hasNext = cr.moveToNext();
        }
        returnSectionModel = new SectionModel(SECTION_TYPE_RESULTS, itemModelArrayList);

        return returnSectionModel;
    }

    ///////////////////////////////////////////////////////////////////////////
    //              Callback Interface
    ///////////////////////////////////////////////////////////////////////////

    TrendingLoadListener mTrendingLoadListener;

    public void setmTrendingLoadListener(TrendingLoadListener mTrendingLoadListener) {
        this.mTrendingLoadListener = mTrendingLoadListener;
    }

    interface TrendingLoadListener {
        void onTrendingLoad(ArrayList<SectionModel> trendingList);
    }

    ResultLoadListener mResultLoadListener;

    public void setmResultLoadListener(ResultLoadListener mResultLoadListener) {
        this.mResultLoadListener = mResultLoadListener;
    }

    interface ResultLoadListener {
        void onResultLoadListener(SectionModel result);
    }

}

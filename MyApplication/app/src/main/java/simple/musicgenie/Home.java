package simple.musicgenie;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Home extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager layoutManager;
    private ResulstsRecyclerAdapter mRecyclerAdapter;
    private ProgressDialog progressDialog;
    private HashMap<String, ArrayList<BaseSong>> songMap;
    private CentralDataRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // create download directory
        configureStorageDirectory(savedInstanceState);
        // instantiate views
        instantiateViews();

        redgisterAdapter();

        if (savedInstanceState == null) {
            invokeAction(Constants.ACTION_TYPE_FIRST_LOAD);
            // register adapter

        }

    }

    private void redgisterAdapter() {
        repository.registerForDataLoadListener(new CentralDataRepository.DataReadyToSubmitListener() {
            @Override
            public void onDataSubmit(ArrayList<SectionModel> items) {
                L.m("Home[dataSubmit Callback]","submitted :" +items.get(0).sectionTitle);
                mRecyclerAdapter.appendSongs(items.get(0).sectionTitle,items);
            }
        });
    }

    /**
     * @param actionType type of action to invoke
     *                   todo: must- attach Adapters
     */
    public void invokeAction(int actionType) {

        repository = CentralDataRepository.getInstance(this);

        switch (actionType) {

            case Constants.ACTION_TYPE_FIRST_LOAD:
                showProgress("Presenting Trending...");
                try {
                    repository.submitAction(CentralDataRepository.FLAG_FIRST_LOAD, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {
                            hideProgress();
                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }

                break;
            case Constants.ACTION_TYPE_RESUME:
                showProgress("Presenting Your Items");
                try {
                    repository.submitAction(CentralDataRepository.FLAG_RESTORE, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {
                            hideProgress();
                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }

                break;

            case Constants.ACTION_TYPE_REFRESS:
                showProgress("Refressing Items");
                try {
                    repository.submitAction(CentralDataRepository.FLAG_REFRESS, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {
                            hideProgress();
                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }

                break;


            case Constants.ACTION_TYPE_SEARCH:
                showProgress("Searching Item");
                try {
                    repository.submitAction(CentralDataRepository.FLAG_SEARCH, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {
                            hideProgress();
                        }
                    });
                } catch (CentralDataRepository.InvalidCallbackException e) {
                    e.printStackTrace();
                }

                break;


        }

    }

    private void showProgress(String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    private void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


//        if (savedInstanceState.getSerializable("mapSong") != null) {
////
////            // clear old data and append append new one
//////            pushDataToRecyclerView(null , "");
////
////            subscribeToTaskAddListener();
////            HashMap<String, ArrayList<BaseSong>> map = (HashMap<String, ArrayList<BaseSong>>) savedInstanceState.getSerializable("mapSong");
////            Iterator iterator = map.entrySet().iterator();
////            while (iterator.hasNext()) {
////                Map.Entry pair = (Map.Entry) iterator.next();
////                L.m("Home onRestoreInstance() ", "reading hashmap for key " + pair.getKey().toString());
////                songMap.put(pair.getKey().toString(), map.get(pair.getKey()));
////
//////                appendDataToReclerView( pair.getKey().toString() , map.get(pair.getKey()));
////            }
//
//
//
//        }

    }

    private void subscribeToTaskAddListener() {
        ResulstsRecyclerAdapter.getInstance(this).setOnTaskAddListener(new TaskAddListener() {
            @Override
            public void onTaskTapped() {
                L.m("Home subscribeToTaskAddListener() ", "callback: task tapped");
                progressDialog = new ProgressDialog(Home.this);
                progressDialog.setMessage("Requesting Your Stuff..");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            public void onTaskAddedToQueue(String task_info) {
                L.m("Home subscribeToTaskAddListener() ", "callback: task added to download queue");
                progressDialog.dismiss();
                Toast.makeText(Home.this, task_info + " Added To Download", Toast.LENGTH_LONG).show();
                //TODO: navigate to DownloadsActivity
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int screenMode() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;

        double diagonal = Math.sqrt(yInches * yInches + xInches * xInches);
        if (diagonal > 6.5) {
            return Constants.SCREEN_MODE_TABLET;
        } else {
            return Constants.SCREEN_MODE_MOBILE;
        }
    }
//
//    private void appendDataToReclerView(String type , ArrayList<Song> list) {
//        mRecyclerAdapter.appendSongs( type,list );
//    }
//
//    private void pushDataToRecyclerView(ArrayList<Song> newList,String type){
//
//        mRecyclerAdapter = TrendingRecyclerViewAdapter.getInstance(this);
//        mRecyclerAdapter.setSongs(newList , type);
//
//    }

    private void plugAdapter() {
        mRecyclerAdapter.setOrientation(getOrientation());
        mRecyclerAdapter.setScreenMode(screenMode());
        mRecyclerView.setAdapter(mRecyclerAdapter);
        subscribeForStreamOption(mRecyclerAdapter);
    }

    private void instantiateViews() {

        int maxCols = (isPortrait(getOrientation())) ? ((screenMode() == Constants.SCREEN_MODE_MOBILE) ? 2 : 3) : 4;
        mRecyclerView = (RecyclerView) findViewById(R.id.trendingRecylerView);
        layoutManager = new StaggeredGridLayoutManager(maxCols, 1);
        mRecyclerAdapter = ResulstsRecyclerAdapter.getInstance(this);
        mRecyclerView.setLayoutManager(layoutManager);
        plugAdapter();

    }

    private boolean isPortrait(int orientation) {
        return orientation % 2 == 0;
    }

    private void configureStorageDirectory(Bundle savedInstance) {

        if (savedInstance == null) {
            L.m("Home configureStorageDirectory()", "making dirs");
            AppConfig.getInstance(this).configureDevice();
        }
    }

    private void subscribeForStreamOption(ResulstsRecyclerAdapter mRecyclerAdapter) {
        mRecyclerAdapter.setOnStreamingSourceAvailable(new ResulstsRecyclerAdapter.OnStreamingSourceAvailableListener() {
            @Override
            public void onPrepared(String uri) {

                progressDialog.dismiss();

            }

            @Override
            public void optioned() {

                progressDialog = new ProgressDialog(Home.this);
                progressDialog.setMessage("Requesting Audio For You....");
                progressDialog.show();

            }
        });

    }

    private int getOrientation() {
        return getWindowManager().getDefaultDisplay().getOrientation();
    }

}

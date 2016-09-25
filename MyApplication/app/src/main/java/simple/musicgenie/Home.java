package simple.musicgenie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;

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
    private FloatingSearchView searchView;
    private SwipeRefreshLayout swipeRefressLayout;
    private Handler mHandler;

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
        }

    }

    private void redgisterAdapter() {

        repository = CentralDataRepository.getInstance(this);

        repository.registerForDataLoadListener(new CentralDataRepository.DataReadyToSubmitListener() {
            @Override
            public void onDataSubmit(SectionModel item) {

                    mRecyclerAdapter.enque(item);

            }
        });
    }

    /**
     * @param actionType type of action to invoke
     *                   todo: must- attach Adapters
     */
    public void invokeAction(int actionType) {

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

                try {
                    repository.submitAction(CentralDataRepository.FLAG_REFRESS, new CentralDataRepository.ActionCompletedListener() {
                        @Override
                        public void onActionCompleted() {
                            // disable refressing
                            L.m("Callback[Refress] ","Refressed");
                            if(swipeRefressLayout.isRefreshing()) {
                                swipeRefressLayout.setRefreshing(false);
                                swipeRefressLayout.setEnabled(true);
                            }
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


    private void plugAdapter() {
        mRecyclerAdapter.setOrientation(getOrientation());
        mRecyclerAdapter.setScreenMode(screenMode());
        mRecyclerView.setAdapter(mRecyclerAdapter);
        subscribeForStreamOption(mRecyclerAdapter);
    }

    private void instantiateViews() {

        int maxCols = (isPortrait(getOrientation())) ? ((screenMode() == Constants.SCREEN_MODE_MOBILE) ? 2 : 3) : 4;
        swipeRefressLayout = (SwipeRefreshLayout) findViewById(R.id.content_refresser);
        mRecyclerView = (RecyclerView) findViewById(R.id.trendingRecylerView);
        layoutManager = new StaggeredGridLayoutManager(maxCols, 1);
        mRecyclerAdapter = ResulstsRecyclerAdapter.getInstance(this);
        mRecyclerView.setLayoutManager(layoutManager);

        swipeRefressLayout.setColorSchemeColors(getResources().getColor(R.color.PrimaryColor), Color.WHITE);
        swipeRefressLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        refressContent();
//                    }
//                });
            }
        });

        plugAdapter();
        setSearchView();
    }

    private boolean isPortrait(int orientation) {
        return orientation % 2 == 0;
    }

    private void refressContent(){

        if(ConnectivityUtils.getInstance(this).isConnectedToNet()){

            invokeAction(Constants.ACTION_TYPE_REFRESS);
            swipeRefressLayout.setRefreshing(true);
            swipeRefressLayout.setEnabled(false);

        }else{
            Snackbar.make(swipeRefressLayout, "No Connectivity !!", Snackbar.LENGTH_SHORT).show();
            swipeRefressLayout.setRefreshing(false);
        }

    }

    public void setSearchView() {
        searchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {


            @Override
            public void onSearchTextChanged(String oldText, String newText) {
                if (!oldText.equals("") && newText.equals("")) {
                    searchView.clearSuggestions();
                } else {

                    searchView.showProgress();
                    SearchSuggestionHelper.getInstance(Home.this).findSuggestion(newText,
                            new SearchSuggestionHelper.OnFindSuggestionListener() {
                        @Override
                        public void onResult(ArrayList<SearchSuggestion> list) {
                            searchView.swapSuggestions(list);
                            searchView.hideProgress();
                        }
                    });
                }
            }
        });


        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(com.arlib.floatingsearchview.suggestions.model.SearchSuggestion searchSuggestion) {
                fireSearch(searchSuggestion.getBody());
            }

            @Override
            public void onSearchAction(String query) {
                fireSearch(query);
            }
        });

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_settings) {
                    Intent i = new Intent(Home.this, UserPreferenceSetting.class);
                    startActivity(i);
                }
                if (id == R.id.action_downloads) {
                    Intent i = new Intent(Home.this, DowloadsActivity.class);
                    startActivity(i);
                }
            }
        });


    }

    private void fireSearch(String query) {

        SharedPrefrenceUtils.getInstance(this).setLastSearchTerm(query);
        invokeAction(Constants.ACTION_TYPE_SEARCH);

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

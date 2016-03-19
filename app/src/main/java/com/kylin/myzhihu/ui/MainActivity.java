package com.kylin.myzhihu.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kylin.myzhihu.R;
import com.kylin.myzhihu.adapters.MyViewPagerAdapter;
import com.kylin.myzhihu.entity.AbstractStoriesItem;
import com.kylin.myzhihu.entity.StoriesItem;
import com.kylin.myzhihu.entity.TopStoriesItem;
import com.kylin.myzhihu.presenters.MainActivityPresenter;
import com.kylin.myzhihu.adapters.StoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityPresenter.IMainActivityUi,
        View.OnClickListener, StoriesAdapter.CustomItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.contents_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
//    @Bind(R.id.sf_stories)
//    SwipeRefreshLayout mRefresh;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.vp_top_stories)
    ViewPager mViewPager;

    public static final String TAG = "MainActivity";
    private MainActivityPresenter mPresenter;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog pDialog = null;
    private MyViewPagerAdapter mViewPagerAdapter;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

//        mRefresh = (SwipeRefreshLayout) findViewById(R.id.sf_stories);
//        mRefresh.setOnRefreshListener(this);
//        mRefresh.setColorSchemeColors(android.R.color.holo_purple);
//        mRefresh.setNestedScrollingEnabled(false);

        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList myDataset = new ArrayList();
        mAdapter = new StoriesAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.progress_loading));

        mViewPagerAdapter = new MyViewPagerAdapter(this, this);
        mViewPager.setAdapter(mViewPagerAdapter);

        mPresenter = new MainActivityPresenter();
        mPresenter.onUiReady(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog.isShowing()){
            pDialog.hide();
            pDialog = null;
        }
        mPresenter.onUiDestory(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void updateTopStories(List<TopStoriesItem> topStories) {
        mViewPagerAdapter.updateAll(topStories);
        mViewPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateStories(List<StoriesItem> stories) {
        if (mAdapter instanceof StoriesAdapter){
            List mDataSet = ((StoriesAdapter) mAdapter).getDataSet();
            mDataSet.addAll(0,stories);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showProgressDialog(boolean shown) {
        if (pDialog!=null){
            if (shown&&!pDialog.isShowing()){
                pDialog.show();
            }else if(!shown&&pDialog.isShowing()){
                pDialog.hide();
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.fab:
                final Snackbar snackbar = Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // manully dismiss, would not refresh view leading to FAB suspend there.
                        // but automatically dismiss will refresh redraw.
                        //snackbar.dismiss();
                    }
                });
                snackbar.show();
                break;
            case R.id.container_view_pager_item:
                Log.d(TAG, "onClick container_view_pager_item"+v.getTag());
                mPresenter.startDetailActivity(String.valueOf(v.getTag()));
                break;
            default:

                break;
        }

    }

    @Override
    public void onItemClick(View v, int postion) {
        if (v instanceof CardView){
            mPresenter.startDetailActivity(String.valueOf(v.getTag()));
        }
    }

    @Override
    public void onRefresh() {
//        Before I know how to refresh it using pull up, disable it temp.
//        if (!isRefresh) {
//            isRefresh = true;
//            new Handler().postDelayed(new Runnable() {
//                public void run() {
//                    mRefresh.setRefreshing(false);
//                    isRefresh = false;
//                }
//            }, 3000);
//        }
    }

}

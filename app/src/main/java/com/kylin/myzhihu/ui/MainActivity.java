package com.kylin.myzhihu.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kylin.myzhihu.R;
import com.kylin.myzhihu.entity.AbstractStoriesItem;
import com.kylin.myzhihu.presenters.MainActivityPresenter;
import com.kylin.myzhihu.utils.StoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityPresenter.IMainActivityUi,
        View.OnClickListener, StoriesAdapter.CustomItemClickListener{

    private MainActivityPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressDialog pDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.contents_recycler_view);
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
    public void updateTopStories(List<? extends AbstractStoriesItem> topStories) {

    }

    @Override
    public void updateStories(List<? extends AbstractStoriesItem> stories) {
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
}

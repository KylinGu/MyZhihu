package com.kylin.myzhihu.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kylin.myzhihu.R;
import com.kylin.myzhihu.presenters.DetailStoryActivityPresenter;
import com.kylin.myzhihu.utils.MyConstant;
import com.kylin.myzhihu.view_interface.IDetailActivityUi;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailStoryActivity extends AppCompatActivity implements IDetailActivityUi,
        OnMenuTabSelectedListener{

    private final static String TAG = "DetaisStoryActivity";
    private DetailStoryActivityPresenter mPresenter;
    private ProgressDialog pDialog = null;
    private BottomBar mBottomBar;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.niv_title_image) NetworkImageView nivTitleImage;
    @Bind(R.id.wv_story) WebView wvStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatil_story);
        ButterKnife.bind(this);
        //Find a bug, if we did not set it once before setting support action bar,
        //then we can not update title again....
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBottomBar = BottomBar.attach(this,savedInstanceState);
        //to remove large space on top and navibar.
        mBottomBar.noTopOffset();
        mBottomBar.noNavBarGoodness();

        mBottomBar.setItemsFromMenu(R.menu.menu_bottombar, this);
        //set color transition
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorPrimary));
        mBottomBar.mapColorForTab(1, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(2, ContextCompat.getColor(this, R.color.material_green));
        mBottomBar.mapColorForTab(3, ContextCompat.getColor(this, R.color.material_purple));

        mBottomBar.setEnableAlwasyShowTitle(true);
        mBottomBar.setEnableUnselectAll(true);
        try{
            mBottomBar.selectTabAtPosition(-1, true);
        }catch (NullPointerException e){
            //just want to unselect the tab.
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        wvStory.setNestedScrollingEnabled(true);
        wvStory.getSettings().setJavaScriptEnabled(true);
        wvStory.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.progress_loading));

        mPresenter = new DetailStoryActivityPresenter();
        mPresenter.onUiReady(this);
        if(getIntent()!=null){
            String storyId = getIntent().getStringExtra(MyConstant.KEY_STORY_ID);
            mPresenter.requestViewDetailStory(storyId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUiDestory(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvStory.canGoBack()){
            wvStory.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateTitleImage(String url, ImageLoader imageLoader) {
        nivTitleImage.setImageUrl(url, imageLoader);
    }

    @Override
    public void showStory(String content) {
        if (content == null ) return;
        wvStory.loadData(content, "text/html;charset=UTF-8", "UTF-8");
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
    public void updateTitle(String title){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)   {
            actionBar.setTitle(title);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onMenuItemSelected(int menuItemId) {

    }
}

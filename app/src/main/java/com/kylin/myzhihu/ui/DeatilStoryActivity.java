package com.kylin.myzhihu.ui;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.kylin.myzhihu.R;
import com.kylin.myzhihu.presenters.DetailStoryActivityPresenter;
import com.kylin.myzhihu.utils.MyConstant;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DeatilStoryActivity extends AppCompatActivity implements DetailStoryActivityPresenter.IDetailActivityUi{

    private final static String TAG = "DetaisStoryActivity";
    private DetailStoryActivityPresenter mPresenter;
    private ProgressDialog pDialog = null;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.niv_title_image) NetworkImageView nivTitleImage;
    @Bind(R.id.wv_story) WebView wvStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatil_story);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Log.d(TAG, "updateTitle = "+title+", actionBar = "+actionBar);
        if(actionBar != null)   {
            actionBar.setTitle(title);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}

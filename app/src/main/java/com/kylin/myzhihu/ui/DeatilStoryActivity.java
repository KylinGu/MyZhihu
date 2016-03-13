package com.kylin.myzhihu.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class DeatilStoryActivity extends AppCompatActivity implements DetailStoryActivityPresenter.IDetailActivityUi{

    private DetailStoryActivityPresenter mPresenter;
    private WebView wvStory;
    private ProgressDialog pDialog = null;
    private NetworkImageView nivTitleImage = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatil_story);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        wvStory = (WebView) findViewById(R.id.wv_story);
        wvStory.setNestedScrollingEnabled(true);
        wvStory.getSettings().setJavaScriptEnabled(true);
        wvStory.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        nivTitleImage = (NetworkImageView) findViewById(R.id.niv_title_image);

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
        wvStory.loadUrl(content);
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
    public void updateTitle(String title) {
        setTitle(title);
    }

    @Override
    public Context getContext() {
        return this;
    }
}

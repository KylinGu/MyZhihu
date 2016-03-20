package com.kylin.myzhihu.view_interface;

import com.android.volley.toolbox.ImageLoader;
import com.kylin.myzhihu.ui.Ui;

/**
 * Created by kylin_gu on 2016/3/20.
 */
public interface IDetailActivityUi extends Ui {
    void updateTitleImage(String url, ImageLoader imageLoader);
    void showStory(String content);
    void showProgressDialog(boolean shown);
    void updateTitle(String title);
}

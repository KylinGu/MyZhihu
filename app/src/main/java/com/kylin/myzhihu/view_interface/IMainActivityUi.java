package com.kylin.myzhihu.view_interface;

import com.kylin.myzhihu.entity.StoriesItem;
import com.kylin.myzhihu.entity.TopStoriesItem;
import com.kylin.myzhihu.ui.Ui;

import java.util.List;

/**
 * Created by kylin_gu on 2016/3/20.
 */
public interface IMainActivityUi extends Ui {
    void updateTopStories(List<TopStoriesItem> topStories);
    void updateStories(List<StoriesItem> stories);
    void showProgressDialog(boolean shown);
}

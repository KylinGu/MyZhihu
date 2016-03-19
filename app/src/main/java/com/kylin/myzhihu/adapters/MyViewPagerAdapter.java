package com.kylin.myzhihu.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.kylin.myzhihu.R;
import com.kylin.myzhihu.entity.TopStoriesItem;
import com.kylin.myzhihu.utils.AppController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kylin_gu on 2016/3/18.
 */


public class MyViewPagerAdapter extends android.support.v4.view.PagerAdapter {

    private List<TopStoriesItem> mDataSet = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private Context mContext;
    private View.OnClickListener listener;

    public MyViewPagerAdapter(Context context, View.OnClickListener listener){
        mContext = context;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TopStoriesItem topStory = mDataSet.get(position);

        View view = View.inflate(mContext, R.layout.view_page_item, null);
        container.addView(view);
        viewList.add(view);

        NetworkImageView nivTopStoriesImg = (NetworkImageView) view.findViewById(R.id.niv_top_storie_img);
        nivTopStoriesImg.setImageUrl(topStory.getImage(), AppController.getInstance().getImageLoader());
        TextView tv_title = (TextView) view.findViewById(R.id.tv_top_stories_title);
        tv_title.setText(topStory.title);
        FrameLayout layout = (FrameLayout) view.findViewById(R.id.container_view_pager_item);
        layout.setTag(topStory.getId());
        layout.setOnClickListener(listener);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    public void updateAll(List<TopStoriesItem> data) {
        data = data == null ? new ArrayList<TopStoriesItem>() : data;
        mDataSet.clear();
        mDataSet.addAll(data);
        notifyDataSetChanged();
    }

}
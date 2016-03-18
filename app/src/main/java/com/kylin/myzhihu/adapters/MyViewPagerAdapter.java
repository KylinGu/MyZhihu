package com.kylin.myzhihu.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
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

    private List<TopStoriesItem> list = new ArrayList<>();
    private List<View> banners = new ArrayList<>();
    private ArrayList<Long> idList = new ArrayList<>();
    private Context mContext;

    public MyViewPagerAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TopStoriesItem topStory = list.get(position);

        View view = View.inflate(mContext, R.layout.view_page_item, null);

        container.addView(view);
        banners.add(view);

        NetworkImageView draweeView = (NetworkImageView) view.findViewById(R.id.list_item_banner_img);
        draweeView.setImageUrl(topStory.getImage(), AppController.getInstance().getImageLoader());
        TextView tv_title = (TextView) view.findViewById(R.id.list_item_banner_title);
        tv_title.setText(topStory.title);
//        view.setOnClickListener(new Listener(topStory));

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(banners.get(position));
    }

    public void updateAll(List<TopStoriesItem> data) {
        data = data == null ? new ArrayList<TopStoriesItem>() : data;
        list.clear();
        list.addAll(data);
        for (TopStoriesItem topStory : list) {
            idList.add(topStory.getId());
        }
        notifyDataSetChanged();
    }

//    private class Listener extends AbsBaseOnItemClickListener<TopStoriesItem> {
//        public Listener(TopStoriesItem data) {
//            super(data);
//        }
//
//        @Override
//        public void onClick(View view, TopStoriesItem data) {
//            Intent intent = new Intent(mContext, DetailStoryActivity.class);
//            intent.putExtra("ids", idList);
//            intent.putExtra("index", idList.indexOf(data.id));
//            startActivity(intent);
//        }
//    }
}
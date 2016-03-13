package com.kylin.myzhihu.presenters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kylin.myzhihu.entity.AbstractStoriesItem;
import com.kylin.myzhihu.entity.LatestStoriesBean;
import com.kylin.myzhihu.ui.DeatilStoryActivity;
import com.kylin.myzhihu.utils.AppController;
import com.kylin.myzhihu.utils.MyConstant;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by kylin_gu on 2016/3/12.
 */
public class MainActivityPresenter {

    private static final String TAG = "MainActivityPresenter";

    private IMainActivityUi mUi;

    private IMainActivityUi getUi(){
        return mUi;
    }

    public void onUiReady(IMainActivityUi ui){
        mUi = ui;
        //register listener

        //init loading.
        requestLatestStories();
    }

    public void onUiUnready(){
        //ungister listener

        //cancel all the request.
        AppController.getInstance().cancelPendingRequests(MyConstant.TAG_REQ_OBJ_MAINACTIVITY);
    }

    /**
     * http://news-at.zhihu.com/api/4/news/$storyId
     * e.g. 3892357
     * @param storyId
     */
    public void requestViewDetailStory(String storyId){
        StringRequest strReq = new StringRequest(Request.Method.GET,
                MyConstant.URL_REQUEST_STORY_BASE + storyId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString()!=null){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        AppController.getInstance().addToRequestQueue(strReq, MyConstant.TAG_REQ_OBJ_MAINACTIVITY);
    }

    public void startDetailActivity(String storyId){
        Intent intent = new Intent();
        intent.setClass(mUi.getContext(), DeatilStoryActivity.class);
        intent.putExtra(MyConstant.KEY_STORY_ID, storyId);
        mUi.getContext().startActivity(intent);

    }

    public void requestLatestStories(){
        //loading progress
        getUi().showProgressDialog(true);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                MyConstant.URL_REQUEST_LATEST_STORIES, "",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        getUi().showProgressDialog(false);
                        Log.d(TAG, response.toString());
                        LatestStoriesBean bean = parseLatestJsonStories(response);
                        if (bean == null) {
                            Log.e(TAG, "requestLatestStories: fatal error bean is null.");
                            return;
                        }
                        getUi().updateTopStories(bean.getTopStories());
                        getUi().updateStories(bean.getStories());

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getUi().showProgressDialog(false);
                        Log.d(TAG, "requestLatestStories: Error: " + error.getMessage());
                    }
        }) {
            /**
             * this is used to post, just override params...
             * @return
             * @throws AuthFailureError
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("name", "Androidhive");
                //params.put("email", "abc@androidhive.info");
                //params.put("password", "password123");
                return super.getParams();
            }

            /**
             * add some request to header
             * @return
             * @throws AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                //headers.put("apiKey", "xxxxxxxxxxxxxxx");
                return super.getHeaders();
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, MyConstant.TAG_REQ_OBJ_MAINACTIVITY);
    }

    private LatestStoriesBean parseLatestJsonStories(JSONObject jObject){
        if (jObject == null) return null;
        LatestStoriesBean mBean = com.alibaba.fastjson.JSON.parseObject(jObject.toString(),
                LatestStoriesBean.class);
        return mBean;
    }



    public interface IMainActivityUi{
        void updateTopStories(List<? extends AbstractStoriesItem> topStories);
        void updateStories(List<? extends AbstractStoriesItem> stories);
        void showProgressDialog(boolean shown);
        Context getContext();
    }

}

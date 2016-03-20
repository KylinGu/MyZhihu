package com.kylin.myzhihu.presenters;

import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kylin.myzhihu.entity.LatestStoriesBean;
import com.kylin.myzhihu.entity.StoriesItem;
import com.kylin.myzhihu.entity.TopStoriesItem;
import com.kylin.myzhihu.ui.DetailStoryActivity;
import com.kylin.myzhihu.utils.AppController;
import com.kylin.myzhihu.utils.MyConstant;
import com.kylin.myzhihu.view_interface.IMainActivityUi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by kylin_gu on 2016/3/12.
 */
public class MainActivityPresenter extends Presetner<IMainActivityUi>{

    private static final String TAG = "MainActivityPresenter";

    public void onUiReady(IMainActivityUi ui){
        super.onUiReady(ui);
        //register listener

        //init loading.
        requestLatestStories();
    }

    @Override
    public void onUiUnready(IMainActivityUi ui){
        super.onUiUnready(ui);
        //ungister listener

        //cancel all the request.
        AppController.getInstance().cancelPendingRequests(MyConstant.TAG_REQ_OBJ_MAINACTIVITY);
    }

    public void startDetailActivity(String storyId){
        Intent intent = new Intent();
        intent.setClass(getUi().getContext(), DetailStoryActivity.class);
        intent.putExtra(MyConstant.KEY_STORY_ID, storyId);
        getUi().getContext().startActivity(intent);
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
        //it can not get topstories...a bug...
        if (mBean!=null && mBean.getDate()==0){
            try {
                mBean.date = jObject.getLong("date");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (mBean !=null && mBean.getStories() == null){
            try {
                JSONArray jArray = jObject.getJSONArray("stories");
                mBean.stories = com.alibaba.fastjson.JSON.parseArray(jArray.toString(), StoriesItem.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (mBean !=null && mBean.getTopStories() == null ){
            try {
                JSONArray jTopArray = jObject.getJSONArray("top_stories");
                mBean.topStories = com.alibaba.fastjson.JSON.parseArray(jTopArray.toString(),
                        TopStoriesItem.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "requestLatestStories:"+mBean.getDate()+", "+mBean.getStories()+", "+mBean.getTopStories());
        return mBean;
    }

}

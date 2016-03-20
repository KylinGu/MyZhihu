package com.kylin.myzhihu.presenters;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kylin.myzhihu.entity.DetailStoryBean;
import com.kylin.myzhihu.ui.Ui;
import com.kylin.myzhihu.utils.AppController;
import com.kylin.myzhihu.utils.MyConstant;
import com.kylin.myzhihu.view_interface.IDetailActivityUi;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by kylin_gu on 2016/3/13.
 */
public class DetailStoryActivityPresenter extends Presetner<IDetailActivityUi>{

    private static final String TAG = "MainActivityPresenter";

    @Override
    public void onUiReady(IDetailActivityUi ui){
        super.onUiReady(ui);
        //register listener

        //init loading.
    }

    @Override
    public void onUiUnready(IDetailActivityUi ui){
        super.onUiUnready(ui);
        //ungister listener

        //cancel all the request.
        AppController.getInstance().cancelPendingRequests(MyConstant.TAG_REQ_OBJ_DETAILACTIVITY);
    }

    /**
     * http://news-at.zhihu.com/api/4/news/$storyId
     * e.g. 3892357
     * @param storyId
     */
    public void requestViewDetailStory(String storyId){
        getUi().showProgressDialog(true);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                MyConstant.URL_REQUEST_STORY_BASE + storyId,
                "",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        getUi().showProgressDialog(false);
                        if (response.toString() != null) {
                            Log.d(TAG, "requestViewDetailStory: onResponse: "+response.toString());
                            DetailStoryBean mBean = com.alibaba.fastjson.JSON.parseObject(response.toString(),
                                    DetailStoryBean.class);
                            getUi().showStory(buildHtmlStyleStory(mBean.getBody(), mBean.getJs(), mBean.getCss()));
                            getUi().updateTitleImage(mBean.getImage().toString(),
                                    AppController.getInstance().getImageLoader());
                            getUi().updateTitle(mBean.getTitle().toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse (VolleyError error){
                            getUi().showProgressDialog(false);
                            Log.d(TAG, "requestViewDetailStory: Error: " + error.getMessage());
                        }
                }
        );
        AppController.getInstance().addToRequestQueue(jsonObjReq, MyConstant.TAG_REQ_OBJ_DETAILACTIVITY);
    }

    /**
     * the method that building a html body is coming from https://github.com/Uphie/ZhiHuDaily
     * As I did not know any thing about html and network-type app  programing before,
     * it is indeed a great help.
     *
     * @param body
     * @param jsList
     * @param cssList
     * @return
     */
    private String buildHtmlStyleStory(String body, List<String> jsList, List<String> cssList ){
        //build a html content and load it with webview
        String css = "";
        for (String css_url : cssList) {
            css += "<link rel=\"stylesheet\" href=" + css_url + ">\n";
        }
        String js = "";
        for (String js_url : jsList) {
            js += "<script src=" + js_url + "/>\n";
        }
        //no need to give a blank space for title image.
        String mBody = body.replaceAll("<div class=\"img-place-holder\"></div>", "");
        Log.d(TAG, "buildHtmlStyleStory: "+mBody);
        StringBuilder builder = new StringBuilder();
        builder.append("<html>\n")
                .append("<head>\n")
                .append(css).append(js)
                .append("</head>\n")
                .append("<body>")
                .append(mBody)
                .append("</body>\n")
                .append("</html>");

        return builder.toString();
    }
}

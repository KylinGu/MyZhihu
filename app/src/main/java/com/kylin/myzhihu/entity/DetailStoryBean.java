package com.kylin.myzhihu.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailStoryBean implements Serializable{

    private String body = "";
    private String imageSource = "";
    private String title = "";
    private String image = "";
    private String shareUrl = "";
    private List<String> js = new ArrayList<String>();
    private String gaPrefix = "";
    private Integer type = 0;
    private Integer id = 0;
    private List<String> css = new ArrayList<String>();

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The imageSource
     */
    public String getImageSource() {
        return imageSource;
    }

    /**
     *
     * @param imageSource
     * The image_source
     */
    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The shareUrl
     */
    public String getShareUrl() {
        return shareUrl;
    }

    /**
     *
     * @param shareUrl
     * The share_url
     */
    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    /**
     *
     * @return
     * The js
     */
    public List<String> getJs() {
        return js;
    }

    /**
     *
     * @param js
     * The js
     */
    public void setJs(List<String> js) {
        this.js = js;
    }

    /**
     *
     * @return
     * The gaPrefix
     */
    public String getGaPrefix() {
        return gaPrefix;
    }

    /**
     *
     * @param gaPrefix
     * The ga_prefix
     */
    public void setGaPrefix(String gaPrefix) {
        this.gaPrefix = gaPrefix;
    }

    /**
     *
     * @return
     * The type
     */
    public Integer getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The css
     */
    public List<String> getCss() {
        return css;
    }

    /**
     *
     * @param css
     * The css
     */
    public void setCss(List<String> css) {
        this.css = css;
    }
}
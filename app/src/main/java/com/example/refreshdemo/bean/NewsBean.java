package com.example.refreshdemo.bean;

/**
 * Created by YANG
 * 2016/8/3 11:06
 */

public class NewsBean {
    /**
     * ctime : 2016-08-03 09:19
     * title : 滴滴收购优步中国后 老外觉得Uber在中国打了一场败仗
     * description : 腾讯科技
     * picUrl : http://inews.gtimg.com/newsapp_ls/0/458952058_300240/0
     * url : http://tech.qq.com/a/20160803/012784.htm
     */
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.tzy.demo.bean;

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
    private String id;
    private String ctime;
    private String title;
    private String description;
    private String picUrl;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsBean newsBean = (NewsBean) o;

        if (!id.equals(newsBean.id)) return false;
        if (!title.equals(newsBean.title)) return false;
        if (!description.equals(newsBean.description)) return false;
        if (!picUrl.equals(newsBean.picUrl)) return false;
        return url.equals(newsBean.url);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + picUrl.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }
}

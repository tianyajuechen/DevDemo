package com.tzy.demo.bean;

import java.util.Objects;

/**
 * Created by tang
 * 2017/6/20
 */
public class WeChatNewsBean {

    /**
     * id : wechat_20150401071581
     * title : 号外：集宁到乌兰花的班车出事了！！！！！
     * source : 内蒙那点事儿
     * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-214279.jpg/168
     * mark :
     * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20150401071581
     */

    private String id;
    private String title;
    private String source;
    private String firstImg;
    private String mark;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

        WeChatNewsBean that = (WeChatNewsBean) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(source, that.source)) return false;
        if (!Objects.equals(firstImg, that.firstImg))
            return false;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (firstImg != null ? firstImg.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}

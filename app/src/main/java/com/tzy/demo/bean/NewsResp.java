package com.tzy.demo.bean;

import java.util.List;

/**
 * Created by YANG
 * 2016/8/3 11:06
 */

public class NewsResp {

    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-08-03 09:19","title":"滴滴收购优步中国后 老外觉得Uber在中国打了一场败仗","description":"腾讯科技","picUrl":"http://inews.gtimg.com/newsapp_ls/0/458952058_300240/0","url":"http://tech.qq.com/a/20160803/012784.htm"}]
     */

    private int code;
    private String msg;
    private List<NewsBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewsBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewsBean> newslist) {
        this.newslist = newslist;
    }
}

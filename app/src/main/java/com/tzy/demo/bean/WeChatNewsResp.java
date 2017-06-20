package com.tzy.demo.bean;

import java.util.List;

/**
 * Created by tang
 * 2017/6/20
 */
public class WeChatNewsResp {

    /**
     * list : [{"id":"wechat_20170620041735","title":"吴用梦见死后的宋江和李逵找他，是梦境还是幻觉？","source":"萌书生","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-28722287.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170620041735"},{"id":"wechat_20170620041617","title":"《水浒传》吴用设\u201c招安局\u201d，朝廷重臣中，谁是他的靠山？","source":"萌书生","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-28722104.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170620041617"},{"id":"wechat_20170620041610","title":"送给曾经相爱的两个人\u2026\u2026","source":"育儿帮帮忙","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-24628521.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170620041610"},{"id":"wechat_20170620036465","title":"史上最让人喷笑的寻人启事：爸妈，我还是你们亲生的吗？？？","source":"史上最贱喵","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-28598218.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170620036465"},{"id":"wechat_20170620032137","title":"《西南商报》副刊（荐读）：【胥德云】小满节里谈谈521","source":"成都祥涵文化传媒","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-28536712.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170620032137"}]
     * totalPage : 18488
     * ps : 5
     * pno : 1
     */

    private int totalPage;
    private int ps;
    private int pno;
    private List<WeChatNewsBean> list;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getPno() {
        return pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public List<WeChatNewsBean> getList() {
        return list;
    }

    public void setList(List<WeChatNewsBean> list) {
        this.list = list;
    }
}

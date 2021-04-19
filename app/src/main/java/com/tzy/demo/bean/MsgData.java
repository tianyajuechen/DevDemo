package com.tzy.demo.bean;

/**
 * @Author tangzhaoyang
 * @Date 2021/3/9
 */
public class MsgData {
    private String from;
    private String to = "robot";
    private String content;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

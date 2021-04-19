package com.tzy.demo.bean;

/**
 * @Author tangzhaoyang
 * @Date 2021/3/9
 */
public class SendMsg {
    private String type = "inputing";
    private MsgData data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MsgData getData() {
        return data;
    }

    public void setData(MsgData data) {
        this.data = data;
    }
}

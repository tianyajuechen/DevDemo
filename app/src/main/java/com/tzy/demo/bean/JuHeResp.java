package com.tzy.demo.bean;

/**
 * Created by tang
 * 2017/6/20
 */
public class JuHeResp {
    private int error_code;
    private WeChatNewsResp result;
    private String reason;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public WeChatNewsResp getResult() {
        return result;
    }

    public void setResult(WeChatNewsResp result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

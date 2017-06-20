package com.tzy.demo.api;

import com.tzy.demo.bean.BaiduTokenBean;
import com.tzy.demo.bean.JuHeResp;
import com.tzy.demo.finals.Urls;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by YANG
 * 2016/5/6 17:14
 * 请求接口
 */
public interface APIService {

    @GET("http://v.juhe.cn/weixin/query?key=" + Urls.NewsKey)
    Call<JuHeResp> getNews(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST("https://aip.baidubce.com/rest/2.0/ocr/v1/general")
    Call<ResponseBody> baidu(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("https://aip.baidubce.com/oauth/2.0/token")
    Call<BaiduTokenBean> getBaiduToken(@FieldMap Map<String, String> params);

    @GET("http://dynamic-image.yesky.com/1080x-/uploadImages/2015/141/13/YK62BBX9GNC1.jpg")
    Call<ResponseBody> getImage();

}
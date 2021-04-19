package com.tzy.demo.api;

import com.tzy.demo.bean.JuHeResp;
import com.tzy.demo.bean.RemoteAddressResp;
import com.tzy.demo.bean.TestHelloBean;
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

    @GET("http://dynamic-image.yesky.com/1080x-/uploadImages/2015/141/13/YK62BBX9GNC1.jpg")
    Call<ResponseBody> getImage();

    @GET("http://bg.niu100.com/app/version?appName=niu100Apk")
    Call<ResponseBody> getVersion1();

    @GET("http://www.xuncloud.net")
    Call<ResponseBody> getVersion2();

    @GET("http://www.niu100.com")
    Call<ResponseBody> getVersion3();

    @GET("http://www.liangplus.com/v2.0/externalToken/pols?rankingTyp=17506&page=1&pageSize=5&polBkTestInfo=1&polPkStk=1")
    Call<ResponseBody> test();

    @Multipart
    @POST("http://192.168.1.164:8088/test/hello")
    Call<ResponseBody> testHello(@Part("who") String who, @Part("action") TestHelloBean action);

    @FormUrlEncoded
    @POST("http://10.191.72.156/visitor_login")
    Call<RemoteAddressResp> getSocketAddress(@Field("token") String token, @Field("module") String module);
}
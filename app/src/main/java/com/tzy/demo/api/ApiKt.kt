package com.tzy.demo.api

import com.tzy.demo.bean.JuHeResp
import com.tzy.demo.finals.Urls
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @Author tangzhaoyang
 * @Date 2023/6/29
 *
 */
interface ApiKt {

    @GET("http://v.juhe.cn/weixin/query?key=" + Urls.NewsKey)
    suspend fun getNews(@QueryMap params: Map<String, String>): JuHeResp
}
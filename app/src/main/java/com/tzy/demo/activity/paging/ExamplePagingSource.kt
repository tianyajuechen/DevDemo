package com.tzy.demo.activity.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tzy.demo.api.ApiKt
import com.tzy.demo.bean.WeChatNewsBean

/**
 * @Author tangzhaoyang
 * @Date 2023/6/29
 *
 */
class ExamplePagingSource(val backend: ApiKt) : PagingSource<Int, WeChatNewsBean>() {

    private val TAG = "ExamplePagingSource"

    override fun getRefreshKey(state: PagingState<Int, WeChatNewsBean>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WeChatNewsBean> {
        val nextPageNumber = params.key ?: 1
        val params = mapOf(
            "ps" to "10",
            "pno" to nextPageNumber.toString(),
        )
        try {
            val response = backend.getNews(params)
            val nextKey = if (response.result.totalPage > nextPageNumber) {
                nextPageNumber + 1
            } else {
                null
            }
            return LoadResult.Page(
                data = response.result.list,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            return LoadResult.Error(e)
        }
    }
}
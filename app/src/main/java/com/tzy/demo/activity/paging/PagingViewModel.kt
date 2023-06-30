package com.tzy.demo.activity.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tzy.demo.application.MyApp

/**
 * @Author tangzhaoyang
 * @Date 2023/6/29
 *
 */
class PagingViewModel : ViewModel() {
    private val api by lazy { MyApp.getInstance().mApiKt }

    val flow = Pager(PagingConfig(pageSize = 10)) {
        ExamplePagingSource(api)
    }.flow.cachedIn(viewModelScope)


}
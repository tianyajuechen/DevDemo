package com.tzy.demo.activity.paging

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tzy.demo.R
import com.tzy.demo.activity.base.BaseActivity
import com.tzy.demo.databinding.ActivityPagingTestBinding
import com.tzy.demo.widget.RecyclerViewItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @Author tangzhaoyang
 * @Date 2023/6/29
 *
 */
class PagingTestActivity : BaseActivity<ActivityPagingTestBinding>() {

    private val viewModel by viewModels<PagingViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.activity_paging_test
    }

    override fun initView() {
        val pagingAdapter = NewsAdapter(NewsComparator)
        mBinding.rv.adapter = pagingAdapter
        mBinding.rv.addItemDecoration(RecyclerViewItemDecoration(10))

        lifecycleScope.launch {
            viewModel.flow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    override fun initEvent() {

    }
}
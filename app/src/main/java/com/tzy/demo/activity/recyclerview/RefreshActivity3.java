package com.tzy.demo.activity.recyclerview;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tzy.demo.R;
import com.tzy.demo.adapter.QuickAdapter;
import com.tzy.demo.application.MyApp;
import com.tzy.demo.bean.JuHeResp;
import com.tzy.demo.bean.WeChatNewsBean;
import com.tzy.demo.widget.RecyclerViewItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RefreshActivity3 extends AppCompatActivity {

    RecyclerView mRv;

    private List<WeChatNewsBean> mNews;
    private QuickAdapter mAdapter;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh3);
        mRv = (RecyclerView) findViewById(R.id.rv);

        mPage = 1;
        mNews = new ArrayList<>();

        mAdapter = new QuickAdapter(mNews, this);
        /*mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.isFirstOnly(false);*/
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                WeChatNewsBean bean = mAdapter.getItem(position);
                Snackbar.make(view, bean.getId(), Snackbar.LENGTH_SHORT)
                        .setAction("DO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(RefreshActivity3.this, "positon:" + position, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                WeChatNewsBean bean = mAdapter.getItem(i);
                switch (view.getId()) {
                    case R.id.tv_date:
                        Snackbar.make(view, bean.getId(), Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_title:
                        Snackbar.make(view, bean.getTitle(), Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_desc:
                        Snackbar.make(view, bean.getSource(), Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }
        });
//        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
//        mAdapter.setNotDoAnimationCount(4);
        mAdapter.setPreLoadNumber(1);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getNews();
            }
        }, mRv);
        //默认第一次加载会进入回调，如果不需要可以配置：
        mAdapter.disableLoadMoreIfNotFullPage();
        /*mAdapter.setStartUpFetchPosition(0);
        mAdapter.setUpFetchEnable(true);
        mAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                mPage = 1;
                getNews();
            }
        });*/

        mRv.setLayoutManager(new LinearLayoutManager(this));
        //mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.addItemDecoration(new RecyclerViewItemDecoration(10));
        mRv.setAdapter(mAdapter);

        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                computeItemVisiblePercent();
            }
        });


        getNews();
    }

    private void getNews() {
        Map<String, String> params = new HashMap<>();
        params.put("ps", "10");
        params.put("pno", mPage + "");
        Call<JuHeResp> call = MyApp.getInstance().mApiService.getNews(params);
        call.enqueue(new Callback<JuHeResp>() {
            @Override
            public void onResponse(Call<JuHeResp> call, Response<JuHeResp> response) {
                mAdapter.loadMoreComplete();
                mAdapter.setUpFetching(false);
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.body().getResult() == null) return;
                List<WeChatNewsBean> list = response.body().getResult().getList();
                if (list != null && list.size() > 0) {
                    if (mPage < 2) {
                        mAdapter.setNewData(list);
                    } else {
                        mAdapter.addData(list);
                    }
                    mPage++;
                }

            }

            @Override
            public void onFailure(Call<JuHeResp> call, Throwable t) {
            }
        });
    }

    private void computeItemVisiblePercent() {
        if (mRv == null) return;
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRv.getLayoutManager();
        if (layoutManager == null) return;
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        Rect rvRect = new Rect();
        mRv.getGlobalVisibleRect(rvRect);
        for (int i = firstPosition; i <= lastPosition; i++) {
            View itemView = layoutManager.findViewByPosition(i);
            if (itemView == null) continue;
            Rect itemRect = new Rect();
            itemView.getGlobalVisibleRect(itemRect);
            int visibleHeight;
            if (itemRect.bottom >= rvRect.bottom) {
                visibleHeight = rvRect.bottom - itemRect.top;
            } else {
                visibleHeight = itemRect.bottom - rvRect.top;
            }
            float alpha = visibleHeight * 1f / itemView.getHeight();
            itemView.setAlpha(alpha);
        }
    }
}

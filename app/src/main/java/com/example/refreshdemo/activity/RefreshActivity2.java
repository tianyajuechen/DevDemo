package com.example.refreshdemo.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.refreshdemo.R;
import com.example.refreshdemo.adapter.NewsAdapter;
import com.example.refreshdemo.application.MyApp;
import com.example.refreshdemo.bean.NewsBean;
import com.example.refreshdemo.bean.NewsResp;
import com.example.refreshdemo.widget.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshActivity2 extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.srl)
    SwipeRefreshLayout mSrl;

    private List<NewsBean> mNews;
    private NewsAdapter mAdapter;
    private int mPage;
    private int mLastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh2);
        ButterKnife.bind(this);

        mPage = 0;
        mNews = new ArrayList<>();
        mAdapter = new NewsAdapter(this, mNews);

        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.addItemDecoration(new RecyclerViewItemDecoration(10));
        mRv.setAdapter(mAdapter);
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //if (newState == RecyclerView.SCROLL_STATE_IDLE && )
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mSrl.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mSrl.setProgressBackgroundColorSchemeResource(R.color.color_5dcd0b);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                getNews();
            }
        });

        getNews();
    }

    private void getNews() {
        Map<String, String> params = new HashMap<>();
        params.put("num", "10");
        params.put("page", mPage + "");
        Call<NewsResp> call = MyApp.getInstance().mApiService.getNews(params);
        call.enqueue(new Callback<NewsResp>() {
            @Override
            public void onResponse(Call<NewsResp> call, Response<NewsResp> response) {
                mSrl.setRefreshing(false);
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.body().getCode() != 200) {
                    return;
                }
                if (response.body().getNewslist() != null && response.body().getNewslist().size() > 0) {
                    if (mPage < 1)
                        mNews.clear();
                    mNews.addAll(response.body().getNewslist());
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                }
            }

            @Override
            public void onFailure(Call<NewsResp> call, Throwable t) {
                mSrl.setRefreshing(false);
            }
        });
    }
}

package com.tzy.demo.activity.recyclerviewrefresh;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tzy.demo.R;
import com.tzy.demo.adapter.NewsAdapter;
import com.tzy.demo.application.MyApp;
import com.tzy.demo.bean.NewsBean;
import com.tzy.demo.bean.NewsResp;
import com.tzy.demo.widget.PullToRefreshLayout;
import com.tzy.demo.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshActivity1 extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    private List<NewsBean> mNews;
    private NewsAdapter mAdapter;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh1);
        ButterKnife.bind(this);

        mPage = 0;
        mNews = new ArrayList<>();
        mAdapter = new NewsAdapter(this, mNews);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 30;
            }
        });
        mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.setAdapter(mAdapter);

        initRefreshView();
        getNews();
    }

    private void initRefreshView() {
        mRefreshLayout.setColorSchemeColors(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                getNews();
            }
        });
        mRefreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                getNews();
            }
        });
    }

    private void getNews() {
        Map<String, String> params = new HashMap<>();
        params.put("num", "10");
        params.put("page", mPage + "");
        Call<NewsResp> call = MyApp.getInstance().mApiService.getNews(params);
        call.enqueue(new Callback<NewsResp>() {
            @Override
            public void onResponse(Call<NewsResp> call, Response<NewsResp> response) {
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

            }
        });
    }
}

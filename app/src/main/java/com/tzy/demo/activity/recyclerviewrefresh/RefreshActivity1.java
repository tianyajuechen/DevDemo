package com.tzy.demo.activity.recyclerviewrefresh;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.tzy.demo.R;
import com.tzy.demo.adapter.NewsAdapter;
import com.tzy.demo.application.MyApp;
import com.tzy.demo.bean.JuHeResp;
import com.tzy.demo.bean.WeChatNewsBean;
import com.tzy.demo.widget.PullToRefreshLayout;
import com.tzy.demo.widget.RefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefreshActivity1 extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView mRv;
    @Bind(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    private List<WeChatNewsBean> mNews;
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
        params.put("ps", "10");
        params.put("pno", mPage + "");
        Call<JuHeResp> call = MyApp.getInstance().mApiService.getNews(params);
        call.enqueue(new Callback<JuHeResp>() {
            @Override
            public void onResponse(Call<JuHeResp> call, Response<JuHeResp> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.body().getResult() == null) return;
                List<WeChatNewsBean> list = response.body().getResult().getList();
                if (list != null && list.size() > 0) {
                    if (mPage < 1)
                        mNews.clear();
                    mNews.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                }
            }

            @Override
            public void onFailure(Call<JuHeResp> call, Throwable t) {

            }
        });
    }
}

package com.tzy.demo.activity.recyclerviewrefresh;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;
import com.tzy.demo.adapter.NewsAdapter;
import com.tzy.demo.application.MyApp;
import com.tzy.demo.bean.JuHeResp;
import com.tzy.demo.bean.WeChatNewsBean;
import com.tzy.demo.okhttp.MyCallback;
import com.tzy.demo.widget.RecyclerViewItemDecoration;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefreshActivity2 extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    private List<WeChatNewsBean> mNews;
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

        mSrl.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mSrl.setProgressBackgroundColorSchemeResource(R.color.color_5dcd0b);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                getNews2();
            }
        });

        getNews2();
    }

    private void getNews2() {
        Map<String, String> params = new HashMap<>();
        params.put("ps", "10");
        params.put("pno", mPage + "");
        Call<JuHeResp> call = MyApp.getInstance().mApiService.getNews(params);
        call.enqueue(new MyCallback<JuHeResp>() {
            @Override
            public void onSuccess(JuHeResp newsResp) {
                mSrl.setRefreshing(false);
                if (newsResp.getResult() == null) return;
                List<WeChatNewsBean> list = newsResp.getResult().getList();
                if (list != null && list.size() > 0) {
                    if (mPage < 1)
                        mNews.clear();
                    mNews.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mSrl.setRefreshing(false);
            }
        });
    }
    
    private void getNews3() {
        
    }
}

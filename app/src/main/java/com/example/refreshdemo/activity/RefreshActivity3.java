package com.example.refreshdemo.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.refreshdemo.R;
import com.example.refreshdemo.adapter.QuickAdapter;
import com.example.refreshdemo.application.MyApp;
import com.example.refreshdemo.bean.NewsBean;
import com.example.refreshdemo.bean.NewsResp;
import com.example.refreshdemo.widget.RecyclerViewItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RefreshActivity3 extends AppCompatActivity {

    @Bind(R.id.rv)
    RecyclerView mRv;

    private List<NewsBean> mNews;
    private QuickAdapter mAdapter;
    private int mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh3);
        ButterKnife.bind(this);

        mPage = 1;
        mNews = new ArrayList<>();

        mAdapter = new QuickAdapter(mNews, this);
        /*mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.isFirstOnly(false);*/
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, final int i) {
                NewsBean bean = mAdapter.getItem(i);
                Snackbar.make(view, bean.getCtime(), Snackbar.LENGTH_SHORT)
                        .setAction("DO", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(RefreshActivity3.this, "positon:" + i, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        mAdapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NewsBean bean = mAdapter.getItem(i);
                switch (view.getId()) {
                    case R.id.tv_date:
                        Snackbar.make(view, bean.getCtime(), Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_title:
                        Snackbar.make(view, bean.getTitle(), Snackbar.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_desc:
                        Snackbar.make(view, bean.getDescription(), Snackbar.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        mAdapter.openLoadMore(10, true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getNews();
            }
        });

        Log.e("recyclerView:", "height=" + mRv.getHeight() + "----" + mRv.getMeasuredHeight() + "----" + mRv.getMeasuredHeightAndState());
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.addItemDecoration(new RecyclerViewItemDecoration(10));
        mRv.setAdapter(mAdapter);
        /*mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //if (newState == RecyclerView.SCROLL_STATE_IDLE && )
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/

        //getNews();
    }

    private void getNews() {
        Map<String, String> params = new HashMap<>();
        params.put("num", "1");
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
                    /*if (mPage < 1) {
                        mAdapter.setNewData(response.body().getNewslist());
                    } else {
                        mAdapter.addData(response.body().getNewslist());
                    }*/
                    if (mPage < 2)
                        mNews.clear();
                    mNews.addAll(response.body().getNewslist());
                    mAdapter.notifyDataChangedAfterLoadMore(true);
                    //mAdapter.notifyDataSetChanged();
                    mPage++;
                }

            }

            @Override
            public void onFailure(Call<NewsResp> call, Throwable t) {
            }
        });
    }
}

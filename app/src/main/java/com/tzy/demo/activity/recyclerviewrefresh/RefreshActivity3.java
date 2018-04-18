package com.tzy.demo.activity.recyclerviewrefresh;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<WeChatNewsBean> mNews;
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
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mAdapter.setNotDoAnimationCount(4);
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
        getNews();
    }

    private void getNews() {
        Map<String, String> params = new HashMap<>();
        params.put("ps", "5");
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
}

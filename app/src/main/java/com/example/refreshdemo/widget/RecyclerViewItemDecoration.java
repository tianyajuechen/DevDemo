package com.example.refreshdemo.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.refreshdemo.application.MyApp;
import com.example.refreshdemo.utils.AppUtil;

/**
 * Created by YANG
 * 2016/8/4 14:29
 * RecyclerView 间隔
 */

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {
    private int mDividerHeight;//单位dp

    public RecyclerViewItemDecoration(int mDividerHeight) {
        this.mDividerHeight = mDividerHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0)
            outRect.top = AppUtil.dp2px(MyApp.getInstance().mContext, mDividerHeight);
    }
}

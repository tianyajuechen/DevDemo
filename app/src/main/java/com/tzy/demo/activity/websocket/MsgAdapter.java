package com.tzy.demo.activity.websocket;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tzy.demo.R;

import java.util.List;

/**
 * @Author tangzhaoyang
 * @Date 2021/1/29
 */
public class MsgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MsgAdapter(@Nullable List<String> data) {
        super(R.layout.item_websock_msg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_msg, item);
    }
}

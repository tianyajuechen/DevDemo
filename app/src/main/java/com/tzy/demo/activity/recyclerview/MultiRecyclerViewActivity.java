package com.tzy.demo.activity.recyclerview;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tzy.demo.R;
import com.tzy.demo.activity.BaseActivity;
import com.tzy.demo.bean.User;
import com.tzy.demo.databinding.ActivityMultiRecyclerviewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author tangzhaoyang
 * @Date 2021/4/19
 */
public class MultiRecyclerViewActivity extends BaseActivity<ActivityMultiRecyclerviewBinding> {

    private List<User> userList;

    @Override
    public int getLayoutId() {
        return R.layout.activity_multi_recyclerview;
    }

    @Override
    public void initView() {
        initData();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
//        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int i) {
//                if (i < 1) {
//                    return 3;
//                }
//                if (i == 4) {
//                    return 2;
//                }
//                return 1;
//            }
//        });
        UserAdapter adapter = new UserAdapter(userList);
        adapter.setSpanSizeLookup((gridLayoutManager, i) -> {
            if (i < 1) {
                return 3;
            }
            if (i == 4) {
                return 2;
            }
            return 1;
        });
        mBinding.rv.setLayoutManager(layoutManager);
        mBinding.rv.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    protected void initData() {
        userList = new ArrayList<User>();
        User mUser0= new User("宋江");
        userList.add(mUser0);
        User mUser1= new User("卢俊义");
        userList.add(mUser1);
        User mUser2= new User("吴用");
        userList.add(mUser2);
        User mUser3= new User("公孙胜");
        userList.add(mUser3);
        User mUser4= new User("关胜");
        userList.add(mUser4);
        User mUser5= new User("林冲");
        userList.add(mUser5);
        User mUser6= new User("秦明");
        userList.add(mUser6);
        User mUser7= new User("呼延灼");
        userList.add(mUser7);
        User mUser8= new User("花荣");
        userList.add(mUser8);
        User mUser9= new User("柴进");
        userList.add(mUser9);
        User mUser10= new User("李应");
        userList.add(mUser10);
        User mUser11= new User("鲁智深");
        userList.add(mUser11);
        User mUser12= new User("武松");
        userList.add(mUser12);
    }

    public static class UserAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

        public UserAdapter(@Nullable List<User> data) {
            super(R.layout.item_user, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, User item) {
            helper.setText(R.id.tv_name, item.getName());
        }
    }
}

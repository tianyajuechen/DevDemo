package com.tzy.demo.presenter;

import androidx.databinding.ObservableFloat;
import android.view.View;
import com.tzy.demo.bean.User;
import com.tzy.demo.bean.User2;

/**
 * Created by YANG
 * 2016/11/10 14:52
 */
public class Presenter1 {

    User user;
    User2 user2;

    public Presenter1(User user) {
        this.user = user;
    }

    public void setUser2(User2 user2) {
        this.user2 = user2;
    }

    public void changeName(View v) {
        if (user == null) return;
        user.setName("李四");
        user.setHeight(new ObservableFloat(1.66f));
    }

    public void changeAge(View v) {
        if (user == null) return;
        user.setName("王麻子");
        user.setAge(10);
        user.setHeight(new ObservableFloat(1.61f));
    }

    public void changeHeight(User2 user2) {
        user2.name.set("赵云");
    }

}

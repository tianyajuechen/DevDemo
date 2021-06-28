package com.tzy.demo.activity.mvvm;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.tzy.demo.R;
import com.tzy.demo.bean.User;
import com.tzy.demo.bean.User2;
import com.tzy.demo.databinding.Test;
import com.tzy.demo.presenter.Presenter1;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Test binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        User user = new User("张三", 30, 1.7f);
        User2 user2 = new User2("用户2");
//        User user1 = new User();
        binding.setUser(user);
        binding.setUser2(user2);


        Presenter1 p = new Presenter1(user);
        p.setUser2(user2);
        binding.setPresenter(p);
    }
}

package com.tzy.demo.activity.mvvm;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.tzy.demo.R;
import com.tzy.demo.bean.User;
import com.tzy.demo.databinding.TestBinding;
import com.tzy.demo.presenter.Presenter1;

public class DataBindingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        User user = new User("Tang", "Zhaoyang");
        binding.setUser(user);
        Presenter1 p = new Presenter1();
        binding.setPresenter(p);
    }
}

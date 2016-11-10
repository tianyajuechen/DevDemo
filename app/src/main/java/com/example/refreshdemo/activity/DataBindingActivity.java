package com.example.refreshdemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.refreshdemo.R;
import com.example.refreshdemo.bean.User;
import com.example.refreshdemo.databinding.TestBinding;
import com.example.refreshdemo.presenter.Presenter1;

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

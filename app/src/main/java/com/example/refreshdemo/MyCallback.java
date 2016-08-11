package com.example.refreshdemo;

import android.widget.Toast;

import com.example.refreshdemo.application.MyApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YANG on 2016/8/10.
 */
public abstract class MyCallback<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onSuccess(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailure(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable t);
}

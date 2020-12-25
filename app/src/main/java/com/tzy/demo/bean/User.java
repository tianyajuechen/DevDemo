package com.tzy.demo.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableFloat;
import com.tzy.demo.BR;

/**
 * Created by YANG
 * 2016/11/10 14:22
 */

public class User extends BaseObservable {
    private String name;
    private int age;
    private ObservableFloat height;

    public User() {
    }

    public User(String name, int age, float height) {
        this.name = name;
        this.age = age;
        this.height = new ObservableFloat(height);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyChange();
    }

    public ObservableFloat getHeight() {
        return height;
    }

    public void setHeight(ObservableFloat height) {
        this.height = height;
    }
}
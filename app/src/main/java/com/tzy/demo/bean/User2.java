package com.tzy.demo.bean;

import androidx.databinding.ObservableField;

/**
 * Created by YANG
 * 2016/11/10 14:22
 */

public class User2 {
    public final ObservableField<String> name;

    public User2(String name) {
        this.name = new ObservableField<>(name);
    }
}
package com.tzy.demo.bean;

import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PackageInfoBean {
    private Drawable icon;
    private String name;
    private String packageName;
    private String launcher;

    public PackageInfoBean() {
    }

    public PackageInfoBean(String name, Drawable icon, String packageName) {
        this.icon = icon;
        this.name = name;
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getLauncher() {
        return launcher;
    }

    public void setLauncher(String launcher) {
        this.launcher = launcher;
        try {
            Process process = Runtime.getRuntime().exec("pm list package");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

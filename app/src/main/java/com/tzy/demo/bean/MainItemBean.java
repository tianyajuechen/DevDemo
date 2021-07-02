package com.tzy.demo.bean;

/**
 * @Author tangzhaoyang
 * @Date 2021/6/30
 */
public class MainItemBean {
    private String name;
    private Class<?> target;

    public MainItemBean(String name, Class<?> target) {
        this.name = name;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getTarget() {
        return target;
    }

    public void setTarget(Class<?> target) {
        this.target = target;
    }
}

package com.tzy.demo.activity.task;

/**
 * @Author tangzhaoyang
 * @Date 2021/1/28
 */
public class TaskB1Activity extends TaskA1Activity {
    @Override
    protected void init() {
        setText(TaskB2Activity.class);
    }
}

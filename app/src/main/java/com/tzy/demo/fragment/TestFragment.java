package com.tzy.demo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzy.demo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {


    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showValue("onCreateView");
        return inflater.inflate(R.layout.test_fragment_layout, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        showValue("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        showValue("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        showValue("onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showValue("onDestroy");
    }

    private void showValue(String tag) {
        Log.e("TestFragment", "--------------------" + tag + "--------------------");
        Log.e(tag, "isAdded=" + isAdded());
        Log.e(tag, "isDetached=" + isDetached());
        Log.e(tag, "isHidden=" + isHidden());
        Log.e(tag, "isRemoving=" + isRemoving());
        Log.e(tag, "isVisible=" + isVisible() + "\n");
    }

}

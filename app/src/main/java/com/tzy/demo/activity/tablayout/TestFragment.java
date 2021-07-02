package com.tzy.demo.activity.tablayout;


import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzy.demo.R;
import com.tzy.demo.databinding.TestFragmentLayoutBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    public static final String TITLE = "TITLE";

    private TestFragmentLayoutBinding mBinding;

    public static TestFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(TITLE, title);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        showValue("onCreateView");
        mBinding = DataBindingUtil.inflate(inflater, R.layout.test_fragment_layout, container, false);
        String title = getArguments() == null ? "" : getArguments().getString(TITLE);
        if (!TextUtils.isEmpty(title)) {
            mBinding.tv.setText(title);
        }

        return mBinding.getRoot();
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

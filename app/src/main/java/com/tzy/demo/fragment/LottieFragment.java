package com.tzy.demo.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.airbnb.lottie.LottieAnimationView;
import com.tzy.demo.R;

public class LottieFragment extends Fragment {

    LottieAnimationView mLottieView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mFolder;
    private String mJson;

    public LottieFragment() {
        // Required empty public constructor
    }

    public static LottieFragment newInstance(String param1, String param2) {
        LottieFragment fragment = new LottieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFolder = getArguments().getString(ARG_PARAM1);
            mJson = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lottie, container, false);
        mLottieView = (LottieAnimationView) view.findViewById(R.id.lottie_view);
//        mLottieView.setImageAssetsFolder(mFolder);
//        mLottieView.setAnimation(mJson);
        mLottieView.setAnimation("like.json");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
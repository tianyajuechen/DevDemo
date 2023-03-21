package com.tzy.demo.activity.animator;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.opensource.svgaplayer.utils.log.SVGALogger;
import com.tzy.demo.R;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class SVGAFragment extends Fragment {


    private static final String PATH = "param1";
    private static final String TAG = "SVGAFragment";

    private SVGAImageView mSVGAView;
    private SVGAParser mParser;
    private String mFilePath;

    public SVGAFragment() {
        // Required empty public constructor
    }

    public static SVGAFragment newInstance(String path) {
        SVGAFragment fragment = new SVGAFragment();
        Bundle args = new Bundle();
        args.putString(PATH, path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFilePath = getArguments().getString(PATH);
        }
        SVGALogger.INSTANCE.setLogEnabled(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_svga, container, false);
        mSVGAView = view.findViewById(R.id.svga);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mParser = new SVGAParser(requireContext());
        Log.e(TAG, mFilePath);
        mParser.decodeFromAssets(mFilePath, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                Log.e(TAG, "complete:" + svgaVideoEntity.getFrames());
            }

            @Override
            public void onError() {
                Log.e(TAG, "error");
            }
        }, new SVGAParser.PlayCallback() {
            @Override
            public void onPlay(@NotNull List<? extends File> list) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
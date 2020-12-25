package com.tzy.demo.activity.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends Activity {

    @BindView(R.id.surfaceview)
    SurfaceView surfaceview;
    @BindView(R.id.bt_take_photo)
    Button btTakePhoto;

    private Camera mCamera;
    private Camera.Parameters mParameters;
    private SurfaceHolder mSurfaceHolder;

    private int mFrontCameraId;
    private int mBackCameraId;
    private int mFrontCameraOrientation;
    private int mBackCameraOrientation;
    private int mDisplayRotaion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        initCameraInfo();

        surfaceview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                openCamera(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                startPreview(holder);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCamera.stopPreview();
                mCamera.release();
            }
        });

        btTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        File file = new File(getExternalCacheDir(), "test.jpg");
                        if (file == null) return;
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(data);
                            fileOutputStream.close();
                            mCamera.startPreview();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initCameraInfo() {
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);

            //后置摄像头
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                mBackCameraId = i;
                mBackCameraOrientation = cameraInfo.orientation;
            }

            //前摄像头
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                mFrontCameraId = i;
                mFrontCameraOrientation = cameraInfo.orientation;
            }
        }

    }

    private void openCamera(SurfaceHolder holder) {
        try {
            mCamera = Camera.open(mBackCameraId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPreview(SurfaceHolder holder) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(mBackCameraId, cameraInfo);
        //旋转偏移
        int cameraRotationOffset = cameraInfo.orientation;
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);

        int rotation = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        int degress = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degress = 0;
                break;
            case Surface.ROTATION_90:
                degress = 90;
                break;
            case Surface.ROTATION_180:
                degress = 180;
                break;
            case Surface.ROTATION_270:
                degress = 270;
                break;
        }

        //根据前置与后置摄像头的不同，设置预览方向，否则会发生预览图像倒过来的情况。
        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mDisplayRotaion = (cameraRotationOffset + degress) % 360;
            mDisplayRotaion = (360 - mDisplayRotaion) % 360;
        } else {
            mDisplayRotaion = (cameraRotationOffset - degress + 360) % 360;
        }
        mCamera.setDisplayOrientation(mDisplayRotaion);

//        parameters.setPreviewSize(4160, 3120);
        parameters.setPictureSize(4160, 3120);

        mCamera.setParameters(parameters);
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
    }
}

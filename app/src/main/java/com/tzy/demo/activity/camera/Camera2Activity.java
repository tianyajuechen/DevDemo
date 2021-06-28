package com.tzy.demo.activity.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import android.view.TextureView;
import android.widget.Button;


import com.tzy.demo.R;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2Activity extends Activity {

    TextureView display;
    Button bt;


    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private CameraCharacteristics mFrontCameraCharacterristics;
    private CameraCharacteristics mBackCameraCharacterristics;
    private ImageReader mImageReader;

    private String mFrontCameraId;
    private String mBackCameraId;
    private int mFrontCameraOrientation;
    private int mBackCameraOrientation;
    private int mDisplayRotaion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera2);
        display = (TextureView) findViewById(R.id.display);
        bt = (Button) findViewById(R.id.bt);

        initCameraInfo();
    }

    private void initCameraInfo() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] ids = mCameraManager.getCameraIdList();
            for (String id : ids) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(id);
                int orientation = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (orientation == CameraCharacteristics.LENS_FACING_FRONT) {
                    mFrontCameraId = id;
                    mFrontCameraOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    mFrontCameraCharacterristics = characteristics;
                }
                if (orientation == CameraCharacteristics.LENS_FACING_BACK) {
                    mBackCameraId = id;
                    mBackCameraOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
                    mBackCameraCharacterristics = characteristics;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            mCameraManager.openCamera(mBackCameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    mCameraDevice = camera;
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    mCameraDevice.close();
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    mCameraDevice.close();
                }
            }, new Handler(Looper.getMainLooper()));
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        if (mCameraDevice == null) {
            return;
        }
//        CaptureRequest.Builder previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//        SurfaceTexture texture = display.getSurfaceTexture();
////        texture.setDefaultBufferSize(4160, 3120);
//        Surface surface = new Surface(texture);
//        previewRequestBuilder.addTarget(surface);
//
//
//        mImageReader = ImageReader.newInstance(4160, 3120, ImageFormat.JPEG, 1);
//        mCameraDevice.createCaptureSession(Arrays.asList(surface, mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
//            @Override
//            public void onConfigured(@NonNull CameraCaptureSession session) {
////                session.setRepeatingRequest()
//            }
//
//            @Override
//            public void onConfigureFailed(@NonNull CameraCaptureSession session) {
//
//            }
//        }, );

    }
}

package com.tzy.demo.activity.takephoto;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.tzy.demo.R;

import java.io.*;

public class TakePhotoActivity extends AppCompatActivity {

    ImageView iv;
    Button btCamera;
    Button btGallery;
    TextView tv;

    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;
    public static final int CROP_REQUEST_CODE = 3;

    private Uri mPhotoUri;//调用相机拍照后图片的uri


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        iv = (ImageView) findViewById(R.id.iv);
        btCamera = (Button) findViewById(R.id.bt_camera);
        btGallery = (Button) findViewById(R.id.bt_gallery);
        tv = (TextView) findViewById(R.id.tv);


        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setPhotoPath()) return;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        btGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (mPhotoUri != null) {
                        zoomPhoto(mPhotoUri);
                    }
                    break;

                case GALLERY_REQUEST_CODE:
                    if (data != null) {
                        zoomPhoto(data.getData());
                    }

                    break;

                case CROP_REQUEST_CODE:
                    setImageToView(data);
                    break;
            }
        }
    }

    public String getFilePath(Uri uri) {
        if (null == uri) return null;
        String scheme = uri.getScheme();
        String path = null;
        if (scheme == null)
            path = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null,
                    null,
                    null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        path = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return path;
    }

    private boolean setPhotoPath() {
        File cacheDir = this.getExternalCacheDir();
        if (cacheDir != null && cacheDir.isDirectory()) {
            mPhotoUri = Uri.fromFile(new File(cacheDir, "head_img.jpg"));
            // 指定照片保存路径 缓存目录，image.jpg为一个临时文件，每次拍照后这个图片都会被替换
            return true;
        } else {
            Toast.makeText(this, "获取缓存目录失败,请确认是否已授予Sd卡读写权限！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void zoomPhoto(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);

//        mPhotoUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    private void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            iv.setImageBitmap(photo);
        }
    }

    private Uri saveBitmap(Bitmap bm) {
        File temDir = new File(Environment.getExternalStorageDirectory() + "aa.jpg");
        if (!temDir.exists()) {
            temDir.mkdir();
        }
        File img = new File(temDir.getAbsolutePath() + "landscape.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bm = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}

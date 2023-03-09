package com.tzy.demo.activity.largeimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import com.tzy.demo.R;
import com.tzy.demo.utils.DisplayUtil;

public class LargeImageActivity extends AppCompatActivity {

    ImageView ivTest1;
    ImageView ivTest2;
    FrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);

        ImageFilterView imageFilterView = findViewById(R.id.iv);
        imageFilterView.setImageResource(R.mipmap.landscape);

        fl = findViewById(R.id.fl);
        ivTest1 = findViewById(R.id.ivTest1);
        ivTest2 = findViewById(R.id.ivTest2);

        ivTest1.postDelayed(() -> {
            int size = DisplayUtil.dp2px(90);
            Bitmap bitmap = null;
            //                bitmap = BitmapFactory.decodeStream(getResources().getAssets().open("b.jpg"));
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_girl_2);
            Matrix matrix = new Matrix();
            matrix.setRotate(-5, bitmap.getWidth() / 2f, bitmap.getHeight() / 2f);
            Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
            bitmap2.setHasAlpha(true);
            ivTest1.setImageBitmap(bitmap2);
            showShot();
            /*Glide.with(this).load(R.drawable.ic_girl_1).asBitmap().centerCrop().listener(new RequestListener<Integer, Bitmap>() {
                @Override
                public boolean onException(Exception e, Integer model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Integer model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate(-5, size / 2f, size / 2f);
                    Bitmap bitmap = Bitmap.createBitmap(resource, 0, 0, size, size, matrix, false);
                    ivTest1.setImageBitmap(bitmap);
                    showShot();
                    return false;
                }
            }).into(size, size);*/
        }, 1000);
    }

    private void showShot() {
        Bitmap bitmap = Bitmap.createBitmap(fl.getWidth(), fl.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        fl.draw(canvas);

        /*fl.setDrawingCacheEnabled(true);
        fl.buildDrawingCache();
        Bitmap bitmap = fl.getDrawingCache();*/

        ivTest2.setImageBitmap(bitmap);
    }
}
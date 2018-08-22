package com.tzy.demo.activity.netrequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.tzy.demo.R;
import com.tzy.demo.application.MyApp;
import com.tzy.demo.bean.TestHelloBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestRequestActivity extends Activity {

    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.bt)
    Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_request);
        ButterKnife.bind(this);

        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
    }

    private void request() {
        TestHelloBean bean = new TestHelloBean();
        Call<ResponseBody> call = MyApp.getInstance().mApiService.testHello("wo", bean);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (!response.isSuccessful()) {
                        mEt.setText("状态码：" + response.code() + "\n" + "errorBody：" + response.errorBody().string());
                        return;
                    }
                    if (response.body() != null) {
                        mEt.setText(response.body().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mEt.setText(t.toString());
            }
        });
    }
}

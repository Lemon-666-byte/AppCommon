package com.ui.main.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.constant.NetConfig;
import com.base.BaseActivity;
import com.common.R;
import com.ui.main.bean.RespUpdate;
import com.utils.GsonUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 */
public class VersionActivity extends BaseActivity {

    @BindView(R.id.tvVersionCode)
    TextView tvVersionCode;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvTest)
    TextView tvTest;
    @BindView(R.id.iv_image)
    ImageView imageView;
    private OkHttpClient mOkHttpClient;


    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_version;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {
        loadData();
    }

    /**
     * 获取列表数据
     */
    public void loadData() {
        showLoading(true);
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
//                .addInterceptor(new LoggerInterceptor("TAG"))
                    .build();
        }
        Request request = new Request.Builder().url(NetConfig.Url.UPLOAD_URL).build();
//        LogUtil.e("http", request.url().url().toString());
        //开启异步线程访问网络
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                hideLoading();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                hideLoading();
                if (response.isSuccessful()) {
                    final String bj = response.body().string();
                    VersionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RespUpdate respUpdate = GsonUtils.getInstance().toObject(bj, RespUpdate.class);
                            if (respUpdate != null) {
                                tvVersionCode.setText("当前版本为：" + respUpdate.getVerName());
                                tvContent.setText(respUpdate.getInfoByUpdateContent());
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addListeners() {

    }

}

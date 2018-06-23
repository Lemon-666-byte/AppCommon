package com.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.common.R;
import com.ui.main.bean.RespUpdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName:UpdateManager
 * @Description:版本更新工具类
 */
public class UpdateManager {
    private ProgressDialog pBar;
    private static int newVerCode;
    private static Dialog dialog;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (!NetworkUtils.isConnected()) {
                        ToastUtils.showShort("网络不给力，请检查网络");
                        pBar.cancel();
                        break;
                    }
                    pBar.setProgress(msg.arg1);
                    break;
            }
        }
    };

    /**
     * 检查是否需要更新
     */
    public Dialog checkIsNeedUpdate(RespUpdate respUpdate, boolean update) {
        if (respUpdate == null)
            return null;
        if (!StringUtils.isEmpty(respUpdate.getVerCode())) {
            newVerCode = Integer.valueOf(respUpdate.getVerCode());
        }
        int currentVersionCode = AppUtils.getAppVersionCode();
        // 服务器code>当前code
        if (newVerCode > currentVersionCode) {
            try {
                String currName = Utils.getApp().getPackageName() + "_" + (newVerCode - 1) + ".apk";
                File file = new File(Environment.getExternalStorageDirectory(), currName);
                if (file.exists())
                    file.delete();

                showUpdateDialog(respUpdate.getUpdateTitle()
                        , respUpdate.getInfoByUpdateContent()
                        , respUpdate.getDownurl());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            if (update) {
                ToastUtils.showShort("已是最新版本");
            }
        }
        return dialog;
    }

    /**
     * 显示更新对话框
     *
     * @param title 标题
     * @param msg   内容
     * @param url   更新地址 包括文件名
     */
    private void showUpdateDialog(final String title, final String msg, final String url) {
        final Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                View view = View.inflate(topActivity, R.layout.main_dialog_update, null);
                if (dialog == null) {
                    dialog = new Dialog(topActivity, R.style.DialogNoTitle);
                }
                dialog.setCancelable(false);
                dialog.setContentView(view);
                TextView mTvUpdateTitle = view.findViewById(R.id.mTvUpdateTitle);
                mTvUpdateTitle.setText(title);
                TextView mTvUpdateMsg = view.findViewById(R.id.mTvUpdateMsg);
                mTvUpdateMsg.setText(msg);
                Button positiveBtn = view.findViewById(R.id.mBtnUpdateOk);
                Button negativeBtn = view.findViewById(R.id.mBtnUpdateCancle);
                positiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pBar = new ProgressDialog(topActivity);
                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pBar.setTitle("软件更新");
                        pBar.setMessage("正在下载新版本，请稍候…");
                        pBar.setIndeterminate(false);
                        pBar.setCancelable(false);
                        downFile(url);
                        dialog.dismiss();
                    }
                });
                negativeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AppUtils.exitApp();
                    }
                });
                if (!dialog.isShowing()) {
                    dialog.show();
                }
            }
        });
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     */
    private void downFile(final String fileUrl) {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.showShort("网络不给力，请检查网络");
            pBar.cancel();
            return;
        }

        pBar.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    // 创建按一个URL实例
                    URL url = new URL(fileUrl);
                    // 创建一个HttpURLConnection的链接对象
                    HttpURLConnection httpConn = (HttpURLConnection) url
                            .openConnection();
                    // 获取所下载文件的InputStream对象
                    InputStream inputStream = httpConn.getInputStream();
                    int length = httpConn.getContentLength();
                    FileOutputStream fileOutputStream = null;
                    if (inputStream != null) {
                        File file = new File(Environment.getExternalStorageDirectory(), getUpdateName());
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[1024 * 128];
                        int ch = -1;
                        int count = 0;
                        while ((ch = inputStream.read(buf)) != -1) {
                            count += ch;
                            fileOutputStream.write(buf, 0, ch);
                            Message msg = new Message();
                            msg.what = 1;
                            msg.arg1 = (count * 100 / length);
                            handler.sendMessage(msg);
                            if (length > 0) {

                            }
                        }
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    down();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();

    }

    /**
     * 下载完毕
     */
    private void down() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                pBar.cancel();
                update();
            }
        });
    }

    /**
     * 安装更新包
     */
    private void update() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File file = new File(Environment.getExternalStorageDirectory(), getUpdateName());
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Utils.getApp().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取更新名称
     *
     * @return
     */
    private String getUpdateName() {
        return AppUtils.getAppPackageName() + "_" + newVerCode + ".apk";
    }
}

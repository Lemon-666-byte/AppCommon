package com.net;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.common.R;
import com.google.gson.JsonParseException;
import com.net.exception.NoDataExceptionException;
import com.net.exception.ServerErrorException;
import com.net.exception.ServerResponseException;
import com.net.exception.SessionKeyInvalidException;
import com.ui.main.activity.LoginActivity;
import com.utils.biz.Biz;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by yang on 2017/4/18.
 */
public abstract class DefaultObserver<T> implements Observer<T> {
    private static AlertDialog sessionKeyInvalidDialog;

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
            onFail(e);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
            onFail(e);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
            onFail(e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
            onFail(e);
        } else if (e instanceof ServerResponseException) {
            onFail(e.getMessage());
        } else if (e instanceof NoDataExceptionException) {
            onSuccess(null);
        } else if (e instanceof ServerErrorException) {
            onFail(e);
            if (!TextUtils.isEmpty(e.getMessage())) {
                ToastUtils.showShort(e.getMessage());
            }
        } else if (e instanceof SessionKeyInvalidException) {
            showSessionKeyInvalidDialog();
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFinish();
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param t 服务器返回的数据
     */
    abstract public void onSuccess(T t);

    abstract public void onFail(Throwable e);

    /**
     * 服务器返回数据，错误数据
     */
    public void onFail(String message) {
        ToastUtils.showShort(message);
    }

    public void onFinish() {

    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showShort(R.string.connect_error);
                break;
            case CONNECT_TIMEOUT:
                ToastUtils.showShort(R.string.connect_timeout);
                break;
            case BAD_NETWORK:
                ToastUtils.showShort(R.string.bad_network);
                break;
            case PARSE_ERROR:
                ToastUtils.showShort(R.string.parse_error);
                break;
            case UNKNOWN_ERROR:
            default:
                ToastUtils.showShort(R.string.unknown_error);
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }

    /**
     * SessionKey失效
     */
    private void showSessionKeyInvalidDialog() {
        final Activity topActivity = ActivityUtils.getTopActivity();
        if (topActivity == null) return;
        AlertDialog.Builder builder = new AlertDialog.Builder(topActivity);  //先得到构造器
        builder.setTitle("提示");
        builder.setMessage("您的账号登录失效或在其他客户端登录,是否重新登录？"); //设置内容
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Biz.getInstance().setSessionKey("");
                Biz.getInstance().setPassword("");
                Intent intent = new Intent();
                intent.setClass(topActivity, LoginActivity.class);
                //需要一次清空基于此类上所有的activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                topActivity.startActivity(intent);
            }
        });
        if (sessionKeyInvalidDialog == null) {
            sessionKeyInvalidDialog = builder.create();
        }
        if (!sessionKeyInvalidDialog.isShowing()) {
            sessionKeyInvalidDialog.show();
        }
    }
}

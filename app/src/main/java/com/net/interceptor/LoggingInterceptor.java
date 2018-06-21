package com.net.interceptor;

import android.annotation.SuppressLint;

import com.utils.LogUtils;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by yang on 2018/4/20.
 */

public class LoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间

        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);

        Charset charset = UTF8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        if (isPlaintext(buffer)) {
            LogUtils.e("request", String.format("url:%s body:%s",
                    request.url(), buffer.readString(charset)));
        } else {
            LogUtils.e("request", String.format("url:%s", request.url()));
        }

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();//收到响应的时间

        /*
           这里不能直接使用response.body().string()的方式输出日志
           因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出
           一个新的response给应用层处理
         */
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        LogUtils.e("result",
                String.format("url: %s time:%.1fms %n result：%s",
                        response.request().url(),
                        (t2 - t1) / 1e6d,
                        responseBody.string()));
//        com.blankj.utilcode.util.LogUtils.json(com.blankj.utilcode.util.LogUtils.E, responseBody.string());
        return response;
    }

    private boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            // Truncated UTF-8 sequence.
            return false;
        }
    }

}

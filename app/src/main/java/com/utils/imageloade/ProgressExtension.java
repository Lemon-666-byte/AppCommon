package com.utils.imageloade;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.utils.LogUtils;
import com.utils.imageloade.okhttp.ProgressInterceptor;

@GlideExtension
public class ProgressExtension {

    private ProgressExtension() {
    }

    @SuppressLint("CheckResult")
    @GlideType(Drawable.class)
    public static void addProgress(RequestBuilder<Drawable> requestBuilder) {

        requestBuilder.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                ProgressInterceptor.removeListener(model.toString());
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ProgressInterceptor.removeListener(model.toString());
                return false;
            }
        });

    }

    @SuppressLint("CheckResult")
    @GlideOption
    public static void roundedCorners(RequestOptions options, int dpCorners) {
        options.transforms(new CenterCrop(), new RoundedCorners(SizeUtils.dp2px(dpCorners)));
    }


}

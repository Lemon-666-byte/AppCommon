package com.ui.main.photobrowse;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.base.BaseFragment;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.constant.Config;
import com.github.chrisbanes.photoview.PhotoView;
import com.hxky.common.R;
import com.ui.main.bean.UploadPicture;
import com.utils.LogUtils;
import com.utils.imageloade.GlideApp;

import butterknife.BindView;

/**
 * 图片的缩放
 *
 * @author Administrator
 */
public class PhotoViewFragment extends BaseFragment {

    /**
     * 缩放控件
     */
    @BindView(R.id.photoView)
    public PhotoView photoView;
    /**
     * 进度条
     */
    @BindView(R.id.pbLoading)
    public ProgressBar progressbar;

    /**
     * 图片对象
     */
    private UploadPicture picture;
    /**
     * 图片路径
     */
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.main_adapter_viewpage_photoview_item;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(Config.Extras.picture)) {
                picture = (UploadPicture) bundle.getSerializable(Config.Extras.picture);
                assert picture != null;
                url = picture.getOriginalUrl();
                GlideApp.with(this)
                        .load(url)
                        .fitCenter()
                        .transform(new RoundedCorners(SizeUtils.dp2px(2)))
                        .fallback(R.mipmap.main_icon_img_default)
                        .error(R.mipmap.main_icon_img_default)
                        .listener(new ImageRequestListener()).into(photoView);
            }
        }
    }

    @Override
    public void addListeners() {
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentResult(Config.ResultCode.resultCode1, null);
                getActivity().finish();
            }
        });
    }

    private class ImageRequestListener implements RequestListener<Drawable> {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            progressbar.setVisibility(View.GONE);
            return false;
        }
//
//        @Override
//        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
//            progressbar.setVisibility(View.GONE);
//            return false;
//        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            progressbar.setVisibility(View.GONE);
            return false;
        }
    }
}


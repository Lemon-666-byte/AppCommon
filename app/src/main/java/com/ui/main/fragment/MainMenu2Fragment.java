package com.ui.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseFragment;
import com.common.R;
import com.constant.Config;
import com.constant.PathConfig;
import com.ui.main.bean.UploadPicture;
import com.utils.LogUtils;
import com.utils.UploadMyAppUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 车源信息列表
 */
public class MainMenu2Fragment extends BaseFragment {

    @BindView(R.id.btn_enter)
    Button btnEnter;
    @BindView(R.id.btn_image)
    Button btnImage;
    @BindView(R.id.btn_mvp)
    Button btnMvp;
    @BindView(R.id.btn_glide)
    Button btnGlide;
    String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528909045156&di=d34ddb503de5801073c4f4373338578f&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c03957c1a2aa0000018c1ba6c35a.jpg";
    String url2 = "http://img.zcool.cn/community/0142135541fe180000019ae9b8cf86.jpg@1280w_1l_2o_100sh.png";
    String download_url = "http://47.93.80.92:8088/Android/HXERP.apk";

    public static MainMenu2Fragment newInstance() {
        return new MainMenu2Fragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            UploadMyAppUtils.getInstance().update(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_menu2;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {

    }

    @Override
    public void addListeners() {
        btnMvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UploadPicture> list = new ArrayList<>();
                UploadPicture uploadPicture1 = new UploadPicture();
                uploadPicture1.setOriginalUrl(url1);
                UploadPicture uploadPicture2 = new UploadPicture();
                uploadPicture2.setOriginalUrl(url2);
                list.add(uploadPicture1);
                list.add(uploadPicture2);
                /**
                 * @see com.ui.main.photobrowse.BasePhotoViewActivity
                 */
                ARouter.getInstance().build(PathConfig.BasePhotoViewActivity)
                        .withInt(Config.Extras.position, 1)
                        .withSerializable(Config.Extras.pictureList, (Serializable) list)
                        .navigation(getActivity(), Config.ResultCode.resultCode1);

//                Bundle bundle = new Bundle();
//                bundle.putInt(Config.Extras.position, 0);
//                bundle.putSerializable(Config.Extras.pictureList, (Serializable) list);
            }
        });

        btnGlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @OnClick(R.id.btn_enter)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putInt(Config.Extras.position, 9);
//        enterPage(VersionActivity.class, bundle);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        LogUtils.e("onFragmentResult requestCode->" + requestCode);
        LogUtils.e("onFragmentResult resultCode->" + resultCode);
    }
}
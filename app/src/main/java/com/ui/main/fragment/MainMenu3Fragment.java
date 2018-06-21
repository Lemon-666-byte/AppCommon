package com.ui.main.fragment;

import android.os.Bundle;

import com.base.BaseFragment;
import com.hxky.common.R;
import com.utils.UploadMyAppUtils;

/**
 * 车源信息列表
 */
public class MainMenu3Fragment extends BaseFragment {


    public static MainMenu3Fragment newInstance() {
        return new MainMenu3Fragment();
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
        return R.layout.main_fragment_menu3;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {
        if (bundle != null) {

        }
    }


    @Override
    public void addListeners() {

    }
}
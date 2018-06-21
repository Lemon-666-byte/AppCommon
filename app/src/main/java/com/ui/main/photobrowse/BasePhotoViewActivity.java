package com.ui.main.photobrowse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.BaseActivity;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.constant.Config;
import com.hxky.common.R;
import com.ui.main.adapter.PhotoViewAdapter;
import com.ui.main.bean.UploadPicture;
import com.widget.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @Description: 查看大图
 */
public class BasePhotoViewActivity extends BaseActivity {
    /**
     * 查看大图
     */
    @BindView(R.id.viewPager)
    public HackyViewPager viewPager;
    /**
     * 返回按钮
     */
    @BindView(R.id.ivBack)
    public ImageView ivBack;
    /**
     * 标题
     */
    @BindView(R.id.tvTitle)
    public TextView tvTitle;

    private List<UploadPicture> pictureLists;

    private int pagerPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_photoview;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(Config.Extras.pictureList))
                pictureLists = (List<UploadPicture>) bundle.getSerializable(Config.Extras.pictureList);
            pagerPosition = bundle.getInt(Config.Extras.position, 0);

            if (pictureLists == null) {
                ToastUtils.showShort("无法查看大图");
                finish();
            }
            if (StringUtils.isEmpty(pictureLists.get(pagerPosition).getPhotoName())) {
                tvTitle.setText((pagerPosition + 1) + "/" + (pictureLists.size()));
            } else {
                tvTitle.setText(pictureLists.get(pagerPosition).getPhotoName() + " " + (pagerPosition + 1) + "/" + (pictureLists.size()));
            }
            List<Fragment> fragments = createFragments();
            PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(getSupportFragmentManager(), fragments);
            viewPager.setAdapter(photoViewAdapter);
            viewPager.setCurrentItem(pagerPosition);
        }
    }

    @Override
    public void addListeners() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerPosition = position;
                if (StringUtils.isEmpty(pictureLists.get(pagerPosition).getPhotoName())) {
                    tvTitle.setText((pagerPosition + 1) + "/" + (pictureLists.size()));
                } else {
                    tvTitle.setText(pictureLists.get(pagerPosition).getPhotoName() + " " + (pagerPosition + 1) + "/" + (pictureLists.size()));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 获取fragment
     *
     * @return
     */
    private List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < pictureLists.size(); i++) {
            PhotoViewFragment photoViewFragment = new PhotoViewFragment();
            Bundle initValues = new Bundle();
            initValues.putSerializable(Config.Extras.picture, pictureLists.get(i));
            photoViewFragment.setArguments(initValues);
            fragments.add(photoViewFragment);
        }
        return fragments;
    }

}

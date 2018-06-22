package com.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseActivity;
import com.blankj.utilcode.util.ToastUtils;
import com.constant.PathConfig;
import com.hxky.common.R;
import com.ui.main.fragment.MainMenu1Fragment;
import com.ui.main.fragment.MainMenu2Fragment;
import com.ui.main.fragment.MainMenu3Fragment;
import com.utils.LogUtils;
import com.utils.UploadMyAppUtils;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

@Route(path = PathConfig.MainActivity)
public class MainActivity extends BaseActivity {

    /**
     * 记录连续按返回键的间隔时间
     */
    private long mExitTime;

    @BindView(R.id.tab)
    RadioGroup tab;

    private ISupportFragment[] mFragments = new ISupportFragment[3];

    private int currentIndex;


    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {
        ISupportFragment homeFragment = findFragment(MainMenu1Fragment.class);

        if (homeFragment == null) {
            mFragments[0] = MainMenu1Fragment.newInstance();
            mFragments[1] = MainMenu2Fragment.newInstance();
            mFragments[2] = MainMenu3Fragment.newInstance();
            loadMultipleRootFragment(R.id.flContent, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = homeFragment;
            mFragments[1] = findFragment(MainMenu2Fragment.class);
            mFragments[2] = findFragment(MainMenu3Fragment.class);
        }
        //更新
        UploadMyAppUtils.getInstance().update(false);
    }

    @Override
    public void addListeners() {
        tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rbMenu1:
                        showHideFragment(mFragments[0], mFragments[currentIndex]);
                        currentIndex = 0;
                        break;
                    case R.id.rbMenu2:
                        showHideFragment(mFragments[1], mFragments[currentIndex]);
                        currentIndex = 1;
                        break;
                    case R.id.rbMenu3:
                        showHideFragment(mFragments[2], mFragments[currentIndex]);
                        currentIndex = 2;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e("MainActivity requestCode->" + requestCode);
        LogUtils.e("MainActivity resultCode->" + resultCode);
        ISupportFragment mainMenu2Fragment = findFragment(MainMenu2Fragment.class);
        if (mainMenu2Fragment != null) {
            mainMenu2Fragment.onFragmentResult(requestCode, resultCode, null);
        }
    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressedSupport();
        }
    }


}

package com.ui.main.activity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseActivity;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.StringUtils;
import com.hxky.common.R;
import com.utils.biz.Biz;

import butterknife.BindView;

import static com.utils.EnterPagerUtils.enterPage;

/**
 * 引导页面
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.rlSplash)
    RelativeLayout rlSplash;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    /**
     * 图片渐变动画
     */
    private AlphaAnimation imageAnim;
    /**
     * 渐变时间
     */
    private static final int ANIMATION_TIME = 3000;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_splash;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void setData(Bundle bundle) {
        imageAnim = new AlphaAnimation(0.1f, 1.0f);
        imageAnim.setDuration(ANIMATION_TIME);
        rlSplash.setAnimation(imageAnim);
        tvVersion.setText(AppUtils.getAppVersionName());
    }

    @Override
    protected void addListeners() {
        imageAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (StringUtils.isEmpty(Biz.getInstance().getSessionKey())) {
                    enterPage(LoginActivity.class);
                } else {
                    enterPage(MainActivity.class);
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        imageAnim.setAnimationListener(null);
        super.onDestroy();
    }


}

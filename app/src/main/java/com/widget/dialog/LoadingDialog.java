package com.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.hxky.common.R;
import com.widget.jumping.JumpingBeans;


/**
 * @ClassName:TransferDialog
 * @date 2016-07-12
 * @Description:自定义dialog
 */
public class LoadingDialog extends ProgressDialog {

    private Context context;
    private ImageView ivLoading;
    private TextView tvMessage;

    private JumpingBeans jumpingBeans;

    private RotateAnimation rotateAnimation;

    /**
     * @param context    上下文对象
     * @param cancleable 对话框是否可以取消 false 触碰屏幕不能取消
     **/
    public LoadingDialog(Context context, boolean cancleable) {
        super(context, R.style.DialogNoTitle);
        setCancelable(cancleable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dialog_loading);
        setScreenBrightness(0);
        initDialog();
        initListener();
    }

    /**
     * 初始化 弹框
     */
    private void initDialog() {
        //初始化 弹框
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        ivLoading = (ImageView) findViewById(R.id.ivLoading);
        jumpingBeans = JumpingBeans.with(tvMessage).appendJumpingDots().build();
        rotateAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000L);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(Animation.RESTART);
    }


    public void setMessage(String str) {
        this.tvMessage.setText(str);
    }

    public void setMessage(@StringRes int str) {
        this.tvMessage.setText(str);
    }

    public void setCancel(boolean isCancel) {
        this.setCancelable(isCancel);
    }

    private void initListener() {
        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ivLoading.startAnimation(rotateAnimation);
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                jumpingBeans.stopJumping();
                ivLoading.clearAnimation();
            }
        });
    }

    /**
     * 设置屏幕亮度值
     *
     * @param dimAmount 代表黑暗数量，也就是昏暗的多少，设置为0则代表完全明亮。 范围是0.0到1.0
     */
    private void setScreenBrightness(float dimAmount) {
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = dimAmount;
        window.setAttributes(lp);
    }
}

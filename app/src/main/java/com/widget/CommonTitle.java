package com.widget;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.R;

/**
 * @Description:自定义title
 */
public class CommonTitle extends RelativeLayout implements View.OnClickListener {


    private ImageView ivBack;//返回图标
    private TextView tvTitle;//中间标题显示控件
    private TextView tvRight;//右侧确定控件
    private CommonTitleCallBackListener mCommonTitleCallBackListener;//监听接口变量


    public CommonTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_header_title, this);
        initView();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        ivBack = findViewById(R.id.ivBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvRight = findViewById(R.id.tvRight);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        ivBack.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ivBack:
                if (mCommonTitleCallBackListener != null)
                    mCommonTitleCallBackListener.btnBackOnClick();
                break;
            case R.id.tvRight:
                if (mCommonTitleCallBackListener != null)
                    mCommonTitleCallBackListener.btnConfirmOnClick();
                break;
        }
    }

    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitle(String text) {
        tvTitle.setText(text);
    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(@StringRes int resId) {
        tvTitle.setText(resId);
    }


    /**
     * 显示隐藏 返回按钮
     *
     * @param bool true 显示，false 隐藏
     */
    public void setVisibilityBack(boolean bool) {
        if (bool) {
            ivBack.setVisibility(VISIBLE);
        } else {
            ivBack.setVisibility(GONE);
        }
    }


    /**
     * 显示隐藏 确定按钮
     *
     * @param bool true 显示，false 隐藏
     */
    public void setVisibilityConfirm(boolean bool) {
        tvRight.setVisibility(bool ? VISIBLE : GONE);
    }

    /**
     * 设置确定按钮文本
     *
     * @param str
     */
    public void setTextConfirm(String str) {
        tvRight.setText(str);
    }

    /**
     * 设置确定按钮文本
     *
     * @param resId
     */
    public void setTextConfirm(@StringRes int resId) {
        tvRight.setText(resId);
    }


    /**
     * 接口的实现类设置
     *
     * @param commonTitleCallBackListener
     */
    public void setCommonTitleCallBackListener(CommonTitleCallBackListener commonTitleCallBackListener) {
        this.mCommonTitleCallBackListener = commonTitleCallBackListener;
    }

    /**
     * 由每个activity自行实现此接口，实现定制的title按钮功能实现
     */
    public interface CommonTitleCallBackListener {

        /**
         * 返回功能回调方法
         */
        void btnBackOnClick();

        /**
         * 确定回调方法
         */
        void btnConfirmOnClick();
    }
}

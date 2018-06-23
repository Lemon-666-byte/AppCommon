package com.ui.main.activity;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.BaseActivity;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.common.R;
import com.constant.PathConfig;
import com.net.DefaultObserver;
import com.net.RetrofitUtils;
import com.ui.main.bean.User;
import com.utils.RxSchedulers;
import com.utils.UploadMyAppUtils;
import com.utils.biz.Biz;
import com.widget.ClearEditText;

import java.util.Map;

import butterknife.BindView;

/**
 * 登陆
 */
@Route(path = PathConfig.LoginActivity)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.etUserName)
    ClearEditText etUserName;
    @BindView(R.id.etPassWord)
    ClearEditText etPassWord;
    @BindView(R.id.cbPassWord)
    CheckBox cbPassWord;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;
    @BindView(R.id.btnLogIn)
    Button btnLogIn;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    private String userName, passWord;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_login;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {
        UploadMyAppUtils.getInstance().update(false);
        if (!StringUtils.isEmpty(Biz.getInstance().getUserAccountName())) {
            etUserName.setText(Biz.getInstance().getUserAccountName());
            etUserName.setSelection(etUserName.getText().toString().length());
        }
        tvVersion.setText(AppUtils.getAppVersionName());
        BarUtils.setStatusBarAlpha(this);
    }

    private void loadData() {
        showLoading(true);
        Map<String, Object> params = new ArrayMap<>();
        params.put("UserName", etUserName.getText().toString().trim());
        params.put("UserPwd", passWord);

        RetrofitUtils.getInstance().logIn(params)
                .compose(RxSchedulers.<User>applySchedulers())
                .compose(this.<User>bindToLifecycle())
                .subscribe(new DefaultObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        Biz.getInstance().setSessionKey(user.getSessionKey());
                        Biz.getInstance().setUserId(user.getUserID());
                        Biz.getInstance().setUserName(user.getUserName());
                        Biz.getInstance().setUserAccountName(userName);
                        Biz.getInstance().setPassword(passWord);
                        ARouter.getInstance().build(PathConfig.MainActivity).navigation();
                        finish();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        hideLoading();
                    }
                });
    }

    @Override
    public void addListeners() {
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = etUserName.getText().toString();
                if (StringUtils.isEmpty(userName)) {
                    ToastUtils.showShort("请输入用户名");
                    return;
                }
                passWord = etPassWord.getText().toString();
                if (StringUtils.isEmpty(passWord)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                KeyboardUtils.hideSoftInput(LoginActivity.this);
                loadData();
            }
        });
        cbPassWord.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int index = etPassWord.getSelectionStart();
                    etPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassWord.setSelection(index);
                } else {
                    int index = etPassWord.getSelectionStart();
                    etPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassWord.setSelection(index);
                }
            }
        });
//        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                enterPage(ForgetPasswordActivity.class);
//            }
//        });

    }

}
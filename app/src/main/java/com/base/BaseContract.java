package com.base;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author Yang
 * @date 2018/3/14
 */
public interface BaseContract {

    interface BasePresenter<T extends BaseContract.BaseView> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        //显示进度中
        void showLoading(boolean isCancel);

        //隐藏进度
        void hideLoading();

        //显示请求成功
        void showSuccess(String message);

        //失败重试
        void showFail(String message);

        //显示当前网络不可用
        void showNoNet();

        //重试
        void onRetry();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();

    }
}

package com.ui.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.ui.main.bean.User;

import java.util.List;

public class DemoMultipleItemRvAdapter extends MultipleItemRvAdapter<User, BaseViewHolder> {
    public static final int SINGLE_TEXT = 1;
    public static final int SINGLE_IMG = 2;
    public static final int TEXT_IMG = 3;

    public DemoMultipleItemRvAdapter(@Nullable List<User> data) {
        super(data);
        finishInitialize();
    }

    @Override
    protected int getViewType(User user) {
        //根据实体类判断并返回对应的viewType，具体判断逻辑因业务不同，这里这是简单通过判断type属性
        //According to the entity class to determine and return the corresponding viewType,
        //the specific judgment logic is different because of the business, here is simply by judging the type attribute
        if (user.getOrganizationID() == SINGLE_TEXT) {
            return SINGLE_TEXT;
        } else if (user.getOrganizationID() == SINGLE_IMG) {
            return SINGLE_IMG;
        } else if (user.getOrganizationID() == TEXT_IMG) {
            return TEXT_IMG;
        }
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册相关的条目provider
        //Register related entries provider
        mProviderDelegate.registerProvider(new TextItemAdapter());
//        mProviderDelegate.registerProvider(new ImgItemProvider());
//        mProviderDelegate.registerProvider(new TextImgItemProvider());
    }
}

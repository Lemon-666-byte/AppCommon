package com.ui.main.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.ui.main.bean.User;

public class TextItemAdapter extends BaseItemProvider<User, BaseViewHolder> {

    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public int layout() {
        return 0;
    }

    @Override
    public void convert(BaseViewHolder helper, User data, int position) {

    }

    @Override
    public void onClick(BaseViewHolder helper, User data, int position) {

    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, User data, int position) {
        return false;
    }
}

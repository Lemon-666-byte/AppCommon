package com.ui.main.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.common.R;
import com.ui.main.bean.CarItem;

/**
 * Created by yang on 2018/1/19.
 */

public class ArticleAdapter extends BaseQuickAdapter<CarItem, BaseViewHolder> {

    public ArticleAdapter() {
        super(R.layout.main_adapter_fragment_menu1);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarItem item) {
        helper.setText(R.id.tvLicencePlateColor, helper.getAdapterPosition() + "<-->" + item.getLicencePlateColor());
//        helper.setText(R.id.tvCarStyle, helper.getAdapterPosition() + "<-->" + item.getCarStyle());
    }

}

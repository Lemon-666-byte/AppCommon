package com.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共adapter类
 */
public class CommonListAdapter<T> extends BaseAdapter {

    /**
     * 上下文
     */
    protected Context context;
    /**
     * 布局填充器
     */
    protected LayoutInflater inflater;
    /**
     * 数据集合;
     */
    protected List<T> list;

    /**
     * @param context 上下文
     * @param list    数据集合
     */
    public CommonListAdapter(Context context, List<T> list) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.setList(list);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    private void setList(List<T> list) {
        if (list != null) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return null;
    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<T> list) {
        setList(list);
        notifyDataSetChanged();
    }

}

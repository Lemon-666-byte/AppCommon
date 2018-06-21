package com.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hxky.common.R;


public class ListViewEmptyUtil {
    /**
     * 上下文
     */
    private Context context;
    /**
     * 空视图
     */
    private View emptyView;
    /**
     * 空视图的提示文字
     */
    private TextView tvEmpty;
    /**
     * 空视图的提示图片
     */
    private ImageView ivEmpty;

    /**
     * ListView组件
     */
    private ListView listView;
    /**
     * GridView组件
     */
    private GridView gridView;

    /**
     * 构造函数
     *
     * @param context
     * @param listView ListView
     */
    public ListViewEmptyUtil(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
        initEmptyView();
    }

    /**
     * 构造函数
     *
     * @param context
     * @param gridView GridView
     */
    public ListViewEmptyUtil(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        initEmptyView();
    }

    /**
     * 初始化 空布局
     */
    private void initEmptyView() {
        emptyView = LayoutInflater.from(context).inflate(R.layout.view_empty_view, null);
        tvEmpty = emptyView.findViewById(R.id.tvEmptyView);
        ivEmpty = emptyView.findViewById(R.id.ivEmptyView);
    }

    /**
     * 设置空布局 的 提示文字
     *
     * @param emptyText
     */
    public void setEmptyText(String emptyText) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(emptyText);
        tvEmpty.setBackgroundResource(0);
        ivEmpty.setVisibility(View.GONE);
        if (this.listView != null) {
            addEmptyView();
            listView.setEmptyView(emptyView);
        } else if (this.gridView != null) {
            addEmptyView();
            gridView.setEmptyView(emptyView);
        }
    }

    /**
     * 设置空布局 的 提示文字 只提示文字
     *
     * @param emptyTextId 空view
     */
    public void setEmptyText(int emptyTextId) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(emptyTextId);
        tvEmpty.setBackgroundResource(0);
        ivEmpty.setVisibility(View.GONE);
        if (this.listView != null) {
            addEmptyView();
            listView.setEmptyView(emptyView);
        } else if (this.gridView != null) {
            addEmptyView();
            gridView.setEmptyView(emptyView);
        }
    }

    /**
     * 设置空布局 的 提示图片 只提示图片
     *
     * @param resId
     */
    public void setEmptyImage(int resId) {
        tvEmpty.setVisibility(View.GONE);
        ivEmpty.setVisibility(View.VISIBLE);
        ivEmpty.setImageResource(resId);
        if (this.listView != null) {
            addEmptyView();
            listView.setEmptyView(emptyView);
        } else if (this.gridView != null) {
            addEmptyView();
            gridView.setEmptyView(emptyView);
        }
    }

    /**
     * 设置空布局 的 提示文字和图片
     *
     * @param resId
     */
    public void setEmptyTextAndImage(String emptyText, int resId) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(emptyText);
        tvEmpty.setBackgroundResource(0);
        ivEmpty.setVisibility(View.VISIBLE);
        ivEmpty.setImageResource(resId);
        if (this.listView != null) {
            addEmptyView();
            listView.setEmptyView(emptyView);
        } else if (this.gridView != null) {
            addEmptyView();
            gridView.setEmptyView(emptyView);
        }
    }

    /**
     * 设置空布局 的 提示图片 只提示图片
     *
     * @param emptyTextId
     * @param resId
     */
    public void setEmptyTextAndImage(int emptyTextId, int resId) {
        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty.setText(emptyTextId);
        tvEmpty.setBackgroundResource(0);
        ivEmpty.setVisibility(View.VISIBLE);
        ivEmpty.setImageResource(resId);
        if (this.listView != null) {
            addEmptyView();
            listView.setEmptyView(emptyView);
        } else if (this.gridView != null) {
            addEmptyView();
            gridView.setEmptyView(emptyView);
        }
    }

    /**
     * listview/gridview 设置空视图时必须添加到父视图
     * <p>
     * emptyView要添加的空视图
     */
    private void addEmptyView() {
        if (null != emptyView) {
            ViewParent newEmptyViewParent = emptyView.getParent();
            if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
                ((ViewGroup) newEmptyViewParent).removeView(emptyView);
            }
            if (this.listView != null) {
                ((ViewGroup) listView.getParent()).addView(emptyView);
            } else if (this.gridView != null) {
                ((ViewGroup) gridView.getParent()).addView(emptyView);
            }
        }
    }

    public View getEmptyView() {
        return emptyView;
    }

}

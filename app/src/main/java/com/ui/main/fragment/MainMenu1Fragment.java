package com.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.BaseFragment;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hxky.common.R;
import com.net.DefaultObserver;
import com.net.RequestUtils;
import com.net.RetrofitUtils;
import com.refreshview.SmartRefreshLayout;
import com.refreshview.api.RefreshLayout;
import com.refreshview.listener.OnRefreshLoadMoreListener;
import com.ui.main.adapter.ArticleAdapter;
import com.ui.main.bean.CarItem;
import com.utils.RxSchedulers;
import com.utils.UploadMyAppUtils;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Menu1
 */
public class MainMenu1Fragment extends BaseFragment {

    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    private ArticleAdapter mArticleAdapter;

    public static MainMenu1Fragment newInstance() {
        return new MainMenu1Fragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            UploadMyAppUtils.getInstance().update(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_menu1;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void setData(Bundle bundle) {
        refreshLayout.autoRefresh();
        mArticleAdapter = new ArticleAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvArticle.setLayoutManager(linearLayoutManager);
        rvArticle.setAdapter(mArticleAdapter);

    }

    private void loadData() {
        Map<String, Object> params = RequestUtils.getParams();
        params.put("IsMyself", 0);
        params.put("PageIndex", 1);
        params.put("PageSize", 10);
        params.put("SearchCode", "");
        RetrofitUtils.getInstance().getCarSourceList(params)
                .compose(RxSchedulers.<List<CarItem>>applySchedulers())
                .subscribe(new DefaultObserver<List<CarItem>>() {
                    @Override
                    public void onSuccess(List<CarItem> response) {
                        finishRefresh();
                        mArticleAdapter.setNewData(response);
                    }

                    @Override
                    public void onFail(Throwable e) {
                        finishRefresh();
                    }
                });

    }


    @Override
    public void addListeners() {
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                finishRefresh();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        mArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("position-->" + position);
            }
        });
    }


    private void finishRefresh() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }
}
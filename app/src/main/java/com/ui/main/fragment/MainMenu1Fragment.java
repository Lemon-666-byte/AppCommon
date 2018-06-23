package com.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.base.BaseFragment;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.R;
import com.constant.LoadType;
import com.constant.NetConfig;
import com.net.DefaultObserver;
import com.net.RequestUtils;
import com.net.RetrofitUtils;
import com.refreshview.SmartRefreshLayout;
import com.refreshview.api.RefreshLayout;
import com.refreshview.listener.OnRefreshListener;
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
    private int pageIndex = 1;

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
        params.put("PageIndex", pageIndex);
        params.put("PageSize", NetConfig.PageSize.SMALL);
        params.put("Type", 1);
        params.put("SearchCode", "");
        RetrofitUtils.getInstance().getCarSourceList(params)
//        RetrofitUtils.getInstance().getHaveCarList(params)
                .compose(RxSchedulers.<List<CarItem>>applySchedulers())
                .compose(this.<List<CarItem>>bindToLifecycle())
                .subscribe(new DefaultObserver<List<CarItem>>() {
                    @Override
                    public void onSuccess(List<CarItem> response) {
                        int loadType = pageIndex == 1 ? LoadType.TYPE_REFRESH_SUCCESS : LoadType.TYPE_LOAD_MORE_SUCCESS;
                        setLoadDataResult(mArticleAdapter, refreshLayout, response, loadType);
                        pageIndex++;
                    }

                    @Override
                    public void onFail(Throwable e) {
                        int loadType = pageIndex == 1 ? LoadType.TYPE_REFRESH_ERROR : LoadType.TYPE_LOAD_MORE_ERROR;
                        setLoadDataResult(mArticleAdapter, refreshLayout, null, loadType);
                    }
                });

    }


    @Override
    public void addListeners() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                loadData();
            }
        });
        mArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData();
            }
        }, rvArticle);
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
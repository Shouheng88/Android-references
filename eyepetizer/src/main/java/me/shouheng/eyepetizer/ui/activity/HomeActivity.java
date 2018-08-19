package me.shouheng.eyepetizer.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.List;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.commons.view.widget.DividerItemDecoration;
import me.shouheng.eyepetizer.R;
import me.shouheng.eyepetizer.databinding.ActivityEyepetizerMenuBinding;
import me.shouheng.eyepetizer.mvp.contract.HomeContract;
import me.shouheng.eyepetizer.mvp.model.bean.HomeBean;
import me.shouheng.eyepetizer.mvp.presenter.HomePresenter;
import me.shouheng.eyepetizer.ui.adapter.HomeAdapter;

@Route(path = BaseConstants.EYEPETIZER_MENU)
public class HomeActivity extends CommonActivity<ActivityEyepetizerMenuBinding> implements HomeContract.IView {

    private HomeAdapter homeAdapter;

    private boolean loading = false;

    private HomeContract.IPresenter presenter;

    {
        presenter = new HomePresenter(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_eyepetizer_menu;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {

        configToolbar();

        configList();

        presenter.requestFirstPage();
        loading = true;
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.menu_item_title_5);
            actionBar.setSubtitle(R.string.menu_item_desc_5);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void configList() {
        homeAdapter = new HomeAdapter(getContext());
        homeAdapter.setOnItemClickListener(((adapter1, view, position) -> {
            HomeBean.IssueList.ItemList itemList = homeAdapter.getData().get(position);
            ARouter.getInstance()
                    .build(BaseConstants.EYEPETIZER_CONTAINER)
                    .withString(BaseConstants.EYEPETIZER_CONTAINER_ACTION_VIDEO_EXTRA_URL, itemList.getData().getPlayUrl())
                    .navigation();
        }));

        getBinding().rv.setAdapter(homeAdapter);
        getBinding().rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST, false));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        getBinding().rv.setLayoutManager(layoutManager);

        getBinding().rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                int totalItemCount = layoutManager.getItemCount();
                if (lastVisibleItem + 1 == totalItemCount && dy > 0) {
                    if (!loading) {
                        loading = true;
                        presenter.requestNextPage();
                    }
                }
            }
        });
    }

    @Override
    public void setFirstPage(List<HomeBean.IssueList.ItemList> itemLists) {
        loading = false;
        homeAdapter.addData(itemLists);
    }

    @Override
    public void setNextPage(List<HomeBean.IssueList.ItemList> itemLists) {
        loading = false;
        homeAdapter.addData(itemLists);
    }

    @Override
    public void onError(String msg) {
        ToastUtils.makeToast(msg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

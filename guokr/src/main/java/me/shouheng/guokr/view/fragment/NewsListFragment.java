package me.shouheng.guokr.view.fragment;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.commons.view.widget.DividerItemDecoration;
import me.shouheng.guokr.R;
import me.shouheng.guokr.databinding.FragmentNewsListBinding;
import me.shouheng.guokr.model.data.GuokrNews;
import me.shouheng.guokr.view.adapter.GuokrNewsAdapter;
import me.shouheng.guokr.viewmodel.GuokrViewModel;

/**
 * @author shouh
 * @version $Id: NewsListFragment, v 0.1 2018/6/10 12:08 shouh Exp$
 */
@Route(path = BaseConstants.GUOKR_NEWS_LIST)
public class NewsListFragment extends CommonFragment<FragmentNewsListBinding> {

    private GuokrViewModel guokrViewModel;

    private int offset = 0;

    private final int limit = 20;

    private GuokrNewsAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        Activity activity = getActivity();
        if (!(activity instanceof FragmentInteraction)) {
            throw new IllegalArgumentException("The associated activity must implement FragmentInteraction");
        }

        guokrViewModel = ViewModelProviders.of(this).get(GuokrViewModel.class);

        configViews();

        registerObservers();

        guokrViewModel.fetchGuokrNews(offset, limit);
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        if (activity != null) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(R.string.menu_item_title_2);
                actionBar.setSubtitle(R.string.menu_item_desc_2);
            }
        }
    }

    private void configViews() {
        adapter = new GuokrNewsAdapter(getContext());
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            GuokrNews.Result result = adapter.getData().get(position);
            Activity activity = getActivity();
            if (activity != null) ((FragmentInteraction) activity).onArticleClicked(result);
        }));
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(() -> {
            offset += limit;
            guokrViewModel.fetchGuokrNews(offset, limit);
        }, getBinding().rv);

        getBinding().rv.setAdapter(adapter);
        getBinding().rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST, false));
        getBinding().rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void registerObservers() {
        guokrViewModel.getGuokrNewsLiveData().observe(this, resources -> {
            if (resources == null) return;
            switch (resources.status) {
                case FAILED:
                    ToastUtils.makeToast(resources.message);
                    break;
                case SUCCESS:
                    adapter.addData(resources.data.getResult());
                    adapter.notifyDataSetChanged();
                    break;
            }
        });
    }

    public interface FragmentInteraction {
        void onArticleClicked(GuokrNews.Result result);
    }
}

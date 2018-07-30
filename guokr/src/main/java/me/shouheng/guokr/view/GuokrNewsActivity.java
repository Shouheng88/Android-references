package me.shouheng.guokr.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;

import javax.inject.Inject;

import me.shouheng.commons.view.activity.CommonDaggerActivity;
import me.shouheng.commons.tools.FragmentHelper;
import me.shouheng.guokr.R;
import me.shouheng.guokr.databinding.ActivityGuokrBewsBinding;
import me.shouheng.guokr.model.GuokrNews;
import me.shouheng.guokr.view.fragment.NewsDetailFragment;
import me.shouheng.guokr.view.fragment.NewsListFragment;
import me.shouheng.guokr.viewmodel.GuokrViewModel;

@Route(path = "/guokr/news")
public class GuokrNewsActivity extends CommonDaggerActivity<ActivityGuokrBewsBinding> implements NewsListFragment.FragmentInteraction {

    @Inject
    GuokrViewModel guokrViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guokr_bews;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        configToolbar();

        toFragment(NewsListFragment.newInstance());
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        toolbar.setBackgroundResource(R.color.light_theme_background);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.menu_item_title_2);
            actionBar.setSubtitle(R.string.menu_item_desc_2);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void toFragment(Fragment fragment) {
        FragmentHelper.replace(this, fragment, R.id.fragment_container);
    }

    private void toFragmentWithCallback(Fragment fragment) {
        FragmentHelper.replaceWithCallback(this, fragment, R.id.fragment_container);
    }

    @Override
    public void onArticleClicked(GuokrNews.Result result) {
        toFragmentWithCallback(NewsDetailFragment.newInstance(result.getId(), result.getTitle()));
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

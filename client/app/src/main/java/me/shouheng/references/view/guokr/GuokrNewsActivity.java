package me.shouheng.references.view.guokr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import me.shouheng.commons.helper.FragmentHelper;
import me.shouheng.references.R;
import me.shouheng.references.databinding.ActivityGuokrBewsBinding;
import me.shouheng.references.view.CommonDaggerActivity;
import me.shouheng.references.view.guokr.fragment.NewsListFragment;
import me.shouheng.references.viewmodel.GuokrViewModel;

public class GuokrNewsActivity extends CommonDaggerActivity<ActivityGuokrBewsBinding> {

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
        }
    }

    private void toFragment(Fragment fragment) {
        FragmentHelper.replace(this, fragment, R.id.fragment_container);
    }

    private Fragment getCurrentFragment() {
        return getCurrentFragment(R.id.fragment_container);
    }
}

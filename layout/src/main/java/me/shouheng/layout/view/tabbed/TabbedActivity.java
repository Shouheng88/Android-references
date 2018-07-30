package me.shouheng.layout.view.tabbed;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.activity.CommonActivity;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.ActivityTabbedBinding;

@Route(path = BaseConstants.LAYOUT_TABBED)
public class TabbedActivity extends CommonActivity<ActivityTabbedBinding> {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tabbed;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        getBinding().toolbar.setTitle(R.string.menu_item_sub_title_2);
        getBinding().toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(getBinding().toolbar);

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        getBinding().vp.setAdapter(adapter);

        getBinding().fab.setOnClickListener(view ->
                Snackbar.make(view, "Fab is clicked!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
        );
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) ARouter.getInstance()
                    .build(BaseConstants.LAYOUT_NORMAL_FRAGMENT)
                    .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_TITLE, "Fragment " + (position + 1))
                    .withString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_CONTENT, "Content fragment of tabbed activity : " + (position + 1))
                    .withBoolean(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_SHOW_TOOLBAR, false)
                    .navigation();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return "Tab 1";
                case 1: return "Tab 2";
                case 2: return "Tab 3";
            }
            return null;
        }
    }
}

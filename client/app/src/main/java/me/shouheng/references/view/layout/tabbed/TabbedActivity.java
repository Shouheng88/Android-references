package me.shouheng.references.view.layout.tabbed;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.shouheng.references.R;
import me.shouheng.references.common.fragment.NormalTipsFragment;
import me.shouheng.references.databinding.ActivityTabbedBinding;
import me.shouheng.references.view.CommonDaggerActivity;

public class TabbedActivity extends CommonDaggerActivity<ActivityTabbedBinding> {

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
            return NormalTipsFragment.newInstance("Tabbed" + position,
                    "This is the content of tabbed framgnet " + position,
                    false);
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

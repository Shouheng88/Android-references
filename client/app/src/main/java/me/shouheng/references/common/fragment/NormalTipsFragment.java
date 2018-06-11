package me.shouheng.references.common.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.shouheng.references.R;
import me.shouheng.references.databinding.FragmentNormalTipsBinding;
import me.shouheng.references.view.CommonDaggerFragment;

/**
 * Created by WngShhng on 2018/6/11.
 */
public class NormalTipsFragment extends CommonDaggerFragment<FragmentNormalTipsBinding> {

    private final static String EXTRA_TITLE = "__extra_title";
    private final static String EXTRA_CONTENT = "__extra_content";
    private final static String EXTRA_SHOW_TOOLBAR = "__extra_show_toolbar";

    private String title;

    private String content;

    private boolean showToolbar;

    public static NormalTipsFragment newInstance(String title, String content, boolean showToolbar) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, title);
        args.putString(EXTRA_CONTENT, content);
        args.putBoolean(EXTRA_SHOW_TOOLBAR, showToolbar);
        NormalTipsFragment fragment = new NormalTipsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_normal_tips;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        handleArguments();

        configToolbar();

        configViews();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void handleArguments() {
        Bundle args = getArguments();
        assert args != null;
        title = args.getString(EXTRA_TITLE);
        content = args.getString(EXTRA_CONTENT);
        showToolbar = args.getBoolean(EXTRA_SHOW_TOOLBAR);
    }

    private void configToolbar() {
        Toolbar toolbar = getBinding().barLayout.toolbar;
        if (!showToolbar) {
            toolbar.setVisibility(View.GONE);
            getBinding().shader.setVisibility(View.GONE);
            return;
        }
        Activity activity = getActivity();
        if (activity != null) {
            ((AppCompatActivity) activity).setSupportActionBar(toolbar);
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(title);
            }
        }
    }

    private void configViews() {
        getBinding().tvTitle.setText(title);
        getBinding().tvContent.setText(content);
    }
}

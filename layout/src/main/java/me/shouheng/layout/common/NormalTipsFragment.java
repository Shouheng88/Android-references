package me.shouheng.layout.common;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.layout.R;
import me.shouheng.layout.databinding.FragmentNormalTipsBinding;

/**
 * Created by WngShhng on 2018/6/11.*/
@Route(path = BaseConstants.LAYOUT_NORMAL_FRAGMENT)
public class NormalTipsFragment extends CommonFragment<FragmentNormalTipsBinding> {

    private String title;

    private String content;

    private boolean showToolbar;

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
        title = args.getString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_TITLE);
        content = args.getString(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_CONTENT);
        showToolbar = args.getBoolean(BaseConstants.LAYOUT_NORMAL_FRAGMENT_EXTRA_SHOW_TOOLBAR);
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

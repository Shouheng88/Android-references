package me.shouheng.guokr.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.shouheng.commons.config.BaseConstants;
import me.shouheng.commons.tools.ToastUtils;
import me.shouheng.commons.view.fragment.CommonFragment;
import me.shouheng.guokr.R;
import me.shouheng.guokr.databinding.FragmentNewsDetailBinding;
import me.shouheng.guokr.model.data.GuokrNewsContent;
import me.shouheng.guokr.viewmodel.GuokrViewModel;

/**
 * @author shouh
 * @version $Id: NewsDetailFragment, v 0.1 2018/6/10 19:02 shouh Exp$
 */
@Route(path = BaseConstants.GUOKR_NEWS_DETAIL)
public class NewsDetailFragment extends CommonFragment<FragmentNewsDetailBinding> {

    private int articleId;

    private String articleTitle;

    private GuokrViewModel guokrViewModel;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        handleArguments();

        configToolbar();

        configViews();

        guokrViewModel = ViewModelProviders.of(this).get(GuokrViewModel.class);

        fetchContent();
    }

    private void handleArguments() {
        Bundle args = getArguments();
        assert args != null;
        articleId = args.getInt(BaseConstants.GUOKR_NEWS_DETAIL_EXTRA_KEY_ARTICLE_ID);
        articleTitle = args.getString(BaseConstants.GUOKR_NEWS_DETAIL_EXTRA_KEY_ARTICLE_TITLE);
    }

    private void configToolbar() {
        Activity activity = getActivity();
        if (activity != null) {
            ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(articleTitle);
                actionBar.setSubtitle("");
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void configViews() {
        getBinding().wv.setScrollbarFadingEnabled(true);
        getBinding().wv.getSettings().setJavaScriptEnabled(true);
        getBinding().wv.getSettings().setBuiltInZoomControls(false);
        getBinding().wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getBinding().wv.getSettings().setDomStorageEnabled(true);
        getBinding().wv.getSettings().setAppCacheEnabled(true);
        getBinding().wv.getSettings().setBlockNetworkImage(false);
        getBinding().wv.setHorizontalScrollBarEnabled(false);
        getBinding().wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
    }

    private void fetchContent() {
        guokrViewModel.getGuokrNewsContent(articleId).observe(this, guokrNewsContentResource -> {
            if (guokrNewsContentResource == null) {
                return;
            }
            switch (guokrNewsContentResource.status) {
                case SUCCESS:
                    assert guokrNewsContentResource.data != null;
                    updateContent(guokrNewsContentResource.data);
                    break;
                case FAILED:
                    ToastUtils.makeToast(guokrNewsContentResource.message);
                    break;
            }
        });
    }

    private void updateContent(GuokrNewsContent content) {
        String body = content.getResult().getContent();
        String css = "<div class=\"article\" id=\"contentMain\">";
        String result = ("<!DOCTYPE html>\n"
                + "<html lang=\"ZH-CN\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n<meta charset=\"utf-8\" />\n"
                + "\n<link rel=\"stylesheet\" href=\"file:///android_asset/guokr_master.css\" />\n"
                + css
                + "<script src=\"file:///android_asset/guokr.base.js\"></script>\n"
                + "<script src=\"file:///android_asset/guokr.articleInline.js\"></script>"
                + "<script>\n"
                + "var ukey = null;\n"
                + "</script>\n"
                + "\n</head>\n<div class=\"content\" id=\"articleContent\"><body>\n"
                + body
                + "\n</div></body>\n</html>");
        getBinding().wv.loadDataWithBaseURL("x-data://base", result, "text/html", "utf-8", null);
    }
}

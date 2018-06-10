package me.shouheng.references.view.guokr.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import javax.inject.Inject;

import me.shouheng.commons.util.ToastUtils;
import me.shouheng.references.R;
import me.shouheng.references.databinding.FragmentNewsDetailBinding;
import me.shouheng.references.model.guokr.data.GuokrNewsContent;
import me.shouheng.references.view.CommonDaggerFragment;
import me.shouheng.references.viewmodel.GuokrViewModel;

/**
 * @author shouh
 * @version $Id: NewsDetailFragment, v 0.1 2018/6/10 19:02 shouh Exp$
 */
public class NewsDetailFragment extends CommonDaggerFragment<FragmentNewsDetailBinding> {

    private final static String EXTRA_ARTICLE_ID = "__extra_article_id";

    private final static String EXTRA_ARTICLE_TITLE = "__extra_article_title";

    private int articleId;

    private String articleTitle;

    @Inject GuokrViewModel guokrViewModel;

    public static NewsDetailFragment newInstance(int articleId, String articleTitle) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ARTICLE_ID, articleId);
        args.putString(EXTRA_ARTICLE_TITLE, articleTitle);
        NewsDetailFragment fragment = new NewsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_detail;
    }

    @Override
    protected void doCreateView(Bundle savedInstanceState) {
        handleArguments();

        configToolbar();

        configViews();

        fetchContent();
    }

    private void handleArguments() {
        Bundle args = getArguments();
        assert args != null;
        articleId = args.getInt(EXTRA_ARTICLE_ID);
        articleTitle = args.getString(EXTRA_ARTICLE_TITLE);
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

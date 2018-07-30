package me.shouheng.guokr.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.LinkedList;

import me.shouheng.commons.tools.TimeUtils;
import me.shouheng.commons.tools.glide.GlideApp;
import me.shouheng.guokr.R;
import me.shouheng.guokr.model.data.GuokrNews;

/**
 * @author shouh
 * @version $Id: GuokrNewsAdapter, v 0.1 2018/6/10 13:41 shouh Exp$
 */
public class GuokrNewsAdapter extends BaseQuickAdapter<GuokrNews.Result, BaseViewHolder> {

    private Context context;

    public GuokrNewsAdapter(Context context) {
        super(R.layout.item_guokr_news, new LinkedList<>());
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GuokrNews.Result item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_tags, item.getSubject().getName());

        GlideApp.with(context)
                .asBitmap()
                .load(item.getSmall_image())
                .placeholder(R.drawable.guokr)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.iv_cover));

        GlideApp.with(context)
                .asBitmap()
                .load(item.getAuthor().getAvatar().getNormal())
                .placeholder(R.drawable.guokr)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.civ_author));
        helper.setText(R.id.tv_author, item.getAuthor().getNickname());
        helper.setText(R.id.tv_date, TimeUtils.getPrettyTime(TimeUtils.from(item.getDate_published())));
    }
}

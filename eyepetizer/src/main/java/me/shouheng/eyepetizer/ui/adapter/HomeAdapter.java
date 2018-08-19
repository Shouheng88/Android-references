package me.shouheng.eyepetizer.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.LinkedList;

import me.shouheng.eyepetizer.R;
import me.shouheng.eyepetizer.mvp.model.bean.HomeBean;

/**
 * @author shouh
 * @version $Id: HomeAdapter, v 0.1 2018/8/19 18:19 shouh Exp$
 */
public class HomeAdapter extends BaseQuickAdapter<HomeBean.IssueList.ItemList, BaseViewHolder> {

    private Context context;

    public HomeAdapter(Context context) {
        super(R.layout.item_home, new LinkedList<>());
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBean.IssueList.ItemList item) {
        helper.setText(R.id.tv_title, item.getData().getTitle());
        helper.setText(R.id.tv_sub_title, item.getData().getAuthor().getName() + " | " + item.getData().getCategory());
        if (item.getData().getCover() != null) {
            Glide.with(context)
                    .load(item.getData().getCover().getHomepage())
                    .thumbnail(Glide.with(context).load(R.drawable.recommend_summary_card_bg_unlike))
                    .into((ImageView) helper.getView(R.id.iv_cover));
        }
        if (item.getData().getAuthor() != null) {
            Glide.with(context)
                    .load(item.getData().getAuthor().getIcon())
                    .thumbnail(Glide.with(context).load(R.mipmap.eyepetizer))
                    .into((ImageView) helper.getView(R.id.iv_author));
        }
    }
}

package me.shouheng.live.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Collections;

import me.shouheng.commons.tools.glide.GlideApp;
import me.shouheng.live.R;
import me.shouheng.live.model.data.Recommend;

/**
 * @author shouh
 * @version $Id: RecommendAdapter, v 0.1 2018/6/9 7:01 shouh Exp$
 */
public class RecommendAdapter extends BaseQuickAdapter<Recommend.RoomBean, BaseViewHolder> {

    private Context context;

    private OnRoomClickListener onRoomClickListener;

    public RecommendAdapter(Context context) {
        super(R.layout.item_remmend, Collections.emptyList());
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Recommend.RoomBean item) {
        helper.setText(R.id.tv_category, item.getName());

        GlideApp.with(context)
                .load(item.getIcon())
                .placeholder(R.drawable.live_default_recommend_icon)
                .into((ImageView) helper.getView(R.id.iv_category));

        helper.addOnClickListener(R.id.tv_more);

        RecommendChildAdapter adapter = new RecommendChildAdapter(context, item.getList());
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            if (onRoomClickListener != null) {
                Recommend.RoomBean.ListBean listBean = item.getList().get(position);
                onRoomClickListener.onRoomClick(listBean);
            }
        }));

        RecyclerView rv = helper.getView(R.id.rv);
        rv.setLayoutManager(new GridLayoutManager(context, 2));
        rv.setAdapter(adapter);
    }

    public void setOnRoomClickListener(OnRoomClickListener onRoomClickListener) {
        this.onRoomClickListener = onRoomClickListener;
    }

    public interface OnRoomClickListener {
        void onRoomClick(Recommend.RoomBean.ListBean listBean);
    }
}

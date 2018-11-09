package me.shouheng.live.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.palette.graphics.Palette;
import android.util.SparseIntArray;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import me.shouheng.commons.tools.ColorUtils;
import me.shouheng.live.R;
import me.shouheng.live.model.data.Recommend;

/**
 * @author shouh
 * @version $Id: RecommendChildAdapter, v 0.1 2018/6/9 7:16 shouh Exp$
 */
public class RecommendChildAdapter extends BaseQuickAdapter<Recommend.RoomBean.ListBean, BaseViewHolder> {

    private Context context;

    private SparseIntArray array;

    RecommendChildAdapter(Context context, List<Recommend.RoomBean.ListBean> beanList) {
        super(R.layout.item_remmend_child, beanList);
        this.context = context;
        array = new SparseIntArray();
    }

    @Override
    protected void convert(BaseViewHolder helper, Recommend.RoomBean.ListBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_name, item.getNick());
        helper.setText(R.id.tv_viewer, item.getViews());

        Glide.with(context)
                .asBitmap()
                .load(item.getThumb())
                .error(Glide.with(context).asBitmap().load(R.drawable.live_default))
                .into(new BitmapImageViewTarget(helper.getView(R.id.iv)) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        setColorPlatte(resource, helper, item);
                    }
                });
    }

    private void setColorPlatte(Bitmap resource, BaseViewHolder helper, Recommend.RoomBean.ListBean item) {
        int color = array.get(item.getNo());
        if (color == 0 && resource != null) {
            new Palette.Builder(resource).generate(palette -> {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                int retColor = 0;
                if (swatch != null) {
                    retColor = swatch.getRgb();
                    array.put(item.getNo(), retColor);
                } else {
                    Palette.Swatch mutedSwatch = palette.getMutedSwatch();
                    if (mutedSwatch != null) {
                        retColor = mutedSwatch.getRgb();
                        array.put(item.getNo(), retColor);
                    }
                }
                if (retColor != 0) {
                    helper.setBackgroundColor(R.id.ll, retColor);
                    int txtColor = ColorUtils.getBlackWhiteColor(retColor);
                    helper.setTextColor(R.id.tv_name, txtColor);
                    helper.setTextColor(R.id.tv_viewer, txtColor);
                }
            });
        } else {
            helper.setBackgroundColor(R.id.ll, color);
            int txtColor = ColorUtils.getBlackWhiteColor(color);
            helper.setTextColor(R.id.tv_name, txtColor);
            helper.setTextColor(R.id.tv_viewer, txtColor);
        }
    }
}

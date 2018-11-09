package me.shouheng.commons.view.widget;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import me.shouheng.commons.tools.ViewUtils;

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int leftDp, upDp, rightDp, downDp;

    public SpaceItemDecoration(int leftDp, int upDp, int rightDp, int downDp) {
        this.leftDp = ViewUtils.dp2Px(leftDp);
        this.upDp = ViewUtils.dp2Px(upDp);
        this.rightDp = ViewUtils.dp2Px(rightDp);
        this.downDp = ViewUtils.dp2Px(downDp);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(leftDp, upDp, rightDp, downDp);
    }
}
package test.com.coordinatorlayouttest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PaddingItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private int leftPadding;
    private Paint paint;
    public PaddingItemDecoration(Context context) {
        mContext=context;
        leftPadding=Uitools.dip2px(mContext,5);
        paint=new Paint();
        paint.setColor(Color.parseColor("#000000"));
    }

    /**
     * 绘制在item下
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        for(int i=0;i<parent.getChildCount();i++){
            View child=parent.getChildAt(i);
            int width=child.getWidth();
            int height=child.getHeight();
            int left=child.getLeft();
            int right=child.getRight();
            int top=child.getTop();
            int bottom=child.getBottom();
            c.drawLine(left,bottom,right,bottom,paint);
        }

    }
    /**
     * 绘制在item上
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left=leftPadding;
        outRect.bottom=leftPadding;
    }
}

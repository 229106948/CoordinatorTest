package test.com.coordinatorlayouttest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class MyBehavior extends CoordinatorLayout.Behavior<View> {
    private ObjectAnimator mAnimator;
    private  int scrollDistance;
    private int mScrollThreshold;
    private Context mContext;
    private boolean fling;

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScrollThreshold=Uitools.dip2px(context,50);
        mContext=context;

    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       View child, View directTargetChild, View target,
                                       int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        scrollDistance+=dyConsumed;
            if(scrollDistance>mScrollThreshold&&dyConsumed>0&&!fling){
                startAnimator(child,-mScrollThreshold);
            }else if(dyConsumed<0&&!fling){
                startAnimator(child,0f);
            }
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY, boolean consumed) {
        if(consumed) {
            if (scrollDistance > mScrollThreshold && velocityY > 0 && !fling) {
                startAnimator(child, -mScrollThreshold);
            } else if (velocityY < 0 && !fling) {
                startAnimator(child, 0f);
            }
        }
        return false;
    }

    public void startAnimator(View target, float value) {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        mAnimator = ObjectAnimator
                .ofFloat(target, View.TRANSLATION_Y, value)
                .setDuration(250);
        mAnimator.start();
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                fling=true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fling=false;
            }
        });
    }
}

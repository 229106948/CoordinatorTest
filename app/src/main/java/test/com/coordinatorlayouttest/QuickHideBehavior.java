package test.com.coordinatorlayouttest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;




/**
 * Simple scrolling behavior that monitors nested events in the scrolling
 * container to implement a quick hide/show for the attached view.
 */
public class QuickHideBehavior extends CoordinatorLayout.Behavior<View> {

    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = -1;

    /* Tracking direction of user motion */
    private int mScrollingDirection;
    /* Tracking last threshold crossed */
    private int mScrollTrigger=-1;

    /* Accumulated scroll distance */
    private int mScrollDistance;
    /* Distance threshold to trigger animation */
    private int mScrollThreshold;


    private ObjectAnimator mAnimator;

    private boolean isHidden=false;

    //Required to instantiate as a default behavior
    public QuickHideBehavior() {
    }

    //Required to attach behavior via XML
    public QuickHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

//        TypedArray a = context.getTheme()
//                .obtainStyledAttributes(new int[] {R.attr.actionBarSize});
        //Use half the standard action bar height
        //mScrollThreshold = a.getDimensionPixelSize(0, 0) / 2;

        mScrollThreshold= Uitools.dip2px(context,50);
        System.out.println("mScrollDistance mScrollThreshold " + mScrollThreshold);
        //a.recycle();
    }

    //Called before a nested scroll event. Return true to declare interest
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       View child, View directTargetChild, View target,
                                       int nestedScrollAxes) {
        //We have to declare interest in the scroll to receive further events
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //Called before the scrolling child consumes the event
    // We can steal all/part of the event by filling in the consumed array
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  View child, View target,
                                  int dx, int dy,
                                  int[] consumed) {
        //Determine direction changes here
        if (dy > 0 && mScrollingDirection != DIRECTION_UP) {
            mScrollingDirection = DIRECTION_UP;
            mScrollDistance = 0;
            System.out.println("mScrollDistance PreScroll " + mScrollDistance);
        } else if (dy < 0 && mScrollingDirection != DIRECTION_DOWN) {
            mScrollingDirection = DIRECTION_DOWN;
            mScrollDistance = 0;
            System.out.println("mScrollDistance PreScroll " + mScrollDistance);
        }
//        else if(dy > 0 && mScrollingDirection == DIRECTION_UP&&isHidden==false){
            //mScrollingDirection = DIRECTION_DOWN;
//            mScrollDistance = 0;
//        }
    }

    //Called after the scrolling child consumes the event, with amount consumed
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               View child, View target,
                               int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        //Consumed distance is the actual distance traveled by the scrolling view


        System.out.println("mScrollDistance Scroll " + mScrollDistance+" dyConsumed "+dyConsumed);
        mScrollDistance += dyConsumed;
        if (mScrollDistance > mScrollThreshold
                && mScrollTrigger != DIRECTION_UP) {
            //Hide the target view
            mScrollTrigger = DIRECTION_UP;
            restartAnimator(child, -getTargetHideValue(coordinatorLayout, child));
            isHidden=true;
        } else if (mScrollDistance < -mScrollThreshold/4
                && mScrollTrigger != DIRECTION_DOWN) {
            //Return the target view
            mScrollTrigger = DIRECTION_DOWN;
            restartAnimator(child, 0f);
            isHidden=false;
        }
//        else if(mScrollDistance > mScrollThreshold&& mScrollingDirection == DIRECTION_UP&&isHidden==false){
            //mScrollTrigger = DIRECTION_DOWN;
//            restartAnimator(child, -getTargetHideValue(coordinatorLayout, child));
//        }
    }


    //Called after the scrolling child handles the fling
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout,
                                 View child, View target,
                                 float velocityX, float velocityY,
                                 boolean consumed) {
        //We only care when the target view is already handling the fling
        if (consumed) {
        System.out.println("mScrollDistance Fling " + mScrollDistance);
            if (velocityY > 0 && mScrollTrigger != DIRECTION_UP&&mScrollDistance >= mScrollThreshold) {
                mScrollTrigger = DIRECTION_UP;
                restartAnimator(child, -getTargetHideValue(coordinatorLayout, child));
            } else if (velocityY < 0 && mScrollTrigger != DIRECTION_DOWN) {
                mScrollTrigger = DIRECTION_DOWN;
                restartAnimator(child, 0f);
                isHidden=false;
            }
//            else if(velocityY > 0 && mScrollingDirection == DIRECTION_UP&&isHidden==false){
                //mScrollTrigger = DIRECTION_DOWN;
//                restartAnimator(child, -getTargetHideValue(coordinatorLayout, child));
//                isHidden=true;
//            }
        }

        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    /* Helper Methods */

    //Helper to trigger hide/show animation
    public void restartAnimator(View target, float value) {
//        if(value==0f){
//            System.out.println("mScrollDistance 向下");
//        }else{
//            System.out.println("mScrollDistance 向上");
//        }
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        mAnimator = ObjectAnimator
                .ofFloat(target, View.TRANSLATION_Y, value)
                .setDuration(250);
        mAnimator.start();
    }

    private float getTargetHideValue(ViewGroup parent, View target) {
        if (target instanceof AppBarLayout) {
            return -target.getHeight();
        } else if (target instanceof FloatingActionButton) {
            return parent.getHeight() - target.getTop();
        }else {
            return target.getHeight();
        }
    }


    public void resetScrollTrigger(){
        mScrollTrigger = DIRECTION_DOWN;
        mScrollDistance=0;
    }
}

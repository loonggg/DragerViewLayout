package com.loonggg.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.loonggg.lib.util.SharedPreferencesUtil;

/**
 * Created by loonggg on 2017/3/18.
 */

public class DragerViewLayout extends RelativeLayout {
    private ViewDragHelper viewDragHelper;
    private boolean drager = true;
    private Context mContext;

    public DragerViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        if (drager) {
            viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
                @Override
                public boolean tryCaptureView(View child, int pointerId) {
                    return true;
                }

                @Override
                public int clampViewPositionHorizontal(View child, int left, int dx) {
                    /**
                     * 如果横向滑动超过右面边界的时候，控制子视图不能够越界
                     */
                    if (left + child.getMeasuredWidth() >= getMeasuredWidth()) {
                        return getMeasuredWidth() - child.getMeasuredWidth();
                    }
                    /**
                     * 如果横向滑动超过左面边界的时候，控制子视图不能够越界
                     */
                    if (left <= 0) {
                        return 0;
                    }
                    return left;
                }

                @Override
                public int clampViewPositionVertical(View child, int top, int dy) {
                    /**
                     * 控制下边界，子视图不能够越界
                     */
                    if (child.getMeasuredHeight() + top > getMeasuredHeight()) {
                        return getMeasuredHeight() - child.getMeasuredHeight();
                    }
                    /**
                     * 控制上边界，子视图不能够越界
                     */
                    if (top <= 0) {
                        return 0;
                    }
                    return top;
                }


                @Override
                public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                    super.onViewPositionChanged(changedView, left, top, dx, dy);
                    SharedPreferencesUtil.saveData(mContext, getChildIndex(changedView) + "", left + "#" + top);
                }

                //当手指释放的时候回调
                @Override
                public void onViewReleased(View releasedChild, float xvel, float yvel) {
                }

                //如果你拖动View添加了clickable = true 或者为 button 会出现拖不动的情况，原因是拖动的时候onInterceptTouchEvent方法，
                // 判断是否可以捕获，而在判断的过程中会去判断另外两个回调的方法getViewHorizontalDragRange和getViewVerticalDragRange，
                // 只有这两个方法返回大于0的值才能正常的捕获。如果未能正常捕获就会导致手势down后面的move以及up都没有进入到onTouchEvent
                @Override
                public int getViewHorizontalDragRange(View child) {
                    return getMeasuredWidth() - child.getMeasuredWidth();
                }

                @Override
                public int getViewVerticalDragRange(View child) {
                    return getMeasuredHeight() - child.getMeasuredHeight();
                }
            });
            viewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (drager) {
            return viewDragHelper.shouldInterceptTouchEvent(event);
        } else {
            return super.onInterceptTouchEvent(event);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drager) {
            viewDragHelper.processTouchEvent(event);
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (drager) {
            if (viewDragHelper.continueSettling(true)) {
                invalidate();
            }
        }
    }

    /**
     * 查询正在拖动的视图所在父容器的索引位置
     *
     * @param srcView
     * @return
     */
    private int getChildIndex(View srcView) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (srcView == view) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            String xy = (String) SharedPreferencesUtil.getData(mContext, i + "", "0");
            View child = getChildAt(i);
            if (!xy.equals("0")) {
                String[] xys = xy.split("#");
                if (xys.length == 2) {
                    child.layout(Integer.parseInt(xys[0]), Integer.parseInt(xys[1]), child.getMeasuredWidth() + Integer.parseInt(xys[0]), child.getMeasuredHeight() + Integer.parseInt(xys[1]));
                }
            }
        }
    }

    /**
     * 设置存储视图位置文件的路径和文件名字
     *
     * @param path
     * @param name
     */
    public void setFilePathAndName(String path, String name) {
        SharedPreferencesUtil.FILE_PATH = path;
        SharedPreferencesUtil.FILE_NAME = name;
    }

    /**
     * 设置视图是否可以拖拽
     *
     * @param flag
     */
    public void isDrager(boolean flag) {
        this.drager = flag;
    }
}

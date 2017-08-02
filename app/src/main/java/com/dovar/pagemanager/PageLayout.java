package com.dovar.pagemanager;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by heweizong on 2017/5/17.
 */

public class PageLayout extends FrameLayout {
    private View mContentView;
    private View mLoadingView;
    private View mServerErrorView;
    private View mEmptyView;
    private View mOutdateView;
    private View mUnloginView;
    private View mNoNetworkView;
    private LayoutInflater mInflater;

    public PageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
    }

    public PageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PageLayout(Context context) {
        this(context, null);
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public void showLoading() {
        if (isMainThread()) {
            showView(mLoadingView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mLoadingView);
                }
            });
        }
    }

    public void showServerError() {
        if (isMainThread()) {
            showView(mServerErrorView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mServerErrorView);
                }
            });
        }

    }

    public void showOutdate() {
        if (isMainThread()) {
            showView(mOutdateView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mOutdateView);
                }
            });
        }
    }

    public void showUnLogin() {
        if (isMainThread()) {
            showView(mUnloginView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mUnloginView);
                }
            });
        }
    }

    public void showNoNetwork() {
        if (isMainThread()) {
            showView(mNoNetworkView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mNoNetworkView);
                }
            });
        }
    }

    public void showContent() {
        if (isMainThread()) {
            showView(mContentView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mContentView);
                }
            });
        }
    }

    public void showEmpty() {
        if (isMainThread()) {
            showView(mEmptyView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(mEmptyView);
                }
            });
        }
    }

    private void showTargetIfSame(View view, View target) {
        if (view == target) {
            target.setVisibility(VISIBLE);
        } else {
            if (target != null) {
                target.setVisibility(GONE);
            }
        }
    }

    private void showView(View view) {
        if (view == null) return;

        showTargetIfSame(view, mContentView);
        showTargetIfSame(view, mLoadingView);
        showTargetIfSame(view, mServerErrorView);
        showTargetIfSame(view, mEmptyView);
        showTargetIfSame(view, mNoNetworkView);
        showTargetIfSame(view, mOutdateView);
        showTargetIfSame(view, mUnloginView);
    }

    public View setLoadingView(int layoutId) {
        View newView = mInflater.inflate(layoutId, this, false);
        if (newView != null) {
            if (mLoadingView != null) {
                removeView(mLoadingView);
            }
            addView(newView);
            mLoadingView = newView;
        }
        return newView;
    }

    public View setEmptyView(int layoutId) {
        View newView = mInflater.inflate(layoutId, this, false);
        if (newView != null) {
            if (mEmptyView != null) {
                removeView(mEmptyView);
            }
            addView(newView);
            mEmptyView = newView;
        }
        return newView;
    }

    public View setServerErrorView(int layoutId) {
        View newView = mInflater.inflate(layoutId, this, false);
        if (newView != null) {
            if (mServerErrorView != null) {
                removeView(mServerErrorView);
            }
            addView(newView);
            mServerErrorView = newView;
        }
        return newView;
    }

    public View setOutdateView(int layoutId) {
        View newView = mInflater.inflate(layoutId, this, false);
        if (newView != null) {
            if (mOutdateView != null) {
                removeView(mOutdateView);
            }
            addView(newView);
            mOutdateView = newView;
        }
        return newView;
    }

    public View setUnLoginView(int layoutId) {
        View newView = mInflater.inflate(layoutId, this, false);
        if (newView != null) {
            if (mUnloginView != null) {
                removeView(mUnloginView);
            }
            addView(newView);
            mUnloginView = newView;
        }
        return newView;
    }

    public View setNoNetworkView(int layoutId) {
        View newView = mInflater.inflate(layoutId, this, false);
        if (newView != null) {
            if (mNoNetworkView != null) {
                removeView(mNoNetworkView);
            }
            addView(newView);
            mNoNetworkView = newView;
        }
        return newView;
    }

    public View setContentView(View newView) {
        if (mContentView != null) {
            removeView(mContentView);
        }
        //需要移除原有的LayoutParams，仅保留宽高属性
        //因为将pageLayout加入时已经应用oldContent原有的LayoutParams
        ViewGroup.LayoutParams oldLp=newView.getLayoutParams();
        if (oldLp!=null){
            newView.setLayoutParams(new FrameLayout.LayoutParams(oldLp.width,oldLp.height));
        }
        addView(newView);
        mContentView = newView;
        return newView;
    }

    public View getServerErrorView() {
        return mServerErrorView;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public View getOutdateView() {
        return mOutdateView;
    }

    public View getUnLoginView() {
        return mUnloginView;
    }

    public View getNoNetworkView() {
        return mNoNetworkView;
    }

    public void removeContentView() {
        if (mContentView != null) {
            removeView(mContentView);
        }
    }
}

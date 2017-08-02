package com.dovar.pagemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

/**
 * Created by heweizong on 2017/5/17.
 * 状态页面管理器
 */

public class PageManager {
    private static int BASE_LOADING_LAYOUT = R.layout.pager_loading;//网络加载中
    private static int BASE_SERVER_ERROR_LAYOUT = R.layout.pager_error;//server error
    private static int BASE_EMPTY_LAYOUT = R.layout.pager_empty;//没有内容
    private static int BASE_NONETWORK_LAYOUT = R.layout.pager_no_network;//没有网络，请检查网络后重试
    private static int BASE_OUTDATE_LAYOUT = R.layout.pager_outdate;//资源已下架
    private static int BASE_UNLOGIN_LAYOUT = R.layout.pager_unlogin;//未登录，点击跳转到登录

    private PageLayout mPageLayout;
    private boolean isLoading;//加载中
    private boolean isOutdating;//当前显示资源已下架


    /**
     * 重置状态布局
     */
    public static void initStateView(int layoutIdOfEmpty, int layoutIdOfLoading, int layoutIdOfError, int layoutIdOfNoNetwork, int layoutIdOfOutdate, int layoutIdOfUnLogin) {
        if (layoutIdOfEmpty > 0) {
            BASE_EMPTY_LAYOUT = layoutIdOfEmpty;
        }

        if (layoutIdOfLoading > 0) {
            BASE_LOADING_LAYOUT = layoutIdOfLoading;
        }

        if (layoutIdOfError > 0) {
            BASE_SERVER_ERROR_LAYOUT = layoutIdOfError;
        }
        if (layoutIdOfNoNetwork > 0) {
            BASE_NONETWORK_LAYOUT = layoutIdOfNoNetwork;
        }

        if (layoutIdOfOutdate > 0) {
            BASE_OUTDATE_LAYOUT = layoutIdOfOutdate;
        }
        if (layoutIdOfUnLogin > 0) {
            BASE_UNLOGIN_LAYOUT = layoutIdOfUnLogin;
        }

    }

    /**
     * 必须为activity或者view.如果是view,则该view对象必须有parent
     *
     * @return 当前页面的状态管理器
     */
    public static PageManager init(final Activity mActivity, PageListener mListener) {
        return new PageManager(mActivity, mListener);
    }

    /**
     * 直接传入fragment时依然存在问题，暂不采用此方法，可使用下面传View的init()方法
     */
//    public static PageManager init(final Fragment mFragment, PageListener mListener) {
//        return new PageManager(mFragment, mListener);
//    }
    public static PageManager init(final View mView, PageListener mListener) {
        return new PageManager(mView, mListener, true);
    }

    public static PageManager init(final View mView, PageListener mListener, boolean showLoadingByDefault) {
        return new PageManager(mView, mListener, showLoadingByDefault);
    }

    public void showContent() {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        mPageLayout.showContent();
    }

    public void showLoading() {
        if (mPageLayout == null) return;

        isLoading = true;
        isOutdating = false;

        mPageLayout.showLoading();
    }

    public void showServerError() {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        mPageLayout.showServerError();
    }

    public void showEmpty() {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        mPageLayout.showEmpty();
    }

    public void showEmpty(String msg) {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        View empty = mPageLayout.getEmptyView();
        if (msg != null) {
            TextView tv_tip = (TextView) empty.findViewById(R.id.tv_tip);
            tv_tip.setText(msg);
        }

        TextView tv_jump = (TextView) empty.findViewById(R.id.tv_jump);
        if (tv_jump != null) {
            tv_jump.setVisibility(View.GONE);
        }
        mPageLayout.showEmpty();
    }

    public void showEmpty(String msg, View.OnClickListener mClickListener) {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        if (mPageLayout == null) {
            Log.d("hwz", "showEmpty: null");
        }

        View empty = mPageLayout.getEmptyView();
        if (empty == null) return;
        if (msg != null) {
            TextView tv_tip = (TextView) empty.findViewById(R.id.tv_tip);
            if (tv_tip != null) {
                tv_tip.setText(msg);
            }
        }

        TextView tv_jump = (TextView) empty.findViewById(R.id.tv_jump);
        if (tv_jump != null) {
            tv_jump.setOnClickListener(mClickListener);
            tv_jump.setVisibility(View.VISIBLE);
        }

        mPageLayout.showEmpty();
    }

    public void showEmpty(String msg, String buttonTip, View.OnClickListener mClickListener) {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        View empty = mPageLayout.getEmptyView();
        if (empty == null) return;
        if (msg != null) {
            TextView tv_tip = (TextView) empty.findViewById(R.id.tv_tip);
            if (tv_tip != null) {
                tv_tip.setText(msg);
            }
        }

        TextView tv_jump = (TextView) empty.findViewById(R.id.tv_jump);
        if (tv_jump != null) {
            if (buttonTip != null) {
                tv_jump.setText(buttonTip);
            }
            tv_jump.setOnClickListener(mClickListener);
            tv_jump.setVisibility(View.VISIBLE);
        }

        mPageLayout.showEmpty();
    }


    public void showOutdate() {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = true;

        mPageLayout.showOutdate();
    }

    public void showNoNetwork() {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        mPageLayout.showNoNetwork();
    }

    public void showNoNetwork(View.OnClickListener mClickListener) {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        View noNetView = mPageLayout.getNoNetworkView();
        if (noNetView == null) return;

        View view = noNetView.findViewById(R.id.tv_tip);
        if (view != null) {
            view.setOnClickListener(mClickListener);
        }
        mPageLayout.showNoNetwork();
    }

    public void showUnLogin() {
        if (mPageLayout == null) return;

        isLoading = false;
        isOutdating = false;

        mPageLayout.showUnLogin();
    }

    public PageManager(Activity mActivity, PageListener mPageListener) {
        ViewGroup contentParent = (ViewGroup) mActivity.findViewById(android.R.id.content);
        if (contentParent == null) {
            Log.d("pageManager", "contentParent==null");
            return;
        }
        setupPageManager(contentParent, 0, mPageListener, true);
    }

    public PageManager(Fragment mFragment, PageListener mPageListener) {
        if (mFragment.getView() != null) {
            ViewGroup contentParent = (ViewGroup) (mFragment.getView().getParent());
            if (contentParent == null) {
                Log.d("pageManager", "contentParent==null");
                return;
            }
            setupPageManager(contentParent, 0, mPageListener, true);
        } else {
            Log.d("pageManager", "fragment has no contentView");
        }
    }

    public PageManager(View container, PageListener mPageListener, boolean showLoadingByDefault) {
        ViewGroup contentParent = (ViewGroup) (container.getParent());
        if (contentParent == null) {
            Log.d("pageManager", "contentParent==null");
            return;
        }
        int childCount = contentParent.getChildCount();
        int index = 0;
        for (int i = 0; i < childCount; i++) {
            if (contentParent.getChildAt(i) == container) {
                index = i;
                break;
            }
        }
        //setup content layout
        setupPageManager(contentParent, index, mPageListener, showLoadingByDefault);
    }

    private void setupPageManager(ViewGroup contentParent, int index, final PageListener mPageListener, boolean showLoadingByDefault) {
        View oldContent = contentParent.getChildAt(index);

        contentParent.removeView(oldContent);

        mPageLayout = new PageLayout(oldContent.getContext());

        ViewGroup.LayoutParams lp = oldContent.getLayoutParams();
        ViewParent oldParent = mPageLayout.getParent();
        if (oldParent != null) {
            if (oldParent instanceof ViewGroup) {
                ((ViewGroup) oldParent).removeView(mPageLayout);
            }
        }
        contentParent.addView(mPageLayout, index, lp);

        mPageLayout.setContentView(oldContent);
        // setup loading,retry,empty layout
        mPageLayout.setLoadingView(BASE_LOADING_LAYOUT);
        mPageLayout.setServerErrorView(BASE_SERVER_ERROR_LAYOUT);
        mPageLayout.setEmptyView(BASE_EMPTY_LAYOUT);
        mPageLayout.setOutdateView(BASE_OUTDATE_LAYOUT);
        mPageLayout.setNoNetworkView(BASE_NONETWORK_LAYOUT);
        mPageLayout.setUnLoginView(BASE_UNLOGIN_LAYOUT);

        //callback
        if (mPageListener != null) {
            mPageListener.setServerErrorEvent(mPageLayout.getServerErrorView());
            mPageListener.setLoadingEvent(mPageLayout.getLoadingView());
            mPageListener.setEmptyEvent(mPageLayout.getEmptyView());
            mPageListener.setOutdateEvent(mPageLayout.getOutdateView());
            mPageListener.setNoNetworkEvent(mPageLayout.getNoNetworkView());
            mPageListener.setUnLoginEvent(mPageLayout.getUnLoginView());
        }

        if (showLoadingByDefault) {
            showLoading();
        } else {
            showContent();
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public boolean isOutdating() {
        return isOutdating;
    }

    private static boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null) {
                return false;
            } else {
                if (info.isAvailable()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 当判断当前手机没有网络时选择是否打开网络设置
     */
    private static AlertDialog showNoNetWorkDlg(final Object container) {
        AlertDialog dialog = null;
        Context context = null;

        if (container instanceof Activity) {
            context = (Activity) container;

        } else if (container instanceof Fragment) {
            context = ((Fragment) container).getActivity();

        } else if (container instanceof View) {
            context = ((View) container).getContext();
        }

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final Activity finalActivity = (Activity) context;

            dialog = builder        //
                    .setTitle("提示")            //
                    .setMessage("当前无网络").setPositiveButton("去设置", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 跳转到系统的网络设置界面
                            //intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);

                            finalActivity.startActivity(intent);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("知道了", null).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dialog;
    }

}

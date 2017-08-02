package com.dovar.pagemanager;

import android.view.View;

/**
 * Created by heweizong on 2017/5/17.
 * 需要在哪种状态页面上增加点击或其他类型事件时，就重写对应的setXxxEvent方法
 * eg:未登录时页面需要增加点击事件，重写setUnLoginEvent()即可
 */

public abstract class PageListener {
    public void setServerErrorEvent(View serverErrorView) {

    }

    public void setLoadingEvent(View loadingView) {

    }

    public void setEmptyEvent(View emptyView) {

    }

    public void setOutdateEvent(View outdateView) {

    }

    public void setUnLoginEvent(View unLoginView) {

    }

    public void setNoNetworkEvent(View noNetworkView) {

    }

}

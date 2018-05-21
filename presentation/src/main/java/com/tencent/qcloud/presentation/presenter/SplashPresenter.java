package com.tencent.qcloud.presentation.presenter;

import android.os.Handler;

import com.tencent.qcloud.presentation.viewfeatures.SplashView;


/**
 * 闪屏界面逻辑
 */
public class SplashPresenter {
    SplashView view;
    private static final String TAG = SplashPresenter.class.getSimpleName();

    public SplashPresenter(SplashView view) {
        this.view = view;
    }


    /**
     * 加载页面逻辑
     */
    public void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if (view.isUserLogin() && !view.isIMLogin()) {
                    view.imLogin();
                }else{
                    view.navToHome();
                }

            }
        }, 1000);
    }


}

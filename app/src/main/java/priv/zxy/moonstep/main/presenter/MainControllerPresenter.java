package priv.zxy.moonstep.main.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import priv.zxy.moonstep.main.module.biz.IMainBiz;
import priv.zxy.moonstep.main.module.biz.mainBiz;
import priv.zxy.moonstep.main.view.IMainView;

public class MainControllerPresenter {

    private IMainBiz mainBiz;
    private IMainView mainView;
    private Context mContext;
    private Activity mActivity;

    public MainControllerPresenter(IMainView mainView, Context mContext, Activity mActivity){
        this.mainView = mainView;
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mainBiz = new mainBiz();
    }

}

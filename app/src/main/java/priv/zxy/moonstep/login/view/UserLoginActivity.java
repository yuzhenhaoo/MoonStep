package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

//import com.mob.MobSDK;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.ref.WeakReference;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.login.presenter.UserLoginPresenter;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;
import priv.zxy.moonstep.util.ShowErrorReasonUtil;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.network.NetworkManager;
import priv.zxy.network.bean.Network;
import priv.zxy.network.type.NetType;
import priv.zxy.network.utils.Constants;

/**
 *  Created by Zxy on 2018/9/23
 */

public class UserLoginActivity extends BaseActivity implements IUserLoginView {

    private Button weiXinBt;
    private Button qqBt;
    private Button weiBoBt;
    private MaterialEditText accountEt;
    private MaterialEditText passwordEt;
    private Button clickBt;
    private Button fgPwdBt;
    private Button rgiPhoneBt;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;

    private Activity mActivity;
    private Context mContext;

    private UserLoginPresenter userLoginPresenter;

    //加载动画资源文件
    Animation shake;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 关于clickLogin中的逻辑处理，本来showLoading和hideLoading都是要放在presenter中减少activity的冗余度的，但是这里要做网络请求的话，必须要用到
     * handler来处理UI界面的变化，不然可能会引起ANR，然而handler又必须使用在View中，所以showLoading和hideLoading不得不写在activity中。
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        //MobSDK的初始化
//        MobSDK.init(this);
        mHandler = new MyHandler(this);
        weiXinBt = (Button) findViewById(R.id.weiXinBt);
        qqBt = (Button) findViewById(R.id.qqBt);
        weiBoBt = (Button) findViewById(R.id.weiBoBt);
        accountEt = (MaterialEditText) findViewById(R.id.accountEt);
        passwordEt = (MaterialEditText) findViewById(R.id.passwordEt);
        clickBt = (Button) findViewById(R.id.clickLogUtilinBt);
        fgPwdBt = (Button) findViewById(R.id.forgetPasswordBt);
        rgiPhoneBt = (Button) findViewById(R.id.registerPhoneBt);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        mActivity = this;
        mContext = this.getApplicationContext();

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        userLoginPresenter = new UserLoginPresenter(this);

        initAccountAndPassword();

        accountEt.requestFocus();

        userLoginPresenter.hideLoading();

        /*
         * 我们需要注册监听，相当于让观察者(NetStateReceiver接收网络请求)
         *     添加所有的被观察者对象(Activity, List<AnnotationMethod>)
         */
        NetworkManager.getInstance().registerObserver(this);

        if (NetworkManager.getInstance().hasNetwork()) {
            LogUtil.d(Constants.LOG_TAG, "onCreate() --> hasNetwork() --> true ");
        }


        clickBt.setOnTouchListener((v, event) -> {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    clickBt.startAnimation(shake);
                    break;
                case MotionEvent.ACTION_UP:
                    userLoginPresenter.showLoading();
                    new Thread(() -> {
                        try {
                            userLoginPresenter.Login();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    break;
                default:
                    break;
            }
            return true;
        });

        rgiPhoneBt.setOnClickListener(v -> {
            rgiPhoneBt.startAnimation(shake);
            userLoginPresenter.toConfirmPhoneActivity();
        });

        fgPwdBt.setOnTouchListener((v, event) -> {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    fgPwdBt.startAnimation(shake);
                    break;
                case MotionEvent.ACTION_UP:
                    userLoginPresenter.toForgetPasswordActivity();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void initAccountAndPassword() {
        accountEt.setText(SharedPreferencesUtil.readLoginInfo().get(SharedConstant.PHONE_NUMBER));
        passwordEt.setText(SharedPreferencesUtil.readLoginInfo().get(SharedConstant.PASSWORD));
    }

    @Network(netType = NetType.AUTO)
    public void network(NetType netType){
        switch (netType) {
            case WIFI:
                //告知用户已经恢复WIFI网络
                Toast.makeText(mActivity, "您的WIFI已经恢复", Toast.LENGTH_SHORT).show();
                break;
            case CMNET:
            case CMWAP:
                Toast.makeText(mActivity, "您的数据流量已经打开", Toast.LENGTH_SHORT).show();
                LogUtil.d(this.getClass().getName(), "网络已经连接， 网络类型:" + netType.name());
                break;
            case NONE:
                Toast.makeText(mActivity, "您的网络未连接", Toast.LENGTH_SHORT).show();
                LogUtil.d(this.getClass().getName(), "");
            default:
        }
    }

    @Override
    public String getUserPhoneNumber() {
        if (accountEt.getText()!=null){
            return accountEt.getText().toString();
        }else{
            return "";
        }
    }

    @Override
    public String getUserPassword() {
        if (passwordEt.getText()!=null){
            return passwordEt.getText().toString();
        }else{
            return "";
        }
    }

    @Override
    public void showLoading() {
        progressBar.show();
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
        qqBt.setEnabled(false);
        weiBoBt.setEnabled(false);
        weiXinBt.setEnabled(false);
        clickBt.setEnabled(false);
        fgPwdBt.setEnabled(false);
        rgiPhoneBt.setEnabled(false);
        accountEt.setEnabled(false);
        passwordEt.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
        qqBt.setEnabled(true);
        weiBoBt.setEnabled(true);
        weiXinBt.setEnabled(true);
        clickBt.setEnabled(true);
        fgPwdBt.setEnabled(true);
        rgiPhoneBt.setEnabled(true);
        accountEt.setEnabled(true);
        passwordEt.setEnabled(true);
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    @Override
    public void toConfirmPhoneActivity() {
        Intent intent = new Intent(this, VerifyPhoneActivity.class);
//        Intent intent = new Intent(this, UserRegisterActivity.class);//测试用
        startActivity(intent);
    }

    @Override
    public void toForgetPasswordActivity() {
        Intent intent = new Intent(this, ForgetPasswordSendMessageActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorTip(ErrorCodeEnum errorCode) {
        ShowErrorReasonUtil.getInstance(mActivity).show(errorCode);
    }

    @Override
    public void handleSendMessage(){
        mHandler.sendEmptyMessage(0x01);
    }

    @Override
    public void setLoginPreferences(String username, String password) {
        SharedPreferencesUtil.setSuccessLoginInfo(username, password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除所有的消息（防止Handler造成内存泄漏）
        mHandler.removeCallbacksAndMessages(null);
        NetworkManager.getInstance().unRegisterObserver(this);
//        NetworkManager.getInstance().unRegisterAllObserver();
    }

    /**
     * 声明一个Handler的静态内部类可以防止持有当前activity的引用，这样的话，当GC回收垃圾的时候，就可以对当前activity进行回收，不会造成内存泄漏
     *     声明一个弱引用，是为了可以调用activity中的方法，否则想在handle中处理，必须全部声明为静态的。
     *     再一个，弱引用本身就是在GC检测到的 时候就回收。
     */
    private static class MyHandler extends Handler{

        private WeakReference<UserLoginActivity> mActivity;

        MyHandler(UserLoginActivity activity){
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UserLoginActivity activity = mActivity.get();
            if (activity != null){
                activity.hideLoading();
            }
        }
    }
}

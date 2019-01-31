package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.lang.ref.WeakReference;
import java.util.Map;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.presenter.UserLoginPresenter;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;
import priv.zxy.moonstep.util.ShowErrorReasonUtil;
import priv.zxy.network.NetworkManager;
import priv.zxy.network.bean.Network;
import priv.zxy.network.type.NetType;
import priv.zxy.network.utils.Constants;

/**
 * @author 张晓翼
 * @createTime 2019/1/29 0029
 * @Describe 实际的登录页面
 */
public class LoginActivity1 extends BaseActivity implements IUserLoginView{
    private Button back;
    private MaterialEditText account;
    private MaterialEditText password;
    private Button landing;
    private Button forgetPassword;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private UserLoginPresenter userLoginPresenter;
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        initView();
        initEvent();
    }

    private void initView() {
        //MobSDK的初始化
//        MobSDK.init(this);
        back = (Button) findViewById(R.id.back);
        account = (MaterialEditText) findViewById(R.id.account);
        password = (MaterialEditText) findViewById(R.id.password);
        forgetPassword = (Button) findViewById(R.id.forget_password);
        landing = (Button) findViewById(R.id.landing);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        userLoginPresenter = new UserLoginPresenter(this);

        initAccountAndPassword();
        account.requestFocus();
        userLoginPresenter.hideLoading();
        mHandler = new MyHandler(this);

        /*
         * 我们需要注册监听，相当于让观察者(NetStateReceiver接收网络请求)
         *     添加所有的被观察者对象(Activity, List<AnnotationMethod>)
         */
        NetworkManager.getInstance().registerObserver(this);

        if (NetworkManager.getInstance().hasNetwork()) {
            LogUtil.d(Constants.LOG_TAG, "onCreate() --> hasNetwork() --> true ");
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        back.setOnClickListener(v->finish());

        landing.setOnTouchListener((v, event) -> {
            switch(event.getAction()){
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

        forgetPassword.setOnTouchListener((v, event) -> {
            switch(event.getAction()){
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
        Map<String, String> data = SharedPreferencesUtil.readLoginInfo();
        // 如果之前登录失败了，这里就会返回null
        if (data == null) {
            return;
        }
        account.setText(data.get(SharedConstant.PHONE_NUMBER));
        password.setText(data.get(SharedConstant.PASSWORD));
    }

    @Network(netType = NetType.AUTO)
    public void network(NetType netType){
        switch (netType) {
            case WIFI:
                //告知用户已经恢复WIFI网络
                Toast.makeText(this, "您的WIFI已经恢复", Toast.LENGTH_SHORT).show();
                break;
            case CMNET:
            case CMWAP:
                Toast.makeText(this, "您的数据流量已经打开", Toast.LENGTH_SHORT).show();
                LogUtil.d(this.getClass().getName(), "网络已经连接， 网络类型:" + netType.name());
                break;
            case NONE:
                Toast.makeText(this, "您的网络未连接", Toast.LENGTH_SHORT).show();
                LogUtil.d(this.getClass().getName(), "");
            default:
        }
    }

    public String getUserPhoneNumber() {
        if (account.getText()!=null){
            return account.getText().toString();
        }else{
            return "";
        }
    }

    public String getUserPassword() {
        if (password.getText()!=null){
            return password.getText().toString();
        }else{
            return "";
        }
    }

    public void showLoading() {
        progressBar.show();
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
        landing.setEnabled(false);
        forgetPassword.setEnabled(false);
        account.setEnabled(false);
        password.setEnabled(false);
    }

    public void hideLoading() {
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
        landing.setEnabled(true);
        forgetPassword.setEnabled(true);
        account.setEnabled(true);
        password.setEnabled(true);
    }

    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }

    public void toForgetPasswordActivity() {
        Intent intent = new Intent(this, ForgetPasswordSendMessageActivity.class);
        startActivity(intent);
    }

    public void showErrorTip(ErrorCodeEnum errorCode) {
        ShowErrorReasonUtil.getInstance(this).show(errorCode);
    }

    public void handleSendMessage(){
        mHandler.sendEmptyMessage(0x01);
    }

    public void setLoginPreferences(String username, String password) {
        SharedPreferencesUtil.setSuccessLoginInfo(username, password);
    }

    protected void onDestroy() {
        super.onDestroy();
        // 移除所有的消息（防止Handler造成内存泄漏）
        mHandler.removeCallbacksAndMessages(null);
        NetworkManager.getInstance().unRegisterObserver(this);
//        NetworkManager.getInstance().unRegisterAllObserver();
    }

    private static class MyHandler extends Handler {

        private WeakReference<LoginActivity1> mActivity;

        MyHandler(LoginActivity1 activity){
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoginActivity1 activity = mActivity.get();
            if (activity != null){
                activity.hideLoading();
            }
        }
    }
}

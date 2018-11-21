package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

//import com.mob.MobSDK;
import com.rengwuxian.materialedittext.MaterialEditText;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.kernel.BaseActivity;
import priv.zxy.moonstep.login.presenter.UserLoginPresenter;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.utils.ShowErrorReason;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.main.view.MainActivity;

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

    private UserLoginPresenter userLogUtilinPresenter;

    //加载动画资源文件
    Animation shake;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideLoading();
        }
    };

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

        userLogUtilinPresenter = new UserLoginPresenter(this);

        userLogUtilinPresenter.initAccountAndPassword(SharedPreferencesUtil.getInstance(mContext));

        accountEt.requestFocus();

        userLogUtilinPresenter.hideLoading();

        clickBt.setOnTouchListener((v, event) -> {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    clickBt.startAnimation(shake);
//                        clickBt.setClickable(false);//设置不能再次点击，当失败了设置为恢复点击效果
                    break;
                case MotionEvent.ACTION_UP:
                    userLogUtilinPresenter.showLoading();
                    new Thread(() -> {
                        try {
                            userLogUtilinPresenter.Login();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                    break;
            }
            return true;
        });

        rgiPhoneBt.setOnClickListener(v -> {
            rgiPhoneBt.startAnimation(shake);
            userLogUtilinPresenter.toConfirmPhoneActivity();
        });

        fgPwdBt.setOnTouchListener((v, event) -> {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    fgPwdBt.startAnimation(shake);
                    break;
                case MotionEvent.ACTION_UP:
                    userLogUtilinPresenter.toForgetPasswordActivity();
                    break;
            }
            return true;
        });
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
    public void initAccount(SharedPreferencesUtil preference) {
        accountEt.setText(SharedPreferencesUtil.getInstance(mContext).readLoginInfo().get("UserName"));
    }

    @Override
    public void initPassword(SharedPreferencesUtil preference) {
        passwordEt.setText(SharedPreferencesUtil.getInstance(mContext).readLoginInfo().get("PassWd"));
    }

    @Override
    public void showErrorTip(ErrorCode errorCode) {
        ShowErrorReason.getInstance(mActivity).show(errorCode);
    }

    @Override
    public void handleSendMessage(){
        mHandler.sendEmptyMessage(0x01);
    }

    @Override
    public void setLoginPreferences(String username, String passwordEt) {
        SharedPreferencesUtil.getInstance(mContext).setSuccessedLoginAccountAndPassword(username, passwordEt);
    }
}

package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.ChangePasswordUtil;
import priv.zxy.moonstep.Utils.ToastUtil;
import priv.zxy.moonstep.Utils.ShowErrorReason;
import priv.zxy.moonstep.login.module.bean.ErrorCode;
import priv.zxy.moonstep.login.presenter.UserForgetPasswordPresenter;

/**
 * Created by Zxy on 2018/9/21
 */

public class ForgetPasswordActivity extends AppCompatActivity implements IForgetPasswordView {

    private EditText phoNum;
    private Button sendNum;
    private EditText checkMessage;
    private EditText password;
    private EditText passwordCheck;
    private Button clickCheck;
    private ImageView backButton;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private Context mContext;
    private Activity mActivity;
    //加载动画资源文件
    private Animation shake;
    private UserForgetPasswordPresenter userForgetPasswordPresenter;
    private ToastUtil toastUtil;
    private String country = "86";

    //Mob短信端的事件触发机制
    EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            mHandler.sendMessage(msg);
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideLoading();
        }
    };

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    //提交验证码成功
                    //提示用户密码修改成功了，可以返回登陆页面
                    ChangePasswordUtil changePasswordUtil = new ChangePasswordUtil(mContext, mActivity);
                    changePasswordUtil.changePassword(getPhoneNumber(), getPassword());

                    /**
                     * 我不知道这里是否开辟了一个新的线程
                     * 如果在加载的时候，刷新界面的显示出现了明显的卡顿，说明这里应该是在当前的UI线程内运行的，改进就是直接new一个新的线程并在里面进行操作。
                     */
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean isSuccess = changePasswordUtil.isSuccess;
                    if(isSuccess){
                        toastUtil.showToast("恭喜您，您的密码已经修改成功");
                        toLoginActivity();
                    }else{
                        ErrorCode errorCode = changePasswordUtil.errorCode;
                        ShowErrorReason showErrorReason = new ShowErrorReason(mContext, mActivity);
                        showErrorReason.show(errorCode);
                    }


                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                } else {
                    toastUtil.showToast("验证码错误");
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_activity);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        phoNum = (EditText) findViewById(R.id.pho_num);
        sendNum = (Button) findViewById(R.id.send_num);
        checkMessage = (EditText) findViewById(R.id.check_message);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        clickCheck = (Button) findViewById(R.id.click_check);
        backButton = (ImageView) findViewById(R.id.back_button);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        mContext = this.getApplicationContext();
        mActivity = this;
        toastUtil = new ToastUtil(mContext, mActivity);
        userForgetPasswordPresenter = new UserForgetPasswordPresenter(this, mContext, mActivity);

        hideLoading();

        sendNum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        sendNum.setAnimation(shake);
                        break;
                    case MotionEvent.ACTION_UP:
                        getPhoneNumber();
                        SMSSDK.getVerificationCode(country, getPhoneNumber());//发送短信验证码到手机号
                        sendNum.setEnabled(false);
                        timer.start();//使用计时器 设置验证码的时间限制
                        break;
                }
                return true;
            }
        });

        sendNum.performClick();//模拟点击

        //当验证码输入正确&两次密码输入一致时候判定为成功
        clickCheck.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        clickCheck.setAnimation(shake);
                        showLoading();
                        break;
                    case MotionEvent.ACTION_UP:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                fixPassword(country, getPhoneNumber(), getCodeNumber(), getPassword(), getConfirmPassword());
                                handler.sendEmptyMessage(0x01);
                            }
                        }).start();
                        break;
                }
                return true;
            }
        });

    }

    /**
     * 使用计时器来限定验证码
     * 在发送验证码的过程 不可以再次申请获取验证码 在指定时间之后没有获取到验证码才能重新进行发送
     * 这里限定的时间是60s
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            sendNum.setText((millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            sendNum.setEnabled(true);
            sendNum.setText("发送");
        }
    };

    @Override
    public void showLoading() {
        progressBar.show();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
    }

    @Override
    public String getPhoneNumber() {
        return phoNum.getText().toString();
    }

    @Override
    public String getCodeNumber() {
        return checkMessage.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return passwordCheck.getText().toString();
    }

    @Override
    public void fixPassword(String country, String phoneNumber, String codeNum, String password, String confirmPassword) {
        userForgetPasswordPresenter.fixPassword(country, phoneNumber, codeNum, password, confirmPassword);
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

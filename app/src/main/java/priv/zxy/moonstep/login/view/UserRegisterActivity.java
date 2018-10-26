package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hyphenate.exceptions.HyphenateException;
import com.rengwuxian.materialedittext.MaterialEditText;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.kernel.BaseActivity;
import priv.zxy.moonstep.utils.ShowErrorReason;
import priv.zxy.moonstep.utils.ToastUtil;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.login.presenter.UserRegisterPresenter;
import priv.zxy.moonstep.main.view.MainActivity;

/**
 *  Created by Zxy on 2018/9/23
 */

public class UserRegisterActivity extends BaseActivity implements IUserRegisterView {

    private MaterialEditText accountName;
    private RadioGroup radioGroup;
    private RadioButton man;
    private RadioButton woman;
    private MaterialEditText password;
    private MaterialEditText passwordCheck;
    private Button clickRegister;
    private Button returnLoginPage;
    private ImageView backButton;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private String phoneNumber;
    private Context mContext;
    private Activity mActivity;
    private String nickName;
    private String userPassword = "";
    private String confirmPassword = "";
    private String userGender = "";

    //创建一个桥接对象，通过Presenter完成和Module层的交互
    private UserRegisterPresenter userRegisterPresenter;
    //加载动画资源
    private Animation shake;

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
        setContentView(R.layout.register_activity);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        accountName = (MaterialEditText) findViewById(R.id.accountName);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        man = (RadioButton) findViewById(R.id.man);
        woman = (RadioButton) findViewById(R.id.woman);
        password = (MaterialEditText) findViewById(R.id.password);
        passwordCheck = (MaterialEditText) findViewById(R.id.password_check);
        clickRegister = (Button) findViewById(R.id.click_register);
        returnLoginPage = (Button) findViewById(R.id.return_login_page);
        backButton = (ImageView) findViewById(R.id.back_button);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        mContext = this.getApplicationContext();
        mActivity = this;

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        phoneNumber = getPhoneNumber();

        userRegisterPresenter = new UserRegisterPresenter(this, mActivity, mContext);
        hideLoading();

        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        backButton.setAnimation(shake);
                        break;
                    case MotionEvent.ACTION_UP:
                        finishActivitySelf();
                        break;
                }
                return true;
            }
        });

        clickRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickRegister.setAnimation(shake);
                        showLoading();
                        break;
                    case MotionEvent.ACTION_UP:
                        getData();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                try {
                                    userRegisterPresenter.doRegister(phoneNumber, nickName, userPassword,  confirmPassword, userGender);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                }
                                mHandler.sendEmptyMessage(0x01);
                                Looper.loop();
                            }
                        }).start();
                        break;
                }
                return true;
            }
        });

        returnLoginPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        returnLoginPage.setAnimation(shake);
                        break;
                    case MotionEvent.ACTION_UP:
                        toLoginActivity();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPassWord() {
        return null;
    }

    @Override
    public void clearUserName() {
        accountName.clearComposingText();
    }

    @Override
    public void clearUserPassword() {
        password.clearComposingText();
    }

    @Override
    public void clearUserConfirmPassword() {
        passwordCheck.clearComposingText();
    }

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
    public void toLoginActivity() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public String getPhoneNumber() {
        Intent intent = getIntent();
        return intent.getStringExtra("phoneNumber");
    }

    @Override
    public void getData() {
        nickName = accountName.getText().toString();
        userPassword = password.getText().toString();
        confirmPassword = passwordCheck.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.man:
                        userGender = "男";
                        break;
                    case R.id.woman:
                        userGender = "女";
                        break;
                }
            }
        });
    }

    @Override
    public void finishActivitySelf() {
        this.finish();
    }

    @Override
    public void showUserNameSuccessTip() {
        ToastUtil.getInstance(mContext, mActivity).showToast("恭喜！您的账号可以使用");

    }

    @Override
    public void showUserNameFailTip(ErrorCode errorCode) {
        ShowErrorReason showErrorReason = new ShowErrorReason(mContext, mActivity);
        showErrorReason.show(errorCode);
    }

    @Override
    public void showRegisterSuccessTip() {
        ToastUtil.getInstance(mContext, mActivity).showToast("恭喜您，可以进入圆月世界了！");

    }

    @Override
    public void showRegisterFailTip(ErrorCode errorCode) {
        ShowErrorReason showErrorReason = new ShowErrorReason(mContext, mActivity);
        showErrorReason.show(errorCode);
    }
}
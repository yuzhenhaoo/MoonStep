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
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.ShowErrorReason;
import priv.zxy.moonstep.Utils.ToastUtil;
import priv.zxy.moonstep.kernel_data.bean.ErrorCode;
import priv.zxy.moonstep.login.presenter.UserChangePasswordPresenter;

/**
 *  Created by Zxy on 2018/9/23
 */

public class UserChangePasswordActivity extends AppCompatActivity implements IChangePasswordView {
    private TextView phone;
    private MaterialEditText password;
    private MaterialEditText confirmPassword;
    private ImageView backButton;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private Button submit;
    private UserChangePasswordPresenter userChangePasswordPresenter;
    private Animation shake;
    private Activity mActivity;
    private Context mContext;

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
        setContentView(R.layout.change_password_activity);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        phone = (TextView) findViewById(R.id.phone);
        password = (MaterialEditText) findViewById(R.id.password);
        confirmPassword = (MaterialEditText) findViewById(R.id.password_check);
        backButton = (ImageView) findViewById(R.id.back_button);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        submit = (Button) findViewById(R.id.submit);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        phone.setText(getPhoneNumber());
        final String phoneNum = phone.getText().toString();
        mActivity = this;
        mContext = this.getApplicationContext();

        hideLoading();
        userChangePasswordPresenter = new UserChangePasswordPresenter(this, mContext, mActivity);
        submit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        submit.setAnimation(shake);
                        showLoading();
                        break;
                    case MotionEvent.ACTION_DOWN:
                        final String pwd = getPassword();
                        final String conPwd = getConfirmPassword();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    userChangePasswordPresenter.setChangePassword(mContext, mActivity, phoneNum, pwd, conPwd);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mHandler.sendEmptyMessage(0x01);
                            }
                        }).start();

                        break;
                }
                return true;
            }
        });
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
    public String getPhoneNumber() {
        Intent intent = getIntent();
        return intent.getStringExtra("phoneNumber");
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return confirmPassword.getText().toString();
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showSuccessTip() {
        new ToastUtil(mContext, mActivity).showToast("恭喜您，您的密码已经修改成功了");
    }

    @Override
    public void showErrorTip(ErrorCode errorCode) {
        new ShowErrorReason(mContext, mActivity).show(errorCode);
    }
}

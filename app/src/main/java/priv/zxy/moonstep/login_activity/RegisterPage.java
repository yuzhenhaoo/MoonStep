package priv.zxy.moonstep.login_activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.PhoneRegisterUtil;
import priv.zxy.moonstep.Utils.ToastUtil;
import priv.zxy.moonstep.Utils.UserNameCheckUtil;

public class RegisterPage extends AppCompatActivity {


    private String phoneNumber;
    private String userName;
    private String userGender;
    private String userPassword;
    private String confirmPassword;
    private Button checkUsername;
    private EditText accountName;
    private RadioGroup radioGroup;
    private EditText password;
    private EditText passwordCheck;
    private Button clickRegister;
    private Button returnLoginPage;
    private ImageView backButton;
    private Context mContext;
    private Activity mActivity;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private boolean userNameCheckResult = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x01:
                    progressBar.hide();
                    deepBackground.setVisibility(View.GONE);
                    plainBackground.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.obtainMessage(0x01).sendToTarget();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initData() {
        //得到RegisterPhone2传递过来的phone参数，要进行电话的注册
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        checkUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检测用户名是否已经存在于数据库中，并跳出弹窗对用户进行提示
                userName = accountName.getText().toString();
                UserNameCheckUtil userNameCheckUtil = new UserNameCheckUtil(mContext, mActivity);
                userNameCheckUtil.UserNameCheck(userName);
                try {
                    refreshPage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Thread(runnable).start();//刷新界面
                userNameCheckResult = userNameCheckUtil.checkResult;
                if (userNameCheckResult)
                    userNameCheckUtil.SuccessTip();

            }
        });

        clickRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickRegister.setTextSize(16);

                        getData();

                        //进行数据的检查并进行注册
                        checkAndOpeateData();
                        break;
                    case MotionEvent.ACTION_UP:
                        clickRegister.setTextSize(14);
                        break;
                }
                return true;
            }
        });

        returnLoginPage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        returnLoginPage.setTextSize(16);
                        break;
                    case MotionEvent.ACTION_UP:
                        returnLoginPage.setTextSize(14);
                        break;
                }
                return true;
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回退键
                FinistThisActivity();
            }
        });
    }

    /**
     * 刷新页面
     */
    private void refreshPage() throws InterruptedException {
        progressBar.show();
        Thread.sleep(200);
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    private void getData() {
        userName = accountName.getText().toString();
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

    /**
     * 对数据进行检测并验证用户名和密码是否合法
     * 如果合法的话，满足注册的需求，给予用户分配一个账户，并对结果进行提示
     */
    private void checkAndOpeateData() {
        UserNameCheckUtil userNameCheckUtil = new UserNameCheckUtil(mContext, mActivity);
        userNameCheckUtil.UserNameCheck(userName);
        try {
            refreshPage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(runnable).start();//刷新界面
        userNameCheckResult = userNameCheckUtil.checkResult;
        if (userNameCheckResult) { // 在点击提交按钮的时候，我们只检测用户名是否不符合要求，并有相应的弹窗，如果用户名符合要求，就不弹窗
            if (userPassword.equals(confirmPassword)) {
                PhoneRegisterUtil prUtil = new PhoneRegisterUtil(this.getApplicationContext(), this);
                prUtil.RegisterRequest(phoneNumber, userName, userGender, userPassword);
            } else {
                ToastUtil toastUtil = new ToastUtil(mContext, mActivity);
                toastUtil.showToast("您的密码和验证密码不符，请重新输入");
            }
        }
    }

    private void FinistThisActivity() {
        this.finish();
    }

    private void initView() {
        checkUsername = (Button) findViewById(R.id.check_username);
        accountName = (EditText) findViewById(R.id.accountName);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        clickRegister = (Button) findViewById(R.id.click_register);
        returnLoginPage = (Button) findViewById(R.id.return_login_page);
        backButton = (ImageView) findViewById(R.id.back_button);

        mContext = this.getApplicationContext();
        mActivity = this;
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
    }
}

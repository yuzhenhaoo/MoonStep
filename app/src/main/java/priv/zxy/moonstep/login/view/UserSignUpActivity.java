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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hyphenate.exceptions.HyphenateException;
import com.rengwuxian.materialedittext.MaterialEditText;

import priv.zxy.moonstep.DAO.RegisterDataRequestDAO;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.RaceDialog;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.util.ShowErrorReasonUtil;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.login.presenter.UserRegisterPresenter;
import priv.zxy.moonstep.main.view.MainActivity;

/**
 *  Created by Zxy on 2018/9/23
 */

public class UserSignUpActivity extends BaseActivity implements IUserRegisterView, View.OnTouchListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "UserSignUpActivity";
    private MaterialEditText accountName;
    private RadioGroup radioGroup;
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
    private RaceDialog raceDialog;
    private String nickName;
    private String userPassword = "";
    private String confirmPassword = "";
    private String userGender = "男";

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
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initView() {
        accountName = (MaterialEditText) findViewById(R.id.accountName);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        password = (MaterialEditText) findViewById(R.id.password);
        passwordCheck = (MaterialEditText) findViewById(R.id.passwordCheck);
        clickRegister = (Button) findViewById(R.id.click_register);
        returnLoginPage = (Button) findViewById(R.id.returnLoginpage);
        backButton = (ImageView) findViewById(R.id.back_button);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        mContext = this.getApplicationContext();
        mActivity = this;

        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        userRegisterPresenter = new UserRegisterPresenter(this, mActivity, mContext);

    }

    protected void initData(){
        phoneNumber = getPhoneNumber();
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initEvent(){
        backButton.setOnTouchListener(this);
        clickRegister.setOnClickListener(this);
        returnLoginPage.setOnTouchListener(this);
        radioGroup.setOnCheckedChangeListener(this);

        hideLoading();
    }


    @Override
    public void onClick(View v) {
        if (v.equals(clickRegister)) {
            getData();
            if (userPassword.equals(confirmPassword)){
                RegisterDataRequestDAO.getInstance().RegisterRequest(new RegisterDataRequestDAO.CallBack() {
                    @Override
                    public void onSuccess(String raceCode, String raceName, String raceDescription, String raceImage, String raceIcon) throws HyphenateException {
                        showDialog(raceName, raceDescription, raceImage, raceIcon);
                        Log.d(TAG, raceImage);
                        Log.d(TAG, raceIcon);
                    }

                    @Override
                    public void onFail(ErrorCodeEnum errorCode) {
                        ShowErrorReasonUtil.getInstance(UserSignUpActivity.this).show(errorCode);
                    }
                }, phoneNumber, nickName, userPassword, userGender);
            }else{
                ShowErrorReasonUtil.getInstance(UserSignUpActivity.this).show(ErrorCodeEnum.PASSWORD_IS_NOT_EQUALS_CONFIRM_PASSWORD);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.equals(returnLoginPage)) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    returnLoginPage.setAnimation(shake);
                    break;
                case MotionEvent.ACTION_UP:
                    toLoginActivity();
                    break;
                default:
                    break;
            }
            return true;
        }

        if (v.equals(backButton)) {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    backButton.setAnimation(shake);
                    break;
                case MotionEvent.ACTION_UP:
                    finishActivitySelf();
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.equals(radioGroup)) {
            switch (checkedId) {
                case R.id.man:
                    userGender = "男";
                    break;
                case R.id.woman:
                    userGender = "女";
                    break;
                default:
                    throw new RuntimeException("性別輸入格式錯誤");
            }
        }
    }

    private void showDialog(String raceName, String raceDescription, String raceImage, String raceIcon){
        Toast.makeText(mContext, "注册成功，您得到的种族如上", Toast.LENGTH_SHORT).show();
        final RaceDialog myDialog = new RaceDialog(this, raceName, raceDescription, raceImage, raceIcon);
        //让dialog消失
        myDialog.setOnClickListener(myDialog::dismiss);
        myDialog.show();//让dialog显示

        myDialog.setOnClickListener(this::toLoginActivity);
    }

    @Override
    public String getUserName() {
        return null;
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
        Intent intent = new Intent(this, LoginSurface.class);
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
    }

    @Override
    public void finishActivitySelf() {
        this.finish();
    }

    @Override
    public void showErrorTip(ErrorCodeEnum errorCode) {
        ShowErrorReasonUtil.getInstance(this).show(errorCode);
    }
}
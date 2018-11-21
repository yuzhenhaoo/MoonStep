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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hyphenate.exceptions.HyphenateException;
import com.rengwuxian.materialedittext.MaterialEditText;

import priv.zxy.moonstep.BuildConfig;
import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.RaceDialog;
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.BaseActivity;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.ShowErrorReason;
import priv.zxy.moonstep.utils.ToastUtil;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.login.presenter.UserRegisterPresenter;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.moonstep.utils.dbUtils.PhoneRegisterUtil;

/**
 *  Created by Zxy on 2018/9/23
 */

public class UserRegisterActivity extends BaseActivity implements IUserRegisterView {

    private static final String TAG = "UserRegisterActivity";
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
        initView();
        initData();
        initEvent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        accountName = (MaterialEditText) findViewById(R.id.accountName);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        password = (MaterialEditText) findViewById(R.id.password);
        passwordCheck = (MaterialEditText) findViewById(R.id.passwordCheck);
        clickRegister = (Button) findViewById(R.id.clickRegister);
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

    private void initData(){
        phoneNumber = getPhoneNumber();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent(){

        backButton.setOnTouchListener((v, event) -> {
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    backButton.setAnimation(shake);
                    break;
                case MotionEvent.ACTION_UP:
                    finishActivitySelf();
                    break;
            }
            return true;
        });

        clickRegister.setOnClickListener(view-> {
            getData();
            if (userPassword.equals(confirmPassword)){
                PhoneRegisterUtil.getInstance().RegisterRequest(new PhoneRegisterUtil.CallBack() {
                    @Override
                    public void onSuccess(String raceCode, String raceName, String raceDescription, String raceImage, String raceIcon) throws HyphenateException {
                        showDialog(raceName, raceDescription, raceImage, raceIcon);
                        Log.d(TAG, raceImage);
                        Log.d(TAG, raceIcon);
                    }

                    @Override
                    public void onFail(ErrorCode errorCode) {
                        ShowErrorReason.getInstance(UserRegisterActivity.this).show(errorCode);
                    }
                }, "15616257889", nickName, userPassword,userGender);
            }else{
                ShowErrorReason.getInstance(UserRegisterActivity.this).show(ErrorCode.PasswordIsNotEqualsConfirmPassword);
            }
        });

        returnLoginPage.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    returnLoginPage.setAnimation(shake);
                    break;
                case MotionEvent.ACTION_UP:
                    toLoginActivity();
                    break;
            }
            return true;
        });

        radioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
            switch (checkedId) {
                case R.id.man:
                    userGender = "男";
                    break;
                case R.id.woman:
                    userGender = "女";
                    break;
            }
        });

        hideLoading();
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
    }

    @Override
    public void finishActivitySelf() {
        this.finish();
    }

    @Override
    public void showErrorTip(ErrorCode errorCode) {
        ShowErrorReason.getInstance(this).show(errorCode);
    }

}
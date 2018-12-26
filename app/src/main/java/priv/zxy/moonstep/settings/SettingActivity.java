package priv.zxy.moonstep.settings;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;

import priv.zxy.moonstep.BuildConfig;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.MyDialog;
import priv.zxy.moonstep.login.view.UserLoginActivity;
import priv.zxy.moonstep.util.LogUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/14
 * 描述:
 **/

public class SettingActivity extends AppCompatActivity implements ISettingView{

    private TextView accountSetting;
    private TextView phoneNumber;
    private TextView help;
    private TextView cleanChatSpace;
    private MyDialog myDialog;
    private AnimatorSet animatorSet = new AnimatorSet();
    private TextView exitLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        initData();

        initEvent();
    }

    private void initView() {
        accountSetting = (TextView) findViewById(R.id.accountSetting);
        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        help = (TextView) findViewById(R.id.help);
        cleanChatSpace = (TextView) findViewById(R.id.cleanChatSpace);
        exitLogin = (TextView) findViewById(R.id.exitLogin);

        //设置Setting的动画
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(exitLogin, "scaleX",1.0f, 1.2f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(exitLogin, "scaleY",1.0f, 1.2f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(exitLogin, "scaleX",1.2f, 1.0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(exitLogin, "scaleY",1.2f, 1.0f);
        animator1.setInterpolator(new DecelerateInterpolator());
        animator2.setInterpolator(new DecelerateInterpolator());
        animator3.setInterpolator(new DecelerateInterpolator());
        animator4.setInterpolator(new DecelerateInterpolator());

        animatorSet.playTogether(animator1, animator2);
        animatorSet.play(animator2).before(animator3);
        animatorSet.playTogether(animator3, animator4);
    }

    private void initData(){

    }

    @Override
    protected void onDestroy() {
        if (myDialog != null){
            myDialog.dismiss();
        }
        super.onDestroy();
    }

    private void initEvent(){
        exitLogin.setOnClickListener(v -> {
            animatorSet.start();
            new Thread(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x01);
            }).start();
        });
    }

    public void popDiaLogWindow() {
        myDialog = new MyDialog(this);
        myDialog.setTitle("退出提示!");
        myDialog.setContent("退出登陆后我们会继续保留您的账户数据，记得常回来看看哦！");
        myDialog.setNegativeClickLister("取消", myDialog::dismiss);
        myDialog.setPositiveClickLister("确认", () -> {
            new Thread(() -> {
                EMClient.getInstance().logout(true);//退出EMC的服务，让当前用户不在线
            }).start();
            if (BuildConfig.DEBUG) LogUtil.d("PersonalSurfaceFragment", "用户已经成功下线");
            toLoginActivity();
        });
        myDialog.show();
    }

    public void toLoginActivity() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            popDiaLogWindow();
        }
    };
}

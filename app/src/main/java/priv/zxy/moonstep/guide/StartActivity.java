package priv.zxy.moonstep.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hanks.htextview.HTextView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.kernel.BaseActivity;
import priv.zxy.moonstep.kernel.MessageReceiverService;
import priv.zxy.moonstep.login.view.UserLoginActivity;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;

/**
 * 这里的关键点是使用定时器Handler.postDelayed(new Runnable, int millions);
 * 这里有个问题就是如果单单使用上面的写法，那么Handler只会被调用一次，想要实现定时器，就用递归的方法，反复调用自身就好了。
 */
public class StartActivity extends BaseActivity {

    private static final String TAG = "StartActivity";

    private Button bt;

    private boolean isStarted = false;

    private String[] words = {"我不知道...", "", "你是否愿意...", "" ,"和我一起..."," ", "踏碎这无聊的天地！"};

    private static int seconds = 0;

    private HTextView hTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        initView();
    }

    public void initView(){
        bt = findViewById(R.id.clickJump);
        hTextView = findViewById(R.id.hTextView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump_to_LogUtilin_page();
                isStarted = true;
            }
        });

        final Handler mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 0:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 1:
                        break;
                    case 2:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 3:
                        bt.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 5:
                        break;
                    case 6:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 7:
                        break;
                    case 8:
                        if (!isStarted){
                            jump_to_LogUtilin_page();
                        }
                        mHandler.removeCallbacks(this);
                        break;
                }
                seconds += 1;
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(runnable, 1000);


    }

    /**
     * 如果上次已经成功登录过了，并将成功登录的信息保存在了mysp文件中
     * 通过检索，直接跳入MainActivity中
     * 如果上次登录失败，登录成功与否的标记位被修改为false，那么就要进入到登录页面
     */
    public  void jump_to_LogUtilin_page(){
        if ( SharedPreferencesUtil.getInstance(this).isSuccessedLogUtilined()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //当无需通过LogUtilinActivity登录的时候就要开启MessageReceiverService
            startService(new Intent(this, MessageReceiverService.class));
        }else{
            Intent intent = new Intent(this, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
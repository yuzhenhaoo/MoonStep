package priv.zxy.moonstep.login.view;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.helper.PermissionHelper;
import priv.zxy.moonstep.service.MessageReceiverService;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.moonstep.manager.DataInitManager;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;
import priv.zxy.moonstep.library.animate.ElastcityDecorAnimate;

/**
 * 这里的关键点是使用定时器Handler.postDelayed(new Runnable, int millions);
 * 这里有个问题就是如果单单使用上面的写法，那么Handler只会被调用一次，想要实现定时器，就用递归的方法，反复调用自身就好了。
 */
public class StartActivity extends BaseActivity {

    private static final int REQUEST_IGNORE_BATTERY_CODE = 1;

    private static final String TAG = "StartActivity";

    private ImageView logo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setView(R.layout.activity_start);

        // 请求加入白名单权限
        if(!isIgnoringBatteryOptimizations()){
            gotoSettingIgnoringBatteryOptimizations();
        }

        // 请求其他运行时权限
        String[] params = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(!PermissionHelper.checkPermission(this, params)){
            PermissionHelper.requestPermission(this, "需要本地权限", 1, params);
        }
    }

    @Override
    protected void initData() {
        setFirstToLoginPage();
    }

    @Override
    protected void initView() {
        logo = findViewById(R.id.moon_logo);
    }

    @Override
    protected void initEvent() {
        ElastcityDecorAnimate.getInstance(logo).show(3000L);
        ElastcityDecorAnimate.getInstance(logo).setAnimateListenting(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtil.d(TAG, "动画结束了");
                toLoginPage();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 如果上次已经成功登录过了，并将成功登录的信息保存在了mysp文件中
     * 通过检索，直接跳入MainActivity中
     * 如果上次登录失败，登录成功与否的标记位被修改为false，那么就要进入到登录页面
     */
    public  void toLoginPage(){
        if ( SharedPreferencesUtil.isSuccessLogin()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // 当无需通过LoginActivity登录的时候就要开启MessageReceiverService
            startService(new Intent(this, MessageReceiverService.class));
            // 同时初始化UserSelfInfo的数据
            DataInitManager.initUserSelfInfo(SharedPreferencesUtil.readMySelfInformation());
        }else{
            Intent intent = new Intent(this, LoginSurface.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    /**
     * 设置第一次进入登陆页面
     */
    public void setFirstToLoginPage(){
        if (SharedPreferencesUtil.isFirstLogin()) return;
        SharedPreferencesUtil.setFirstLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 判断App是否已加入省电白名单
     */
    private boolean isIgnoringBatteryOptimizations(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            return pm.isIgnoringBatteryOptimizations(packageName);
        }
        return false;
    }

    /**
     * 请求加入省电白名单
     */
    @SuppressLint("BatteryLife")
    private void gotoSettingIgnoringBatteryOptimizations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent();
                String packageName = getPackageName();
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivityForResult(intent, REQUEST_IGNORE_BATTERY_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 请求结果处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == REQUEST_IGNORE_BATTERY_CODE) {
                LogUtil.d(TAG,"加入省电白名单成功");
            }
        }else if (resultCode == RESULT_CANCELED) {
            if (requestCode == REQUEST_IGNORE_BATTERY_CODE) {
                LogUtil.d(TAG,"加入省电白名单失败");
            }
        }
    }
}
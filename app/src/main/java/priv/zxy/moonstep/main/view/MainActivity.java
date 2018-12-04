package priv.zxy.moonstep.main.view;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import org.litepal.tablemanager.Connector;

import java.util.Map;

import priv.zxy.moonstep.service.MoonFriendService;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.gps.map;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.login.view.UserLoginActivity;
import priv.zxy.moonstep.settings.SettingActivity;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.utils.ShowErrorReason;
import priv.zxy.moonstep.data.bean.ErrorCode;
import priv.zxy.moonstep.kefu.KeFuActivity;
import priv.zxy.moonstep.commerce.view.FragmentParent;
import priv.zxy.moonstep.task.FourthMainPageFragment;
import priv.zxy.moonstep.title.ThirdMainPageFragment1;
import priv.zxy.moonstep.utils.ToastUtil;
import priv.zxy.moonstep.wheel.animate.AnimateEffect;
import priv.zxy.moonstep.wheel.animate.ElasticityFactory;

/**
 * 我们可以在MainActivity中获得Moonfriends的获取和MessageQueue的获取
 * 二就是在广播接收到MessageQueue后，传递给MoonFriendFragment改变当前好友列表的消息显示，再经过判断，通过ChattingActivity加载相应的数据。(问题二：我在得到消息队列后，给不同好友的消息在没有打开ChattingActivity的时候应该存储在哪里？是本地的数据库吗？直到打开ChattingActivity的时候再重新加载？)
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,IMainView {

    private static final String TAG = "MainActivity";

    private NavigationView navigationView;

    private View nav_header_main;

    private TextView name;

    private TextView race;

    private Button setting;

    private Button mode;

    private Activity mActivity;

    private Intent service;

    private AnimateEffect effect;

    private static boolean isNight = true;

    private static boolean isExit = false;//定义一个变量，来标识是否退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SQLiteDatabase db = Connector.getDatabase();//实现数据库的创建

        setContentView(R.layout.activity_main);

        initView(savedInstanceState);

        initData();

        initEvent();

    }

    private void initView(Bundle savedInstanceState){
        bindService();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new FragmentParent()).commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView = findViewById(R.id.nav_view);
        nav_header_main = navigationView.getHeaderView(0);

        name = (TextView) nav_header_main.findViewById(R.id.name);
        race = (TextView) nav_header_main.findViewById(R.id.race);
        setting = (Button) nav_header_main.findViewById(R.id.settingBt);
        mode = (Button) nav_header_main.findViewById(R.id.mode);

        mActivity = this;

        effect = new ElasticityFactory().createEffectObject();

        effect.setAnimate(setting);

        //在程序打开的时候设置点击第一个按钮
        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    private void initData(){
        try {
            Map<String, String> data = SharedPreferencesUtil.getInstance(Application.getContext()).readMySelfInformation();
            LogUtil.d(TAG, data.toString());
            name.setText(data.get("nickName"));
            race.setText(data.get("userRaceName"));
        } catch (NullPointerException e) {
            LogUtil.d(TAG, e.getMessage());
        }
    }

    private void initEvent(){
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

        mode.setOnClickListener(v -> mode.animate()
                .alpha(0)
                .setDuration(1000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isNight){
                            mode.setBackgroundResource(R.drawable.sun);
                            isNight = false;
                            ToastUtil.getInstance(Application.getContext(), MainActivity.this).showToast("已设置为日间模式");
                            mode.setAlpha(1);
                        }else{
                            mode.setBackgroundResource(R.mipmap.moon);
                            isNight = true;
                            ToastUtil.getInstance(Application.getContext(), MainActivity.this).showToast("已设置为夜间模式");
                            mode.setAlpha(1);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start());

        setting.setOnClickListener(v -> {
            effect.show();
            new Thread(()->{
                try{
                    Thread.sleep(500);
                }catch (InterruptedException e){
                    e.printStackTrace();
                    LogUtil.e(TAG, "Thread is Interrupted!");
                }
                animatorHandle.sendEmptyMessage(0x01);
            }).start();
        });
    }

    @Override
    public void toSettingActivity(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addFragmentToStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    public void toFifthPage() {
        Intent intent = new Intent(this, KeFuActivity.class);
        startActivity(intent);
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_place) {
            addFragmentToStack(new FragmentParent());
        } else if (id == R.id.nav_real_world) {
            addFragmentToStack(new map());
        } else if (id == R.id.nav_wangguan) {
            addFragmentToStack(new ThirdMainPageFragment1());
        } else if (id == R.id.nav_task) {
            addFragmentToStack(new FourthMainPageFragment());
        } else if (id == R.id.nav_kefu) {
            toFifthPage();
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 在ChattingActivity中对为了监听EMClient而设立的SharedPreferences文件而进行销毁，保证每次进入ChattingActivity只让EMClient的SharedPreferences初始化一次
     * 但是需要检测只通过Edit清除缓存的话是不是会改变到文件本身的内容，如果可以改变，那么我们不需要删除文件，如果不能改变，那么我们不但必须清除缓存，也必须删除文件
     */
    @Override
    protected void onDestroy() {
        unBindService();
        super.onDestroy();
    }

    @Override
    public void bindService() {
        //绑定LoadingMoonFriendsService
        service = new Intent(this, MoonFriendService.class);
        bindService(service, connection, Service.BIND_AUTO_CREATE);
        startService(service);
    }

    @Override
    public void unBindService() {
        //解除绑定LoadingMoonFriendsService
        stopService(service);
        unbindService(connection);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void exit(){
        if (!isExit){
            isExit = true;
            Toast.makeText(Application.getContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0, 2000);
        }else{
            finish();
            System.exit(0);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler animatorHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            toSettingActivity();
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d(TAG, "MoonFriendService Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d(TAG, "MoonFriendService DisConnected");
        }
    };

    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error){
            runOnUiThread(() -> {
                if (error == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
                    ShowErrorReason.getInstance(mActivity).show(ErrorCode.AccountISRemoverd);
                    LogUtil.e("error", String.valueOf(error));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    toLoginActivity();//强制退出到登陆页面
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    ShowErrorReason.getInstance(mActivity).show(ErrorCode.AccountIsLogUtilinInOtherDevice);
                    LogUtil.e("error", String.valueOf(error));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    toLoginActivity();//强制退出到登陆页面
                } else {
                    if (NetUtils.hasNetwork(MainActivity.this)){
                        //连接不到聊天服务器
                        ShowErrorReason.getInstance(mActivity).show(ErrorCode.ConnectChatServiceFail);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        toLoginActivity();//强制退出到登陆页面
                    }
                    else{
                        //当前网络不可用，请检查网络设置
                        ShowErrorReason.getInstance(mActivity).show(ErrorCode.NetNotResponse);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        toLoginActivity();//强制退出到登陆页面
                    }
                }
            });
        }
    }
}
package priv.zxy.moonstep.main.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import org.litepal.tablemanager.Connector;

import java.lang.ref.WeakReference;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.framework.stroage.RaceInfo;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;
import priv.zxy.moonstep.gps.MapFragment;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.login.view.LoginActivity;
import priv.zxy.moonstep.settings.SettingActivity;
import priv.zxy.moonstep.util.ImageCacheUtil.GlideCacheUtil;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.ShowErrorReasonUtil;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.kefu.KeFuActivity;
import priv.zxy.moonstep.commerce.view.FragmentParent;
import priv.zxy.moonstep.task.FourthMainPageFragment;
import priv.zxy.moonstep.title.ThirdMainPageFragment;
import priv.zxy.moonstep.util.ToastUtil;
import priv.zxy.moonstep.wheel.animate.AbstractAnimateEffect;
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
    private CircleImageView head;
    private TextView name;
    private TextView race;
    private Button setting;
    private Button mode;
    private Activity mActivity;
    private AbstractAnimateEffect effect;

    private static boolean isNight = true;

    private static boolean isExit = false;//定义一个变量，来标识是否退出

    private AnimatorHandler animatorHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 实现数据库的创建
        Connector.getDatabase();

        setContentView(R.layout.activity_main);

        initView(savedInstanceState);
        initData();
        initEvent();
    }

    private void initView(Bundle savedInstanceState){

        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new FragmentParent()).commit();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView = findViewById(R.id.nav_view);
        nav_header_main = navigationView.getHeaderView(0);

        head = nav_header_main.findViewById(R.id.user_head_image);
        name = (TextView) nav_header_main.findViewById(R.id.name);
        race = (TextView) nav_header_main.findViewById(R.id.race);
        setting = (Button) nav_header_main.findViewById(R.id.settingBt);
        mode = (Button) nav_header_main.findViewById(R.id.mode);

        mActivity = this;

        effect = new ElasticityFactory().createEffectObject();

        effect.setAnimate(setting);

        animatorHandler = new AnimatorHandler(this);

        // 在程序打开的时候设置点击第一个按钮
        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

    /**
     * 初始化侧滑栏的展示数据
     */
    private void initData(){
        User user = UserSelfInfo.getInstance().getMySelf();
        LogUtil.d(TAG, user.toString());
        name.setText(user.getNickName());
        race.setText(String.valueOf(RaceInfo.getInstance().getRace().getRaceName()));
        Glide.with(this).load(UserSelfInfo.getInstance().getMySelf().getHeadPath()).into(head);
    }

    private void initEvent(){
        // 注册一个监听连接状态的listener
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
                animatorHandler.sendEmptyMessage(0x01);
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

        // getMenuInflater().inflate(R.menu.main, menu);
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
        Intent intent = new Intent(this, LoginActivity.class);
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
            addFragmentToStack(new MapFragment());
        } else if (id == R.id.nav_wangguan) {
            addFragmentToStack(new ThirdMainPageFragment());
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

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 在ChattingActivity中对为了监听EMClient而设立的SharedPreferences文件而进行销毁，
     * 保证每次进入ChattingActivity只让EMClient的SharedPreferences初始化一次,
     * 但是需要检测只通过Edit清除缓存的话是不是会改变到文件本身的内容，
     * 如果可以改变，那么我们不需要删除文件，如果不能改变，
     * 那么我们不但必须清除缓存，也必须删除文件
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
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
            new Thread(()->isExit = false);
        }else{
            finish();
            System.exit(0);
        }
    }

    private static class AnimatorHandler extends Handler{

        private WeakReference<MainActivity> weakReference;

        AnimatorHandler(MainActivity mainActivity){
            weakReference = new WeakReference<>(mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = weakReference.get();
            activity.toSettingActivity();
        }
    }

    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error){
            runOnUiThread(() -> {
                if (error == EMError.USER_REMOVED) {
                    // 显示帐号已经被移除
                    ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.ACCOUNT_IS_DELETED);
                    LogUtil.e("error", String.valueOf(error));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 强制退出到登陆页面
                    toLoginActivity();
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.ACCOUNT_IS_LOGGING_IN_OTHER_DEVICE);
                    LogUtil.e("error", String.valueOf(error));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 强制退出到登陆页面
                    toLoginActivity();
                } else {
                    if (NetUtils.hasNetwork(MainActivity.this)){
                        // 连接不到聊天服务器
                        ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.CONNECT_CHAT_SERVICE_FAIL);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 强制退出到登陆页面
                        toLoginActivity();
                    }
                    else{
                        // 当前网络不可用，请检查网络设置
                        ShowErrorReasonUtil.getInstance(mActivity).show(ErrorCodeEnum.NET_NOT_RESPONSE);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 强制退出到登陆页面
                        toLoginActivity();
                    }
                }
            });
        }
    }
}
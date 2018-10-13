package priv.zxy.moonstep.main.view;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.util.NetUtils;

import org.litepal.tablemanager.Connector;

import java.util.List;

import priv.zxy.moonstep.EM.bean.OnMoonFriendListener;
import priv.zxy.moonstep.EM.service.MoonFriendService;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.ShowErrorReason;
import priv.zxy.moonstep.kernel_data.bean.ErrorCode;
import priv.zxy.moonstep.kernel_data.bean.User;
import priv.zxy.moonstep.login.view.UserLoginActivity;
import priv.zxy.moonstep.main_fifth_page_activity.MainFifthPageActivity;
import priv.zxy.moonstep.moonstep_palace.FirstMainPageFragmentParent;
import priv.zxy.moonstep.main_fourth_page_fragment.FourthMainPageFragment;
import priv.zxy.moonstep.main_second_page_fragment.SecondMainPageFragmentParent;
import priv.zxy.moonstep.main_third_page_fragment.ThirdMainPageFragment1;

/**
 * 我们可以在MainActivity中获得Moonfriends的获取和MessageQueue的获取
 * 二就是在广播接收到MessageQueue后，传递给MoonFriendFragment改变当前好友列表的消息显示，再经过判断，通过ChattingActivity加载相应的数据。(问题二：我在得到消息队列后，给不同好友的消息在没有打开ChattingActivity的时候应该存储在哪里？是本地的数据库吗？直到打开ChattingActivity的时候再重新加载？)
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,IMainView {

    private static final String TAG = "MainActivity";

    private TextView name;

    private TextView race;

    private Context mContext;

    private Activity mActivity;

    private OnMoonFriendListener moonFriendListener;

    private List<User> lists = null;

    private List<EMMessage> emMessages = null;

    private Intent service;

    public static String userId = null;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "MoonFriendService Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "MoonFriendService DisConnected");
        }
    };

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error){
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    ShowErrorReason showErrorReason = new ShowErrorReason(mContext, mActivity);
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                        showErrorReason.show(ErrorCode.AccountISRemoverd);
                        Log.e("error", String.valueOf(error));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        toLoginActivity();//强制退出到登陆页面
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        showErrorReason.show(ErrorCode.AccountIsLoginInOtherDevice);
                        Log.e("error", String.valueOf(error));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        toLoginActivity();//强制退出到登陆页面
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)){
                            //连接不到聊天服务器
                            showErrorReason.show(ErrorCode.ConnectChatServiceFail);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            toLoginActivity();//强制退出到登陆页面
                        }
                        else{
                            //当前网络不可用，请检查网络设置
                            showErrorReason.show(ErrorCode.NetNotResponse);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            toLoginActivity();//强制退出到登陆页面
                        }
                    }
                }
            });
        }
    }

    //通过EM获取好友的消息队列
    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            Log.d(TAG,"接收到了消息:");
            emMessages = messages;
            for(EMMessage message: emMessages){
                Log.d(TAG, message.toString());
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            Log.d(TAG, "收到透传消息");
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            Log.d(TAG, "收到已读回执");
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            Log.d(TAG, "收到已送达回执");
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            Log.d(TAG, "消息状态变动");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase db = Connector.getDatabase();//实现数据库的创建
        getUserID();
        //隐藏通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainpage);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new FirstMainPageFragmentParent()).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mContext = this.getApplicationContext();

        mActivity = this;

        bindService();

        changeMyInformation();

        doEMConnectionListener();

        EMClient.getInstance().chatManager().addMessageListener(msgListener);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addFragmentToStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    @Override
    public void toFifthPage() {
        Intent intent = new Intent(this, MainFifthPageActivity.class);
        startActivity(intent);
    }

    @Override
    public void changeMyInformation() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View nav_header_main = navigationView.getHeaderView(0);
        try {
            name = (TextView) nav_header_main.findViewById(R.id.name);
            race = (TextView) nav_header_main.findViewById(R.id.race);

            name.setText("张默尘");
            race.setText("月神族");
            //修改User_Information
//            thread.start();
        } catch (NullPointerException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void getUserID(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("UserID");
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
            addFragmentToStack(new FirstMainPageFragmentParent());
        } else if (id == R.id.nav_real_world) {
            addFragmentToStack(new SecondMainPageFragmentParent());
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

    @Override
    protected void onDestroy() {
        unBindService();
        super.onDestroy();
    }


    @Override
    public void doEMConnectionListener() {
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
    }

    @Override
    public void bindService() {
//        //绑定LoadingMoonFriendsService
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

    /**
     * 注册接口回调方法，供MoonFriendFragment来调用
     * @param onMoonFriendListener 接口回调对象
     */
//    public void setOnCallBackListener(OnMoonFriendListener onMoonFriendListener){
//        this.moonFriendListener = onMoonFriendListener;
//        this.moonFriendListener.getMoonFriends(lists);
//    }
}
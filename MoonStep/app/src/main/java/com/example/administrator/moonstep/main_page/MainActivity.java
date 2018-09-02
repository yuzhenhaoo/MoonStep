package com.example.administrator.moonstep.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.administrator.moonstep.R;
import com.example.administrator.moonstep.main_first_page_fragment.FirstMainPageFragmentParent;
import com.example.administrator.moonstep.main_fifth_page_activity.MainFifthPageActivity;
import com.example.administrator.moonstep.main_fourth_page_fragment.FourthMainPageFragment;
import com.example.administrator.moonstep.main_second_page_fragment.SecondMainPageFragmentParent;
import com.example.administrator.moonstep.main_third_page_fragment.ThirdMainPageFragment1;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private Context context;
    private TextView name;
    private TextView race;
    private List<Fragment> fragment_list = new ArrayList<Fragment>();
    //创建handle对象，实现UI改写
    private final MyHandler handle = new MyHandler(this);
    /**
     * 为了解决Handler可能造成的内存泄漏的危险，重写一个内部类
     * 通过继承Handler
     */

    private static class MyHandler extends Handler{
        private final WeakReference<MainActivity> mActivity;
        private MyHandler(MainActivity activity){
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg){
            MainActivity activity = mActivity.get();
            switch(msg.what){
                case 0x01:
                    Log.i("TAG",activity.name.getText().toString());
                    //这里设置用户的信息
                    activity.name.setText("张默尘");
                    Log.i("TAG",activity.name.getText().toString());
                    activity.race.setText("月神族");
                    break;
            }
        }
    }


    final Thread thread = new Thread(){
        public void run(){
            Message msg = new Message();
            msg.what = 0x01;
            handle.sendMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainpage);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, new FirstMainPageFragmentParent()).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setUserInformation();
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

    private void addFragmentToStack(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }

    private void jumpToFifthPage(){
        Intent intent = new Intent(this, MainFifthPageActivity.class);
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
            jumpToFifthPage();
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 可以直接更新页面，但是这里用Handler来处理
     */
    public void setUserInformation(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View nav_header_main = navigationView.getHeaderView(0);
        try{
            name = (TextView)nav_header_main.findViewById(R.id.name);
            race = (TextView)nav_header_main.findViewById(R.id.race);

            name.setText("张默尘");
            race.setText("月神族");
            //修改User_Information
//            thread.start();
        }catch(NullPointerException e){
            Log.i("TAG",e.getMessage());
        }
    }
}
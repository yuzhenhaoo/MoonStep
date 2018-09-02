package com.example.administrator.moonstep.guide_activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.moonstep.R;
import com.example.administrator.moonstep.start_activity.StartActivity;
import com.example.administrator.moonstep.Utils.SharedPreferencesUtil;
import com.example.administrator.moonstep.guide_fragment.FifthGuideFragment;
import com.example.administrator.moonstep.guide_fragment.FirstGuideFragment;
import com.example.administrator.moonstep.guide_fragment.ForthGuideFragment;
import com.example.administrator.moonstep.guide_fragment.SecondGuideFragment;
import com.example.administrator.moonstep.guide_fragment.ThirdGuideFragment;

import java.util.ArrayList;
import java.util.List;

public class GuidePage extends AppCompatActivity {
    //引导页对象
    private ViewPager vp;
    //Button的OnClick属性在页面第一次展现的时候就会出现一次点击，所以必须进行避免。
    private Boolean button_is_clicked = false;
    //Fragment适配器
    private FragmentPagerAdapter mAdapter;
    //所有页面的集合
    private List<View> views;
    //每个fragment是一个布局
    private ArrayList<Fragment> fragments;
    //ViewPager滑动消失的效果对象
    GuidePageTransformer guidePageTransformer;
    //引导页结束的时候需要销毁引导页本身，所以在下一个跳转的Activity中销毁引导页这个Activity

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //调用以后开启应用是否为第一次登陆的检测功能
        jump_activity();

        Window window = getWindow();
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        setContentView(R.layout.main_guide_parent_page);

        initView();

        viewpagerListener();
    }

    /**
     * 第一次登陆圆月行的时候，需要进入圆月行引导页
     * 第二次登陆圆月行，直接进入登录页面
     */
    public void jump_activity(){
        SharedPreferencesUtil sp = new SharedPreferencesUtil(this);
        if(!sp.is_first_log()){
            jumpLoginActivity();
        }
        sp.save_first_login();
    }

    private void initView(){
        fragments = new ArrayList<Fragment>();
        vp = (ViewPager) findViewById(R.id.crime_view_pager);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public int getCount(){
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0){
                return fragments.get(arg0);
            }
        };
        //声明各个页面的实例
        FirstGuideFragment first_guide_fragment = new FirstGuideFragment();
        SecondGuideFragment second_guide_fragment = new SecondGuideFragment();
        ThirdGuideFragment third_guide_fragment = new ThirdGuideFragment();
        ForthGuideFragment forth_guide_fragment = new ForthGuideFragment();
        FifthGuideFragment fifth_guide_fragment = new FifthGuideFragment();
        fragments.add(first_guide_fragment);
        fragments.add(second_guide_fragment);
        fragments.add(third_guide_fragment);
        fragments.add(forth_guide_fragment);
        fragments.add(fifth_guide_fragment);
        guidePageTransformer = new GuidePageTransformer();
        guidePageTransformer.setCurrentItem(this, 0 ,fragments);
        vp.setPageTransformer(true, guidePageTransformer);
        vp.setAdapter(mAdapter);
        //设置ViewPager缓存页面的个数
        vp.setOffscreenPageLimit(3);
    }

    private void viewpagerListener(){
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageSelected(int i) {
                switch(i){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        button_is_clicked = false;
                        break;
                    case 4:
                        buttonListener(findViewById(R.id.image_button));
                        break;
                }
            }

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void buttonListener(View v){
        if(button_is_clicked){
            jumpLoginActivity();
        }
        button_is_clicked = true;
    }

    /**
     * 通过intent的两个标记为来实现从Viewpager跳转到StartActivity的时候确保Viewpager_Activity的销毁
     */
    public void jumpLoginActivity(){
        Intent intent = new Intent(this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

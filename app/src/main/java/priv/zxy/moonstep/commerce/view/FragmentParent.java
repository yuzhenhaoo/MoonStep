package priv.zxy.moonstep.commerce.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.ref.WeakReference;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.MainAdapter;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;

public class FragmentParent extends Fragment {

    private static final String TAG = "FirstMainPageFragmentPa";

    private View view;

    private ViewPager viewPager;

    private RadioGroup rg_tab_bar;

    private RadioButton rb_tree;

    private RadioButton rb_community;

    private RadioButton rb_heart;

    private RadioButton rb_me;

    private Context mContext;

    private MyHandler mHandler;

    private static class MyHandler extends Handler{
        private WeakReference<FragmentParent> weakReference;

        MyHandler(FragmentParent fragmentParent){
            weakReference = new WeakReference<>(fragmentParent);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentParent instance = weakReference.get();
            if (instance.viewPager.getCurrentItem() != 2){
                switch (msg.what) {
                    case 0x00:
                        instance.rb_heart.setCompoundDrawables(null, instance.changeBtnTop(R.drawable.tab_menu_heart_tip), null, null);
                        break;
                    case 0x01:
                        instance.rb_heart.setCompoundDrawables(null, instance.changeBtnTop(R.drawable.tab_menu_heart), null, null);
                        break;
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_parent_page, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    public void initView() {
        viewPager = view.findViewById(R.id.vp);
        rg_tab_bar = view.findViewById(R.id.rg_tab_bar);
        rb_tree = view.findViewById(R.id.rb_tree);
        rb_community = view.findViewById(R.id.rb_community);
        rb_heart = view.findViewById(R.id.rb_heart);
        rb_me = view.findViewById(R.id.rb_me);
        viewPager.setCurrentItem(0);
        mContext = this.getContext();
        mHandler = new MyHandler(this);
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (SharedPreferencesUtil.getInstance(Application.getContext()).isMessageTip()) {
                    mHandler.sendEmptyMessage(0x00);
                } else {
                    mHandler.sendEmptyMessage(0x01);
                }
            }
        }).start();
    }

    public void initDate() {
        //通过getChildFragment得到子容器的管理器，实现Fragment的嵌套
        MainAdapter mAdapter = new MainAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        setChecked(rb_tree, R.mipmap.moon_pressed);

        rb_tree.setChecked(true);

        rg_tab_bar.setOnCheckedChangeListener((radioGroup, id) -> {
            switch (id) {
                case R.id.rb_tree:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.rb_community:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.rb_heart:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.rb_me:
                    viewPager.setCurrentItem(3);
                    break;
            }
        });

        //实现滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                resetTopDrawable();
                switch (i) {
                    case 0:
                        rb_tree.setChecked(true);
                        setChecked(rb_tree, R.mipmap.tree_pressed);
                        break;
                    case 1:
                        rb_community.setChecked(true);
                        setChecked(rb_community, R.mipmap.community_pressed);
                        break;
                    case 2:
                        rb_heart.setChecked(true);
                        setChecked(rb_heart, R.mipmap.heart_pressed);
                        break;
                    case 3:
                        rb_me.setChecked(true);
                        setChecked(rb_me, R.mipmap.me_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void resetTopDrawable() {
        rb_tree.setCompoundDrawables(null, changeBtnTop(R.mipmap.moon_normal), null, null);
        rb_community.setCompoundDrawables(null, changeBtnTop(R.mipmap.pet_normal), null, null);
        rb_me.setCompoundDrawables(null, changeBtnTop(R.mipmap.me_normal), null, null);
    }

    private void setChecked(RadioButton rb, int id) {
        rb.setCompoundDrawables(null, changeBtnTop(id), null, null);
    }

    private Drawable changeBtnTop(int id) {
        Drawable drawableTop = Application.getContext().getResources().getDrawable(id);
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
        return drawableTop;
    }
}

package priv.zxy.moonstep.commerce.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.ref.WeakReference;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Community.MoonCommunity;
import priv.zxy.moonstep.commerce.view.Friend.MoonFriendFragment;
import priv.zxy.moonstep.commerce.view.Me.MeFragment;
import priv.zxy.moonstep.commerce.view.Map.MapFragment;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 其中对于Fragment按钮提示的处理在FragmentParent中实现逻辑，这里不再针对设计方面对代码进行拆分，会增加开发的时间成本。
 */
public class FragmentParent extends BaseFragment {

    private static final String TAG = "FragmentParent";

    private View view;
    private RadioGroup rg;
    private RadioButton treeBt;
    private RadioButton communityBt;
    private RadioButton friendBt;
    private RadioButton myselfBt;
    private MyHandler mHandler;

    private FragmentManager manager;
    /**
     * 检测当前friendBt是否为选中状态
     */
    private static boolean friendBtIsChecked = false;

    private final static String MAP = "map";
    private final static String COMMUNITY = "community";
    private final static String MOON_FRIEND = "moon_friend";
    private final static String ME = "me";

    private Fragment currentFragment;


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
        rg = view.findViewById(R.id.rg_tab_bar);
        treeBt = view.findViewById(R.id.rb_map);
        communityBt = view.findViewById(R.id.rb_community);
        friendBt = view.findViewById(R.id.rb_heart);
        myselfBt = view.findViewById(R.id.rb_me);
        mHandler = new MyHandler(this);
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();

        assert getActivity() != null;
        manager = getActivity().getSupportFragmentManager();
    }

    public void initDate() {
        setChecked(treeBt, R.mipmap.moon_pressed);

        treeBt.setChecked(true);
        addFragment(MAP);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            resetTopDrawable();
            switch (checkedId) {
                case R.id.rb_map:
                    friendBtIsChecked = false;
                    treeBt.setChecked(true);
                    setChecked(treeBt, R.mipmap.moon_pressed);
                    addFragment(MAP);
                    break;
                case R.id.rb_community:
                    friendBtIsChecked = false;
                    communityBt.setChecked(true);
                    setChecked(communityBt, R.mipmap.community_pressed);
                    addFragment(COMMUNITY);
                    break;
                case R.id.rb_heart:
                    friendBtIsChecked = true;
                    friendBt.setChecked(true);
                    setChecked(friendBt, R.mipmap.heart_pressed);
                    addFragment(MOON_FRIEND);
                    break;
                case R.id.rb_me:
                    friendBtIsChecked = false;
                    myselfBt.setChecked(true);
                    setChecked(myselfBt, R.mipmap.me_pressed);
                    addFragment(ME);
                    break;
                default:
            }
        });
    }

//    private void addFragmentToStack(Fragment fragment) {
//        if (getActivity() == null) {
//            return;
//        }
////        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragments, fragment).commit();
//        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragments, fragment).commit();
//    }

    /**
     * 将Fragment添加到Fragment栈中
     * 需要在添加的时候判断两件事情：
     *  1.栈中是否已经存在该Fragment的实例了，如果存在，则隐藏当前显示的Fragment，并显示指定的Fragment
     *  2.栈中不存在该Fragment的实例，所以，若当前显示的currentFragment不为空的话，那我们直接对它进行显示
     * @param fTag 传入的标记位
     */
    private void addFragment(String fTag) {
        // 判断这个标签是否存在Fragment对象，如果存在就返回true，不存在就返回null
        Fragment fragment = manager.findFragmentByTag(fTag);
        // 如果这个fragment不存在于栈中
        FragmentTransaction transaction;
        if (fragment == null) {
            // 初始化FragmentTransaction
            transaction = manager.beginTransaction();
            // 根据RadioButton点击穿融入的tag，实例化，添加显示不同的Fragment
            if (fTag.equals(MAP)) {
                fragment = new MapFragment();
            }
            if (fTag.equals(COMMUNITY)) {
                fragment = new MoonCommunity();
            }
            if (fTag.equals(MOON_FRIEND)) {
                fragment = new MoonFriendFragment();
            }
            if (fTag.equals(ME)) {
                fragment = new MeFragment();
            }
            // 添加之前先将上一个Fragment隐藏掉
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.fragments, fragment, fTag);
            transaction.commit();
            // 更新可见
            currentFragment = fragment;
        } else {
            transaction = manager.beginTransaction();
            transaction.show(fragment);
            transaction.hide(currentFragment);

            // 更新可见
            currentFragment = fragment;
            transaction.commit();
        }
    }

    private void resetTopDrawable() {
        treeBt.setCompoundDrawables(null, changeBtnTop(R.mipmap.moon_normal), null, null);
        communityBt.setCompoundDrawables(null, changeBtnTop(R.mipmap.community_normal), null, null);
        myselfBt.setCompoundDrawables(null, changeBtnTop(R.mipmap.me_normal), null, null);
    }

    private void setChecked(RadioButton rb, int id) {
        rb.setCompoundDrawables(null, changeBtnTop(id), null, null);
    }

    private Drawable changeBtnTop(int id) {
        Drawable drawableTop = Application.getContext().getResources().getDrawable(id);
        drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
        return drawableTop;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (SharedPreferencesUtil.isMessageTip()) {
                    mHandler.sendEmptyMessage(0x00);
                } else {
                    mHandler.sendEmptyMessage(0x01);
                }
            }
        }
    }

    private static class MyHandler extends Handler {
        private WeakReference<FragmentParent> weakReference;

        MyHandler(FragmentParent fragmentParent) {
            weakReference = new WeakReference<>(fragmentParent);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FragmentParent instance = weakReference.get();
            if (!friendBtIsChecked) {
                switch (msg.what) {
                    case 0x00:
                        instance.friendBt.setCompoundDrawables(null, instance.changeBtnTop(R.drawable.tab_menu_heart_tip), null, null);
                        break;
                    case 0x01:
                        instance.friendBt.setCompoundDrawables(null, instance.changeBtnTop(R.drawable.tab_menu_heart), null, null);
                        break;
                }
            }
        }
    }
}

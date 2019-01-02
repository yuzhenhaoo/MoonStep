package priv.zxy.moonstep.commerce.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.ref.WeakReference;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Community.MoonCommunity;
import priv.zxy.moonstep.commerce.view.Friend.MoonFriendFragment;
import priv.zxy.moonstep.commerce.view.Me.PersonalSurfaceFragment;
import priv.zxy.moonstep.commerce.view.Tree.TreeFragment;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

public class FragmentParent extends BaseFragment {

    private static final String TAG = "FragmentParent";

    private View view;
    private RadioGroup rg;
    private RadioButton treeBt;
    private RadioButton communityBt;
    private RadioButton friendBt;
    private RadioButton myselfBt;
    private MyHandler mHandler;

    private TreeFragment treeFragment = new TreeFragment();
    private MoonCommunity communityFragment = new MoonCommunity();
    private MoonFriendFragment friendFragment = new MoonFriendFragment();
    private PersonalSurfaceFragment personalInfoFragment = new PersonalSurfaceFragment();

    /**
     * 一个变量来检测当前friendBt是不是被选中了
     */
    private static boolean friendBtIsChecked = false;

    private static class MyHandler extends Handler{
        private WeakReference<FragmentParent> weakReference;

        MyHandler(FragmentParent fragmentParent){
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
        treeBt = view.findViewById(R.id.rb_tree);
        communityBt = view.findViewById(R.id.rb_community);
        friendBt = view.findViewById(R.id.rb_heart);
        myselfBt = view.findViewById(R.id.rb_me);
        mHandler = new MyHandler(this);
        new Thread(() -> {
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
        }).start();
    }

    public void initDate() {
        setChecked(treeBt, R.mipmap.moon_pressed);

        treeBt.setChecked(true);
        addFragmentToStack(treeFragment);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            resetTopDrawable();
            switch (checkedId) {
                case R.id.rb_tree:
                    friendBtIsChecked = false;
                    treeBt.setChecked(true);
                    setChecked(treeBt, R.mipmap.tree_pressed);
                    addFragmentToStack(treeFragment);
                    break;
                case R.id.rb_community:
                    friendBtIsChecked = false;
                    communityBt.setChecked(true);
                    setChecked(communityBt, R.mipmap.community_pressed);
                    addFragmentToStack(communityFragment);
                    break;
                case R.id.rb_heart:
                    friendBtIsChecked = true;
                    friendBt.setChecked(true);
                    setChecked(friendBt, R.mipmap.heart_pressed);
                    addFragmentToStack(friendFragment);
                    break;
                case R.id.rb_me:
                    friendBtIsChecked = false;
                    myselfBt.setChecked(true);
                    setChecked(myselfBt, R.mipmap.me_pressed);
                    addFragmentToStack(personalInfoFragment);
                    break;
                default:
            }
        });
    }

    private void addFragmentToStack(Fragment fragment) {
        if (getActivity() == null) {
            return ;
        }
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragments, fragment).commit();
    }

    private void resetTopDrawable() {
        treeBt.setCompoundDrawables(null, changeBtnTop(R.mipmap.moon_normal), null, null);
        communityBt.setCompoundDrawables(null, changeBtnTop(R.mipmap.pet_normal), null, null);
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
    }
}

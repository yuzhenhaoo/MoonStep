package priv.zxy.moonstep.commerce.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.EMClient;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.BuildConfig;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.AnimationButton;
import priv.zxy.moonstep.customview.MyDialog;
import priv.zxy.moonstep.customview.WaveViewByBezier;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.login.view.UserLoginActivity;

/**
 * 检查settings按钮设置是否可以生效，若是不能生效，则说明是toolbar设置的问题，针对toolbar进行改进。
 */
public class PersonalSurfaceFragment extends Fragment {

    private static final String TAG = "PersonalSurfaceFragment";
    private View view;
    private ContextMenuDialogFragment mMenuDiaLogFragment;
    private ArrayList<MenuObject> menuObjects = new ArrayList<>();
    private Activity mActivity;
    private WaveViewByBezier waveViewByBezier;
    private AnimationButton bt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this.getActivity();
        setHasOptionsMenu(true);//在Fragment要想让onCreateOptionsMenu生效必须先调用setHasOptionsMenu的方法
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage4, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        initEvent();

    }

    private void initView(){
        bt = view.findViewById(R.id.clickButton);

        waveViewByBezier = view.findViewById(R.id.waveView);
    }

    private void initEvent(){
        waveViewByBezier.startAnimation();

        bt.setAnimationButtonListener(new AnimationButton.AnimationButtonListener(){
            @Override
            public void onClickListener() {
                bt.start();
            }

            @Override
            public void animationFinish() {
                jumpToDetailActivity();
                bt.reset();
            }
        });
    }

    private void jumpToDetailActivity(){
        Intent intent = new Intent(mActivity, UserDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        waveViewByBezier.pauseAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        waveViewByBezier.resumeAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        waveViewByBezier.stopAnimation();
    }

}
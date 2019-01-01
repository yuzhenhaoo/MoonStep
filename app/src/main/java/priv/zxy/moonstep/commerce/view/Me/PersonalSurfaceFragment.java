package priv.zxy.moonstep.commerce.view.Me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;

import java.util.ArrayList;
import java.util.Map;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.customview.AnimationButton;
import priv.zxy.moonstep.customview.WaveViewByBezier;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.user.UserSelfInfo;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

public class PersonalSurfaceFragment extends BaseFragment {

    private static final String TAG = "PersonalSurfaceFragment";
    private View view;
    private ContextMenuDialogFragment mMenuDiaLogFragment;
    private ArrayList<MenuObject> menuObjects = new ArrayList<>();
    private Activity mActivity;
    private WaveViewByBezier waveViewByBezier;
    private AnimationButton bt;
    private TextView userNickName;
    private TextView userLevelName;

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
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        initData();

        initEvent();

    }

    private void initView() {
        bt = view.findViewById(R.id.clickButton);

        waveViewByBezier = view.findViewById(R.id.waveView);
        userNickName = view.findViewById(R.id.userNickName);
        userLevelName = view.findViewById(R.id.userLevelName);
    }

    private void initData() {
        User user = UserSelfInfo.getInstance().getMySelf();
        userNickName.setText(user.getNickName());
        // FIXME (张晓翼，2018/12/30， 这里的数据还没有传)
        userLevelName.setText("一阶");
    }

    private void initEvent() {
        waveViewByBezier.startAnimation();

        bt.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                bt.start();
            }

            @Override
            public void animationFinish() {
                jumpToDetailActivity();
            }
        });
    }

    private void jumpToDetailActivity() {
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
        bt.reset();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        waveViewByBezier.stopAnimation();
    }

}
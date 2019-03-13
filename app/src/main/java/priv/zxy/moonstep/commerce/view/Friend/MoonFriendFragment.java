package priv.zxy.moonstep.commerce.view.Friend;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.hyphenate.exceptions.HyphenateException;

import org.litepal.LitePal;

import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.MoonFriendAdapter;
import priv.zxy.moonstep.commerce.presenter.MoonFriendPresenter;
import priv.zxy.moonstep.customview.PackDialog;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.library.animate.AbstractAnimateEffect;
import priv.zxy.moonstep.library.animate.RotateAnimation;
import priv.zxy.moonstep.library.animate.RotationMoveAnimation;

import static android.view.animation.Animation.INFINITE;

public class MoonFriendFragment extends BaseFragment implements IMoonFriendView {

    private static final String TAG = "MoonFriendFragment";
    private static final String name = "星空漩涡";

    private View view = null;
    private RecyclerView recyclerView;
    /**
     * 月友集合
     */
    private List<User> mList = null;
    private MoonFriendAdapter mAdapter;
    private Button chooseButton;

    private PackDialog packDialog = null;
    private LinearLayoutManager layoutManager;

    private MoonFriendPresenter moonFriendPresenter;

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private ImageView rotateVortex;
    private static ObjectAnimator animator1;
    private static ObjectAnimator animator2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 初始化好友列表
        mList = LitePal.findAll(User.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage3, container, false);
        initView();
        initEvent();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    public void initView() {
        assert getActivity() != null;
        Drawable drawable = this.getActivity().getDrawable(R.drawable.gradient_rectangle1);
        assert drawable != null;

//        mPullToRefreshView = view.findViewById(R.id.pull_to_refresh);
        recyclerView = view.findViewById(R.id.recyclerview);

        initRecyclerView();
        // 自定义recyclerView的分割线
        DividerItemDecoration divide_line = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        divide_line.setDrawable(drawable);
        recyclerView.addItemDecoration(divide_line);
        Context mContext = this.getContext();
        final Activity mActivity = this.getActivity();

        packDialog = new PackDialog(this.getActivity());

        moonFriendPresenter = new MoonFriendPresenter(this, mContext, mActivity);

        /// 注释原因：上面已经说过，因为刷新事件会和下拉熔炉界面发生冲突
        // 设置刷新事件的监听
//        mPullToRefreshView.setOnRefreshListener(() -> mPullToRefreshView.postDelayed(() -> {
//            mPullToRefreshView.setRefreshing(false);
//            try {
//                try {
//                    checkClientAndDatabase(mList);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                mList = LitePal.findAll(MoonFriend.class);
//                mAdapter.clear();
//                mAdapter.addAll(mList);
//                mAdapter.notifyDataSetChanged();//设置Adapter的刷新
//            } catch (HyphenateException e) {
//                e.printStackTrace();
//            }
//        }, REFRESH_DELAY));

        intentFilter = new IntentFilter();
        intentFilter.addAction("priv.zxy.moonstep.commerce.view.LOCAL_BROADCAST");
        localBroadcastManager = LocalBroadcastManager.getInstance(this.getActivity());
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(name);
        rotateVortex = (ImageView) view.findViewById(R.id.rotateVortex);
        chooseButton = (Button) view.findViewById(R.id.choose_button);

        animator1 = ObjectAnimator.ofFloat(rotateVortex, "rotation",0, 360);
        animator2 = ObjectAnimator.ofFloat(chooseButton, "rotation",0, 360);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setRepeatCount(INFINITE);
        animator1.setDuration(300);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.setDuration(300);
        animator1.start();
    }

    public void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new MoonFriendAdapter(this.getActivity());
        // 在每次进入好友页面的时候，都需要对当前页面的ViewHolder进行一次刷新
        mAdapter.clear();
        if (mList != null) {
            mAdapter.addAll(mList);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        chooseButton.setOnTouchListener((v, event) -> {
            if (animator1.isRunning()) {
                animator1.cancel();
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animator2.start();
                    break;
                case MotionEvent.ACTION_UP:
                    animator2.cancel();
                    packDialog.show();
                    break;
                default:
            }
            return false;
        });

        packDialog.setClickListener(new PackDialog.Callback() {
            @Override
            public void onSure() {
                playChooseAnimation();
                packDialog.doSure();
            }

            @Override
            public void onCancel() {
                packDialog.doClear();
                animator1.start();
            }
        });
    }

    /**
     * 选中并点击确定以后播放动画
     */
    private void playChooseAnimation() {
        RotationMoveAnimation.getInstance(packDialog.getChooseView(), getRelativeDistanceX(), getRelativeDistanceY()).show();
    }

    /**
     * @return 获得当前选择框物品图片中心到旋转中心的水平相对距离
     */
    private int getRelativeDistanceX() {
        return -100;
    }

    /**
     * @return 获得当前选择框物品图片中心到旋转中心的水平相对距离
     */
    private int getRelativeDistanceY() {
        return -100;
    }

    @Override
    public void checkClientAndDatabase(List<User> lists) throws HyphenateException, InterruptedException {
        moonFriendPresenter.checkClientAndDatabase(lists);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
        animator1.cancel();
        animator2.cancel();
        view = null;
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 一旦监听到消息就让界面刷新
            mAdapter.notifyDataSetChanged();
        }
    }
}

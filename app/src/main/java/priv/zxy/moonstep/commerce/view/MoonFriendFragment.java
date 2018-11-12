package priv.zxy.moonstep.commerce.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.hyphenate.exceptions.HyphenateException;
import com.yalantis.phoenix.PullToRefreshView;

import org.litepal.LitePal;

import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.commerce.presenter.MoonFriendAdapter;
import priv.zxy.moonstep.commerce.presenter.MoonFriendPresenter;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.utils.LogUtil;

public class MoonFriendFragment extends Fragment implements IMoonFriendView{

    private static final String TAG = "MoonFriendFragment";

    private View view = null;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private PullToRefreshView mPullToRefreshView;

    private long REFRESH_DELAY = 1000;

    private MoonFriendPresenter moonFriendPresenter;

    private List<MoonFriend> mList = null;//月友结果集

    private MoonFriendAdapter mAdapter;

    private IntentFilter intentFilter;

    private LocalReceiver localReceiver;

    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //这里开始初始化好友列表
        mList = LitePal.findAll(MoonFriend.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage3, container, false);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(TAG, "onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    public void initView() {
//        mPullToRefreshView = view.findViewById(R.id.pull_to_refresh);
        recyclerView = view.findViewById(R.id.recycleview);

        initRecyclerView();
        //自定义recyclerView的分割线
        DividerItemDecoration divide_line = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        divide_line.setDrawable(this.getActivity().getDrawable(R.drawable.gradient_rectangle1));
        recyclerView.addItemDecoration(divide_line);
        Context mContext = this.getContext();
        final Activity mActivity = this.getActivity();

        moonFriendPresenter = new MoonFriendPresenter(this, mContext, mActivity);

        //设置刷新事件的监听
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

        String name = "星空的熔炉";
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        collapsingToolbar.setTitle(name);
        Button rotateVortex = view.findViewById(R.id.rotateVortex);
        Animation rotationAnimation = AnimationUtils.loadAnimation(Application.getContext(),R.anim.rotate_anim);
        rotateVortex.startAnimation(rotationAnimation);
    }

    public void initRecyclerView(){
        layoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new MoonFriendAdapter(this.getActivity());
        mAdapter.clear();//在每次进入好友页面的时候，都需要对当前页面的ViewHolder进行一次刷新
        if(mList != null){
            mAdapter.addAll(mList);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void checkClientAndDatabase(List<MoonFriend> lists) throws HyphenateException, InterruptedException {
        moonFriendPresenter.checkClientAndDatabase(lists);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mAdapter.notifyDataSetChanged();//一旦监听到消息就让界面刷新
        }
    }

}

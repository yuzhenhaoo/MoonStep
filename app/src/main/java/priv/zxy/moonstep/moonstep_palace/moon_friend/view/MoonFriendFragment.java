package priv.zxy.moonstep.moonstep_palace.moon_friend.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.BuildConfig;
import priv.zxy.moonstep.EC.application.ECApplication;
import priv.zxy.moonstep.EC.bean.OnMoonFriendListener;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.kernel_data.bean.User;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.moonstep.moonstep_palace.ThirdPageRecyclerviewAdapter;
import priv.zxy.moonstep.moonstep_palace.moon_friend.presenter.MoonFriendPresenter;

public class MoonFriendFragment extends Fragment implements IMoonFriendView{

    private View view = null;

    private RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;

    private PullToRefreshView mPullToRefreshView;

    private long REFRESH_DELAY = 1000;

    @SuppressLint("StaticFieldLeak")
    private static MoonFriendFragment instance;

    private ImageView background;

    private TextView myMoonFriend;

    private MoonFriendPresenter moonFriendPresenter;

    private List<User> mList;//月友结果集

    private ThirdPageRecyclerviewAdapter mAdapter;

    private MainActivity mainActivity;//获取回调者的对象


    public static MoonFriendFragment getInstance() {
        if (instance == null) {
            instance = new MoonFriendFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) this.getActivity();
        //注册回调接口来接收moonFriends的传递
        mainActivity.setOnCallBackListener(new OnMoonFriendListener() {
            @Override
            public void getMoonFriends(List<User> moonfriends) {
                mList = moonfriends;
            }
        });
        Log.d("MoonFriendFragment", "onCreate");
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
        Log.d("MoonFriendFragment", "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("MoonFriendFragment", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MoonFriendFragment", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MoonFriendFragment", "Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MoonFriendFragment", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("MoonFriendFragment", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MoonFriendFragment", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("MoonFriendFragment", "onDetach");
    }

    public void initView() {
        mPullToRefreshView = view.findViewById(R.id.pull_to_refresh);
        recyclerView = view.findViewById(R.id.recycleview);

        initRecyclerView();
        //自定义recyclerView的分割线
        DividerItemDecoration divide_line = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        divide_line.setDrawable(this.getActivity().getDrawable(R.drawable.gradient_rectangle1));
        recyclerView.addItemDecoration(divide_line);

        //设置刷新事件的监听
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        mAdapter.notifyDataSetChanged();//设置Adapter的刷新
                    }
                }, REFRESH_DELAY);
            }
        });
    }

    public void initRecyclerView(){
        layoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new ThirdPageRecyclerviewAdapter(this.getActivity());
        mAdapter.clear();//在每次进入好友页面的时候，都需要对当前页面的ViewHolder进行一次刷新
        mAdapter.addAll(mList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public Bitmap getMoonFriendHeadPortrait(){
        return BitmapFactory.decodeFile("/mipmap-xhdpi/guide_page3.png");
    }


}

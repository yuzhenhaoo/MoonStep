package priv.zxy.moonstep.commerce.view.Community;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.MoonCommunityAdapter;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/28
 * 描述: 月友社区的Fragment(第二个子页面)
 **/

public class MoonCommunity extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "MoonCommunity";

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private MoonCommunityAdapter mAdapter;
    /**
     * mContext会持有Activity的引用，记得退出的时候要清除
     */
    private Context mContext;
    private List<BaseCommunityMessage> lists = new ArrayList<>();//初始化一部分信息列表
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_information_share, null);
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
        mContext = getActivity();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

    private void initData(){
        // 设置下拉刷新
        refreshLayout.setOnRefreshListener(this);
        //  进行联网请求数据
        getMessageData();

        BaseCommunityMessage communityBase = new BaseCommunityMessage();
        communityBase.setAddress("安居客圣诞节");
        communityBase.setLatitude("192.123456");
        communityBase.setLongitude("28.988645");
        communityBase.setShowTime("3天前");
        communityBase.setContent("魔前叩首三千年，回首凡尘不做仙");
        communityBase.setMediaPath("https://img.icons8.com/color/2x/hearts.png");
        communityBase.setPraiseNumber("123");
        communityBase.setUser(UserSelfInfo.getInstance().getMySelf());
        for (int i = 0; i < 10; i++){
            lists.add(communityBase);
        }
    }

    private void initEvent(){

    }

    /**
     * 从加载好信息的类中获取消息数据
     */
    private void getMessageData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }

    public void initRecyclerView(){
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new MoonCommunityAdapter(mContext);
        // 每次需要清空一次mAdapter中的列表书局
        mAdapter.clear();
        if(lists != null){
            mAdapter.addAll(lists);
        }
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);
        // 每次进来记得要对lists进行重置
        lists.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContext = null;
        view = null;
    }

    /**
     * SwipeRefreshLayout下拉刷新的回调接口
     */
    @Override
    public void onRefresh() {
        /*
         重新获取数据，然后从数据库端获取30条按照最近时间加载的消息，重新设置RecyclerView的数据，并进行页面的刷新
         */
    }
}

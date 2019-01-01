package priv.zxy.moonstep.commerce.view.Community;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.MoonCommunityAdapter;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.user.UserSelfInfo;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/28
 * 描述: 月友社区的Fragment(第二个子页面)
 **/

public class MoonCommunity extends Fragment {
    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private MoonCommunityAdapter mAdapter;
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
        initRecyclerView();
    }

    private void initView() {
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
    }

    private void initData(){
        User user = UserSelfInfo.getInstance().getMySelf();
        BaseCommunityMessage communityBase = new BaseCommunityMessage();
        communityBase.setAddress("安居客圣诞节");
        communityBase.setLatitude("192.123456");
        communityBase.setLongitude("28.988645");
        communityBase.setMediaPath("https://img.icons8.com/color/2x/hearts.png");
        communityBase.setPraiseNumber("123");
        communityBase.setUser(user);
        for (int i=0; i<10; i++){
            lists.add(communityBase);
        }
    }

    private void initEvent(){

    }

    public void initRecyclerView(){
        layoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new MoonCommunityAdapter(this.getActivity());
        ((MoonCommunityAdapter) mAdapter).clear();//在每次进入好友页面的时候，都需要对当前页面的ViewHolder进行一次刷新
        if(lists != null){
            mAdapter.addAll(lists);
        }
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);
    }
}

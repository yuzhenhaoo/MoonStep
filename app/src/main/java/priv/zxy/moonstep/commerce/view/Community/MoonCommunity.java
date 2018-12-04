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


public class MoonCommunity extends Fragment {
    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private MoonCommunityAdapter mAdapter;
    private List<CommunityBase> lists = new ArrayList<>();//初始化一部分信息列表
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
        CommunityBase communityBase = new CommunityBase();
        communityBase.setAddress("安居客圣诞节");
        communityBase.setLangtitude("192.123456");
        communityBase.setLongitude("28.988645");
        communityBase.setMediaPath("https://img.icons8.com/color/2x/hearts.png");
        communityBase.setPraiseNumber("123");
        for (int i=0; i<10; i++){
            lists.add(communityBase);
        }
    }

    private void initEvent(){

    }

    public void initRecyclerView(){
        layoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new MoonCommunityAdapter();
        ((MoonCommunityAdapter) mAdapter).clear();//在每次进入好友页面的时候，都需要对当前页面的ViewHolder进行一次刷新
        if(lists != null){
            mAdapter.addAll(lists);
        }
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);
    }
}

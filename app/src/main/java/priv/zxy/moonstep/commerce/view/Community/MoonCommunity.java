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
import priv.zxy.moonstep.executor.ExecutorManager;
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
        //  线程池中请求数据
        ExecutorManager.getInstance().execute(() -> getMessageData());

        BaseCommunityMessage communityBase = new BaseCommunityMessage();
        communityBase.setAddress("湖南省长沙市岳麓区");
        communityBase.setLatitude("192.123456");
        communityBase.setLongitude("28.988645");
        communityBase.setShowTime("刚才");
        communityBase.setContent("岳麓区还有这样的地方~");
        communityBase.setMediaPath("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1425116775,3834826108&fm=27&gp=0.jpg");
        communityBase.setPraiseNumber("7");
        communityBase.setUser(UserSelfInfo.getInstance().getMySelf());

        BaseCommunityMessage communityBase1 = new BaseCommunityMessage();
        communityBase1.setAddress("湖南省长沙市岳麓区");
        communityBase1.setLatitude("191.213123");
        communityBase1.setLongitude("28.421311");
        communityBase1.setShowTime("五分钟前");
        communityBase1.setContent("停车坐爱枫林晚，霜叶红于二夜花");
        communityBase1.setMediaPath("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1009672364,1746307723&fm=26&gp=0.jpg");
        communityBase1.setPraiseNumber("21");
        communityBase1.setUser(UserSelfInfo.getInstance().getMySelf());


        BaseCommunityMessage communityBase2 = new BaseCommunityMessage();
        communityBase2.setAddress("山东青岛黄湾");
        communityBase2.setLatitude("186.123421");
        communityBase2.setLongitude("27.532412");
        communityBase2.setShowTime("三十分钟前");
        communityBase2.setContent("海边的景色令人欣喜");
        communityBase2.setMediaPath("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1071814984,478996050&fm=26&gp=0.jpg");
        communityBase2.setPraiseNumber("21");
        communityBase2.setUser(UserSelfInfo.getInstance().getMySelf());

        for (int i = 0; i < 10; i++) {
            lists.add(communityBase);
            lists.add(communityBase1);
            lists.add(communityBase2);
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
        // 网格式流动布局管理器
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
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

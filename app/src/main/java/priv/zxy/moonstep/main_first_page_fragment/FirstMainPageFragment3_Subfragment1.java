package priv.zxy.moonstep.main_first_page_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import priv.zxy.moonstep.R;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;

/**
 * 这个Fragment中最有可能出错的一点就是子Fragment对于Activity的获取
 */

public class FirstMainPageFragment3_Subfragment1 extends Fragment {
    private View view = null;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ThirdPageRecyclerviewAdapter mAdapter;
    private ArrayList<MoonFriend> mList = new ArrayList<MoonFriend>();
    private PullToRefreshView mPullToRefreshView;
    private long REFRESH_DELAY = 1000;
    private static FirstMainPageFragment3_Subfragment1 instance;
    public static FirstMainPageFragment3_Subfragment1 getInstance(){
        if (instance == null){
            instance = new FirstMainPageFragment3_Subfragment1();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage3_fragment1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList();
        initView();
    }

    public void initList(){
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));
        mList.add(new MoonFriend(R.mipmap.my_photo,1,"张晓翼","20·月灵族·在线"));


    }

    public void initView(){
        mPullToRefreshView = view.findViewById(R.id.pull_to_refresh);
        recyclerView = view.findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(this.getActivity());
        mAdapter = new ThirdPageRecyclerviewAdapter(this.getActivity());
        mAdapter.addAll(mList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        //自定义recyclerView的分割线
        DividerItemDecoration divide_line = new DividerItemDecoration(this.getActivity(),DividerItemDecoration.VERTICAL);
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
                    }
                }, REFRESH_DELAY);
            }
        });
    }
}

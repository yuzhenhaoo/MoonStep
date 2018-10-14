package priv.zxy.moonstep.moonstep_palace.moon_friend.view;

import android.annotation.SuppressLint;
import android.content.Context;
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

import com.yalantis.phoenix.PullToRefreshView;

import org.litepal.LitePal;

import java.util.List;

import priv.zxy.moonstep.EM.bean.OnMoonFriendListener;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.db.MoonFriend;
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

    private List<MoonFriend> mList = null;//月友结果集

    private ThirdPageRecyclerviewAdapter mAdapter;

//    private MainActivity mainActivity;//获取回调者的对象


    public static MoonFriendFragment getInstance() {
        if (instance == null) {
            instance = new MoonFriendFragment();
        }
        return instance;
    }

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
        Log.d("MoonFriendFragment", "onActivityCreated");
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
        if(mList != null){
            mAdapter.addAll(mList);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}

package priv.zxy.moonstep.task;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.util.TaskInfoUtils;

public class FourthMainPageFragment extends BaseFragment {

    private View view;
    private RecyclerView recyclerView;
    private FourthMainPageAdapter mAdapter;
    final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_fourth_subpage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView(){
        recyclerView = view.findViewById(R.id.task_recyclerView);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mAdapter = new FourthMainPageAdapter(this.getActivity());
        mAdapter.addAll(TaskInfoUtils.generateTaskInfos());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //中心滚动效果
        recyclerView.addOnScrollListener(new CenterScrollListener());
        //缩放效果
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view = null;
    }
}

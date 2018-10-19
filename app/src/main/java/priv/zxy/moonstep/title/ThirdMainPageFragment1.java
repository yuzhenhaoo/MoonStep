package priv.zxy.moonstep.title;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.cleveroad.fanlayoutmanager.callbacks.FanChildDrawingOrderCallback;

import java.util.ArrayList;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.utils.TitleNamesUtils;

public class ThirdMainPageFragment1 extends Fragment {
    private RecyclerView recyclerView;
    private View view = null;
    private ThirdMainPageRecyclerViewAdapter mAdapter = null;
    private ArrayList<Integer> list = new ArrayList<Integer>();
    private FanLayoutManager fanLayoutManager;
    private Boolean is_collapse = false;

    public static ThirdMainPageFragment1 newInstance(){
        Bundle args = new Bundle();
        ThirdMainPageFragment1 fragment = new ThirdMainPageFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_third_parent_page,container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void initView(){
        recyclerView = view.findViewById(R.id.recycleview);
        mAdapter = new ThirdMainPageRecyclerViewAdapter(this.getActivity());
        //RecycleView的花样全在这个地方，纵向，横向，各种花式布局，
        //这里使用风扇LayoutManager
        FanLayoutManagerSettings fanLayoutManagerSettings = FanLayoutManagerSettings
                .newBuilder(getContext())
                .withFanRadius(true)    //选择是否用风扇的风格去展示它
                .withAngleItemBounce(5) //弹跳角度
                .withViewWidthDp(160)   //每个View的宽度
                .withViewHeightDp(200)  //每个View的高度
                .build();

        fanLayoutManager = new FanLayoutManager(getContext(), fanLayoutManagerSettings);
        recyclerView.setLayoutManager(fanLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new ThirdMainPageRecyclerViewAdapter(getContext());
        mAdapter.addAll(TitleNamesUtils.generateTitleNames());

        mAdapter.setOnItemClickListener(new ThirdMainPageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition,final View view) {
                if (fanLayoutManager.getSelectedItemPosition() != itemPosition){
                    fanLayoutManager.switchItem(recyclerView, itemPosition);
                }else{
                    fanLayoutManager.straightenSelectedItem(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                onClick(view, fanLayoutManager.getSelectedItemPosition());
                            } else {
                                onClick(fanLayoutManager.getSelectedItemPosition());
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }
            }
        });

        recyclerView.setAdapter(mAdapter);

        recyclerView.setChildDrawingOrderCallback(new FanChildDrawingOrderCallback(fanLayoutManager));

        (view.findViewById(R.id.click_collpase)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(is_collapse){
                    is_collapse = false;
                    view.findViewById(R.id.click_collpase).setBackgroundResource(R.drawable.collapse_logo);
                }else{
                    is_collapse = true;
                    view.findViewById(R.id.click_collpase).setBackgroundResource(R.drawable.open_logo);
                }
                fanLayoutManager.collapseViews();
            }
        });
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view, int pos) {
        Log.i("TAG","onClick被点击了");
        FullInfoTabFragment fragment = FullInfoTabFragment.newInstance(mAdapter.getModelByPos(pos));
        Toast.makeText(this.getContext(), "pos:" + pos, Toast.LENGTH_SHORT).show();

        fragment.setSharedElementEnterTransition(new SharedTransitionSet());
        fragment.setEnterTransition(new Fade());
        setExitTransition(new Fade());
        fragment.setSharedElementReturnTransition(new SharedTransitionSet());

        Log.i("TAG","fragment配置完成了，开始进行跳转");
        //进行fragment的跳转
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(view, "shared")
                .replace(R.id.main_content, fragment)
                .addToBackStack(null)
                .commit();
        Log.i("TAG","跳转完成");
    }

    public void onClick(int pos) {
        FullInfoTabFragment fragment = FullInfoTabFragment.newInstance(mAdapter.getModelByPos(pos));
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .addToBackStack(null)
                .commit();
    }

    public boolean deselectIfSelected() {
        if (fanLayoutManager.isItemSelected()) {
            fanLayoutManager.deselectItem();
            return true;
        } else {
            return false;
        }
    }
}

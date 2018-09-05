package priv.zxy.moonstep.main_second_page_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import priv.zxy.moonstep.R;

public class SecondMainPageFragmentParent extends Fragment {
    private View view = null;
    private ViewPager vp;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_map;
    private RadioButton rb_radar;
    private RadioButton rb_package;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_second_parent_page, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    public void initView(){
        vp = view.findViewById(R.id.vp);
        rg_tab_bar = view.findViewById(R.id.rg_tab_bar);
        rb_map = view.findViewById(R.id.rb_map);
        rb_radar = view.findViewById(R.id.rb_radar);
        rb_package = view.findViewById(R.id.rb_package);
        vp.setCurrentItem(0);
    }

    public void initData(){
        SecondMainPageAdapter mAdapter = new SecondMainPageAdapter(getChildFragmentManager());
        vp.setAdapter(mAdapter);
        rb_map.setChecked(true);
        rg_tab_bar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.rb_map:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rb_radar:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.rb_package:
                        vp.setCurrentItem(2);
                        break;
                }
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rb_map.setChecked(true);
                        break;
                    case 1:
                        rb_radar.setChecked(true);
                        break;
                    case 2:
                        rb_package.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}

package priv.zxy.moonstep.main_first_page_fragment;

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

public class FirstMainPageFragmentParent extends Fragment {
    private View view;
    private ViewPager viewPager;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_moon;
    private RadioButton rb_pet;
    private RadioButton rb_heart;
    private RadioButton rb_me;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_parent_page, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    public void initView(){
        viewPager = view.findViewById(R.id.vp);
        rg_tab_bar = view.findViewById(R.id.rg_tab_bar);
        rb_moon = view.findViewById(R.id.rb_moon);
        rb_pet = view.findViewById(R.id.rb_pet);
        rb_heart = view.findViewById(R.id.rb_heart);
        rb_me = view.findViewById(R.id.rb_me);
        viewPager.setCurrentItem(0);
    }

    public void initDate(){
        //通过getChildFragment得到子容器的管理器，实现Fragment的嵌套
        FirstMainPageAdapter mAdapter = new FirstMainPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        rb_moon.setChecked(true);
        rg_tab_bar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch(id){
                    case R.id.rb_moon:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_pet:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_heart:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_me:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });

        //实现滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        rb_moon.setChecked(true);
                        break;
                    case 1:
                        rb_pet.setChecked(true);
                        break;
                    case 2:
                        rb_heart.setChecked(true);
                        break;
                    case 3:
                        rb_me.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}

package priv.zxy.moonstep.main_first_page_fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import priv.zxy.moonstep.R;

public class FirstMainPageFragment3 extends Fragment {
    private View view;
    private Context mContext;
    private FragmentTransaction ft;
    private FragmentManager fragmentManager;
    private FirstMainPageFragment3_Subfragment1 subfragment1;
    private FirstMainPageFragment3_Subfragment2 subfragment2;
    private RadioButton rb1;
    private RadioButton rb2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = this.getContext();
        view = inflater.inflate(R.layout.fg_main_first_subpage3, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initFragment();
    }

    private void initView(){
        RadioGroup radioGroup = view.findViewById(R.id.headerBottom);
        rb1 = radioGroup.findViewById(R.id.rb1);
        rb2 = radioGroup.findViewById(R.id.rb2);
        RadioButtonSelectedSettings(0);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                //当RadioButton被选中的时候对界面做相应的操作
                int index = 0;
                if(id == 2131230950) index = 1;
                RadioButtonSelectedSettings(index);
                Log.i("TAG","你已经进来了");
                switch (index){
                    case 0:
                        Toast.makeText(mContext, "点击了Button1", Toast.LENGTH_SHORT).show();
                        changeFragment(0);
                        break;
                    case 1:
                        Toast.makeText(mContext, "点击了Button2", Toast.LENGTH_SHORT).show();
                        changeFragment(1);
                        break;
                }
            }
        });
    }

    private void RadioButtonSelectedSettings(int index){
        Drawable bottomDrawable = getResources().getDrawable(R.drawable.red_rectangle_line);
        switch(index){
            case 0:
                rb1.setTextColor(Color.parseColor("#000000"));
                rb2.setTextColor(Color.parseColor("#808080"));
                rb1.setTextSize(17);
                rb1.setChecked(true);
                rb2.setChecked(false);
                rb2.setTextSize(14);

                //动态设置下滑条
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
                rb1.setCompoundDrawables(null, null, null, bottomDrawable);
                rb2.setCompoundDrawables(null, null, null, null);
                break;
            case 1:
                rb2.setTextColor(Color.parseColor("#000000"));
                rb1.setTextColor(Color.parseColor("#808080"));
                rb2.setTextSize(17);
                rb1.setChecked(false);
                rb2.setChecked(true);
                rb1.setTextSize(14);

                //动态设置下滑条
                bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
                rb2.setCompoundDrawables(null, null, null, bottomDrawable);
                rb1.setCompoundDrawables(null, null, null, null);
                break;
        }
    }

    private void initFragment(){
        subfragment1 = new FirstMainPageFragment3_Subfragment1();
        subfragment2 = new FirstMainPageFragment3_Subfragment2();

        //开启事务
        fragmentManager = getFragmentManager();
        ft = fragmentManager.beginTransaction();

        //设置第一个fragment为首页
        addFragmentToStack(subfragment1);
    }

    private void changeFragment(int index){
        switch(index){
            case 0:
                addFragmentToStack(subfragment1);
                break;
            case 1:
                addFragmentToStack(subfragment2);
                break;
        }
    }

    private void addFragmentToStack(Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();
    }
}

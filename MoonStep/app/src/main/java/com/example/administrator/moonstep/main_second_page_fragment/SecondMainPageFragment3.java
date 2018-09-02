package com.example.administrator.moonstep.main_second_page_fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.moonstep.R;

public class SecondMainPageFragment3 extends Fragment {
    private View view;
    private Button bt_auction;
    private Button bt_mall;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fg_main_second_subpage3,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView(){
        bt_auction = view.findViewById(R.id.bt_auction);
        bt_mall = view.findViewById(R.id.bt_mall);

        bt_auction.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    bt_auction.setPadding(3,3,0,0);
                    jump_to_auction_page();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    bt_auction.setPadding(-3,-3,0,0);
                }
                return false;
            }
        });
        bt_mall.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    bt_mall.setPadding(10,10,0,0);
                    jump_to_moon_mall_page();
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    bt_mall.setPadding(-10,-10,0,0);
                }
                return false;

            }
        });
    }

    public void jump_to_auction_page(){

    }

    public void jump_to_moon_mall_page(){

    }
}

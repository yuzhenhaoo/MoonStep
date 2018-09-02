package com.example.administrator.moonstep.main_first_page_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.administrator.moonstep.R;
import com.example.administrator.moonstep.main_fifth_page_activity.ChatMessage;
import com.example.administrator.moonstep.user_info_activity.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ChatMessage> lists = new ArrayList<ChatMessage>();
    private ChattingAdapter mAdapter;
    private Button back;
    private Button person_info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //告诉你的activity你要切换动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
        Transition slide_left = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
        Transition slide_right = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
        //退出时使用
        getWindow().setExitTransition(slide_left);
        //第一次进入时使用
        getWindow().setEnterTransition(slide_right);
        //再次进入时使用
        getWindow().setReenterTransition(explode);
        setContentView(R.layout.fg_main_fifth_subpage);

        initView();
        initData();
    }

    public void initView(){
        back = this.findViewById(R.id.back);
        person_info = this.findViewById(R.id.person_info);

        recyclerView = this.findViewById(R.id.recycleview);
        mAdapter = new ChattingAdapter(getApplicationContext());
        lists.add(new ChatMessage("Hello？", 0 ,false));
        lists.add(new ChatMessage("Yeah,I'm here！", 0 ,true));
        lists.add(new ChatMessage("How old are you!", 0 ,true));
        lists.add(new ChatMessage("I am fine", 0 ,false));
    }

    public void initData(){
        mAdapter.addAll(lists);
        //设置列表布局管理
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置适配器
        recyclerView.setAdapter(mAdapter);

        //对两个按钮设立监听事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里直接调用了返回按键，没有对数据进行保存，设立客服系统的时候，要增加一个函数，对当前activity的状态进行保存。
                savedThisState();
                FinishesThisActivity();
            }
        });

        person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToPersonInfoPage();
            }
        });
    }

    public void FinishesThisActivity(){
        this.finish();
    }
    /**
     * 这里调用返回键以后，将聊天信息保存在这个函数当中
     */
    public void savedThisState(){

    }

    /**
     * 这里跳转到对方的个人信息栏中
     * （暂时还未写好）
     */
    public void jumpToPersonInfoPage(){
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }
}

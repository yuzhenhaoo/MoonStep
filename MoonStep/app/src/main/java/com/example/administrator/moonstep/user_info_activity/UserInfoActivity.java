package com.example.administrator.moonstep.user_info_activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.administrator.moonstep.R;

public class UserInfoActivity extends AppCompatActivity {

    private AppCompatImageButton magicWend;
    private AppCompatImageButton addFriends;
    private AppCompatImageButton sendMessages;
    private Button backBt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_info_page);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView(){
        backBt = this.findViewById(R.id.backBt);
        magicWend = this.findViewById(R.id.magicWend);
        addFriends = this.findViewById(R.id.addFriedns);
        sendMessages = this.findViewById(R.id.sendMesssages);

        magicWend.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    magicWend.setPadding(10,10,0,0);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    magicWend.setPadding(-10,-10,0,0);
                }
                return false;
            }
        });

        addFriends.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    addFriends.setPadding(10,10,0,0);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    addFriends.setPadding(-10,-10,0,0);
                }
                return false;
            }
        });

        sendMessages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    sendMessages.setPadding(10,10,0,0);
                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    sendMessages.setPadding(-10,-10,0,0);
                }
                return false;
            }
        });

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThisOne();
            }
        });
    }

    public void finishThisOne(){
        this.finish();
    }
}

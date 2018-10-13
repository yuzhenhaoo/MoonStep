package priv.zxy.moonstep.moonstep_palace.moon_friend.view;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.R;

public class UserInfoActivity extends AppCompatActivity implements IUserInfoView {

    private Intent intent;//创建intent对象用来接收ChattingActivity传递过来的User数据
    private Button backBt;
    private CircleImageView userPhoto;
    private TextView nickName;
    private TextView userLevel;
    private TextView userPet;
    private TextView userRace;
    private Button magicWend;
    private Button addFriend;
    private Button sendMessage;
    private TextView signature;
    /**
     * 对方的列表数据(为了防止安全问题，所以这个地方不加电话号码，只从ChattingActivity中获得应该显示在界面上的信息)
     */

    private Bitmap HeadPortrait = null;
    private String NickName = null;
    private String Race = null;
    private String Level = null;
    private String UserGender = null;
    private String Pet = null;
    private String Signature = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        initView();
        initData();

        updateData();

        setListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        backBt = (Button) findViewById(R.id.backBt);
        userPhoto = (CircleImageView) findViewById(R.id.userPhoto);
        nickName = (TextView) findViewById(R.id.nickName);
        userLevel = (TextView) findViewById(R.id.userLevel);
        userPet = (TextView) findViewById(R.id.userPet);
        userRace = (TextView) findViewById(R.id.userRace);
        magicWend = (Button) findViewById(R.id.magicWend);
        addFriend = (Button) findViewById(R.id.addFriend);
        sendMessage = (Button) findViewById(R.id.sendMessage);
        signature = (TextView) findViewById(R.id.signature);
    }

    public void initData() {
        intent = getIntent();
        // headPortrait = ?
        NickName = intent.getStringExtra("userNickName");
        Race = intent.getStringExtra("race");
        Level = intent.getStringExtra("level");
        UserGender = intent.getStringExtra("userGender");
        Pet = intent.getStringExtra("pet");
        Signature = intent.getStringExtra("signature");
    }

    /**
     * 这里设置更新对方信息
     */
    @Override
    public void updateData() {
        nickName.setText(NickName);
        userLevel.setText(Level);
        userPet.setText(Pet);
        userRace.setText(Race);
        signature.setText(Signature);
    }

    public void setListener(){
        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backBt.animate()
                        .rotation(-90)//逆时针旋转90°
                        .alpha(1)//旋转完成后改变透明度
                        .setDuration(600)//设置动画时长为600ms
                        .setListener(new Animator.AnimatorListener() {//设置动画的监听器，在动画完成的时候实现效果
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                finishThisOne();//结束当前页面
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
            }
        });

        //道具使用按钮
        magicWend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //添加好友按钮，点击的时候在本地数据库中检索是不是已经添加了该好友，如果本地数据库没有，则从网络上去检索是不是添加了该好友
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //发送消息按钮,只有身为好友的时候才能向对方发送消息，不是好友的话弹出添加好友的提示框，如果是好友的话，就直接进入聊天页面
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void finishThisOne() {
        this.finish();
    }

}

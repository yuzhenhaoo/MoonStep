package priv.zxy.moonstep.commerce.view.Friend;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Me.IUserInfoView;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.framework.user.User;

public class UserInfoActivity extends BaseActivity implements IUserInfoView {

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
    private Bitmap HeadPortrait = null;
    /**
     * 对方的列表数据(为了防止安全问题，所以这个地方不加电话号码，只从ChattingActivity中获得应该显示在界面上的信息)
     */
    private User personInfo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
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

    @Override
    protected void initEvent() {

    }

    public void initData() {
        personInfo = (User) getIntent().getParcelableExtra("moonfriend");
    }

    /**
     * 这里设置更新对方信息
     */
    @Override
    public void updateData() {
        nickName.setText(personInfo.getNickName());
//        userLevel.setText(personInfo.getLevelName());
        userPet.setText("还没有写好");
//        userRace.setText(personInfo.getRaceName());
        signature.setText(personInfo.getSignature());
    }

    public void setListener(){
        backBt.setOnClickListener(v -> backBt.animate()
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
                }));

        //道具使用按钮
        magicWend.setOnClickListener(v -> {

        });

        //添加好友按钮，点击的时候在本地数据库中检索是不是已经添加了该好友，如果本地数据库没有，则从网络上去检索是不是添加了该好友
        addFriend.setOnClickListener(v -> {

        });

        //发送消息按钮,只有身为好友的时候才能向对方发送消息，不是好友的话弹出添加好友的提示框，如果是好友的话，就直接进入聊天页面
        sendMessage.setOnClickListener(v -> {

        });
    }

    @Override
    public void finishThisOne() {
        this.finish();
    }

}

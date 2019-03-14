package priv.zxy.moonstep.commerce.view.Friend;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Me.IUserInfoView;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.framework.user.User;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener{

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
        backBt.setOnClickListener(this);
    }

    public void initData() {
        personInfo = (User) getIntent().getParcelableExtra("moonfriend");
        nickName.setText(personInfo.getNickName());
        userLevel.setText(personInfo.getLevel());
        userPet.setText("还没有写好");
        userRace.setText(personInfo.getRaceCode());
        signature.setText(personInfo.getSignature());
    }

    @Override
    public void onClick(View v) {
        if (v == backBt) {
            finish();
        }
        // 道具使用按钮
        if (v == magicWend) {

        }
        // 添加好友按钮，点击的时候在本地数据库中检索是不是已经添加了该好友，如果本地数据库没有，则从网络上去检索是不是添加了该好友
        if (v == addFriend) {

        }
        // 发送消息按钮,只有身为好友的时候才能向对方发送消息，不是好友的话弹出添加好友的提示框，如果是好友的话，就直接进入聊天页面
        if (v == sendMessage) {

        }
    }
}

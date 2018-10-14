package priv.zxy.moonstep.moonstep_palace.moon_friend.view;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.ToastUtil;
import priv.zxy.moonstep.kernel_data.bean.MoonStepHelper;
import priv.zxy.moonstep.moonstep_palace.moon_friend.presenter.ChattingAdapter;
import priv.zxy.moonstep.db.Message;

public class ChattingActivity extends AppCompatActivity implements IChattingView, View.OnLayoutChangeListener{

    private Activity mActivity;
    private Context mContext;
    private RecyclerView recyclerView;
    private List<Message> messagesQueues = new ArrayList<Message>();
    private ChattingAdapter mAdapter;
    private Button back;
    private Button person_info;
    private Button moreFuctions;
    private TextView userName;
    private TextView time;
    private EditText inputMessage;
    private Button sendMessage;
    private ScrollView scrollView;
    private ConstraintLayout root;
    private int rootHeight;//根布局原始高度
    private int keyHeight;//屏幕高度阀值
    private Intent intent;//用来接收Adapter中传过来的数据
    private Animation animation;

    /**
     * 对方的数据
     */
    private Bitmap HeadPortrait = null;
    private String PhoneNumber = null;
    private String NickName = null;
    private String Race = null;
    private String Level = null;
    private String UserGender = null;
    private String Pet = null;
    private String Signature = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fg_main_fifth_subpage);

        initView();

        initData();

        initList();

        updateData();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView(){
        mActivity = this;
        mContext = this.getApplicationContext();
        intent = getIntent();
        back = this.findViewById(R.id.back);
        person_info = this.findViewById(R.id.person_info);
        moreFuctions = this.findViewById(R.id.moreFunctions);
        inputMessage = this.findViewById(R.id.inputMessage);
        sendMessage = this.findViewById(R.id.sendMessage);
        userName = this.findViewById(R.id.userName);
        time = this.findViewById(R.id.time);
        recyclerView = this.findViewById(R.id.recycleview);
        scrollView = this.findViewById(R.id.scrollview);

        root = this.findViewById(R.id.root);

        rootHeight = this.getWindowManager().getDefaultDisplay().getHeight();

        keyHeight =  rootHeight / 3;

        mAdapter = new ChattingAdapter(getApplicationContext());

        animation = AnimationUtils.loadAnimation(mContext, R.anim.animation1);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toPersonInfoPage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        sendMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        sendMessageToMoonFriend();
                        break;
                    case MotionEvent.ACTION_UP://软键盘弹起的时候滑到最底部
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        getFocusPopUpSoftKeyboard();
                        break;
                }
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back.animate()
                        .rotation(-90)//逆时针旋转90°
                        .alpha(1)//旋转完成后改变透明度
                        .setDuration(600)//设置动画时长为600ms
                        .setListener(new Animator.AnimatorListener() {//设置动画的监听器，在动画完成的时候实现效果
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                FinishesThisActivity(mActivity);
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

        person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person_info.startAnimation(animation);
            }
        });
    }

    public void initData(){
//        HeadPortrait = ?
        PhoneNumber = intent.getStringExtra("phoneNumber");
        NickName = intent.getStringExtra("userNickName");
        Race = intent.getStringExtra("race");
        Level = intent.getStringExtra("level");
        UserGender = intent.getStringExtra("userGender");
        Pet = intent.getStringExtra("pet");
        Signature = intent.getStringExtra("signature");

        //设置列表布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * 初始化List列表
     * 方法：从数据库中查找到当前聊天对象的所有聊天记录放在当前messagesQueues当中
     *       并将取出的列表初始化到页面当中，默认初始化30条，更早的需要通过recyclerView的刷新来控制
     */
    public void initList(){
        messagesQueues = LitePal.where("object = ?", PhoneNumber).find(Message.class);
        //显示的想法：每个用户的历史聊天记录存储上限为1000条，如果上限超过1000，就把最早的一部分进行清除，保留最近的1000条消息记录
        //一开始最多只显示30条消息记录，如果要显示更多，则需要下拉刷新来控制，需要设置监听器。
        mAdapter.addAll(messagesQueues);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);//向下滑动
        mAdapter.notifyDataSetChanged();
    }

    public void updateData(){
        userName.setText(NickName);
        time.setText(getTime());
    }

    @Override
    public void FinishesThisActivity(Activity mActivity) {
        this.finish();
    }

    /**
     * 发送的消息和接收的消息都应该存储到数据库中
     */
    @Override
    public Message savedChattingMessage(String content, int type) {
        Message message = new Message();
        message.setContent(content);
        message.setDirection(1);//1、我发送的;2、对方发送的
        message.setObject(PhoneNumber);
        message.setType(type);//1、文字；2、图片；3、音频；4、视频；5、红包；6、文件；7、位置
        message.save();
        messagesQueues.add(message);
        return message;
    }

    @Override
    public void toPersonInfoPage() {
        Log.e("TAG", "personInfo2");
        Intent intent = new Intent(mActivity, UserInfoActivity.class);
        //        intent.putExtra("headPortrait", item.getHeadPortrait());//暂时还不知道Bitmap怎么通过Activity进行传输
        intent.putExtra("phoneNumber", PhoneNumber);
        intent.putExtra("userNickName", NickName);
        intent.putExtra("race", Race);
        intent.putExtra("level", Level);
        intent.putExtra("userGender", UserGender);
        intent.putExtra("pet", Pet);
        intent.putExtra("signature", Signature);
        mActivity.startActivity(intent);
    }

    /**
     * 向好友发送消息的模块
     */
    @Override
    public void sendMessageToMoonFriend() {
        String message = inputMessage.getText().toString();
        if(message.trim().isEmpty()){
            new ToastUtil(this.getApplicationContext(), this).showToast("发送地消息不能为空哦！");
        }else{
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);//向下滑动

            MoonStepHelper.getInstance().EMsendMessage(message, "moonstep" + PhoneNumber, EMMessage.ChatType.Chat);
//            savedChattingMessage(message, 1);
            inputMessage.getText().clear();//发送后立刻清空输入框

            mAdapter.add(savedChattingMessage(message, 1));
            mAdapter.notifyDataSetChanged();
        }
    }

    private void getFocusPopUpSoftKeyboard(){
        inputMessage.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        root.addOnLayoutChangeListener(this);
        scrollView.post(new Runnable() {//使用Runnable的意义是将消息加入到消息队列中，在界面显示的时候开始调用
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
            }
        });
    }


    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        // old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        // 现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0
                && (oldBottom - bottom > keyHeight)) {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);//向下滑动
        } else if (oldBottom != 0 && bottom != 0
                && (bottom - oldBottom > keyHeight)) {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);//向下滑动
        }
    }

    @Override
    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}

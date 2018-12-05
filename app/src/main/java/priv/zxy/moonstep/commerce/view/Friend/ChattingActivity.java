package priv.zxy.moonstep.commerce.view.Friend;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.ActivityCollector;
import priv.zxy.moonstep.framework.message.Message;
import priv.zxy.moonstep.framework.message.MessageOnline;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.ToastUtil;
import priv.zxy.moonstep.helper.MoonStepHelper;
import priv.zxy.moonstep.adapter.ChattingMessageAdapter;

/**
 * 创建人: Administrator
 * 创建时间: 2018/09/11
 * 描述: 创建聊天Activity，但是这里没有继承BaseActivity,因为沉浸式体验（全屏模式）会与软键盘弹起时的windowSoftInputMode
 *       属性发生冲突，为了避免这种冲突，只能避免使用全屏模式，被迫显示通知栏，使用多种方式解决均没有产生效果。
 **/

public class ChattingActivity extends AppCompatActivity implements IChattingView, View.OnLayoutChangeListener{

    private static final String TAG = "ChattingActivity";
    private Activity mActivity;
    private Context mContext;
    private RecyclerView recyclerView;
    private List<Message> messagesQueues = new ArrayList<>();
    private ChattingMessageAdapter mAdapter;
    private Button back;
    private Button person_info;
    private Button moreFuctions;
    private TextView userName;
    private TextView time;
    private EditText inputMessage;
    private Button sendMessage;
//    private NestedScrollView scrollView;
    private ConstraintLayout root;
    private int rootHeight;//根布局原始高度
    private int keyHeight;//屏幕高度阀值
    private Animation animation;
    private LinearLayout messageBar;

    /**
     * 获得当前聊天好友的对象
     */
    private User moonFriend;

    /**
     * 接收到的服务端消息集合
     */
    private List<EMMessage> emMessages = null;

    //通过EM获取好友的消息队列
    private EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            LogUtil.d(TAG,"接收到了消息:");
            emMessages = messages;
            for(final EMMessage message: emMessages){
//                LogUtil.d(TAG,"message来源:    " + message.getFrom().substring(8, message.getFrom().length()));
                final String[] msg = MoonStepHelper.getInstance().getMessageTypeWithBody(message.getBody().toString().trim());
                switch (MoonStepHelper.getInstance().transformMessageType(msg[0])){
                    case TEXT://处理文本消息
                        LogUtil.e("Message","ChattingActivity" + msg[1]);
                        runOnUiThread(()->savedChattingMessage(msg[1], 0, 1, message.getFrom().substring(8)));
                        break;
                    case IMAGE://处理图片消息
                        break;
                    case VIDEO://处理视频消息
                        break;
                    case LOCATION://处理位置消息
                        break;
                    case VOICE://处理声音消息
                        break;
                }
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
            LogUtil.d(TAG, "收到透传消息");
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
            LogUtil.d(TAG, "收到已读回执");
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
            LogUtil.d(TAG, "收到已送达回执");
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
            LogUtil.d(TAG, "消息状态变动");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityCollector.addActivity(this);
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
        back = this.findViewById(R.id.back);
        person_info = this.findViewById(R.id.person_info);
        moreFuctions = this.findViewById(R.id.moreFunctions);
        inputMessage = this.findViewById(R.id.inputMessage);
        sendMessage = this.findViewById(R.id.sendMessage);
        userName = this.findViewById(R.id.userName);
        time = this.findViewById(R.id.time);
        recyclerView = this.findViewById(R.id.recycleview);

        messageBar = findViewById(R.id.message_bar);

        root = this.findViewById(R.id.root);

        rootHeight = this.getWindowManager().getDefaultDisplay().getHeight();

        keyHeight =  rootHeight / 3;

        mAdapter = new ChattingMessageAdapter(getApplicationContext());

        animation = AnimationUtils.loadAnimation(mContext, R.anim.animation1);

        recyclerView.setNestedScrollingEnabled(false);

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

        sendMessage.setOnTouchListener((v, event)->{
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                     sendMessageToMoonFriend();
                     recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);//将RecyclerView定位到最后一行
                     break;
                case MotionEvent.ACTION_UP://软键盘弹起的时候滑到最底部
                     getFocusPopUpSoftKeyboard();
                     break;
            }
            return true;
        });

        back.setOnClickListener(v->{
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
                            finishThisActivity(mActivity);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
        });

        person_info.setOnClickListener(v->person_info.startAnimation(animation));

        new Handler().postDelayed(()->{
            inputMessage.setFocusable(true);
            inputMessage.setFocusableInTouchMode(true);
            inputMessage.requestFocus();
            InputMethodManager inputMethodManager = (InputMethodManager)inputMessage.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(inputMessage, 0);
        }, 300);

        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    public void initData(){
//        HeadPortrait = ?
        moonFriend = (User) getIntent().getParcelableExtra("moonfriend");

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
        messagesQueues = MessageOnline.getInstance().getMessageFromDatabase(moonFriend.getPhoneNumber());
        //显示的想法：每个用户的历史聊天记录存储上限为1000条，如果上限超过1000，就把最早的一部分进行清除，保留最近的1000条消息记录
        //一开始最多只显示30条消息记录，如果要显示更多，则需要下拉刷新来控制，需要设置监听器。
        mAdapter.addAll(messagesQueues);
        mAdapter.notifyDataSetChanged();
    }

    public void updateData(){
        userName.setText(moonFriend.getNickName());
        time.setText(getTime());
    }

    @Override
    public void finishThisActivity(Activity mActivity) {
        this.finish();
    }

    /**
     * 发送的消息和接收的消息都应该存储到数据库中
     * 同时让scrollView下滑
     */
    @Override
    public Message savedChattingMessage(String content, int direction, int type, String phoneNumber) {
        MessageOnline.getInstance().saveMessageToDataBase(content, direction, type, phoneNumber);
        Message msg = new Message();
        msg.setContent(content);
        msg.setDirection(direction);
        msg.setType(type);
        msg.setObject("moonstep " + phoneNumber);
        messagesQueues.add(msg);
        mAdapter.add(msg);
        mAdapter.notifyDataSetChanged();
        return msg;
    }

    @Override
    public void toPersonInfoPage() {
        Intent intent = new Intent(mActivity, UserInfoActivity.class);
        //        intent.putExtra("headPortrait", item.getHeadPortrait());//暂时还不知道Bitmap怎么通过Activity进行传输
        intent.putExtra("moonfriend", moonFriend);
        mActivity.startActivity(intent);
    }

    /**
     * 向好友发送消息的模块
     */
    @Override
    public void sendMessageToMoonFriend() {
        final String message = inputMessage.getText().toString();
        if(message.trim().isEmpty()){
            ToastUtil.getInstance(mContext, this).showToast("发送地消息不能为空哦！");
        }else{
            new Thread(()->MoonStepHelper.getInstance().EMsendMessage(message, "moonstep" + moonFriend.getPhoneNumber(), EMMessage.ChatType.Chat)).start();//向对方发送消息(异步)
            inputMessage.getText().clear();//发送后立刻清空输入框
            inputMessage.requestFocus();//对输入框请求焦点
            savedChattingMessage(message, 1,1, moonFriend.getPhoneNumber());
        }
    }

    private void getFocusPopUpSoftKeyboard(){
        inputMessage.requestFocus();
    }

    /**
     * 初始化进入界面后，让ScrollView自动滑动到最底部
     */
    @Override
    protected void onResume() {
        super.onResume();
        root.addOnLayoutChangeListener(this);
        new Handler().post(()->recyclerView.scrollToPosition(mAdapter.getItemCount() - 1));
    }


    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        // old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
        // 现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0
                && (oldBottom - bottom > keyHeight)) {
            inputMessage.requestFocus();//请求焦点
            recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);//将RecyclerView定位到最后一行
        } else if (oldBottom != 0 && bottom != 0
                && (bottom - oldBottom > keyHeight)) {
            inputMessage.requestFocus();//请求焦点
            recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);//将RecyclerView定位到最后一行
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);//移除Listener
        ActivityCollector.removeActivity(this);
        LogUtil.d("ChattingActivity","onDestroy");
    }

    @Override
    public String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}

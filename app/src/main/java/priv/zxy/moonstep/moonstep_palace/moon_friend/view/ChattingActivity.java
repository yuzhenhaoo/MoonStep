package priv.zxy.moonstep.moonstep_palace.moon_friend.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.ToastUtil;
import priv.zxy.moonstep.kernel_data.bean.ChatMessage;
import priv.zxy.moonstep.kernel_data.bean.MoonStepHelper;
import priv.zxy.moonstep.moonstep_palace.moon_friend.presenter.ChattingAdapter;

public class ChattingActivity extends AppCompatActivity implements IChattingView, View.OnLayoutChangeListener{

    private Activity mActivity;
    private RecyclerView recyclerView;
    private List<ChatMessage> lists = new ArrayList<ChatMessage>();
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
        //告诉你的activity你要切换动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.explode);
//        Transition slide_left = TransitionInflater.from(this).inflateTransition(R.transition.slide_left);
        Transition slide_right = TransitionInflater.from(this).inflateTransition(R.transition.slide_right);
        //退出时使用
//        getWindow().setExitTransition(slide_left);
        //第一次进入时使用
        getWindow().setEnterTransition(slide_right);
        //再次进入时使用
        getWindow().setReenterTransition(explode);
        setContentView(R.layout.fg_main_fifth_subpage);

        initView();
        initData();

        updateData();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView(){
        mActivity = this;
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

        //对两个按钮设立监听事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这里直接调用了返回按键，没有对数据进行保存，设立客服系统的时候，要增加一个函数，对当前activity的状态进行保存。
                savedChattingMessage();
                FinishesThisActivity(mActivity);
            }
        });

        person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "personInfo1");
                toPersonInfoPage();
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

        mAdapter.clear();
        mAdapter.addAll(lists);
        //设置列表布局管理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        recyclerView.setAdapter(mAdapter);
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
     * 要在这里保存ChattingMessage地历史信息，实际上是从EC服务器上请求历史地交流数据，是一个网络耗时操作
     * 由于是一个网络耗时操作，所以要放在module中进行处理
     */
    @Override
    public void savedChattingMessage() {

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

    @Override
    public void sendMessageToMoonFriend() {
        String message = inputMessage.getText().toString();
        if(message.trim().isEmpty()){
            new ToastUtil(this.getApplicationContext(), this).showToast("发送地消息不能为空哦！");
        }else{
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);//向下滑动

            MoonStepHelper.getInstance().EMsendMessage(message, "moonstep" + PhoneNumber, EMMessage.ChatType.Chat);

            inputMessage.getText().clear();//发送后立刻清空输入框
            lists.add(new ChatMessage(message, true));
            mAdapter.notifyDataSetChanged();
            initData();
        }
    }

    private void getFocusPopUpSoftKeyboard(){
        inputMessage.requestFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        root.addOnLayoutChangeListener(this);
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

package priv.zxy.moonstep.connectation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.kernel.BaseActivity;
import priv.zxy.moonstep.kernel.bean.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class MainFifthPageActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<ChatMessage> lists = new ArrayList<ChatMessage>();
    private MainFifthPageAdapter mAdapter;
    private Button back;
    private Button person_info;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fg_main_fifth_subpage);

        initView();
        initData();
    }

    public void initView(){
        back = this.findViewById(R.id.back);
        person_info = this.findViewById(R.id.person_info);

        recyclerView = this.findViewById(R.id.recycleview);
        mAdapter = new MainFifthPageAdapter(getApplicationContext());
        lists.add(new ChatMessage("您好，我是您的客服，有什么可以帮助您的吗？" ,false));
        lists.add(new ChatMessage("是的，也没有什么别的事情！",true));
        lists.add(new ChatMessage("我想知道你们官方对于圆月行的态度如何!",true));
        lists.add(new ChatMessage("这是一款非常好的app，希望您可以坚持玩下去就可以看到它的风采",false));
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
     * 这里调用返回键以后，保存客服对话信息的数据在这个函数里面
     */
    public void savedThisState(){

    }

    /**
     * 这里跳转到对方的个人信息栏中
     * （暂时还未写好）
     */
    public void jumpToPersonInfoPage(){

    }
}

package priv.zxy.moonstep.gps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.Adapter;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.framework.good.Props;
import priv.zxy.moonstep.framework.good.bean.Good;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 背包Activity
 **/

public class PackActivity extends AppCompatActivity {

    private static final String TAG = "PackActivity";
    private Adapter<Good> mAdapter = null;
    private List<Good> goods = null;
    private GridView packView = null;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAdapter = new Adapter<Good>(goods, R.layout.pack_item) {
                @Override
                public void bindView(ViewHolder holder, Good obj) {
                    holder.setImageResource(R.id.itemSrc, obj.getGoodImagePath());
                    holder.setText(R.id.itemNumber, String.valueOf(obj.getNumber()));
                }
            };
            packView.setAdapter(mAdapter);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack);

        initView();

        initData();

        initEvent();
    }

    private void initView(){
        packView = findViewById(R.id.GridLayout);

        goods = new ArrayList<>();
    }

    public void initData(){
        Props props = new Props();
        Map<String, String> data = SharedPreferencesUtil.getInstance(Application.getContext()).readMySelfInformation();

        props.getUserGoods(gs -> {
            goods = gs;
            LogUtil.d(TAG, goods.toString());
            handler.sendEmptyMessage(0x01);
        }, data.get("phoneNumber"));
        
        packView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(PackActivity.this, "你点击了" + position + "个背包" , Toast.LENGTH_SHORT).show());
    }

    public void initEvent(){

    }
}

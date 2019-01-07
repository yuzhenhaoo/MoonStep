package priv.zxy.moonstep.gps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.AbstractAdapter;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.framework.good.GoodSelfInfo;
import priv.zxy.moonstep.framework.good.Props;
import priv.zxy.moonstep.framework.good.bean.Good;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.user.UserSelfInfo;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 背包Activity
 **/

public class PackActivity extends AppCompatActivity {

    private static final String TAG = "PackActivity";
    private GridView packView;

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
    }

    public void initData(){
        AbstractAdapter<Good> mAbstractAdapter = new AbstractAdapter<Good>(GoodSelfInfo.getInstance().getGoods(), R.layout.pack_item) {
            @Override
            public void bindView(ViewHolder holder, Good obj) {
                holder.setImageResource(R.id.itemSrc, obj.getGoodImagePath());
                holder.setText(R.id.itemNumber, String.valueOf(obj.getNumber()));
            }
        };
        packView.setAdapter(mAbstractAdapter);
        
        packView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(PackActivity.this, "你点击了" + position + "个背包" , Toast.LENGTH_SHORT).show());
    }

    public void initEvent(){

    }
}

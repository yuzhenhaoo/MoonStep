package priv.zxy.moonstep.gps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.AbstractAdapter;
import priv.zxy.moonstep.framework.good.GoodSelfInfo;
import priv.zxy.moonstep.framework.good.bean.Good;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 背包Activity
 **/

public class PackActivity extends AppCompatActivity {

    private static final String TAG = "PackActivity";
    private GridView packView;
    private LinearLayout fg;

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
        fg = findViewById(R.id.fg_bottom);
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
        fg.setOnTouchListener((v, event) -> true);
    }
}

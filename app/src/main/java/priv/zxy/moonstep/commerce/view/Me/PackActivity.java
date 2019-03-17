package priv.zxy.moonstep.commerce.view.Me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.AbstractAdapter;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.framework.good.GoodInfoManager;
import priv.zxy.moonstep.framework.good.bean.Good;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 背包Activity
 **/

public class PackActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "PackActivity";
    private GridView packView;
    private Button bt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_pack);
    }

    @Override
    protected void initEvent() {
        bt.setOnClickListener(this);
    }

    public void initData(){
        List<Good> goods = GoodInfoManager.getInstance().getGoods();
        initGoods(goods);
        AbstractAdapter<Good> mAbstractAdapter = new AbstractAdapter<Good>(GoodInfoManager.getInstance().getGoods(), R.layout.pack_item) {
            @Override
            public void bindView(ViewHolder holder, Good obj) {
                holder.setImageResource(R.id.itemSrc, obj.getGoodImagePath());
                holder.setText(R.id.itemNumber, String.valueOf(obj.getNumber()));
            }
        };
        packView.setAdapter(mAbstractAdapter);
        
        packView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(PackActivity.this, "你点击了" + position + "个背包" , Toast.LENGTH_SHORT).show());
    }

    private void initGoods(List<Good> goods) {
        if (goods.size() < 4 * 6) {
            for (int i = 4 * 6 - goods.size(); i > 0; i--) {
                goods.add(new Good());
            }
        }
    }

    @Override
    protected void initView() {
        packView = findViewById(R.id.gridView);
        bt = findViewById(R.id.back);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(bt)) {
            finish();
        }
    }
}

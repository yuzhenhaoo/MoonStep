package priv.zxy.moonstep.commerce.view.Me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseActivity;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/9
 * 描述: 用来展示用户详情页面的Activity
 **/

public class UserDetailActivity extends BaseActivity{

    private Button back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peronal_info);

        initView();

        initData();

        initEvent();
    }

    private void initView(){
        back = findViewById(R.id.backBt);
    }

    private void initData(){

    }

    private void initEvent(){
        back.setOnClickListener(v->{
            UserDetailActivity.this.finish();
        });
    }
}

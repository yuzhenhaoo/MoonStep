package priv.zxy.moonstep.commerce.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.kernel.BaseActivity;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/9
 * 描述: 用来展示用户详情页面的Activity
 **/
public class UserDetailActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
    }
}

package priv.zxy.moonstep.commerce.view.Me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.presenter.UserDetailPresenter;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.framework.race.Race;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/9
 * 描述: 用来展示用户详情页面的Activity
 **/

public class UserDetailActivity extends BaseActivity implements IUserDetailView{

    private UserDetailPresenter mUserDetailPresenter;

    private Button back;

    private TextView raceNameTV;

    private TextView raceDescriptionTV;

    private ImageView raceHeadPortrait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peronal_info);

        initView();

        initData();

        initEvent();
    }

    private void initView(){
        mUserDetailPresenter = new UserDetailPresenter(this);
        back = findViewById(R.id.backBt);
        raceNameTV = findViewById(R.id.raceNameTV);
        raceDescriptionTV = findViewById(R.id.raceDescriptionTV);
        raceHeadPortrait = findViewById(R.id.raceHeadPortrait);
    }

    private void initData(){
        mUserDetailPresenter.setRaceData();
    }

    private void initEvent(){
        back.setOnClickListener(v->{
            UserDetailActivity.this.finish();
        });
    }

    @Override
    public void initRaceData(Race race){
        raceNameTV.setText(race.getRaceName());
        raceDescriptionTV.setText(race.getRaceDescription());
    }

    @Override
    public void error(String error_msg){
        Toast.makeText(this, error_msg, Toast.LENGTH_SHORT).show();
    }
}

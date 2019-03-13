package priv.zxy.moonstep.commerce.view.Me;

import android.graphics.Bitmap;
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

public class RaceActivity extends BaseActivity implements IUserDetailView{

    private UserDetailPresenter mUserDetailPresenter;

    private Button back;

    private TextView raceNameTV;

    private TextView raceDescriptionTV;

    private ImageView raceHeadPortrait;

    private ImageView raceIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peronal_info);
    }

    protected void initView(){
        mUserDetailPresenter = new UserDetailPresenter(this);
        back = findViewById(R.id.backBt);
        raceNameTV = findViewById(R.id.raceNameTV);
        raceDescriptionTV = findViewById(R.id.raceDescriptionTV);
        raceHeadPortrait = findViewById(R.id.raceHeadPortrait);
        raceIcon = findViewById(R.id.raceIcon);
    }

    protected void initData(){
        mUserDetailPresenter.setRaceData();
    }

    protected void initEvent(){
        back.setOnClickListener(v->{
            RaceActivity.this.finish();
        });
    }

    @Override
    public void initRaceData(Race race){
        raceNameTV.setText(race.getRaceName());
        raceDescriptionTV.setText(race.getRaceDescription());
    }

    @Override
    public void setRaceIcon(Bitmap bitmap) {
        raceIcon.setImageBitmap(bitmap);
    }

    @Override
    public void setRaceImage(Bitmap bitmap) {
        raceHeadPortrait.setImageBitmap(bitmap);
    }

    @Override
    public void error(String error_msg){
        Toast.makeText(this, error_msg, Toast.LENGTH_SHORT).show();
    }
}
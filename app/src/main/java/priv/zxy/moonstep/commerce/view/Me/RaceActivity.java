package priv.zxy.moonstep.commerce.view.Me;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.presenter.UserDetailPresenter;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.framework.race.Race;

/**
 * 创建人: LYJ
 * 创建时间: 2019/02/09
 * 描述: 用来展示用户种族信息的页面
 **/

public class RaceActivity extends BaseActivity implements IUserDetailView, View.OnClickListener{

    private UserDetailPresenter mUserDetailPresenter;

    private Button back;
    private ImageView raceHeadPortrait;
    private ImageView raceIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peronal_info);
    }

    protected void initView(){
        back = findViewById(R.id.back);
        raceHeadPortrait = findViewById(R.id.raceHeadPortrait);
        raceIcon = findViewById(R.id.raceIcon);
    }

    protected void initData(){
        mUserDetailPresenter = new UserDetailPresenter(this);
        mUserDetailPresenter.setRaceData();
    }

    protected void initEvent(){
        back.setOnClickListener(this);
    }

    @Override
    public void initRaceData(Race race){
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

    @Override
    public void onClick(View v) {
        if (v.equals(back)) {
            finish();
        }
    }
}

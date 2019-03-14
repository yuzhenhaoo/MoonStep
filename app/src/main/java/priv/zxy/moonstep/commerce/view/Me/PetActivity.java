package priv.zxy.moonstep.commerce.view.Me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Map.IPetView;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.framework.pet.Pet;
import priv.zxy.moonstep.framework.stroage.PetInfo;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/28
 * 描述:宠物UI界面
 **/
public class PetActivity extends BaseActivity implements IPetView, View.OnClickListener {


    private TextView mPetDescription;
    private TextView mPetEffectiveness;
    private ImageView mPetImage;
    private TextView mPetNameValue;
    private TextView mPetRaceValue;
    private TextView mGrowLevel;
    private TextView mPetEffectivenessValue;
    private TextView mSkill3;
    private TextView mSkill2;
    private TextView mPetLevelValue;
    private TextView mSkill1;
    private Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_pet);
    }
    @Override
    protected void initView() {
        mPetDescription = findViewById(R.id.pet_description);
        mPetEffectiveness = findViewById(R.id.pet_effectiveness);
        mPetImage = findViewById(R.id.pet_image);
        mPetNameValue = findViewById(R.id.pet_name_value);
        mPetRaceValue = findViewById(R.id.pet_race_value);
        mGrowLevel = findViewById(R.id.grow_level);
        mPetEffectivenessValue = findViewById(R.id.pet_effectiveness_value);
        mSkill3 = findViewById(R.id.skill_3);
        mSkill2 = findViewById(R.id.skill_2);
        mPetLevelValue = findViewById(R.id.pet_level_value);
        mSkill1 = findViewById(R.id.skill_1);
        back = findViewById(R.id.back);
    }

    public void initData() {
//        Glide.with(this).load(PetInfo.getInstance().getPet().getPetImagePath()).into(mPetImage);
        mPetNameValue.setText(PetInfo.getInstance().getPet().getPetNickName());
        mPetRaceValue.setText(PetInfo.getInstance().getPet().getPetRace());
//        mPetLevelValue.setText(PetInfo.getInstance().getPet().getPetLevel());
//        mPetDescription.setText(PetInfo.getInstance().getPet().getPetDescription());
//        mPetEffectiveness.setText(PetInfo.getInstance().getPet().getPetCbPw());
    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(this);
    }

    /**
     * 设置UI数据
     */
    @Override
    public void setData(Pet pet) {

    }

    @Override
    public void error(String error_msg) {
        Toast.makeText(this, error_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == back) {
            finish();
        }
    }
}

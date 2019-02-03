package priv.zxy.moonstep.commerce.view.Tree;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.presenter.PetPresenter;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.framework.pet.Pet;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/28
 * 描述:宠物UI界面
 **/
public class PetActivity extends BaseActivity implements IPetView{

    private PetPresenter mPetPresenter;

    private TextView levelNameText;

    private TextView raceNameText;

    private TextView petIntroduceTVText;

    private TextView skillTVText;

    private TextView uiIV3Text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);
        initData();
        mPetPresenter.setData();
    }

    /**
     *  数据初始化
     */
    public void initData() {
        mPetPresenter = new PetPresenter(this);
        levelNameText = findViewById(R.id.levelName);
        raceNameText = findViewById(R.id.raceName);
        petIntroduceTVText = findViewById(R.id.petIntroduceTV);
        skillTVText = findViewById(R.id.skillTV);
        uiIV3Text = findViewById(R.id.uiIV3);
    }

    /**
     *  设置UI数据
     */
    @Override
    public void setData(Pet pet){
        levelNameText.setText(String.valueOf(pet.getPetLevel()));
        raceNameText.setText(pet.getPetRace());
        petIntroduceTVText.setText(pet.getPetDescription());
        skillTVText.setText(pet.getSkillName());
        uiIV3Text.setText(pet.getSkillDescription());
        
    }

    @Override
    public void error(String error_msg){
        Toast.makeText(this, error_msg, Toast.LENGTH_SHORT).show();
    }
}

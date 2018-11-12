package priv.zxy.moonstep.db;

import org.litepal.crud.LitePalSupport;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/5
 * 描述: 宠物基类
 **/
public class Pet extends LitePalSupport{

    public int getPetCode() {
        return petCode;
    }

    public void setPetCode(int petCode) {
        this.petCode = petCode;
    }

    public String getPetNickName() {
        return petNickName;
    }

    public void setPetNickName(String petNickName) {
        this.petNickName = petNickName;
    }

    public int getPetCbPw() {
        return petCbPw;
    }

    public void setPetCbPw(int petCbPw) {
        this.petCbPw = petCbPw;
    }

    public String getPetRace() {
        return petRace;
    }

    public void setPetRace(String petRace) {
        this.petRace = petRace;
    }

    public int getPetLevel() {
        return petLevel;
    }

    public void setPetLevel(int petLevel) {
        this.petLevel = petLevel;
    }

    public float getPetProbability() {
        return petProbability;
    }

    public void setPetProbability(float petProbability) {
        this.petProbability = petProbability;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public String getPetImagePath() {
        return petImagePath;
    }

    public void setPetImagePath(String petImagePath) {
        this.petImagePath = petImagePath;
    }

    public String getGrowthDegree() {
        return growthDegree;
    }

    public void setGrowthDegree(String growthDegree) {
        this.growthDegree = growthDegree;
    }

    //宠物编码
    private int petCode;

    //宠物昵称
    private String petNickName;

    //宠物战斗力
    private int petCbPw;

    //宠物种族
    private String petRace;

    //宠物等阶
    private int petLevel;

    //宠物获得概率
    private float petProbability;

    //宠物描述
    private String petDescription;

    //技能名称
    private String skillName;

    //技能描述
    private String skillDescription;

    //宠物图片
    private String petImagePath;

    //宠物成长度
    private String growthDegree;
}

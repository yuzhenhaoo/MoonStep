package priv.zxy.moonstep.login.module.bean;

/**
 *  Created by Zxy on 2018/9/20
 */

public class User {
    //用户姓名
    private String nickName;

    //用户密码
    private String passWord;

    //用户手机号
    private String phoneNumber;

    //用户性别
    private String gender;

    //用户种族
    private String race;

    //用户阶别
    private String level;

    //用户宠物
    private String pet;

    public String getNickName(){
        return nickName;
    }

    public void setNickName(String userName){
        this.nickName = nickName;
    }

    public String getUserPassword(){
        return passWord;
    }

    public void setPassword(String passWord){
        this.passWord = passWord;
    }

    public void setUserPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getUserPhoneNumber(){
        return phoneNumber;
    }

    public void setUserGender(String gender){
        this.gender = gender;
    }

    public String getUserGender(){
        return gender;
    }

    public void setUserRace(String race){
        this.race = race;
    }

    public String getUserRace(){
        return race;
    }

    public void setUserLevel(String level){
        this.level = level;
    }

    public String getUserLevel(){
        return level;
    }

    public void setUserPet(String pet){
        this.pet = pet;
    }

    public String getUserPet(){
        return pet;
    }
}

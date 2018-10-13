package priv.zxy.moonstep.db;


import android.graphics.Bitmap;

import org.litepal.crud.LitePalSupport;

import java.sql.Blob;

public class MoonFriend extends LitePalSupport{
    //用户姓名
    private String nickName;

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

    //用户头像(存储用户头像的时候，要存储的实际上是二进制的字节)
    private byte[] headPortrait;

    //用户签名
    private String signature;

    //当前用户是否已经登陆,0没有登陆，1登陆,获取方式详见REST API
    private int isOnline;

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public byte[] getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(byte[] headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}

package priv.zxy.moonstep.framework.user;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/5
 * 描述: 用户基类
 **/

public class User extends LitePalSupport implements Parcelable {

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getRaceCode() {
        return raceCode;
    }

    public void setRaceCode(int raceCode) {
        this.raceCode = raceCode;
    }

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getLocation() {
        return address;
    }

    public void setLocation(String address) {
        this.address = address;
    }

    public String getCurrentTitleCode() {
        return currentTitleCode;
    }

    public void setCurrentTitleCode(String currentTitleCode) {
        this.currentTitleCode = currentTitleCode;
    }

    public int getLuckyValue() {
        return luckyValue;
    }

    public void setLuckyValue(int luckyValue) {
        this.luckyValue = luckyValue;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    //用户id
//    private String userID;

    //用户手机号
    private String phoneNumber;

    //用户密码
//    private String passWord;

    //用户昵称
    private String nickName;

    //用户性别
    private String gender;

    //种族编码
    private int raceCode;

    //用户头像路径
    private String headPath;//将头像文件存到本地一个文件夹下构建成为一个文件对象，这里直接存路径

    //用户签名
    private String signature;

    //用户位置
    private String address;

    //用户当前称号
    private String currentTitleCode;

    //幸运值
    private int luckyValue;

    //是否在线
    private int isOnline;

    public User(){

    }

    protected User(Parcel in) {
//        userID = in.readString();
        phoneNumber = in.readString();
//        passWord = in.readString();
        nickName = in.readString();
        gender = in.readString();
        raceCode = in.readInt();
        headPath = in.readString();
        signature = in.readString();
        address = in.readString();
        currentTitleCode = in.readString();
        luckyValue = in.readInt();
        isOnline = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(userID);
        dest.writeString(phoneNumber);
//        dest.writeString(passWord);
        dest.writeString(nickName);
        dest.writeString(gender);
        dest.writeInt(raceCode);
        dest.writeString(headPath);
        dest.writeString(signature);
        dest.writeString(address);
        dest.writeString(currentTitleCode);
        dest.writeInt(luckyValue);
//        dest.writeInt(isOnline);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

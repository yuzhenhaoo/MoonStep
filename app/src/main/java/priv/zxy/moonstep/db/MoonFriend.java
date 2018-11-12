package priv.zxy.moonstep.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;

/**
 * 利用Parcelable进行序列化，方便在Activity之间利用Intent传递数据
 * 尽量使用Serializable而不要使用Serializable，因为Serializable会将整个对象序列化，效率比Parcelable要低一些。
 */
public class MoonFriend extends LitePalSupport implements Parcelable{
    //用户姓名
    private String nickName;

    //用户手机号
    private String phoneNumber;

    //用户性别
    private String gender;

    //用户种族
    private String raceName;

    //种族描述
    private String raceDescription;

    //用户阶别
    private String levelName;

    //用户阶别描述
    private String levelDescription;

    //当前称号
    private String currentTitle;

    //用户头像存储的是网络上的路径
    private String headPortraitPath;

    //用户签名
    private String signature;//

    //当前用户是否已经登陆,0没有登陆，1登陆,获取方式详见REST API
    private int isOnline;

    public MoonFriend(){

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

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getRaceDescription() {
        return raceDescription;
    }

    public void setRaceDescription(String raceDescription) {
        this.raceDescription = raceDescription;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelDescription() {
        return levelDescription;
    }

    public void setLevelDescription(String levelDescription) {
        this.levelDescription = levelDescription;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getHeadPortraitPath() {
        return headPortraitPath;
    }

    public void setHeadPortraitPath(String headPortraitPath) {
        this.headPortraitPath = headPortraitPath;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    protected MoonFriend(Parcel in) {
        nickName = in.readString();
        phoneNumber = in.readString();
        gender = in.readString();
        raceName = in.readString();
        levelName = in.readString();
        levelDescription = in.readString();
        raceDescription = in.readString();
        currentTitle = in.readString();
        headPortraitPath = in.readString();
        signature = in.readString();
        isOnline = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickName);
        dest.writeString(phoneNumber);
        dest.writeString(gender);
        dest.writeString(raceName);
        dest.writeString(levelName);
        dest.writeString(levelDescription);
        dest.writeString(raceDescription);
        dest.writeString(currentTitle);
        dest.writeString(headPortraitPath);
        dest.writeString(signature);
        dest.writeInt(isOnline);
    }

    public static final Creator<MoonFriend> CREATOR = new Creator<MoonFriend>() {
        @Override
        public MoonFriend createFromParcel(Parcel in) {
            return new MoonFriend(in);
        }

        @Override
        public MoonFriend[] newArray(int size) {
            return new MoonFriend[size];
        }
    };
}

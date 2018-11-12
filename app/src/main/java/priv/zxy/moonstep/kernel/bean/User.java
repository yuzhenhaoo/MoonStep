package priv.zxy.moonstep.kernel.bean;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/5
 * 描述: 用户基类
 **/
public class User {

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
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

    public int getLuckyValue() {
        return luckyValue;
    }

    public void setLuckyValue(int luckyValue) {
        this.luckyValue = luckyValue;
    }

    //用户id
    private String userID;

    //用户手机号
    private String phoneNumber;

    //用户密码
    private String passWord;

    //用户昵称
    private String nickName;

    //用户性别
    private String gender;

    //种族编码
    private int raceCode;

    //种族名称
    private String raceName;

    //种族描述
    private String raceDescription;

    //用户头像路径
    private String headPath;//将头像文件存到本地一个文件夹下构建成为一个文件对象，这里直接存路径

    //用户签名
    private String signature;

    //用户位置
    private String location;

    //用户当前称号
    private String currentTitle;

    //等阶名称
    private String levelName;

    //等阶描述
    private String levelDescription;

    //幸运值
    private int luckyValue;
}

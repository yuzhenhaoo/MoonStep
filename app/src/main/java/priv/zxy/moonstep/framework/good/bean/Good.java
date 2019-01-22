package priv.zxy.moonstep.framework.good.bean;

import org.litepal.crud.LitePalSupport;

import priv.zxy.moonstep.framework.level.LevelEnum;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/8
 * 描述: 物品的数据类
 **/

public class Good extends LitePalSupport {

    /**
     * 物品编码
     */
    private String goodCode;

    /**
     * 物品名称
     */
    private String goodName;

    /**
     * 物品描述
     */
    private String goodSubscription;

    /**
     * 物品等阶
     */
    private LevelEnum goodLevel;

    /**
     * 物品游戏价值
     */
    private String goodGameValue;

    /**
     * 物品现实价值
     */
    private String goodLifeValue;

    /**
     * 物品图片路径
     */
    private String goodImagePath;

    /**
     * 物品个数
     */
    private String number;

    public String getGoodGameValue() {
        return goodGameValue;
    }

    public void setGoodGameValue(String goodGameValue) {
        this.goodGameValue = goodGameValue;
    }

    public String getGoodLifeValue() {
        return goodLifeValue;
    }

    public void setGoodLifeValue(String goodLifeValue) {
        this.goodLifeValue = goodLifeValue;
    }

    public String getGoodImagePath() {
        return goodImagePath;
    }

    public void setGoodImagePath(String goodImagePath) {
        this.goodImagePath = goodImagePath;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodSubscription() {
        return goodSubscription;
    }

    public void setGoodSubscription(String goodSubscription) {
        this.goodSubscription = goodSubscription;
    }

    public LevelEnum getGoodLevel() {
        return goodLevel;
    }

    public void setGoodLevel(LevelEnum goodLevel) {
        this.goodLevel = goodLevel;
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodCode='" + goodCode + '\'' +
                ", goodName='" + goodName + '\'' +
                ", goodSubscription='" + goodSubscription + '\'' +
                ", goodLevel=" + goodLevel +
                ", goodGameValue='" + goodGameValue + '\'' +
                ", goodLifeValue='" + goodLifeValue + '\'' +
                ", goodImagePath='" + goodImagePath + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}

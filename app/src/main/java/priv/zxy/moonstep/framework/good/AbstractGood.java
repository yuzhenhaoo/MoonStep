package priv.zxy.moonstep.framework.good;

import priv.zxy.moonstep.framework.level.LevelEnum;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 抽象物品类
 **/

public class AbstractGood {

    /**
     * 物品编码
     */
    private String code;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品描述
     */
    private String Description;

    /**
     * 等阶
     */
    private LevelEnum level;

    /**
     * 游戏货币的衡量价值
     */
    private int gameValue;

    /**
     * 现实生活的衡量价值
     */
    private int lifeValue;

    /**
     * 物品的图片路径
     */
    private String imagePath;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public int getGameValue() {
        return gameValue;
    }

    public void setGameValue(int gameValue) {
        this.gameValue = gameValue;
    }

    public int getLifeValue() {
        return lifeValue;
    }

    public void setLifeValue(int lifeValue) {
        this.lifeValue = lifeValue;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}

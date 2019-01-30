package priv.zxy.moonstep.framework.good.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.LitePalSupport;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import priv.zxy.moonstep.framework.level.LevelEnum;
import priv.zxy.moonstep.framework.level.LevelTransformUtil;

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

    /**
     * 根据JsonObject获得Good对象
     * @param json json数据
     * @return good对象
     */
    public static Good createItemformJson(JSONObject json) {
        Good good = new Good();
        try {
            good.setNumber(json.getString("number"));
            good.setGoodSubscription(json.getString("good_subscription"));
            good.setGoodName(json.getString("good_name"));
            good.setGoodLifeValue(json.getString("good_life_value"));
            good.setGoodLevel(LevelTransformUtil.toLevelEnum(json.getString("good_level")));
            good.setGoodImagePath(json.getString("good_image_path"));
            good.setGoodGameValue(json.getString("good_game_value"));
            good.setGoodCode(json.getString("good_code"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return good;
    }

    /**
     * 由Gson获取到Goods的全部对象
     * @param jsonArray json数组
     * @return Good的所有对象的链表
     */
    public static List<Good> createItemsformJson(JSONArray jsonArray) {
        Type listType = new TypeToken<LinkedList<Good>>(){}.getType();
        Gson gson = new Gson();
        return gson.<LinkedList<Good>>fromJson(jsonArray.toString(), listType);
    }
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

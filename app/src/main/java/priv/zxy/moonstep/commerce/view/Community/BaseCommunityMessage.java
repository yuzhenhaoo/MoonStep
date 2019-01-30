package priv.zxy.moonstep.commerce.view.Community;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.LinkedList;

import priv.zxy.moonstep.framework.user.User;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/28
 * 描述: javaBean——社区消息
 **/

public class BaseCommunityMessage {

    /**
     * 媒体视频/图片路径
     */
    private String mediaPath;

    /**
     * 消息文字内容

     */

    private String content;

    /**
     * 消息发出时间
     */
    private String showTime;

    /**
     * 点赞数
     */
    private String praiseNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 消息来源
     */
    private String user;

    public static BaseCommunityMessage createItemfromJson(JSONObject json) {
        BaseCommunityMessage message = new BaseCommunityMessage();
        try {
            message.address = json.getString("address");
            message.content = json.getString("content");
            message.latitude = json.getString("latitude");
            message.longitude = json.getString("longitude");
            message.mediaPath = json.getString("media_path");
            message.praiseNumber = json.getString("praise_number");
            message.showTime = json.getString("show_time");
            message.user = json.getString("user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 由Gson解析jsonArray获得相应的BaseCommunityMessage对象链表
     */
    public static LinkedList<BaseCommunityMessage> createItemsfromJsonArray(JSONArray jsonArray) {
        Type listType = new TypeToken<LinkedList<BaseCommunityMessage>>(){}.getType();
        Gson gson = new Gson();
        return gson.<LinkedList<BaseCommunityMessage>>fromJson(jsonArray.toString(), listType);
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getPraiseNumber() {
        return praiseNumber;
    }

    public void setPraiseNumber(String praiseNumber) {
        this.praiseNumber = praiseNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BaseCommunityMessage{" +
                "mediaPath='" + mediaPath + '\'' +
                ", content='" + content + '\'' +
                ", showTime='" + showTime + '\'' +
                ", praiseNumber='" + praiseNumber + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", user=" + user +
                '}';
    }
}

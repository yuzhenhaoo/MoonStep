package priv.zxy.moonstep.framework.message;

import org.litepal.crud.LitePalSupport;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 抽象消息类
 **/

public class Message extends LitePalSupport {

    private int id;

    /**
     * 发送消息的类型，因为这里直接关乎到数据库的存储，所以不用枚举类型
     * 1、文字；2、图片；3、音频；4、视频；5、红包；6、文件；7、位置
     */
    private int type;

    /**
     * 消息发送方向：0、对方发送的 1、我发送的；
     */
    private int direction;

    /**
     * 发送对象
     * 实际上是UserID("moonstep" + 手机号)
     */
    private String object;

    /**
     * 消息发送内容
     */
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

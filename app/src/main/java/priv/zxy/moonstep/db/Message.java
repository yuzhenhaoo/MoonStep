package priv.zxy.moonstep.db;

import org.litepal.crud.LitePalSupport;

/**
 * 想要让表进行CRUD的操作，必须要让类继承与LitePalSupport这个类
 */
public class Message extends LitePalSupport{
    int id;

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

    /**
     * 发送消息的类型，因为这里直接关乎到数据库的存储，所以不用枚举类型
     * 1、文字；2、图片；3、音频；4、视频；5、红包；6、文件；7、位置
     */
    int type;

    /**
     * 消息发送方向：1、我发送的；2、对方发送的
     */
    int direction;

    /**
     * 发送对象
     * 实际上是UserID("moonstep" + 手机号)
     */
    String object;

    /**
     * 消息发送内容
     */
    String content;


}

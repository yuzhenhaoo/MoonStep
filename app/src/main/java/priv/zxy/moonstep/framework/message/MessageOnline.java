package priv.zxy.moonstep.framework.message;

import org.litepal.LitePal;

import java.util.List;

/**
 * 实质上为了保证面向对象的特性，对于SQLite的存储需要继承LitePalSupport这个特性，我们将这个继承性从MessageOnline转移到了它的父类当中
 * 所以MessageOnline的职责实质上发生了改变。
 * 现在MessageOnline的职责是处理所有的线上消息，它应该有自己的存储消息的方法和调用消息的方法
 */

public class MessageOnline extends Message {

    private static MessageOnline instance;

    public static MessageOnline getInstance() {
        if (instance == null){
            synchronized(MessageOnline.class){
                if (instance == null){
                    instance = new MessageOnline();
                }
            }
        }
        return instance;
    }

    public List<Message> listMessageFromDatabase(String phoneNumber){
        return LitePal.where("object = ?", phoneNumber).find(Message.class);
    }


    public void saveMessageToDataBase(String content, int direction, int type, String phoneNumber){
        Message message = new Message();
        message.setContent(content);
        message.setDirection(direction);//0、对方发送的;1、我发送的;
        message.setObject(phoneNumber);
        message.setType(type);//1、文字；2、图片；3、音频；4、视频；5、红包；6、文件；7、位置
        message.save();
    }
}

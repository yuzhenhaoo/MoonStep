package priv.zxy.moonstep.framework.message;

import java.util.List;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/4
 * 描述: 用来获取离线消息的消息抽象
 **/

public class MessageOffline extends Message {

    /**
     * 将单个的消息存入数据库中
     * @param msg 消息
     */
    public void saveMessageToDatabase(Message msg){

    }

    /**
     * 将一组消息存入数据库中
     * @param messages 消息队列
     */
    public void saveMessageToDatabase(List<Message> messages){

    }

    /**
     * 从网络上获得phoneNumber的离线消息
     */
    public void getMessageFromNet(String phoneNumber){

    }
}

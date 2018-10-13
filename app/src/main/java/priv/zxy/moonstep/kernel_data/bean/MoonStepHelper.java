package priv.zxy.moonstep.kernel_data.bean;

import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Blob;

public class MoonStepHelper {

    private static MoonStepHelper instance = new MoonStepHelper();//饿汉式单例模式

    private MoonStepHelper(){};

    public static MoonStepHelper getInstance(){
        return instance;
    }

    /**
     * 向ChatUsername发送文本消息
     * @param content 发送内容
     * @param toChatUsername 接收方的id
     * @param chatType 消息类型，默认为Chat(单聊模式)
     */
    public void EMsendMessage(String content, String toChatUsername, EMMessage.ChatType chatType){
        EMMessage message = EMMessage.createTxtSendMessage(content, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == EMMessage.ChatType.GroupChat)
            message.setChatType(EMMessage.ChatType.GroupChat);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 向ChatUsername发送语音消息
     * @param filePath 语音文件路径
     * @param length 录音时间(秒)
     * @param toChatUsername 接收方的id
     * @param chatType 消息类型，默认为Chat(单聊模式)
     */
    public void EMsendSoundMessage(String filePath, int length, String toChatUsername, EMMessage.ChatType chatType){
        EMMessage message = EMMessage.createVoiceSendMessage(filePath, length, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == EMMessage.ChatType.GroupChat)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 向ChatUsername发送视频消息
     * @param videoPath 视频本地路径
     * @param thumbPath 视频预览图路径
     * @param videoLength 视频时间长度(秒)
     * @param toChatUsername 接收方的id
     * @param chatType 消息类型，默认为Chat(单聊模式)
     */
    public void EMsendVideoMessage(String videoPath, String thumbPath, int videoLength, String toChatUsername, EMMessage.ChatType chatType){
        EMMessage message = EMMessage.createVideoSendMessage(videoPath, thumbPath, videoLength, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == EMMessage.ChatType.GroupChat)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 向ChatUsername发送图片消息
     * @param imagePath 图片本地路径
     * @param isTrue 是否发送原图
     * @param toChatUsername 接收方的id
     * @param chatType 消息类型，默认为Chat(单聊模式)
     */
    public void EMsendImageMessage(String imagePath, boolean isTrue, String toChatUsername, EMMessage.ChatType chatType){
        //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
        EMMessage message = EMMessage.createImageSendMessage(imagePath, isTrue, toChatUsername);
        //如果是群聊，设置chattype，默认是单聊
        if (chatType == EMMessage.ChatType.GroupChat)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 向ChatUsername发送文件消息
     * @param filePath 文件路径
     * @param toChatUsername 接收方的id
     * @param chatType 消息类型，默认为Chat(单聊模式)
     */
    public void EMsendFileMessage(String filePath, String toChatUsername, EMMessage.ChatType chatType){
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        // 如果是群聊，设置chattype，默认是单聊
        if (chatType == EMMessage.ChatType.GroupChat)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 判断文件是否存在
     * @param strFile 文件路径
     * @return 存在就返回true，不存在返回False
     */
    public boolean fileIsExists(String strFile){
        try{
            File f = new File(strFile);
            if(!f.exists()){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();//打印错误信息
            return false;
        }
        return true;
    }
}

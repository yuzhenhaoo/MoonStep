package priv.zxy.moonstep.helper;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.File;

import priv.zxy.moonstep.commerce.view.Friend.MessageTypeEnum;
import priv.zxy.moonstep.util.LogUtil;

public class MoonStepHelper {

    private static final String TAG = "MoonStepHelper";

    private static MoonStepHelper instance = new MoonStepHelper();//饿汉式单例模式

    /**
     * 为了保持ChattingActivity中的EMClient的监听器的唯一性，产生一个临时文件来做判别
     * 如果包名修改的话，那么相应的文件路径也要做改动
     */
    private String filePath = "data/data/priv.zxy.moonstep/temporaryFile";

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
     * 创建临时文件
     */
    public void createTemporaryFile(){

        File file = new File(filePath);
        if (!fileIsExists(filePath)){
            file.mkdir();
            LogUtil.d(TAG, "文件创建成功");
        }
        else
            LogUtil.d(TAG, "文件创建失败");
    }

    /**
     * 清空临时文件
     */
    public void clearTemporaryFile(){
        if (fileIsExists()){
            File file = new File(filePath);
            file.delete();
            if (!fileIsExists())
                LogUtil.d(TAG, "文件删除成功");
            else
                LogUtil.d(TAG, "文件删除失败");
        }
    }

    /**
     * 判断临时文件是否存在
     * @return 存在就返回True,不存在就返回False;
     */
    public boolean fileIsExists(){
        try{
            File f = new File(filePath);
            if (f.exists())
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 判断文件是否存在
     * @param strFile 文件路径
     * @return 存在就返回true，不存在返回False
     */
    public boolean fileIsExists(String strFile){
        try{
            File f = new File(strFile);
            if (f.exists())
                return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 将环信传来的消息类型转为设定好的Message的枚举类型方便判断
     * @param type 消息类型
     * @return 相应的枚举类型
     */
    public MessageTypeEnum transformMessageType(String type){
        switch (type) {
            case "txt":
                return MessageTypeEnum.TEXT;
            case "image":
                return MessageTypeEnum.IMAGE;
            case "location":
                return MessageTypeEnum.LOCATION;
            case "video":
                return MessageTypeEnum.VIDEO;
            case "voice":
                return MessageTypeEnum.VOICE;
            default:
        }
        return MessageTypeEnum.UNKNOWN_TYPE;
    }

    /**
     * 解析EMMessage.body()的数据，将其拆分为两个和部分：  类型   和   主体
     * @param EMMessageBody 包含类型和主体的消息字符串
     * @return 返回一个长度为2的字符串，temp[0]代表消息的类型，temp[1]就是消息的内容
     */
    public String[] getMessageTypeWithBody(String EMMessageBody){
        String[] temp =EMMessageBody.split("\"");
        temp[0] = temp[0].substring(0, temp[0].length() - 1);
        return temp;
    }
}

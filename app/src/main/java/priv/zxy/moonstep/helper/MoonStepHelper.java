package priv.zxy.moonstep.helper;

import android.content.Context;
import android.os.Environment;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.File;

import cn.sharesdk.onekeyshare.OnekeyShare;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Friend.MessageTypeEnum;
import priv.zxy.moonstep.util.LogUtil;

import static com.mob.tools.utils.Strings.getString;

public class MoonStepHelper {

    private static final String TAG = "MoonStepHelper";

    /**
     * 为了保持ChattingActivity中的EMClient的监听器的唯一性，产生一个临时文件来做判别
     * 如果包名修改的话，那么相应的文件路径也要做改动
     */
    private static final String FILE_PATH = "data/data/priv.zxy.moonstep/temporaryFile";

    /**
     * 向ChatUsername发送文本消息
     * @param content 发送内容
     * @param toChatUsername 接收方的id
     * @param chatType 消息类型，默认为Chat(单聊模式)
     */
    public static void EMsendMessage(String content, String toChatUsername, EMMessage.ChatType chatType){
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
    public static void EMsendSoundMessage(String filePath, int length, String toChatUsername, EMMessage.ChatType chatType){
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
    public static void EMsendVideoMessage(String videoPath, String thumbPath, int videoLength, String toChatUsername, EMMessage.ChatType chatType){
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
    public static void EMsendImageMessage(String imagePath, boolean isTrue, String toChatUsername, EMMessage.ChatType chatType){
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
    public static void EMsendFileMessage(String filePath, String toChatUsername, EMMessage.ChatType chatType){
        EMMessage message = EMMessage.createFileSendMessage(filePath, toChatUsername);
        // 如果是群聊，设置chattype，默认是单聊
        if (chatType == EMMessage.ChatType.GroupChat)
            message.setChatType(EMMessage.ChatType.GroupChat);
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    /**
     * 创建临时文件
     */
    public static void createTemporaryFile(){

        File file = new File(FILE_PATH);
        if (!fileIsExists(FILE_PATH)){
            file.mkdir();
            LogUtil.d(TAG, "文件创建成功");
        }
        else
            LogUtil.d(TAG, "文件创建失败");
    }

    /**
     * 清空临时文件
     */
    public static void clearTemporaryFile(){
        if (fileIsExists()){
            File file = new File(FILE_PATH);
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
    public static boolean fileIsExists(){
        try{
            File f = new File(FILE_PATH);
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
    public static boolean fileIsExists(String strFile){
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
    public static MessageTypeEnum transformMessageType(String type){
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
    public static String[] getMessageTypeWithBody(String EMMessageBody){
        String[] temp =EMMessageBody.split("\"");
        temp[0] = temp[0].substring(0, temp[0].length() - 1);
        return temp;
    }

    /**
     * 分享页面内容
     * @param context Activity上下文
     */
    public static void showShare(Context context) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share_title));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("https://www.zxyblog.xyz");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("快来看看我获得的新道具吧！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(Environment.getExternalStorageDirectory().getPath() + "test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://www.zxyblog.xyz");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("这是我的评论");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://www.zxyblog.xyz");

// 启动分享GUI
        oks.show(context);
    }
}

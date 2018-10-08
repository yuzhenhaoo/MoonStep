package priv.zxy.moonstep.moonstep_palace.moon_friend.module.biz;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.Utils.dbUtils.GetMoonFriendUtil;
import priv.zxy.moonstep.kernel_data.bean.ErrorCode;
import priv.zxy.moonstep.kernel_data.bean.User;

public class MoonFriendBiz implements IMoonFriendBiz{

    /**
     * 用来保存聊天记录地消息记录
     * 通常地做法是显示最近地10条消息，然后要继续显示，就用一个RecyclerView地刷新框，下拉刷新，出现旋转小圆圈，同时开始进行网络请求
     * 当网络请求完毕，小圆圈结束，并且继续显示历史地30条消息
     */
    void savedChattingMessage(){

    }
}

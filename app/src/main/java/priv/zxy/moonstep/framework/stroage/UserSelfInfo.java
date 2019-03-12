package priv.zxy.moonstep.framework.stroage;

import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/1
 * 描述: 存储用户信息的一个临时类
 *       在加载用户信息的时候开始初始化数据
 *       在程序结束的时候，需要清空所有的引用
 **/

public class UserSelfInfo {

    /**
     * 存储当前用户信息的User对象
     * 注意，这里是引用对象，使用完毕后，要记得清除引用
     */
    private User mySelf = null;

    public static UserSelfInfo getInstance() {
        return UserSelfHolder.instance;
    }

    public void setMySelf(User user) {
        this.mySelf = user;
    }

    public User getMySelf() {
        return mySelf;
    }
    /**
     * 清理引用，以便JVM调用GC
     */
    public void clear() {
        mySelf = null;
    }

    private static class UserSelfHolder {
        private static final UserSelfInfo instance = new UserSelfInfo();
    }
}

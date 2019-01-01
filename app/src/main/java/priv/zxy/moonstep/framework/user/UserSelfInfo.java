package priv.zxy.moonstep.framework.user;

import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/1
 * 描述: 存储用户信息的一个临时类
 *       在加载用户信息的时候开始初始化数据
 *       在程序结束的时候，需要清空所有的引用。
 **/

public class UserSelfInfo {

    /**
     * 存储当前用户信息的User对象
     * 注意，这里是引用对象，使用完毕后，要记得清除引用
     */
    private User mySelf = null;

    /**
     * 使用饿汉式是为了提高效率
     */
    private static UserSelfInfo instance = new UserSelfInfo();

    public static UserSelfInfo getInstance() {
        return instance;
    }

    public void setMySelf(User user) {
        this.mySelf = user;
    }

    public User getMySelf() {
        if (mySelf == null) {
            // 如果没有从网络端获取到数据，就从xml文件中获取数据
            mySelf = SharedPreferencesUtil.readMySelfInformation();
            // 清理内部的引用
            SharedPreferencesUtil.clear();
        }
        return mySelf;
    }

    /**
     * 清理引用，以便JVM调用GC
     */
    public void clear() {
        mySelf = null;
    }
}

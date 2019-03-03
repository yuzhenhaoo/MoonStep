package priv.zxy.moonstep.framework.user;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/3 18:45
 * 类描述: 用户类的管理类，管控当前用户的状态监听，消息监听等信息
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */
public class UserManager {

    private static User user;

    private static UserManager instance;

    public static UserManager getInstance(User user) {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    UserManager.user = user;
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    public static void setUserStateListener() {
        assert user != null;
    }

    public static void setUserMessageListener() {
        assert user != null;
    }

}

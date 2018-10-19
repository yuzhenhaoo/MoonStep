package priv.zxy.moonstep.kernel.bean;

public class EMBase {

    private String OrgName = "1106181001228289";

    private String AppName = "moonstep";

    private String Client_ID = "YXA6CMIGMMV8Eeizb0k32Qw9gQ";

    private String Client_Secret = "YXA670qEVABVtogui2OPZI5bRUHQhPQ";

    private String baseRequest = "http://a1.easemob.com/";

    private static EMBase instance = null;

    /**
     * 设计模式：双重锁定(Double-Check Locking)
     * 只有在第一次初始化的时候需要判断instance是不是已经初始化了，如果还没有初始化过，进入同步锁的锁定模式，在同步锁下完成实例的实例化。
     * @return instance
     */
    public static EMBase getInstance() {
        if (instance == null) {
            synchronized (EMBase.class) {
                if (instance == null) {
                    instance = new EMBase();
                }
            }
        }
        return instance;
    }

    public String getOrgName() {
        return OrgName;
    }

    public String getAppName() {
        return AppName;
    }

    public String getClient_ID() {
        return Client_ID;
    }

    public String getClient_Secret() {
        return Client_Secret;
    }

    public String getBaseRequest() {
        return baseRequest;
    }
}

package priv.zxy.network.type;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/29
 * 描述:网络枚举类型
 **/
public enum NetType {

    /**
     * 有网络，包含GPRS和WIFI
     */
    AUTO,

    /**
     * WIFI网络
     */
    WIFI,

    /**
     * CMWAP和CMNET知识中国移动人为划分的两个GPRS接入的方式
     * CMNET主要是为了PC、笔记本电脑、PDA等利用GPRS上网服务
     */
    CMNET,

    /**
     * CMWAP是为手机WAP上网设立的
     * 它们再实现方式上并没有任何差别，知识因为定位不同
     */
    CMWAP,

    /**
     * 没有任何网络
     */
    NONE
}

package priv.zxy.moonstep.data.bean;

/**
 * Created by Zxy on 2018/9/21
 *
 * 使用枚举类是为了规范错误码的编号
 * 一方面使得代码可读性增高
 * 一方面不能产生额外的错误码编号。
 */

public enum ErrorCode {
    /**
     * 电话号码为空
     */
    PHONE_NUMBER_IS_EMPTY,

    /**
     * 网络没有响应
     */
    NET_NOT_RESPONSE,

    /**
     * 修改密码失败
     */
    CHANGE_PASSWORD_FAIL,

    /**
     * JSON数据异常
     */
    JSON_EXCEPTION,

    /**
     * 电话号码已经注册了
     */
    PHONE_NUMBER_IS_REGISTERED,

    /**
     * 电话号码还没被注册
     */
    PHONE_NUMBER_IS_NOT_REGISTERED,

    /**
     * 电话号码/密码错误
     */
    PHONE_NUMBER_OR_PASSWORD_IS_WRONG,

    /**
     * 密码格式不正确，应为6-16位
     */
    PASSWORD_FOR_MAT_IS_NOT_RIGHT,

    /**
     * 密码为空
     */
    PASSWORD_IS_EMPTY,

    /**
     * 密码错误
     */
    PASSWORD_IS_WRONG,

    /**
     * 验证密码位空
     */
    CONFIRM_PASSWORD_IS_EMPTY,

    /**
     * 密码不等于验证密码
     */
    PASSWORD_IS_NOT_EQUALS_CONFIRM_PASSWORD,

    /**
     * 服务器发生错误
     */
    SERVER_IS_FAULT,

    /**
     * EC注册失败
     */
    EC_REGISTERD_FAIL,

    /**
     * 连接聊天服务器失败
     */
    CONNECT_CHAT_SERVICE_FAIL,

    /**
     * 账户已经被移除
     */
    ACCOUNT_IS_DELETED,

    /**
     * 账户在其它设备上登陆
     */
    ACCOUNT_IS_LOGGING_IN_OTHER_DEVICE,

    /**
     * EC获取好友列表失败
     */
    EC_GET_FRIENDS_LIST_FAIL,
    /**
     * 月友账号不存在
     */
    USER_IS_NOT_EXISTED
}

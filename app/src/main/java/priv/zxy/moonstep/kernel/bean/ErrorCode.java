package priv.zxy.moonstep.kernel.bean;

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
    PhoneNumberISEmpty,

    /**
     * 网络没有响应
     */
    NetNotResponse,

    /**
     * 修改密码失败
     */
    ChangePasswordFail,

    /**
     * JSON数据异常
     */
    JSONException,

    /**
     * 电话号码已经注册了
     */
    PhoneNumberIsRegistered,

    /**
     * 电话号码还没被注册
     */
    PhoneNumberIsNotRegistered,

    /**
     * 电话号码/密码错误
     */
    PhoneNumberOrPasswordIsWrong,

    /**
     * 密码格式不正确，应为6-16位
     */
    PasswordFormatISNotRight,

    /**
     * 密码为空
     */
    PasswordIsEmpty,

    /**
     * 验证密码位空
     */
    ConfirmPasswordIsEmpty,

    /**
     * 密码不等于验证密码
     */
    PasswordIsNotEqualsConfirmPassword,

    /**
     * 服务器发生错误
     */
    ServerIsFault,

    /**
     * EC注册失败
     */
    ECRegisterFail,

    /**
     * 连接聊天服务器失败
     */
    ConnectChatServiceFail,

    /**
     * 账户已经被移除
     */
    AccountISRemoverd,

    /**
     * 账户在其它设备上登陆
     */
    AccountIsLoginInOtherDevice,

    /**
     * EC获取好友列表失败
     */
    ECGetFriendsListFail,
    /**
     * 月友账号不存在
     */
    MoonFriendUserIsNotExisted
}

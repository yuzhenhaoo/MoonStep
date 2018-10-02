package priv.zxy.moonstep.login.module.bean;

/**
 * Created by Zxy on 2018/9/21
 *
 * 使用枚举类是为了规范错误码的编号
 * 一方面使得代码可读性增高
 * 一方面不能产生额外的错误码编号。
 */
public enum ErrorCode {
    PhoneNumberISEmpty,

    NetNotResponse,

    ChangePasswordFail,

    JSONException,

    PhoneNumberIsRegistered,

    PhoneNumberIsNotRegistered,

    UserNameIsEmpty,

    UserNameIsExisted,

    PhoneNumberOrPasswordIsWrong,

    PasswordFormatISNotRight,

    PasswordIsEmpty,

    ConfirmPasswordIsEmpty,

    PasswordIsNotEqualsConfirmPassword,

    ServerIsFault
}

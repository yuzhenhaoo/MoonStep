package priv.zxy.moonstep.login.view;

/**
 * Created by Zxy on 2018/9/20
 */

public interface IForgetPasswordSendMessageView {

  void toChangePasswordActivity();

  void finishActivitySelf();

  void sendVoiceCode();

  String getPhoneNumber();

  void showLoading();

  void hideLoading();

  void showSuccessTip();

  void showErrorTip();
}
package priv.zxy.moonstep.EM.bean;

public interface VolleyCallback {

    String onSuccess(String result);

    boolean onSuccess();

    String onFail(String error);

    boolean onFail();
}

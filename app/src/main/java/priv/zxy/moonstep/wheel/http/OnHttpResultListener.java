package priv.zxy.moonstep.wheel.http;

import okhttp3.Response;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述: 创建一个接口用来实现HttpBase的返回结果
 **/

public interface OnHttpResultListener {

    void onResult(Response reponse);
}

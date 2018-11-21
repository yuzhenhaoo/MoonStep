package priv.zxy.moonstep.wheel.http;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述: 实现网络请求的抽象基类
 *      目的是为了满足依赖倒转原则，面向接口编程，而不是面向细节编程
 *      然后就是尽量完善的同时减少对于okhttp和Gson的依赖，尽量自己实现一个自己的网络请求库
 **/
abstract class AbstractHttpBase {

    abstract void doGet(OnHttpResultListener onHttpResultListener);

    abstract void doPost(OnHttpResultListener onHttpResultListener);

    abstract void doPut(OnHttpResultListener onHttpResultListener);

    abstract void doDelete(OnHttpResultListener onHttpResultListener);

}

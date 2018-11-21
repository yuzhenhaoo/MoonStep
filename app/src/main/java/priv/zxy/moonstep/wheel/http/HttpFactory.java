package priv.zxy.moonstep.wheel.http;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述: Factory的抽象接口
 *       工厂模式
 **/
abstract class HttpFactory {

    abstract HttpBase createHttpObject();
}

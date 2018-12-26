package priv.zxy.moonstep.wheel.http;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述:
 **/
public class DataHttpFactory extends AbstractHttpFactory {

    @Override
    public HttpBase createHttpObject() {
        return new DataHttp();
    }
}

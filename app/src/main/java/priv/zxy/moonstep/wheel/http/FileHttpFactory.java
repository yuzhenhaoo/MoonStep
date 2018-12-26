package priv.zxy.moonstep.wheel.http;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/20
 * 描述:
 **/
public class FileHttpFactory extends AbstractHttpFactory {

    @Override
    HttpBase createHttpObject() {
        return new FileHttp();
    }
}

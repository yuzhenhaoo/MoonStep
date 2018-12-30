package priv.zxy.network.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import priv.zxy.network.type.NetType;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/29
 * 描述:网络监听方法注解
 **/

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Network {

    NetType netType() default NetType.AUTO;
}

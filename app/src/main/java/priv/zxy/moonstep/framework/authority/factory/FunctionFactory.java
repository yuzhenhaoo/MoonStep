package priv.zxy.moonstep.framework.authority.factory;

import priv.zxy.moonstep.framework.authority.base.AbstractAuthority;
import priv.zxy.moonstep.framework.authority.base.AbstractFunctionAuthority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述:功能权限工厂，用来产生功能权限类的对象
 **/

public class FunctionFactory extends AbstractFactory {

    private static FunctionFactory instance = null;

    /**
     * 保证多次调用工厂的时候不会产生太多的对象占用资源
     * @return 返回该工厂的实例
     */
    public FunctionFactory getInstance() {
        if (instance == null){
            synchronized (FunctionFactory.class){
                if (instance == null){
                    instance = new FunctionFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 我们利用父Factory中的HashMap存储所有的权限属性，子类中实现反射获取权限子对象，设计实在巧妙，该设计值得被记录下来，面试的时候用
     * @param code 传入的权限码
     * @return 具体的权限对象
     */
    @Override
    public AbstractAuthority createAuthority(String code){
        AbstractFunctionAuthority functionAuthority;
        try {
            Class<?> authorityClass = Class.forName(maps.get(code));
            Object object = authorityClass.newInstance();
            functionAuthority = (AbstractFunctionAuthority) object;
            return functionAuthority;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
}

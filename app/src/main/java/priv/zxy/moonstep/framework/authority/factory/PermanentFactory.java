package priv.zxy.moonstep.framework.authority.factory;

import priv.zxy.moonstep.framework.authority.base.AbstractAuthority;
import priv.zxy.moonstep.framework.authority.base.PermanentAuthority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述:永久权限的抽象工厂，用来产生永久权限的对象
 **/

public class PermanentFactory extends AbstractFactory {

    private static PermanentFactory instance = null;

    public static PermanentFactory getInstance() {
        if (instance == null){
            synchronized(PermanentFactory.class){
                if (instance == null){
                    instance = new PermanentFactory();
                }
            }
        }
        return instance;
    }

    /**
     * 我们利用父Factory中的HashMap存储所有的权限属性，子类中实现反射获取权限子对象，设计实在巧妙
     * @param code 权限码
     * @return 权限对象
     */
    @Override
    public AbstractAuthority createAuthority(String code) {
        PermanentAuthority permanentAuthority;
        try {
            Class<?> authorityClass = Class.forName(maps.get(code));
            Object object = authorityClass.newInstance();
            permanentAuthority = (PermanentAuthority) object;
            return permanentAuthority;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
}

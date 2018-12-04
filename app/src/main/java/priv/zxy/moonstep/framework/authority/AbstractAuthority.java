package priv.zxy.moonstep.framework.authority;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/3
 * 描述: 抽象权限类
 *       我们将抽象类抽象为两个分类：永久获得的权限和使用一次的权限
 *       对于永久获得的权限，往往是真正意义上的权限，而一次使用的权限，更适用于奖励机制，针对某一次的一次性权限（功能）
 *       所以下面会有两个子类来继承AbstractAuthority，实质上代表上述两个类的基类。
 *       而对于权限类的设计模式，我们采用工厂模式（或许会利用反射减少大量which语句的产生）。
 **/

public abstract class AbstractAuthority {

    public String authorityCode = null;

    public String authorityDescription = null;

    public void setAuthorityCode(String authorityCode) {
        this.authorityCode = authorityCode;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public void setAuthorityDescription(String authorityDescription) {
        this.authorityDescription = authorityDescription;
    }

    public String getAuthorityDescription() {
        return authorityDescription;
    }

    abstract public void operator();

}

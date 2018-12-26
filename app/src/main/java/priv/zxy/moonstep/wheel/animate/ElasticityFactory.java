package priv.zxy.moonstep.wheel.animate;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述:波动效果工厂，用来产生波动动画的对象
 **/

public class ElasticityFactory extends AbstractAnimateFactory {

    @Override
    public AbstractAnimateEffect createEffectObject() {
        return new ElasticityAnimation();
    }
}

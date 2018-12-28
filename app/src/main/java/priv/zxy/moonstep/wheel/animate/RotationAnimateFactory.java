package priv.zxy.moonstep.wheel.animate;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/28
 * 描述: 旋转动画工厂类，用来产生旋转动画的对象
 **/

public class RotationAnimateFactory extends AbstractAnimateFactory {

    @Override
    public AbstractAnimateEffect createEffectObject() {
        return new RotateAnimation();
    }
}

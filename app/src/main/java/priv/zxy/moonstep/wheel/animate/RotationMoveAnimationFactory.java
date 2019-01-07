package priv.zxy.moonstep.wheel.animate;

import android.animation.AnimatorSet;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/7
 * 描述: 返回旋转移动动画的类对象
 **/

public class RotationMoveAnimationFactory extends AbstractAnimateFactory{

    @Override
    public AbstractAnimateEffect createEffectObject() {
        return new RotationMoveAnimation();
    }
}

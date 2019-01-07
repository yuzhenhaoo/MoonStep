package priv.zxy.moonstep.wheel.animate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/7
 * 描述: 同时实现旋转和向指定位置移动的动画效果
 **/

public class RotationMoveAnimation extends AbstractAnimateEffect {

    private AnimatorSet animatorSet = new AnimatorSet();

    @Override
    public void setAnimate(View view) {
    }

    public void setAnimate(View view, int translationX, int translationY) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotation",0, 360);
        ObjectAnimator moveX = ObjectAnimator.ofFloat(view, "translationX",0, translationX);
        ObjectAnimator moveY = ObjectAnimator.ofFloat(view, "translationY", 0, translationY);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(rotation, moveX, moveY);
    }
    @Override
    public void setAnimate(View view, long duration) {
        animatorSet.setDuration(duration);
    }

    @Override
    public void cancelAnimate() {
        animatorSet.cancel();
    }

    @Override
    public void show() {
        animatorSet.start();
    }

    @Override
    public Object getAnimateObj() {
        if (animatorSet == null){
            return null;
        }
        return animatorSet;
    }

    @Override
    public boolean isRunning() {
        return animatorSet.isRunning();
    }
}

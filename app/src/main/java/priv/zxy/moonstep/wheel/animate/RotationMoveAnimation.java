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

    private static ObjectAnimator rotation = null;
    private static ObjectAnimator moveX = null;
    private static ObjectAnimator moveY = null;

    private static ElasticityAnimation instance = new ElasticityAnimation();
    public static ElasticityAnimation getInstance(View view, int translationX, int translationY) {
        if (instance == null) {
            synchronized (ElasticityAnimation.class) {
                if (instance == null) {
                    instance = new ElasticityAnimation();
                    rotation = ObjectAnimator.ofFloat(view, "rotation",0, 360);
                    moveX = ObjectAnimator.ofFloat(view, "translationX",0, translationX);
                    moveY = ObjectAnimator.ofFloat(view, "translationY", 0, translationY);
                }
            }
        }
        return instance;
    }

    @Override
    void setAnimate() {
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(rotation, moveX, moveY);
    }

    @Override
    void setAnimateWithDuration(long duration) {
        animatorSet.setDuration(duration);
    }

    @Override
    public void cancelAnimate() {
        animatorSet.cancel();
    }

    @Override
    public void show(long duration) {
        setAnimateWithDuration(duration);
        animatorSet.start();
    }

    public void show() {
        setAnimate();
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

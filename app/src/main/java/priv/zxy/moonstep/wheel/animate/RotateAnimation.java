package priv.zxy.moonstep.wheel.animate;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.view.animation.Animation.INFINITE;


/**
 * 创建人: Administrator
 * 创建时间: 2018/12/28
 * 描述: 旋转动画，主要变现为实现view的旋转效果
 **/

public class RotateAnimation extends AbstractAnimateEffect {

    private static ObjectAnimator animator = null;

    private static RotateAnimation instance = null;
    public static RotateAnimation getInstance(View view) {
        if (instance == null) {
            synchronized (RotateAnimation.class) {
                if (instance == null) {
                    instance = new RotateAnimation();
                    animator = ObjectAnimator.ofFloat(view, "rotation",0, 360);
                }
            }
        }
        return instance;
    }

    @Override
    void setAnimate() {
        // 采用均值插值器
        animator.setInterpolator(new LinearInterpolator());
        /*
         INFINIFE 是ValueAnimator中的字段，setRepeatCount()也是来自ValueAnimator中的方法，
         用来设置动画循环的次数
        */
        animator.setRepeatCount(INFINITE);
        animator.setDuration(300);
    }

    @Override
    void setAnimateWithDuration(long duration) {
        setAnimate();
        animator.setDuration(duration);
    }

    @Override
    public void cancelAnimate() {
        if (animator == null) {
            return;
        }
        animator.cancel();
    }

    @Override
    public void show(long duration) {
        setAnimateWithDuration(duration);
        animator.start();
    }

    @Override
    public void show() {
        if (animator == null) {
            return;
        }
        animator.start();
    }

    @Override
    public Object getAnimateObj() {
        if (animator == null){
            return null;
        }
        return animator;
    }

    @Override
    public boolean isRunning() {
        if (animator == null) {
            return false;
        }
        return animator.isRunning();
    }
}

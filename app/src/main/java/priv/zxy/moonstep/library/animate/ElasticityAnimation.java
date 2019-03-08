package priv.zxy.moonstep.library.animate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;

import priv.zxy.moonstep.Exception.AnimateException;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 弹性动画，主要表现为先扩大，后缩小，就像弹簧一样
 **/
public class ElasticityAnimation extends AbstractAnimateEffect {

    private AnimatorSet animatorSet = new AnimatorSet();
    private static ObjectAnimator animator1 = null;
    private static ObjectAnimator animator2 = null;
    private static ObjectAnimator animator3 = null;
    private static ObjectAnimator animator4 = null;

    private static ElasticityAnimation instance = new ElasticityAnimation();
    public static ElasticityAnimation getInstance(View view) {
        if (instance == null) {
            synchronized (ElasticityAnimation.class) {
                if (instance == null) {
                    instance = new ElasticityAnimation();
                    animator1 = ObjectAnimator.ofFloat(view, "scaleX",1.0f, 1.3f);
                    animator2 = ObjectAnimator.ofFloat(view, "scaleY",1.0f, 1.3f);
                    animator3 = ObjectAnimator.ofFloat(view, "scaleX",1.3f, 1.0f);
                    animator4 = ObjectAnimator.ofFloat(view, "scaleY",1.3f, 1.0f);
                }
            }
        }
        return instance;
    }

    @Override
    void setAnimate() {
        animatorSet.playTogether(animator1, animator2);
        animatorSet.play(animator2).before(animator3);
        animatorSet.playTogether(animator3, animator4);
        if(Build.VERSION.SDK_INT >= 24){
            animatorSet.getTotalDuration();
        }
    }

    @Override
    void setAnimateWithDuration(long duration) {
        setAnimate();
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
            AnimateException exception = new AnimateException("AnimatorSet is Null");
            try {
                throw exception;
            } catch (AnimateException e) {
                exception.handleWay();
            }
        }
        return animatorSet;
    }

    @Override
    public boolean isRunning() {
        return animatorSet.isRunning();
    }
}

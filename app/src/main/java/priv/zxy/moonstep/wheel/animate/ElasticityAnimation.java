package priv.zxy.moonstep.wheel.animate;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 弹性动画，主要表现为先扩大，后缩小，就像弹簧一样
 **/
public class ElasticityAnimation extends AbstractAnimateEffect {

    private AnimatorSet animatorSet = new AnimatorSet();

    @Override
    public void setAnimate(View view) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX",1.0f, 1.3f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY",1.0f, 1.3f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "scaleX",1.3f, 1.0f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(view, "scaleY",1.3f, 1.0f);
        animator1.setInterpolator(new DecelerateInterpolator());
        animator2.setInterpolator(new DecelerateInterpolator());
        animator3.setInterpolator(new DecelerateInterpolator());
        animator4.setInterpolator(new DecelerateInterpolator());

        animatorSet.playTogether(animator1, animator2);
        animatorSet.play(animator2).before(animator3);
        animatorSet.playTogether(animator3, animator4);
        if(Build.VERSION.SDK_INT >= 24){
            animatorSet.getTotalDuration();
        }
    }

    @Override
    public void setAnimate(View view, long duration) {

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

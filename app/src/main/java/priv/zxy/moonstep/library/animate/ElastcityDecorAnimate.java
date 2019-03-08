package priv.zxy.moonstep.library.animate;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import priv.zxy.moonstep.Exception.AnimateException;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/1 17:22
 * 类描述: logo加载动画，效果为逐渐放大并显现
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */
public class ElastcityDecorAnimate extends ElasticityAnimation {

    private AnimatorSet animatorSet = new AnimatorSet();
    private static ObjectAnimator animator1 = null;
    private static ObjectAnimator animator2 = null;
    private static ObjectAnimator animator3 = null;

    private static ElastcityDecorAnimate instance = null;
    public static ElastcityDecorAnimate getInstance(View view) {
        if (instance == null) {
            synchronized (ElastcityDecorAnimate.class) {
                if (instance == null) {
                    instance = new ElastcityDecorAnimate();
                    animator1 = ObjectAnimator.ofFloat(view, "scaleX",0.0f, 1.0f);
                    animator2 = ObjectAnimator.ofFloat(view, "scaleY",0.0f, 1.0f);
                    animator3 = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
                }
            }
        }
        return instance;
    }

    private ElastcityDecorAnimate(){}

    @Override
    void setAnimate() {
        animator1.setInterpolator(new DecelerateInterpolator());
        animator2.setInterpolator(new DecelerateInterpolator());
        animator3.setInterpolator(new DecelerateInterpolator());

        animatorSet.playTogether(animator1, animator2, animator3);
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

    public void setAnimateListenting(Animator.AnimatorListener listenting) {
        animatorSet.addListener(listenting);
    }

    @Override
    public boolean isRunning() {
        return animatorSet.isRunning();
    }

    public interface AnimateListening{
        void onListening();
    }
}

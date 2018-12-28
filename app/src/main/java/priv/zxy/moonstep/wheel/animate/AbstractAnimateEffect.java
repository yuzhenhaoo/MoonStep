package priv.zxy.moonstep.wheel.animate;

import android.view.View;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述: 动画效果的超类
 **/

public abstract class AbstractAnimateEffect {

    /**
     * 设置动画效果
     * @param view 加载view
     */
    public abstract void setAnimate(View view);

    public abstract void setAnimate(View view, long duration);

    /**
     * 取消动画
     */
    public abstract void cancelAnimate();

    /**
     * 展示动画
     */
    public abstract void show();

    /**
     * 获取动画的执行对象
     */
    public abstract Object getAnimateObj();

    /**
     * 检测动画是不是正在播放
     * @return 如果动画正在播放，返回true，否则返回false
     */
    public abstract boolean isRunning();

}

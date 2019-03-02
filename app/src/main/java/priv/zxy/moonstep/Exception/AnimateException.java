package priv.zxy.moonstep.Exception;

import priv.zxy.moonstep.util.LogUtil;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/1 17:34
 * 类描述:
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */
public class AnimateException extends Exception {

    private static final String TAG = "AnimateException";
    private ExceptionCode code = ExceptionCode.ANIMATION_EXCEPTION;
    public AnimateException(String message) {
        super(message);
        setCode();
    }

    private void setCode() {
        super.setCode(code);
    }

    /**
     * 该类不建议直接进行调用，调用操作会放在Exception基类中，由ExceptionManager进行管控
     */
    @Override
    public void handleWay() {
        LogUtil.d(TAG, "AnimationSet is null, please check your code");
    }
}

package priv.zxy.moonstep.algorithm.MinimumDISTDectation;

import java.util.List;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/23
 * 描述:
 **/

public abstract class AbstractMinDISTDetection<T> {

    private static final String TAG = "AbstractMinDISTDetection";

    public abstract List<T> getResult();

    public abstract int getResultLength();
}

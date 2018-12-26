package priv.zxy.moonstep.algorithm.ChooseMapDots;

import java.util.List;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/24
 * 描述:定义算法家族的抽象类，作为父类约束子类的所有行为(即方法)，让Context获得不同的算法对象的时候，在客户端调用不同的算法
 *     不用做任何的改动。
 **/

public abstract class AbstractDotChooseAlgorithm {

    public abstract List<MapDot> listDots(double latitude, double longtutide, int number);
}

package priv.zxy.moonstep.framework.stroage;

import priv.zxy.moonstep.framework.race.Race;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:存储用户种族信息的一个类
 **/
public class RaceInfo {

    private Race mRace = null;

    /**
     * 使用饿汉式是为了提高效率
     */
    private static RaceInfo instance = new RaceInfo();

    public static RaceInfo getInstance() {
        return instance;
    }

    public void setRace(Race race) {
        this.mRace = race;
    }

    public Race getRace() {
        if (mRace == null) {
            // 如果没有从网络端获取到数据，就从xml文件中获取数据
            mRace = SharedPreferencesUtil.readRaceInformation();
            // 清理内部的引用
            SharedPreferencesUtil.clear();
        }
        return mRace;
    }

    /**
     * 清理引用，以便JVM调用GC
     */
    public void clear() {
        mRace = null;
    }
}

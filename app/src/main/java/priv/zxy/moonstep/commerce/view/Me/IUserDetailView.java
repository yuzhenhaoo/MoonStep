package priv.zxy.moonstep.commerce.view.Me;

import android.graphics.Bitmap;

import priv.zxy.moonstep.framework.race.Race;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:用户具体信息接口
 **/
public interface IUserDetailView {

    /**
     *  种族数据设置
     */
    void initRaceData(Race race);

    /**
     * 设置种族图片
     */
    void setRaceImage(Bitmap bitmap);

    /**
     * 设置种族小图片
     */
    void setRaceIcon(Bitmap bitmap);

    /**
     *  错误提示
     */
    void error(String error_msg);
}

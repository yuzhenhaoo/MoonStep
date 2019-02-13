package priv.zxy.moonstep.commerce.module.biz;


import android.graphics.Bitmap;

import priv.zxy.moonstep.framework.race.Race;
import priv.zxy.moonstep.framework.stroage.UserRaceInfo;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;
import priv.zxy.moonstep.util.ImageCacheUtil.MemoryCacheUtil;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/7
 * 描述:用户具体信息数据处理
 **/
public class UserDetailBiz implements IUserDetailBiz{

    public UserDetailBiz(){}

    /**
     * 读取用户种族信息
     * @return race
     */
    @Override
    public Race readRaceData(){
        return UserRaceInfo.getInstance().getRace();
    }

    /**
     * 根据用户性别读取种族图片信息
     */
    @Override
    public Bitmap readRaceImage() {
        Bitmap bitmap;
        if(UserSelfInfo.getInstance().getMySelf().getGender().equals("男")){
            bitmap = MemoryCacheUtil.getInstance().getBitmapFromMemory(UserRaceInfo.getInstance().getRace().getRacePathMan());
        }
        else{
            bitmap = MemoryCacheUtil.getInstance().getBitmapFromMemory(UserRaceInfo.getInstance().getRace().getRacePathWoMan());
        }
        return bitmap;
    }

    /**
     * 读取种族小图片信息
     */
    @Override
    public Bitmap readRaceIcon() {
        return MemoryCacheUtil.getInstance().getBitmapFromMemory(UserRaceInfo.getInstance().getRace().getRaceIcon());
    }
}

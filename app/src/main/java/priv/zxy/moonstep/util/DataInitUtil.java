package priv.zxy.moonstep.util;


import priv.zxy.moonstep.DAO.PullPetInfoDAO;
import priv.zxy.moonstep.data.bean.ErrorCodeEnum;
import priv.zxy.moonstep.framework.good.GoodSelfInfo;
import priv.zxy.moonstep.framework.good.Props;
import priv.zxy.moonstep.framework.pet.Pet;
import priv.zxy.moonstep.framework.stroage.GoodTreasureInfo;
import priv.zxy.moonstep.framework.stroage.MapDotsInfo;
import priv.zxy.moonstep.framework.stroage.MoonFriendInfo;
import priv.zxy.moonstep.framework.stroage.PetInfo;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/6
 * 描述:数据初始化类
 *      对当前应用中所有需要进入前申请的数据做一个封装，目的是为了让开发人员便捷的知道到底哪些数据是需要初始化的。
 *      数据初始化遵从就近原则：如果数据库中没有的话，才从服务器进行获取
 *      判断数据库中是否有数据，取决于新建一个单独的SharedPreference文件，当相应的数据被初始化过后，就设定相应的标识位
 **/

public class DataInitUtil {
    private static final String TAG = "DataInitUtil";

    /**
     * 初始化用户的个人数据
     * 在start的时候，会进行一次用户曾经是否经过成功登录的记录，如果是的话就会调用此函数，否则就会在发送网络请求(登录)之后进行调用
     * 也就是说，一定会在MainActivity之前进行调用
     * @param user 用户的信息类
     */
    public static void initUserSelfInfo(User user) {
        UserSelfInfo.getInstance().setMySelf(user);
    }

    /**
     * 初始化用户的物品数据
     * 用户物品数据直接从网络上获取
     */
    public static void initGoodSelfInfo() {
        new Props().getUserGoods(gs -> GoodSelfInfo.getInstance().addAll(gs), UserSelfInfo.getInstance().getMySelf().getPhoneNumber());
    }

    /**
     * 初始化地图坐标数据
     */
    public static void initMapDotData() {
        MapDotsInfo.getInstance().getDots();
    }
    /**
     * 初始化用户地图界面的宝物信息(如果数据库中没有的话，就从服务器上获取然后存储到数据库中)
     */
    public static void initUserMapTreasures() {
        GoodTreasureInfo.getInstance().initGoodTreasure();
    }

    /**
     * 初始化用户的宠物信息
     */
    public static void initPetInfo() {
        // 宠物信息已从服务器缓存到本地，直接读取到PetInfo
        if(SharedPreferencesUtil.isSavedMyPetInformation()){
            PetInfo.getInstance().setPet(SharedPreferencesUtil.readMyPetInformation());
        }
        // 宠物信息未缓存，则向服务器请求数据
        else{
            PullPetInfoDAO.getInstance().getPetInfo(new PullPetInfoDAO.CallBack() {
                @Override
                public void onSuccess(Pet pet) {
                    LogUtil.d(TAG, "宠物信息缓存成功");
                    SharedPreferencesUtil.saveMyPetInformation(pet);
                    PetInfo.getInstance().setPet(SharedPreferencesUtil.readMyPetInformation());
                }

                @Override
                public void onFail(ErrorCodeEnum errorCodeEnum) {
                    LogUtil.d(TAG, "宠物信息缓存失败");
                }
            }, UserSelfInfo.getInstance().getMySelf().getPhoneNumber());
        }
    }

    /**
     * 初始化用户的好友信息
     */
    public static void initUserMoonFriendsInfo() {
        MoonFriendInfo.getInstance().initMoonFriends();
    }

    /**
     * 初始化用户权限信息
     */
    public static void initUserAuthority() {

    }

    /**
     * 初始化离线消息
     */
    public static void initOfflineMessage() {

    }
}

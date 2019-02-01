package priv.zxy.moonstep.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import priv.zxy.moonstep.constant.SharedConstant;
import priv.zxy.moonstep.framework.pet.Pet;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.wheel.cache.FacadeSharedPreference;


public class SharedPreferencesUtil {

    private static final String TAG = "SharedPreferencesUtil";

    private static FacadeSharedPreference sp;

    private static void init() {
        if (sp == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (sp == null) {
                    sp = new FacadeSharedPreference(Application.getContext());
                }
            }
        }
    }

    /**
     * 存储第一次登陆的信息
     */
    public static void setFirstLogin(){
        if (sp == null) {
            init();
        }
        sp.saveElement(SharedConstant.LANDING_LIBRARY,SharedConstant.IS_FIRST_LANDING, true);
    }

    /**
     * 判断是否为第一次登陆
     * @return 是第一次登录的话，返回True
     *          非第一次登录的话，返回False
     */
    public static Boolean isFirstLogin(){
        if (sp == null) {
            init();
        }
        return !sp.checkElement(SharedConstant.LANDING_LIBRARY, SharedConstant.IS_FIRST_LANDING);
    }

    /**
     * 当登陆成功的时候存储用户的手机号和密码
     * @param username 用户名
     * @param password 用户密码
     */
    public static void setSuccessLoginInfo(String username, String password){
        if (sp == null) {
            init();
        }
        Map<String, String> params = new HashMap<>();
        params.put(SharedConstant.PHONE_NUMBER, username);
        params.put(SharedConstant.PASSWORD, password);
        sp.save(SharedConstant.LANDING_LIBRARY, params);
        sp.saveElement(SharedConstant.LANDING_LIBRARY, SharedConstant.IS_SUCCESS, true);
    }

    /**
     * 当用户登陆失败的时候设置用户的登陆检测字段为false,直到下次登陆成功为止不允许自动登陆
     */
    public static void setFailLoginInfo(){
        if (sp == null) {
            init();
        }
        sp.saveElement(SharedConstant.LANDING_LIBRARY, SharedConstant.IS_SUCCESS, false);
    }

    /**
     * 读取登陆信息
     * @return 返回登陆信息的集合<用户名，密码>
     */
    public static Map<String, String> readLoginInfo(){
        if (sp == null) {
            init();
        }
        // 上次登陆不成功，就退出登陆
        if (!isSuccessLogin()) {
            return null;
        }
        Map<String, String> data = new HashMap<>();
        data.put(SharedConstant.PHONE_NUMBER, sp.read(SharedConstant.LANDING_LIBRARY).get(SharedConstant.PHONE_NUMBER).toString());
        data.put(SharedConstant.PASSWORD, sp.read(SharedConstant.LANDING_LIBRARY).get(SharedConstant.PASSWORD).toString());
        return data;
    }

    /**
     * 判断上次登陆是否成功
     * @return 返回上次登陆的结果值
     */
    public static boolean isSuccessLogin(){
        if (sp == null) {
            init();
        }
        return sp.checkElement(SharedConstant.LANDING_LIBRARY, SharedConstant.IS_SUCCESS);
    }

    /**
     * 存储用户的个人信息
     * @param moonFriend 用户信息
     */
    public static void saveMySelfInformation(User moonFriend){
        if (sp == null) {
            init();
        }
        Map<String ,String> params = new HashMap<>();
        params.put(SharedConstant.NICK_NAME, moonFriend.getNickName());
        params.put(SharedConstant.PHONE_NUMBER, moonFriend.getPhoneNumber());
        params.put(SharedConstant.GENDER, moonFriend.getGender());
        params.put(SharedConstant.RACE_CODE, String.valueOf(moonFriend.getRaceCode()));
        params.put(SharedConstant.HEAD_PATH, moonFriend.getHeadPath());
        params.put(SharedConstant.SIGNATURE, moonFriend.getSignature());
        params.put(SharedConstant.ADDRESS, moonFriend.getLocation());
        params.put(SharedConstant.CURRENT_TITLE_CODE, moonFriend.getCurrentTitleCode());
        params.put(SharedConstant.LEVEL, moonFriend.getLevel());
        params.put(SharedConstant.LUCKY_VALUE, String.valueOf(moonFriend.getLuckyValue()));
        sp.save(SharedConstant.PERSONAL_INFO_LIBRARY, params);
        sp.saveElement(SharedConstant.PERSONAL_INFO_LIBRARY, SharedConstant.IS_SAVED, true);
    }

    /**
     * 读取用户的个人信息
     *
     * <p>不推荐使用，建议使用 UserSelfInfo.getInstance().getMySelf()来获取用户个人信息</p>
     * @return 返回个人信息的集合
     */
    @SuppressWarnings("not recommended")
    public static User readMySelfInformation(){
        if (sp == null) {
            init();
        }
        // 之前还没有登陆成功的话，那么就从网络上请求数据，存储进磁盘的同时，要写入到UserSelfInfo中方便读取
        if (!isSavedMySelfInformation()) {
            throw new RuntimeException("磁盘中不存在用户信息，无法进行读取");
        }
        User user = new User();
        user.setNickName(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.NICK_NAME).toString());
        user.setPhoneNumber(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.PHONE_NUMBER).toString());
        user.setGender(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.GENDER).toString());
        user.setRaceCode(Integer.parseInt(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.RACE_CODE).toString()));
        user.setHeadPath(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.HEAD_PATH).toString());
        user.setSignature(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.SIGNATURE).toString());
        user.setLocation(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.ADDRESS).toString());
        user.setCurrentTitleCode(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.CURRENT_TITLE_CODE).toString());
        user.setLevel(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.LEVEL).toString());
        user.setLuckyValue(Integer.parseInt(sp.read(SharedConstant.PERSONAL_INFO_LIBRARY).get(SharedConstant.LUCKY_VALUE).toString()));
        return user;
    }

    /**
     * 判断是否为第一次读取用户的个人信息
     * @return 返回是否存储了用户个人信息的结果
     */
    private static boolean isSavedMySelfInformation(){
        if (sp == null) {
            init();
        }
        return sp.checkElement(SharedConstant.PERSONAL_INFO_LIBRARY, "isSaved");
    }

    /**
     * 判断是否为第一次读取用户的宠物信息
     * @return 返回是否存储了用户宠物信息的结果
     */
    private static boolean isSavedMyPetInformation(){
        if (sp == null) {
            init();
        }
        return sp.checkElement(SharedConstant.PET_INFO_LIBRARY, "pet_info_is_saved");
    }

    /**
     * 存储用户的宠物信息
     * @param pet 用户的宠物信息
     */
    public static void saveMyPetInformation(Pet pet){
        if (sp == null) {
            init();
        }
        Map<String ,String> params = new HashMap<>();
        params.put(SharedConstant.PET_CODE, String.valueOf(pet.getPetCode()));
        params.put(SharedConstant.PET_NICK_NAME, pet.getPetNickName());
        params.put(SharedConstant.PET_CB_PW, String.valueOf(pet.getPetCbPw()));
        params.put(SharedConstant.PET_RACE, String.valueOf(pet.getPetRace()));
        params.put(SharedConstant.PET_LEVEL, String.valueOf(pet.getPetLevel()));
        params.put(SharedConstant.PET_PROBABILITY, String.valueOf(pet.getPetProbability()));
        params.put(SharedConstant.PET_DESCRIPTION, pet.getPetDescription());
        params.put(SharedConstant.SKILL_NAME, pet.getSkillName());
        params.put(SharedConstant.SKILL_DESCRIPTION, pet.getSkillDescription());
        params.put(SharedConstant.PET_IMAGE_PATH, pet.getPetImagePath());
        params.put(SharedConstant.GROWTH_DEGREE, pet.getGrowthDegree());
        sp.save(SharedConstant.PET_INFO_LIBRARY, params);
        sp.saveElement(SharedConstant.PET_INFO_LIBRARY, SharedConstant.PET_INFO_IS_SAVED, true);
    }

    /**
     * 读取用户的宠物信息
     * @return 返回宠物信息的集合
     */
    @SuppressWarnings("not recommended")
    public static Pet readMyPetInformation(){
        if (sp == null) {
            init();
        }
        // 之前还没有登陆成功的话，那么就从网络上请求数据，存储进磁盘的同时，要写入到UserSelfInfo中方便读取
        if (!isSavedMyPetInformation()) {
            throw new RuntimeException("磁盘中不存在宠物信息，无法进行读取");
        }
        Pet pet = new Pet();
        pet.setPetCode(Integer.parseInt(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_CODE).toString()));
        pet.setPetNickName(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_NICK_NAME).toString());
        pet.setPetCbPw(Integer.parseInt(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_CB_PW).toString()));
        pet.setPetRace(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_RACE).toString());
        pet.setPetLevel(Integer.parseInt(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_LEVEL).toString()));
        pet.setPetProbability(Float.parseFloat(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_PROBABILITY).toString()));
        pet.setPetDescription(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_DESCRIPTION).toString());
        pet.setSkillName(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.SKILL_NAME).toString());
        pet.setSkillDescription(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.SKILL_DESCRIPTION).toString());
        pet.setPetImagePath(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.PET_IMAGE_PATH).toString());
        pet.setGrowthDegree(sp.read(SharedConstant.PET_INFO_LIBRARY).get(SharedConstant.GROWTH_DEGREE).toString());
        return pet;
    }

    /**
     * 当有消息来临时，如果页面没有关闭，就先将消息存储到缓存中
     * 为了让FirstMainPageFragmentParent对缓存进行检测并对用户进行提示
     */
    public static void saveIsMessageTip(String phoneNumber){
        if (sp == null) {
            init();
        }
        sp.saveNumber(SharedConstant.MESSAGE_TIP_LIBRARY, phoneNumber, 1, null);
    }

    /**
     * 将每个人发来的消息数目和消息人存入缓存中
     * 当传递进来phoneNumber地时候，获得当前未读的消息数目。
     * @param phoneNumber 电话号码
     * @return 返回当前电话号码的未读消息个数
     */
    public static int readMessageNumber(String phoneNumber){
        if (sp == null) {
            init();
        }
        SharedPreferences sp = Application.getContext().getSharedPreferences(SharedConstant.MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        return sp.getInt(phoneNumber, 0);
    }

    /**
     * 当点击进入一个人的聊天页面，说明他的消息已经被看过来，此时应该将他的消息记录从缓存中清除。
     */
    public static void handleMessageTip(String phoneNumber){
        SharedPreferences sp = Application.getContext().getSharedPreferences(SharedConstant.MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(phoneNumber);
        if (sp.contains(SharedConstant.MESSAGE) && sp.getAll().size() == 1){
            editor.remove(SharedConstant.MESSAGE);
        }
        editor.apply();
    }

    /**
     * 检测当前是否有临时消息
     * @return 当sp中包含的消息只有message地时候，说明这个时候没有任何新来的消息，那么就返回false，
     *              若非这种情况，就返回true
     */
    public static boolean isMessageTip(){
        if (sp == null) {
            init();
        }
        SharedPreferences sp = Application.getContext().getSharedPreferences(SharedConstant.MESSAGE_TIP_LIBRARY, Context.MODE_PRIVATE);
        return sp.getAll().size() > 1;
    }

    /**
     * 判断数据是否已经被初始化过
     * @return
     */
    public static boolean dataIsInited(String dataName) {
        if (sp == null) {
            init();
        }
        return sp.checkElement(SharedConstant.DATA_INIT_LIBRARY, dataName);
    }

    /**
     * 设置数据被初始化过
     */
    public static void setDataInited(String dataName) {
        if (sp == null) {
            init();
        }
        sp.saveElement(SharedConstant.DATA_INIT_LIBRARY, dataName, true);
    }
    /**
     * 用在地图宝藏刷新的API
     * 用来检索当前时间和数据库中存入的时间（上次存入的时间）是否相差大于3(天)
     * @param days 大于3返回true,否则返回false
     */
    public static boolean checkMapTime(int days){
        if (sp == null) {
            init();
        }
        LogUtil.d(TAG, SharedConstant.DAYS + days);
        SharedPreferences sps = Application.getContext().getSharedPreferences(SharedConstant.MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sps.edit();
        // 第一次的时候，只有过三天，才能重新存一次days
        if (days >= readMapTime() + 3){
            editor.putInt(SharedConstant.DAYS, days);
            editor.apply();
            return true;
        }else if (readMapTime() > days){
            editor.putInt(SharedConstant.DAYS, days);
            editor.apply();
            return false;
        }
        return false;
    }

    private static int readMapTime(){
        SharedPreferences sp = Application.getContext().getSharedPreferences(SharedConstant.MAP_REFRESH_TIME_CHECK, Context.MODE_PRIVATE);
        return sp.getInt(SharedConstant.DAYS, 0);
    }

    /**
     * 清理引用
     */
    public static void clear() {
        sp = null;
    }
}

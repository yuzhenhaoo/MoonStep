package priv.zxy.moonstep.framework.stroage;

import priv.zxy.moonstep.framework.pet.Pet;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/1
 * 描述: 存储宠物信息的一个临时类
 *       在加载宠物信息的时候开始初始化数据
 *       在程序结束的时候，需要清空所有的引用
 **/

public class PetInfo {

    /**
     * 存储当前用户宠物信息的Pet对象
     * 注意，这里是引用对象，使用完毕后，要记得清除引用
     */
    private Pet pet = null;

    /**
     * 使用饿汉式是为了提高效率
     */
    private static PetInfo instance = new PetInfo();

    public static PetInfo getInstance() {
        return instance;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        if (pet == null) {
            // 如果没有从网络端获取到数据，就从xml文件中获取数据
            pet = SharedPreferencesUtil.readMyPetInformation();
            // 清理内部的引用
            SharedPreferencesUtil.clear();
        }
        return pet;
    }

    /**
     * 清理引用，以便JVM调用GC
     */
    public void clear() {
        pet = null;
    }
}

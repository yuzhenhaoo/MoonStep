package priv.zxy.moonstep.library.image_loader;

import android.graphics.Bitmap;

/**
 * @author 张晓翼
 * @createTime 2019/3/8 0008
 * @Describe 创建缓存管理器接口
 */
interface CacheManager {

    void put(String url, Bitmap bitmap);

    Bitmap get(String url);
}

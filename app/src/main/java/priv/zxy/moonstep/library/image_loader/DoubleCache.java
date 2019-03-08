package priv.zxy.moonstep.library.image_loader;

import android.graphics.Bitmap;

/**
 * @author 张晓翼
 * @createTime 2019/3/8 0008
 * @Describe
 */
class DoubleCache implements CacheManager {

    CacheManager memoryCache = new MemoryCache();
    CacheManager diskCache = new DiskCache();

    @Override
    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
        diskCache.put(url, bitmap);
    }

    /**
     * 先从内存中获取，如果内存中没有，再从磁盘中获取
     * @param url 图片地址
     */
    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap == null) {
            bitmap = diskCache.get(url);
        }
        return bitmap;
    }

}

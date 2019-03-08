package priv.zxy.moonstep.library.image_loader;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @author 张晓翼
 * @createTime 2019/3/8 0008
 * @Describe 内存缓存器
 */
public class MemoryCache implements CacheManager {

    private static LruCache<String , Bitmap> mMemoryCache;

    private static final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static final int cacheSize = maxMemory / 4;

    public MemoryCache() {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String url, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }

}

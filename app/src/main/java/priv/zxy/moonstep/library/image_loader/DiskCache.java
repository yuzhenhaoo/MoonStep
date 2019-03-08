package priv.zxy.moonstep.library.image_loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;

import com.bumptech.glide.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import priv.zxy.moonstep.data.application.Application;

import static android.os.Environment.isExternalStorageRemovable;

/**
 * @author 张晓翼
 * @createTime 2019/3/8 0008
 * @Describe
 */
public class DiskCache implements CacheManager {

    private static DiskLruCache mDiskCache;
    private static final String STORAGE_DIR_NAME = "image";

    public DiskCache(){
        File diskStorageDir = getDiskCacheDir(Application.getContext(), STORAGE_DIR_NAME);
        if (!diskStorageDir.exists()) {
            diskStorageDir.mkdirs();
        }
        try {
            mDiskCache = DiskLruCache.open(diskStorageDir, getAppVersion(Application.getContext()), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(String url, Bitmap bitmap) {

    }

    private String hashKeyForDisk(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    @Override
    public Bitmap get(String url) {
        return null;
    }

    /**
     * 动态的获取到app版本号，优点在于灵活
     * 因为版本号不一致就会删除所有相关缓存
     *
     * @param context 环境变量
     * @return 版本信息
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获得一个可用的磁盘存储目录
     * external：如：/storage/emulated/0/Android/data/package_name/cache
     * internal 如：/data/data/package_name/cache
     *
     * @param context    The context to use
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */
    private static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||!isExternalStorageRemovable()
                ? context.getExternalCacheDir().getPath()
                : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

}

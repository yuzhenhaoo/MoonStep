package priv.zxy.moonstep.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import java.util.List;
import priv.zxy.moonstep.util.LogUtil;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/14
 * 描述:权限请求帮助类
 **/
public class PermissionHelper implements EasyPermissions.PermissionCallbacks{

    private static final String TAG = "PermissionHelper";

    private static PermissionHelper instance;

    public static PermissionHelper getInstance() {
        if (instance == null) {
            synchronized (PermissionHelper.class) {
                if (instance == null) {
                    instance = new PermissionHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 检查权限
     * @param context
     * return true:已经获取权限
     * return false: 未获取权限，主动请求权限
     */
    public static boolean checkPermission(Activity context,String[] perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 请求权限
     * @param context 上下文对象
     */
    public static void requestPermission(Activity context,String tip,int requestCode, String[] perms) {
        EasyPermissions.requestPermissions(context, tip, requestCode, perms);
    }

    /**
     * 请求权限成功
     * @param requestCode 请求权限返回结果
     * @param perms 请求权限列表
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        LogUtil.d(TAG, "请求权限成功");
    }

    /**
     * 请求权限失败
     * @param requestCode 请求权限返回结果
     * @param perms 请求权限列表
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms ) {
        LogUtil.d(TAG, "请求权限失败");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}

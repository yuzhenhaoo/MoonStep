package priv.zxy.moonstep.commerce.view.Me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import priv.zxy.moonstep.DAO.ImageInfo.PullImage2Server;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseActivity;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.util.FileUtil;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/2 18:34
 * 类描述: 图片上传的管理模块
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */
public class ImageManager {

    private static final String TAG = "ImageManager";

    private String headPath;

    private PopupWindow pop;
    private static Activity mActivity;
    private static Fragment fragment;
    private static ImageView view;
    private static final String CAMERA_IMAGE_NAME = "moonstep" + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png";
    private static final String CACHE_HEAD_NAME = "head_portrait.png";

    private static ImageManager instance;

    public static ImageManager getInstance(Activity mActivity, ImageView view) {
        if (instance == null) {
            synchronized (ImageManager.class) {
                instance = new ImageManager();
                ImageManager.mActivity = mActivity;
                ImageManager.view = view;
            }
        }
        return instance;
    }

    public static ImageManager getInstance(Fragment fragment, ImageView view) {
        if (instance == null) {
            synchronized (ImageManager.class) {
                instance = new ImageManager();
                ImageManager.mActivity = fragment.getActivity();
                ImageManager.fragment = fragment;
                ImageManager.view = view;
            }
        }
        return instance;
    }

    public void showPop() {
        View bottomView = View.inflate(mActivity, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(lp);
        pop.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = mActivity.getWindow().getAttributes();
            lp1.alpha = 1f;
            mActivity.getWindow().setAttributes(lp1);
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = view -> {
            switch (view.getId()) {
                case R.id.tv_album:
                    // 打开系统相册
                    openSysAlbum();
                    break;
                case R.id.tv_camera:
                    // 打开系统相机
                    openSysCamera();
                    break;
                case R.id.tv_cancel:
                    // 取消
                    closePopupWindow();
                    break;
            }
            closePopupWindow();
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    private final static int ALBUM_RESULT_CODE = 0;
    private final static int CAMERA_RESULT_CODE = 1;

    /**
     * 打开系统相机
     */
    private void openSysCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                new File(Environment.getExternalStorageDirectory(), CAMERA_IMAGE_NAME)));
        mActivity.startActivityForResult(cameraIntent, CAMERA_RESULT_CODE);
    }

    /**
     * 打开系统相册
     */
    public void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
        Uri mDestinationUri = Uri.fromFile(new File(mActivity.getCacheDir(), CACHE_HEAD_NAME));
        switch (requestCode) {
            case ALBUM_RESULT_CODE:
                Uri uri = data.getData();
                assert uri != null;
                startCropActivity(uri, mDestinationUri);
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = UCrop.getOutput(data);
                    assert resultUri != null;
                    updateCropImageToCache();
                    updateCropImageToDisk(resultUri);
                    updateCropImageToCurrentPage(headPath, view);
                    updateCropImageToRemoteService(headPath);
                    LogUtil.d(TAG, "userHeadPath" + UrlBase.USER_HEAD_BASE_PATH + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png");
                }
                break;
            case UCrop.RESULT_ERROR:
                LogUtil.e(TAG, "裁剪URI获取错误");
        }
    }

    /**
     * 对外暴露的接口，开启剪裁Activity
     * @param sourceUri
     * @param resultUri
     */
    public void startCropActivity(Uri sourceUri, Uri resultUri) {
        if (fragment == null) {
            UCrop.of(sourceUri, resultUri)
                    .withAspectRatio(1, 1) // 设置裁剪框宽高比1:1
                    .start(mActivity);
        }
        if (fragment != null) {
            UCrop.of(sourceUri, resultUri)
                    .withAspectRatio(1, 1) // 设置裁剪框宽高比1:1
                    .start(fragment.getContext(), fragment);
        }
    }
    /**
     * 将裁剪后图片更新到当前页面
     */
    public void updateCropImageToCurrentPage(String imagePath, ImageView view) {
        assert imagePath != null;
        LogUtil.d(TAG, "updateCropImageToCurrentPage" + imagePath);
        Glide.with(mActivity).load(imagePath).into(view);
    }

    /**
     * 将裁剪后图片更新到磁盘
     * 1. 将原始图片复制一份到本地空间的cache文件夹下并进行改名
     * 2. 对cache下的图片进行上传
     */
    public void updateCropImageToDisk(Uri uri) {
        String oldPath = uri.getPath();
        LogUtil.d(TAG, "oldPath" + oldPath);
        String newPath = mActivity.getCacheDir().getPath() + "/" + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png";
        headPath = newPath;
        FileUtil.copyFile(oldPath, newPath);
        // 将已经从缓存中更新的用户数据更新到xml文件中
        SharedPreferencesUtil.saveMySelfInformation(UserSelfInfo.getInstance().getMySelf());
    }

    /**
     * 将裁剪后图片更新到当前缓存（UserSelfInfo）
     */
    public void updateCropImageToCache() {
        User user = UserSelfInfo.getInstance().getMySelf();
        user.setHeadPath(UrlBase.USER_HEAD_BASE_PATH + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png");
        UserSelfInfo.getInstance().setMySelf(user);
    }

    /**
     * 将裁剪后图片更新到远程服务器
     */
    public void updateCropImageToRemoteService(String imagePath) {
        assert imagePath != null;
        LogUtil.d(TAG, "updateCropImageToRemoteService" + imagePath);
        PullImage2Server.upLoad2Server(imagePath, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                LogUtil.d(TAG, "上传成功" + responseBody.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LogUtil.d(TAG, "上传失败");
                LogUtil.e(TAG, error.getMessage());
            }
        });
    }
}

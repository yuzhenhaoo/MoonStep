package priv.zxy.moonstep.commerce.view.Me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import priv.zxy.moonstep.DAO.ImageInfo.PullImage2Server;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.AnimationButton;
import priv.zxy.moonstep.customview.WaveViewByBezier;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;
import priv.zxy.moonstep.util.FileUtil;
import priv.zxy.moonstep.util.ImageCacheUtil.GlideCacheUtil;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;

public class PersonalSurfaceFragment extends BaseFragment {

    private static final String TAG = "PersonalSurfaceFragment";
    private View view;
    private Activity mActivity;
    private WaveViewByBezier waveViewByBezier;
    private AnimationButton bt;
    private TextView userNickName;
    private TextView userLevelName;
    private PopupWindow pop;
    private ImageView head;
    private String headPath;

    private static final String CAMERA_IMAGE_NAME = "moonstep" + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png";
    private static final String CACHE_HEAD_NAME = "head_portrait.png";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this.getActivity();
        setHasOptionsMenu(true);//在Fragment要想让onCreateOptionsMenu生效必须先调用setHasOptionsMenu的方法
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage4, container, false);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();

        initData();

        initEvent();

    }

    private void initView() {
        bt = view.findViewById(R.id.clickButton);

        waveViewByBezier = view.findViewById(R.id.waveView);
        userNickName = view.findViewById(R.id.userNickName);
        userLevelName = view.findViewById(R.id.userLevelName);
        head = view.findViewById(R.id.headPortrait);
    }

    private void initData() {
        User user = UserSelfInfo.getInstance().getMySelf();
        userNickName.setText(user.getNickName());
        userLevelName.setText(UserSelfInfo.getInstance().getMySelf().getLevel());
        Glide.with(this).load(user.getHeadPath()).placeholder(R.drawable.default_man_head).into(head);
    }

    private void initEvent() {
        waveViewByBezier.startAnimation();

        bt.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                bt.start();
            }

            @Override
            public void animationFinish() {
                jumpToDetailActivity();
            }
        });

        head.setOnClickListener(v -> {
            showPop();
        });
    }

    private void jumpToDetailActivity() {
        Intent intent = new Intent(mActivity, UserDetailActivity.class);
        startActivity(intent);
    }

    private void showPop() {
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
        startActivityForResult(cameraIntent, CAMERA_RESULT_CODE);
    }

    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "请求码" + requestCode);
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
                    assert  resultUri != null;
                    updateCropImageToCache();
                    updateCropImageToDisk(resultUri);
                    updateCropImageToCurrentPage(headPath);
                    updateCropImageToRemoteService(headPath);
                    LogUtil.d(TAG, "userHeadPath" + UrlBase.USER_HEAD_BASE_PATH + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png");
                }
                break;
            case UCrop.RESULT_ERROR:
                LogUtil.e(TAG, "裁剪URI获取错误");
        }
    }

    private void startCropActivity(Uri sourceUri, Uri resultUri) {
        UCrop.of(sourceUri, resultUri)
                .withAspectRatio(1, 1) // 设置裁剪框宽高比1:1
                .start(this.getContext(), this);
    }

    /**
     * 将裁剪后图片更新到当前页面
     */
    private void updateCropImageToCurrentPage(String imagePath) {
        assert imagePath != null;
        LogUtil.d(TAG, "updateCropImageToCurrentPage" + imagePath);
        Glide.with(this).load(imagePath).into(head);
    }

    /**
     * 将裁剪后图片更新到磁盘
     * 1. 将原始图片复制一份到本地空间的cache文件夹下并进行改名
     * 2. 对cache下的图片进行上传
     */
    private void updateCropImageToDisk(Uri uri) {
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
    private void updateCropImageToCache() {
        User user = UserSelfInfo.getInstance().getMySelf();
        user.setHeadPath(UrlBase.USER_HEAD_BASE_PATH + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png");
        UserSelfInfo.getInstance().setMySelf(user);
    }

    /**
     * 将裁剪后图片更新到远程服务器
     */
    private void updateCropImageToRemoteService(String imagePath) {
        assert imagePath != null;
        LogUtil.d(TAG, "updateCropImageToRemoteService" + imagePath);
        PullImage2Server.upLoad2Server(imagePath,  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                LogUtil.d(TAG, "上传成功" + responseBody.toString());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LogUtil.d(TAG, "上传失败");
                LogUtil.e(TAG, error.getMessage()); }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        waveViewByBezier.pauseAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        waveViewByBezier.resumeAnimation();
        bt.reset();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (waveViewByBezier == null) {
            return;
        }
        waveViewByBezier.stopAnimation();
        view = null;
        bt = null;
    }
}
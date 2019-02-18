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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.AnimationButton;
import priv.zxy.moonstep.customview.WaveViewByBezier;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;
import priv.zxy.moonstep.util.LogUtil;

import static android.app.Activity.RESULT_OK;

public class PersonalSurfaceFragment extends BaseFragment {

    private static final String TAG = "PersonalSurfaceFragment";
    private View view;
    private ContextMenuDialogFragment mMenuDiaLogFragment;
    private ArrayList<MenuObject> menuObjects = new ArrayList<>();
    private Activity mActivity;
    private WaveViewByBezier waveViewByBezier;
    private AnimationButton bt;
    private TextView userNickName;
    private TextView userLevelName;
    private PopupWindow pop;
    private Button head;

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
        Uri mDestinationUri = Uri.fromFile(new File(mActivity.getCacheDir(), CACHE_HEAD_NAME));
        switch (resultCode) {
            case ALBUM_RESULT_CODE:
                Uri uri = data.getData();
                assert uri != null;
                UCrop.of(uri, mDestinationUri)
                        .withAspectRatio(1, 1) // 设置裁剪框宽高比1:1
                        .start(mActivity);
                break;
            case CAMERA_RESULT_CODE:
                File cameraResult = new File(Environment.getExternalStorageDirectory(), CAMERA_IMAGE_NAME);
                Uri cameraUri = Uri.fromFile(cameraResult);
                UCrop.of(cameraUri, mDestinationUri)
                        .withAspectRatio(1, 1) // 设置裁剪框宽高比1:1
                        .start(mActivity);
                break;
            case RESULT_OK:
                final Uri resultUri = UCrop.getOutput(data);
                updateCropImageToCurrentPage();
                updateCropImageToCache();
                updateCropImageToDisk();
                updateCropImageToRemoteService();
                break;
            case UCrop.RESULT_ERROR:
                LogUtil.e(TAG, "裁剪URI获取错误");
        }
    }

    /**
     * 将裁剪后图片更新到当前页面
     */
    private void updateCropImageToCurrentPage() {

    }

    /**
     * 将裁剪后图片更新到当前缓存
     */
    private void updateCropImageToCache() {

    }

    /**
     * 将裁剪后图片更新到磁盘
     */
    private void updateCropImageToDisk() {

    }

    /**
     * 将裁剪后图片更新到远程服务器
     */
    private void updateCropImageToRemoteService() {

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
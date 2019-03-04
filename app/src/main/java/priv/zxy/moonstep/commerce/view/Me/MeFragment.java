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
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yalantis.ucrop.UCrop;

import java.io.File;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.DAO.ImageInfo.PullImage2Server;
import priv.zxy.moonstep.DAO.constant.UrlBase;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.kefu.KeFuActivity;
import priv.zxy.moonstep.settings.SettingActivity;
import priv.zxy.moonstep.util.FileUtil;
import priv.zxy.moonstep.util.LogUtil;
import priv.zxy.moonstep.util.SharedPreferencesUtil;

import static android.app.Activity.RESULT_OK;

/**
 * 创建人: zhang376358913
 * 创建时间: 2019/3/2 18:32
 * 类描述: 我的信息的Fragment
 * 修改人: zhang376358913
 * 修改时间: zhang376358913
 * 修改备注:
 */

public class MeFragment extends BaseFragment implements View.OnClickListener{

    private static final String TAG = "MeFragment";
    private Activity mActivity;
    private View view;
    private CircleImageView mHead;
    private TextView mTask;
    private TextView mLevel;
    private Button mSettingBt;
    private CardView mCardView;
    private TextView mName;
    private TextView mRaceName;
    private TextView mTitle;
    private CircleImageView mCircleImageView;
    private GridLayout mGridView;
    private TextView mCoin;

    private Button packBt;
    private Button petBt;
    private Button messageBt;
    private Button titleBt;
    private Button mallBt;
    private Button collectBt;
    private Button cacheClearBt;
    private Button updateLevel;
    private Button feedBack;

    private PopupWindow pop;
    private String headPath;

    private static final String CAMERA_IMAGE_NAME = "moonstep" + UserSelfInfo.getInstance().getMySelf().getPhoneNumber() + ".png";
    private static final String CACHE_HEAD_NAME = "head_portrait.png";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_me, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        mSettingBt.setOnClickListener(this);
        packBt.setOnClickListener(this);
        petBt.setOnClickListener(this);
        messageBt.setOnClickListener(this);
        titleBt.setOnClickListener(this);
        mallBt.setOnClickListener(this);
        collectBt.setOnClickListener(this);
        cacheClearBt.setOnClickListener(this);
        updateLevel.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        mCircleImageView.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        mActivity = this.getActivity();
        mHead = view.findViewById(R.id.head);
        mTask = view.findViewById(R.id.task);
        mLevel = view.findViewById(R.id.level);
        mSettingBt = view.findViewById(R.id.settingBt);
        mCardView = view.findViewById(R.id.cardView);
        mName = view.findViewById(R.id.name);
        mRaceName = view.findViewById(R.id.raceName);
        mTitle = view.findViewById(R.id.title);
        mCircleImageView = view.findViewById(R.id.circleImageView);
        mGridView = view.findViewById(R.id.gridView);
        mCoin = view.findViewById(R.id.coin);

        packBt = view.findViewById(R.id.packBt);
        petBt = view.findViewById(R.id.petBt);
        messageBt = view.findViewById(R.id.messageBt);
        titleBt = view.findViewById(R.id.titleBt);
        mallBt = view.findViewById(R.id.mallBt);
        collectBt = view.findViewById(R.id.collectBt);
        cacheClearBt = view.findViewById(R.id.cacheClearBt);
        updateLevel = view.findViewById(R.id.updateLevel);
        feedBack = view.findViewById(R.id.feedBack);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mSettingBt)) {
            toTargetActivity(this, SettingActivity.class);
        }
        if (v.equals(packBt)) {
            toTargetActivity(this, PackActivity.class);
        }
        if (v.equals(petBt)) {
            toTargetActivity(this, PetActivity.class);
        }
        if (v.equals(messageBt)) {
            Toast.makeText(mActivity, "消息页面还没有开发完哦！", Toast.LENGTH_SHORT).show();
        }
        if (v.equals(titleBt)) {
            toTargetActivity(this, RaceActivity.class);
        }
        if (v.equals(mallBt)) {
            Toast.makeText(mActivity, "商城页面还没有开发完哦！", Toast.LENGTH_SHORT).show();
        }
        if (v.equals(collectBt)) {
            Toast.makeText(mActivity, "收藏页面还没有开发完哦！", Toast.LENGTH_SHORT).show();
        }
        if (v.equals(cacheClearBt)) {
            Toast.makeText(mActivity, "清理缓存页面还没有开发完哦！", Toast.LENGTH_SHORT).show();
        }
        if (v.equals(updateLevel)) {
            Toast.makeText(mActivity, "等级提升页面还没有开发完哦！", Toast.LENGTH_SHORT).show();
        }
        if (v.equals(feedBack)) {
            toTargetActivity(this, KeFuActivity.class);
        }
        if (v.equals(mCircleImageView)) {
            showPop();
        }
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
        Glide.with(this).load(imagePath).into(mCircleImageView);
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

}

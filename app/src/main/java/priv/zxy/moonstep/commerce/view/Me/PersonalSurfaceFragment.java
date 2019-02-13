package priv.zxy.moonstep.commerce.view.Me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;

import java.util.ArrayList;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.AnimationButton;
import priv.zxy.moonstep.customview.WaveViewByBezier;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.stroage.UserSelfInfo;

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
    private Button headPortrait;
    private PopupWindow pop;

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
        headPortrait = view.findViewById(R.id.headPortrait);
    }

    private void initData() {
        User user = UserSelfInfo.getInstance().getMySelf();
        userNickName.setText(user.getNickName());
        userLevelName.setText(user.getLevel());
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

        headPortrait.setOnClickListener(v -> {
            showPop(mActivity, R.layout.dialog_pop);
        });
    }

    private void jumpToDetailActivity() {
        Intent intent = new Intent(mActivity, UserDetailActivity.class);
        startActivity(intent);
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
    }

    private void showPop(Context context, int dialogLayoutId) {
        // 创建勇于PopupWindow的View
        View bottomView = View.inflate(context, dialogLayoutId, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        PopupWindow pop = new PopupWindow(bottomView, -1, -2);
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
                    //相册
                    PictureSelector.create(PersonalSurfaceFragment.this)
                            .openGallery(PictureMimeType.ofImage())
                            .maxSelectNum(1)
                            .minSelectNum(1)
                            .imageSpanCount(4)
                            .selectionMode(PictureConfig.MULTIPLE)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                    break;
                case R.id.tv_camera:
                    //拍照
                    PictureSelector.create(PersonalSurfaceFragment.this)
                            .openCamera(PictureMimeType.ofImage())
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                    break;
                case R.id.tv_cancel:
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

    /// 进入相册以及相关的样式设置
//    private void setPictureSelector() {
//        PictureSelector.create(PersonalSurfaceFragment.this)
//                .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//                .maxSelectNum()// 最大图片选择数量 int
//                .minSelectNum()// 最小选择数量 int
//                .imageSpanCount(4)// 每行显示个数 int
//                .selectionMode()// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
//                .previewImage()// 是否可预览图片 true or false
//                .previewVideo()// 是否可预览视频 true or false
//                .enablePreviewAudio() // 是否可播放音频 true or false
//                .isCamera()// 是否显示拍照按钮 true or false
//                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
//                .enableCrop()// 是否裁剪 true or false
//                .compress()// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
//                .isGif()// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
//                .freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
//                .circleDimmedLayer()// 是否圆形裁剪 true or false
//                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                .openClickSound()// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
//                .previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .minimumCompressSize(100)// 小于100kb的图片不压缩
//                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled() // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
//                .isDragFrame(false)// 是否可拖动裁剪框(固定)
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//    }
}
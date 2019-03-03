package priv.zxy.moonstep.commerce.view.Me;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.commerce.view.Map.PetActivity;
import priv.zxy.moonstep.data.bean.BaseFragment;
import priv.zxy.moonstep.pet.PackActivity;
import priv.zxy.moonstep.kefu.KeFuActivity;
import priv.zxy.moonstep.settings.SettingActivity;

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
            Toast.makeText(mActivity, "称号页面还没有开发完哦！", Toast.LENGTH_SHORT).show();
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
    }
}

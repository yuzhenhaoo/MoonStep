package priv.zxy.moonstep.gps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import priv.zxy.moonstep.R;

/**
 * 雷达页面，目的是为了在点击按钮的时候开启位置检测，若当前位置和数据库中具备奖励地点的经纬度距离在一定范围内，就要给予用户奖励。
 */
public class SecondMainPageFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fg_main_second_subpage2,null);
    }
}

package priv.zxy.moonstep.data.bean;

import android.support.v4.app.Fragment;

import priv.zxy.moonstep.data.application.Application;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/22
 * 描述: 所有Fragment的基类
 **/

public class BaseFragment extends Fragment {

    /**
     * 我们在Fragment的onDestroy中，去使用这个静态的RefWatcher进行观察，如果onDestroy了当前这个Fragment还没有被回收，说明该Fragment还没被回收，说明该Fragment产生了内存泄漏。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Application.mRefWatcher.watch(this);
    }
}

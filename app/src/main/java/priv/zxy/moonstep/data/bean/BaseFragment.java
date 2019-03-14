package priv.zxy.moonstep.data.bean;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import priv.zxy.moonstep.data.application.Application;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/22
 * 描述: 所有Fragment的基类
 **/

public class BaseFragment extends Fragment {

    /**
     * 跳转到某个Activity中
     * @param fragment 要跳转的起始Fragment
     * @param className 要跳转的目的Activity
     */
    protected void toTargetActivity(Fragment fragment, Class className) {
        Activity activity = fragment.getActivity();
        Intent intent = new Intent(activity, className);
        startActivity(intent);
    }

    /**
     * 我们在Fragment的onDestroy中，去使用这个静态的RefWatcher进行观察，如果onDestroy了当前这个Fragment还没有被回收，说明该Fragment还没被回收，说明该Fragment产生了内存泄漏。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Application.mRefWatcher != null) {
            Application.mRefWatcher.watch(this);
        }
    }
}

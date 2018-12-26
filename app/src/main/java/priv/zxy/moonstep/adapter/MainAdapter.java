package priv.zxy.moonstep.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import priv.zxy.moonstep.commerce.view.Tree.TreeActivity;
import priv.zxy.moonstep.commerce.view.Community.MoonCommunity;
import priv.zxy.moonstep.commerce.view.Friend.MoonFriendFragment;
import priv.zxy.moonstep.commerce.view.Me.PersonalSurfaceFragment;

public class MainAdapter extends FragmentPagerAdapter {

    private static final int PAGER_COUNT = 4;

    private TreeActivity myFragment1;

    private MoonCommunity myFragment2;

    private MoonFriendFragment myFragment3;

    private PersonalSurfaceFragment myFragment4;

    public MainAdapter(FragmentManager fm){
        super(fm);
        myFragment1 = new TreeActivity();
        myFragment2 = new MoonCommunity();
        myFragment3 = new MoonFriendFragment();
        myFragment4 = new PersonalSurfaceFragment();
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        System.out.println("position Destroy" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = myFragment1;
                break;
            case 1:
                fragment = myFragment2;
                break;
            case 2:
                fragment = myFragment3;
                break;
            case 3:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }
}

package priv.zxy.moonstep.moonstep_palace;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import priv.zxy.moonstep.moonstep_palace.moon_friend.view.MoonFriendFragment;
import priv.zxy.moonstep.moonstep_palace.moon_friend.view.PersonalInfoFragment;

public class FirstMainPageAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4;
    private FirstMainPageFragment1 myFragment1 = null;
    private FirstMainPageFragment2 myFragment2 = null;
    private MoonFriendFragment myFragment3 = null;
    private PersonalInfoFragment myFragment4 = null;

    public FirstMainPageAdapter(FragmentManager fm){
        super(fm);
        myFragment1 = new FirstMainPageFragment1();
        myFragment2 = new FirstMainPageFragment2();
        myFragment3 = new MoonFriendFragment();
        myFragment4 = new PersonalInfoFragment();
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
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

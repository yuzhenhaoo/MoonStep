package com.example.administrator.moonstep.main_second_page_fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class SecondMainPageAdapter extends FragmentPagerAdapter {
    private final int Pager_Count = 3;
    private SecondMainPageFragment1 secondMainPageFragment1 = null;
    private SecondMainPageFragment2 secondMainPageFragment2 = null;
    private SecondMainPageFragment3 secondMainPageFragment3 = null;

    public SecondMainPageAdapter(FragmentManager fm){
        super(fm);
        secondMainPageFragment1 = new SecondMainPageFragment1();
        secondMainPageFragment2 = new SecondMainPageFragment2();
        secondMainPageFragment3 = new SecondMainPageFragment3();
    }

    @Override
    public int getCount() {
        return Pager_Count;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment = secondMainPageFragment1;
                break;
            case 1:
                fragment = secondMainPageFragment2;
                break;
            case 2:
                fragment = secondMainPageFragment3;
                break;
        }
        return fragment;
    }
}

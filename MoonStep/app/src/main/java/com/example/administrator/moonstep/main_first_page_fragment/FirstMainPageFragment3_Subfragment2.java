package com.example.administrator.moonstep.main_first_page_fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.moonstep.R;

public class FirstMainPageFragment3_Subfragment2 extends Fragment{
    private static FirstMainPageFragment3_Subfragment2 ourInstance = new FirstMainPageFragment3_Subfragment2();
    private View view;
    public static FirstMainPageFragment3_Subfragment2 getInstance() {
        if (ourInstance == null){
            ourInstance = new FirstMainPageFragment3_Subfragment2();
        }
        return ourInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage3_fragment2, container, false);
        return view;
    }
}

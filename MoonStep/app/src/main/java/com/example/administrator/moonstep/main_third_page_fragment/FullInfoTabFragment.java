package com.example.administrator.moonstep.main_third_page_fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.moonstep.R;


public class FullInfoTabFragment extends Fragment {

    private static final String EXTRA_SRORT_CARD_MODEL = "EXTRA_SRORT_CARD_MODEL";
    //    String transitionTag;
    private MoonTitleModel moon_title_model;
    private Toolbar toolbar;
    private ImageView ivPhoto;
    private TextView tvDescription;
    private TextView titleName;
    private TextView titleLevel;

    public static FullInfoTabFragment newInstance(MoonTitleModel moon_title_model) {
        FullInfoTabFragment fragment = new FullInfoTabFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_SRORT_CARD_MODEL, moon_title_model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            moon_title_model = getArguments().getParcelable(EXTRA_SRORT_CARD_MODEL);
        }
        if (savedInstanceState != null) {
            moon_title_model = savedInstanceState.getParcelable(EXTRA_SRORT_CARD_MODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_thirdpage_item_info, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        tvDescription = view.findViewById(R.id.title_description);
        titleName = view.findViewById(R.id.titleName);
        titleLevel = view.findViewById(R.id.titleLevel);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle(moon_title_model.getTitleName());
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), moon_title_model.getBackgroundColorResId()));
        ivPhoto.setImageResource(moon_title_model.getImageResId());
        ivPhoto.setBackgroundResource(R.mipmap.full_into_fragment_background);
        tvDescription.setText(moon_title_model.getTitleDescription());
        titleName.setText(moon_title_model.getTitleName());
        titleLevel.setText(moon_title_model.getTitleLevel());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_SRORT_CARD_MODEL, moon_title_model);
        super.onSaveInstanceState(outState);
    }

    static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        /**
         * Default divider will be used
         */
        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        /**
         * Custom divider will be used
         */
        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}

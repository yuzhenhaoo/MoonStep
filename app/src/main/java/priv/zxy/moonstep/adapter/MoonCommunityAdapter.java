package priv.zxy.moonstep.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.commerce.view.Community.BaseCommunityMessage;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/28
 * 描述: 社区的适配器
 **/

public class MoonCommunityAdapter extends RecyclerView.Adapter<MoonCommunityAdapter.MyHolder> {

    private final List<BaseCommunityMessage> mItems = new ArrayList<>();
    private View view;//子类item

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_item, viewGroup, false);
        //是自定义ViewHolder持有我们的item
        return new MyHolder(view);
    }

    /**
     * 给每个item绑定数据
     */
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {
        BaseCommunityMessage cb = mItems.get(position);
        Glide.with(Application.getContext()).load(cb.getMediaPath()).into(myHolder.mediaOrImage);
        myHolder.address.setText(cb.getAddress());
        myHolder.langtitude.setText(cb.getLangtitude());
        myHolder.longitude.setText(cb.getLongitude());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public boolean add(BaseCommunityMessage item) {
        boolean isAdded = mItems.add(item);
        if (isAdded) {
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public boolean addAll(Collection<BaseCommunityMessage> items) {
        boolean isAdded = mItems.addAll(items);
        if (isAdded) {
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        final ImageView mediaOrImage;
        final ImageView praise;
        final TextView praiseNumber;
        final TextView address;
        final TextView longitude;
        final TextView langtitude;

        MyHolder(View itemView) {
            super(itemView);
            mediaOrImage = (ImageView) itemView.findViewById(R.id.mediaOrImage);
            praise = (ImageView) itemView.findViewById(R.id.praise);
            praiseNumber = (TextView) itemView.findViewById(R.id.praiseNumber);
            address = (TextView) itemView.findViewById(R.id.address);
            longitude = (TextView) itemView.findViewById(R.id.longitude);
            langtitude = (TextView) itemView.findViewById(R.id.langtitude);
        }
    }
}

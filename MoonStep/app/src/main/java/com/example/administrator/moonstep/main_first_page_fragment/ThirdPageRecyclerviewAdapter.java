package com.example.administrator.moonstep.main_first_page_fragment;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.moonstep.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThirdPageRecyclerviewAdapter extends RecyclerView.Adapter<ThirdPageRecyclerviewAdapter.MyHolder> {
    private final List<MoonFriend> mItems = new ArrayList<>();
    private Context mContext;
    private ThirdPageRecyclerviewAdapter.OnItemClickListener mOnItemClickListener;

    ThirdPageRecyclerviewAdapter(Context context){
        this.mContext = context;
    }

    public boolean add(MoonFriend item){
        boolean isAdded = mItems.add(item);
        if (isAdded){
            notifyDataSetChanged();
        }
        return isAdded;
    }

    boolean addAll(Collection<MoonFriend> items){
        boolean isAdded = mItems.addAll(items);
        if (isAdded){
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ThirdPageRecyclerviewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moon_friend_item, viewGroup, false);
        //是自定义ViewHolder持有我们的item
        return new ThirdPageRecyclerviewAdapter.MyHolder(view);
    }

    /**
     * 我们操作item
     itemView是底层的属性，表示当前ViewHolder持有的这个view本身
     * @param holder 当前view的持有者
     * @param position 当前view在items中的索引号
     */
    @Override
    public void onBindViewHolder(@NonNull final ThirdPageRecyclerviewAdapter.MyHolder holder, int position) {
        MoonFriend item = mItems.get(position);
        holder.userPhoto.setImageResource(item.getUserphoto());
        holder.userName.setText(item.getUserName());
        holder.userInfo.setText(item.getUserInfo());
        if (item.getUserGender() == 0){
            holder.userGender.setImageResource(R.mipmap.female_gender);
        }else{
            holder.userGender.setImageResource(R.mipmap.man_gender);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","调用了Adapter中的itemView.setOnClickListener");
                JumpToChattingActivity(view);
            }
        });
    }

    private void JumpToChattingActivity(View view){
        Intent intent = new Intent(view.getContext(), ChattingActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public ThirdPageRecyclerviewAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    void setOnItemClickListener(ThirdPageRecyclerviewAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    MoonFriend getModelByPos(int pos){
        return mItems.get(pos);
    }

    interface OnItemClickListener {
        void onItemClicked(int pos, View view);
    }

    /**
     * 这个viewHolder用来初始化控件
     * ViewHolder本身是一个item的持有器，这样的话，只要存过的内容就不需要从布局中往出拿了
     * 直接从ViewHolder中往出拿就好，就像是一个缓存一样。
     * ViewHolder这个类只有属性，没有方法，它要做的事情就是存我们的item的find出的id
     */
    class MyHolder extends RecyclerView.ViewHolder{
        final ImageView userPhoto;
        final ImageView userGender;
        final TextView userName;
        final TextView userInfo;
        MyHolder(View itemView){
            super(itemView);
            userPhoto = itemView.findViewById(R.id.user_photo);
            userGender = itemView.findViewById(R.id.user_gender);
            userName = itemView.findViewById(R.id.user_name);
            userInfo = itemView.findViewById(R.id.user_info);
        }
    }
}

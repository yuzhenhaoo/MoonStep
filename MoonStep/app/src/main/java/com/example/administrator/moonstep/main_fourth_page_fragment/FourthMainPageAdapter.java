package com.example.administrator.moonstep.main_fourth_page_fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.moonstep.R;
import com.example.administrator.moonstep.custom_textView.CustomTextView;
import com.example.administrator.moonstep.main_third_page_fragment.MoonTitleModel;
import com.example.administrator.moonstep.main_third_page_fragment.ThirdMainPageRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FourthMainPageAdapter extends RecyclerView.Adapter<FourthMainPageAdapter.MyHolder> {
    private final List<TaskInfo> mItems = new ArrayList<>();
    private Context mContext;
    private FourthMainPageAdapter.OnItemClickListener mOnItemClickListener;

    FourthMainPageAdapter(Context context){
        this.mContext = context;
    }

    public boolean add(TaskInfo item){
        boolean isAdded = mItems.add(item);
        if (isAdded){
            notifyDataSetChanged();
        }
        return isAdded;
    }

    boolean addAll(Collection<TaskInfo> items){
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
    public FourthMainPageAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fg_main_fourth_recyclerview_item, viewGroup, false);
        //是自定义ViewHolder持有我们的item
        return new FourthMainPageAdapter.MyHolder(view);
    }

    /**
     * 我们操作item
     itemView是底层的属性，表示当前ViewHolder持有的这个view本身
     * @param holder 当前view的持有者
     * @param position 当前view在items中的索引号
     */
    @Override
    public void onBindViewHolder(@NonNull final FourthMainPageAdapter.MyHolder holder, int position) {
        TaskInfo item = mItems.get(position);
        holder.taskName.setText(item.getTaskName());
        holder.taskLevel.setText(item.getTaskLevel());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("TAG","调用了Adapter中的itemView.setOnClickListener");
//                if (mOnItemClickListener != null) {
//                    mOnItemClickListener.onItemClicked(holder.getAdapterPosition(), holder.iv);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public FourthMainPageAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    void setOnItemClickListener(FourthMainPageAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    TaskInfo getModelByPos(int pos){
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
        final CustomTextView taskName;
        final CustomTextView taskLevel;
        MyHolder(View itemView){
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            taskLevel = itemView.findViewById(R.id.task_level);
        }
    }
}

package priv.zxy.moonstep.moonstep_palace;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.kernel_data.bean.User;
import priv.zxy.moonstep.moonstep_palace.moon_friend.view.ChattingActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ThirdPageRecyclerviewAdapter extends RecyclerView.Adapter<ThirdPageRecyclerviewAdapter.MyHolder> {
    private final List<MoonFriend> mItems = new ArrayList<>();
    private Context mContext;
    private ThirdPageRecyclerviewAdapter.OnItemClickListener mOnItemClickListener;

    public ThirdPageRecyclerviewAdapter(Context context){
        this.mContext = context;
    }

    public boolean add(MoonFriend item){
        boolean isAdded = mItems.add(item);
        if (isAdded){
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public boolean addAll(Collection<MoonFriend> items){
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
        Log.d("TAG","onCreateViewHolder执行了");
        //是自定义ViewHolder持有我们的item
        return new ThirdPageRecyclerviewAdapter.MyHolder(view);
    }

    /**
     * onbindViewHolder负责将每个子项绑定数据
     * 我们操作item
     itemView是底层的属性，表示当前ViewHolder持有的这个view本身
     * @param holder 当前view的持有者
     * @param position 当前view在items中的索引号
     */
    @Override
    public void onBindViewHolder(@NonNull final ThirdPageRecyclerviewAdapter.MyHolder holder, int position) {
        final MoonFriend item = mItems.get(position);
//        holder.userPhoto.setImageResource(item.getHeadPortrait());
        //当item 取了上述表达式的时候，会有两种情况，一种是当position为0时有值，第二种就是position为0时没有值，这个时候如果用户点击过快就会出现空指针异常
        if(item != null){
//            holder.userPhoto.setImageBitmap(BitmapFactory.decodeByteArray(item.getHeadPortrait(), 0, item.getHeadPortrait().length));//这才是设置头像的正确方式
            holder.userPhoto.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.my_photo));//设置头像
            holder.userNickName.setText(item.getNickName());//设置昵称
            holder.race.setText(item.getRace());//设置种族
            holder.level.setText(item.getLevel());//设置等阶
            if (item.getGender().equals("女")){ // 设置性别
                holder.userGender.setImageResource(R.mipmap.female_gender);
            }else{
                holder.userGender.setImageResource(R.mipmap.man_gender);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("TAG","调用了Adapter中的itemView.setOnClickListener");
                    JumpToChattingActivity(view, item);
                }
            });
        }
    }

    private void JumpToChattingActivity(View view, MoonFriend item){
        Intent intent = new Intent(view.getContext(), ChattingActivity.class);
//        intent.putExtra("headPortrait", item.getHeadPortrait());//暂时还不知道Bitmap怎么通过Activity进行传输
        intent.putExtra("phoneNumber", item.getPhoneNumber());
        intent.putExtra("userNickName", item.getNickName());
        intent.putExtra("race", item.getRace());
        intent.putExtra("level", item.getLevel());
        intent.putExtra("userGender", item.getGender());
        intent.putExtra("pet", item.getPet());
        intent.putExtra("signature", item.getSignature());
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
        final TextView userNickName;
        final TextView race;
        final TextView level;
        final TextView isOnline;//檢測好友的狀態，看是幾秒之前登陸的
        MyHolder(View itemView){
            super(itemView);
            userPhoto = itemView.findViewById(R.id.user_photo);
            userGender = itemView.findViewById(R.id.user_gender);
            userNickName = itemView.findViewById(R.id.user_name);
            race = itemView.findViewById(R.id.user_race);
            level = itemView.findViewById(R.id.user_level);
            isOnline = itemView.findViewById(R.id.user_isOnLine);
        }
    }
}

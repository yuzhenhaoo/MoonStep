package priv.zxy.moonstep.commerce.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import priv.zxy.moonstep.EM.bean.VolleyCallback;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.BubbleView;
import priv.zxy.moonstep.db.MoonFriend;
import priv.zxy.moonstep.commerce.view.ChattingActivity;
import priv.zxy.moonstep.helper.EMHelper;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.bean.ErrorCode;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 我需要在该adapter中判断用户是否在线，方法就是利用环信的Rest进行网络数据的请求
 */
public class MoonFriendAdapter extends RecyclerView.Adapter<MoonFriendAdapter.MyHolder> {
    private static final String TAG = "MoonFriendAdapter";

    private final List<MoonFriend> mItems = new ArrayList<>();

    private Context mContext;

    private int[] bubbleColor =
            {Color.parseColor("#bce672"),
                    Color.parseColor("#ff7500"),
                    Color.parseColor("#00e09e"),
                    Color.parseColor("#bce672"),
                    Color.parseColor("#bce672"),
                    Color.parseColor("#c32136"),
                    Color.parseColor("#44cef6"),
                    Color.parseColor("#4b5cc4")};

    public MoonFriendAdapter(Context context) {
        this.mContext = context;
    }

    public boolean add(MoonFriend item) {
        boolean isAdded = mItems.add(item);
        if (isAdded) {
            notifyDataSetChanged();
        }
        return isAdded;
    }

    public boolean addAll(Collection<MoonFriend> items) {
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

    @NonNull
    @Override
    public MoonFriendAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.moon_friend_item, viewGroup, false);
        //是自定义ViewHolder持有我们的item
        return new MoonFriendAdapter.MyHolder(view);
    }

    /**
     * onbindViewHolder负责将每个子项绑定数据
     * 我们操作item
     * itemView是底层的属性，表示当前ViewHolder持有的这个view本身
     *
     * @param holder   当前view的持有者
     * @param position 当前view在items中的索引号
     */
    @Override
    public void onBindViewHolder(@NonNull final MoonFriendAdapter.MyHolder holder, int position) {
        final MoonFriend item = mItems.get(position);
        Log.d(TAG,"运行到了这里");
        Glide.with(mContext).load(item.getHeadPortraitPath()).into(holder.userPhoto);//加载用户头像
        holder.userNickName.setText(item.getNickName());//设置昵称
        holder.race.setText(item.getRaceName());//设置种族
        holder.level.setText(item.getLevelName());//设置等阶
        if (item.getGender().equals("女")) { // 设置性别
            holder.userGender.setImageResource(R.mipmap.female_gender);
        } else {
            holder.userGender.setImageResource(R.mipmap.man_gender);
        }

        holder.itemView.setOnClickListener(view -> {
            JumpToChattingActivity(view, item);
            SharedPreferencesUtil.getInstance(mContext).handleMessageTip(item.getPhoneNumber());
        });

        EMHelper.getInstance(Application.getContext()).getUserState(new VolleyCallback() {
            @Override
            public String onSuccess(String result) {
                if (result.equals("offline")) {
                    holder.isOnline.setText("离线");
                } else if (result.equals("online")) {
                    holder.isOnline.setText("在线");
                }
                return null;
            }

            @Override
            public boolean onSuccess() {
                return false;
            }

            @Override
            public String onFail(String error) {
                return null;
            }

            @Override
            public boolean onFail() {
                return false;
            }

            @Override
            public void getMoonFriend(MoonFriend moonFriend) {

            }

            @Override
            public void getErrorCode(ErrorCode errorCode) {

            }
        }, "moonstep" + item.getPhoneNumber());
        Random rd = new Random();
        holder.bubbleView.setBubbleColor(bubbleColor[rd.nextInt(bubbleColor.length - 1)]);//通过随机数设定气泡的颜色【有bug】
        int messageNumber = SharedPreferencesUtil.getInstance(mContext).readMessageNumber(item.getPhoneNumber());
//            holder.bubbleView.setTextColor();//这里可以用来设置文字的颜色
        if (messageNumber != 0) {
            holder.bubbleView.setVisibility(View.VISIBLE);
            holder.bubbleView.setNumber(messageNumber);
        } else {
            holder.bubbleView.setVisibility(View.GONE);
        }
    }

    private void JumpToChattingActivity(View view, MoonFriend item) {
        Intent intent = new Intent(view.getContext(), ChattingActivity.class);
//        intent.putExtra("headPortrait", item.getHeadPortrait());//暂时还不知道Bitmap怎么通过Activity进行传输
        intent.putExtra("moonfriend", item);
        view.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "mItems的长度为:"+mItems.size());
        return mItems.size();
    }

    /**
     * 这个viewHolder用来初始化控件
     * ViewHolder本身是一个item的持有器，这样的话，只要存过的内容就不需要从布局中往出拿了
     * 直接从ViewHolder中往出拿就好，就像是一个缓存一样。
     * ViewHolder这个类只有属性，没有方法，它要做的事情就是存我们的item的find出的id
     */
    class MyHolder extends RecyclerView.ViewHolder {
        final ImageView userPhoto;
        final ImageView userGender;
        final TextView userNickName;
        final TextView race;
        final TextView level;
        final TextView isOnline;//检测好友是否在线
        final TextView lastTime;//最后一次更新的时间，可能性是两种：我最后一次发消息的时间/对方最后一次发消息的时间
        final BubbleView bubbleView; //气泡

        MyHolder(View itemView) {
            super(itemView);
            userPhoto = itemView.findViewById(R.id.userPhoto);
            userGender = itemView.findViewById(R.id.userGender);
            userNickName = itemView.findViewById(R.id.userName);
            race = itemView.findViewById(R.id.userRace);
            level = itemView.findViewById(R.id.userLevel);
            isOnline = itemView.findViewById(R.id.userIsOnLine);
            lastTime = itemView.findViewById(R.id.lastTime);
            bubbleView = itemView.findViewById(R.id.bubbleView);
        }
    }
}

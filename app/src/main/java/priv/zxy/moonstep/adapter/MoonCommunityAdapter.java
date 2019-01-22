package priv.zxy.moonstep.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.application.Application;
import priv.zxy.moonstep.commerce.view.Community.BaseCommunityMessage;
import priv.zxy.moonstep.framework.user.User;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/28
 * 描述: 社区消息的适配器
 **/

public class MoonCommunityAdapter extends RecyclerView.Adapter<MoonCommunityAdapter.MyHolder> {

    private final List<BaseCommunityMessage> mItems = new ArrayList<>();
    private View view;//子类item

    private Context mContext;

    private PopupMenu.OnMenuItemClickListener listener = item -> {
        switch (item.getItemId()) {
            case R.id.blocking:
                break;
            case R.id.using_magic:
                break;
            case R.id.using_prop:
                break;
            default:
        }
        return false;
    };

    public MoonCommunityAdapter(Context context){
        mContext = context;
    }

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
        User user = cb.getUser();
        Glide.with(Application.getContext()).load(user.getHeadPath()).into(myHolder.userPhoto);
        myHolder.nickName.setText(user.getNickName());
        myHolder.level.setText(user.getLevel());
        // FIXME (张晓翼， 2018/12/31， 种族编码没有匹配成功
        myHolder.race.setText(String.valueOf(user.getRaceCode()));
        myHolder.content.setText(cb.getContent());
        myHolder.showTime.setText(cb.getShowTime());
        myHolder.praiseNumber.setText(cb.getPraiseNumber());
        Glide.with(Application.getContext()).load(cb.getMediaPath()).into(myHolder.mediaOrImage);
        myHolder.address.setText(cb.getAddress());
        myHolder.latitude.setText(cb.getLatitude());
        myHolder.longitude.setText(cb.getLongitude());

        myHolder.choice.setOnClickListener(v -> {
            // 创建弹出式菜单对象
            PopupMenu popupMenu = new PopupMenu(mContext, myHolder.choice);
            // 获取菜单填充器
            MenuInflater inflater = popupMenu.getMenuInflater();
            // 填充菜单
            inflater.inflate(R.menu.community_pop_up_menu, popupMenu.getMenu());
            // 绑定菜单项的点击事件
            popupMenu.setOnMenuItemClickListener(listener);
            popupMenu.show();
        });
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
        final CircleImageView userPhoto;
        final TextView nickName;
        final TextView level;
        final TextView race;
        final TextView content;
        final TextView showTime;
        final ImageView mediaOrImage;
        final Button praise;
        final TextView praiseNumber;
        final TextView address;
        final TextView longitude;
        final TextView latitude;
        final Button choice;

        MyHolder(View itemView) {
            super(itemView);
            userPhoto = (CircleImageView) itemView.findViewById(R.id.user_image);
            nickName = (TextView) itemView.findViewById(R.id.nick_name);
            level = (TextView) itemView.findViewById(R.id.level);
            race = (TextView) itemView.findViewById(R.id.race);
            content = (TextView) itemView.findViewById(R.id.description);
            showTime = (TextView) itemView.findViewById(R.id.show_time);
            choice = (Button) itemView.findViewById(R.id.choice);
            mediaOrImage = (ImageView) itemView.findViewById(R.id.show_image);
            praise = (Button) itemView.findViewById(R.id.praise_bt);
            praiseNumber = (TextView) itemView.findViewById(R.id.praiseNumber);
            address = (TextView) itemView.findViewById(R.id.address);
            longitude = (TextView) itemView.findViewById(R.id.longitude);
            latitude = (TextView) itemView.findViewById(R.id.latitude);
        }
    }
}

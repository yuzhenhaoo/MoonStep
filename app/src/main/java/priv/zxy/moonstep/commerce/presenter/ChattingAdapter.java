package priv.zxy.moonstep.commerce.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.db.Message;

public class ChattingAdapter  extends RecyclerView.Adapter<ChattingAdapter.MyHolder> {
    private Context context;
    private List<Message> lists = new ArrayList<Message>();

    public interface IMsgViewType {
        int IMVT_COM_MSG = 0;// 收到对方的消息
        int IMVT_TO_MSG = 1;// 自己发送出去的消息
    }

    public ChattingAdapter(Context context){
        this.context = context;
    }

    public boolean add(Message entity){
        boolean success = lists.add(entity);
        if(success){
            notifyDataSetChanged();
        }
        return success;
    }

    public boolean addAll(List<Message> list_items){
        boolean success = lists.addAll(list_items);
        if(success){
            notifyDataSetChanged();
        }
        return success;
    }

    public void clear(){
        this.lists.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(lists.get(position).getDirection() == 1){//当前消息发送的方向为我所发送
            return IMsgViewType.IMVT_TO_MSG;
        }else{//当前消息发送的方向是对方所发送
            return IMsgViewType.IMVT_COM_MSG;
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @NonNull
    @Override
    public ChattingAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = null;
        if(viewType == IMsgViewType.IMVT_TO_MSG){
            Log.d("TAG","发送消息");
            view = LayoutInflater.from(context).inflate(R.layout.fg_main_fifth_item_right, viewGroup, false);
        }else if(viewType == IMsgViewType.IMVT_COM_MSG){
            Log.d("TAG","接收消息");
            view = LayoutInflater.from(context).inflate(R.layout.fg_main_fifth_item_left, viewGroup, false);
        }
        return new ChattingAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChattingAdapter.MyHolder viewHolder, int i) {
        Message entity = lists.get(i);
        viewHolder.show_words.setText(entity.getContent());//利用当前ViewHolder设置内容
    }

    class MyHolder extends RecyclerView.ViewHolder{
        final TextView show_words;
        MyHolder(View itemView){
            super(itemView);
            show_words = itemView.findViewById(R.id.show_words);
        }
    }
}

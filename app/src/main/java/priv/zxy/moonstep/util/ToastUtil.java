package priv.zxy.moonstep.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import priv.zxy.moonstep.R;

public class ToastUtil {

    private Context mContext;

    private static ToastUtil instance;

    private Activity mActivity;

    private ToastUtil(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = activity;
    }

    public static ToastUtil getInstance(Context context, Activity activity) {
        if (instance == null) {
            synchronized (ToastUtil.class) {
                if (instance == null) {
                    instance = new ToastUtil(context, activity);
                }
            }
        }
        return instance;
    }

    public void showToast(String content){
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, null);
        ImageView imageView = layout.findViewById(R.id.image);
        TextView textView = layout.findViewById(R.id.textview);
        textView.setText(content);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}

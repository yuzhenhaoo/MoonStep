package priv.zxy.moonstep.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.AbstractAdapter;
import priv.zxy.moonstep.framework.good.Props;
import priv.zxy.moonstep.framework.good.bean.Good;
import priv.zxy.moonstep.framework.user.User;
import priv.zxy.moonstep.framework.user.UserSelfInfo;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/5
 * 描述:用在月友界面的背包dialog
 **/
// TODO (GridView不显示的改进措施：我们可以在程序刚加载的时候就采用备忘录模式构建一个物品保存类，用来得到用户的所有物品数据并将其加载到内存中，当我们需要访问内存数据的时候，就直接像UserSelfInfo一样获得其引用即可。)
public class PackDialog extends Dialog {

    private Context mContext;
    private Callback callback = null;

    private ImageView chooseImage;
    private GridView packView;
    private List<Good> goods = null;
    private MyHandler myHandler;
    private AbstractAdapter<Good> mAbstractAdapter;

    public PackDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext,R.layout.dialog_pack,null);
        setContentView(view);
        init();
        initData();
    }

    public void setClickListener(Callback callback) {
        this.callback = callback;
    }

    private void init() {
        myHandler = new MyHandler(this);

        Button cancelBt = findViewById(R.id.cancel);
        Button sureBt = findViewById(R.id.sure);
        chooseImage = findViewById(R.id.be_placed_item);
        packView = findViewById(R.id.pack);

        /*
         * 给确认和取消的按钮同时设置监听
         */
        sureBt.setOnClickListener(new ClickListener());
        cancelBt.setOnClickListener(new ClickListener());

        //TODO(你必须在这里就开始用适配器加载数据）

        Window dialogWindow = getWindow();
        if (dialogWindow == null) {
            throw new RuntimeException("dialog获得视窗失败，请重新尝试");
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 获取屏幕宽、高用
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        // 宽度设置为屏幕的0.8
        lp.width = (int) (d.widthPixels * 0.9);
        dialogWindow.setAttributes(lp);
    }

    /**
     * 用来判断当前选择框里是否有物品
     *
     * @return 是就返回true，不是就返回false
     */
    private boolean isGoodInChooseFramework() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Drawable drawable = chooseImage.getForeground();
            return drawable != null;
        }
        Toast.makeText(mContext, "您当前的api等级小于23，不能使用该功能，请升级系统版本", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 清空选择框中的物品
     */
    public void doClear() {
        if (isGoodInChooseFramework()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                chooseImage.setForeground(null);
            } else {
                Toast.makeText(mContext, "您当前的api等级小于23，不能使用该功能，请升级系统版本", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        dismiss();
    }

    /**
     * 点击确定后，提交物品到提交栏中
     */
    public void doSure() {
        if (!isGoodInChooseFramework()) {
            Toast.makeText(mContext, "您还未选择物品呢！", Toast.LENGTH_SHORT).show();
            return;
        }
        dismiss();
    }

    /**
     * 初始化用户的背包中物品的数据
     */
    private void initData() {
        Props props = new Props();
        User user = UserSelfInfo.getInstance().getMySelf();

        props.getUserGoods(gs -> {
            goods = gs;
            mAbstractAdapter = new AbstractAdapter<Good>(goods, R.layout.pack_item) {
                @Override
                public void bindView(ViewHolder holder, Good obj) {
                    holder.setImageResource(R.id.itemSrc, obj.getGoodImagePath());
                    holder.setText(R.id.itemNumber, String.valueOf(obj.getNumber()));
                }
            };
            myHandler.sendEmptyMessage(0x01);
        }, user.getPhoneNumber());

    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.sure:
                    callback.onSure();
                    break;
                case R.id.cancel:
                    callback.onCancel();
                    break;
                default:
                    break;
            }
        }
    }

    public interface Callback {
        void onSure();

        void onCancel();
    }

    private static class MyHandler extends Handler{

        /**
         * 创建弱引用
         * 拓展一下：
         * 强引用就是通常new出来的引用
         * 软引用在GC发现内存不足的时候就会从堆中回收
         * 而弱引用在GC发现对象不再使用的时候从堆中回收
         * 而虚引用的话任何时候都有可能会被回收
         */
        private WeakReference<PackDialog> weakReference;

        MyHandler(PackDialog dialog) {
            weakReference = new WeakReference<>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PackDialog packDialog = weakReference.get();
            packDialog.packView.setAdapter(packDialog.mAbstractAdapter);
        }
    }
}

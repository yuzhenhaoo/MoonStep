package priv.zxy.moonstep.customview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.adapter.AbstractAdapter;
import priv.zxy.moonstep.framework.good.GoodSelfInfo;
import priv.zxy.moonstep.framework.good.bean.Good;

/**
 * 创建人: Administrator
 * 创建时间: 2019/1/5
 * 描述:用在月友界面的背包dialog
 **/
public class PackDialog extends Dialog {

    private Context mContext;
    private Callback callback;

    private ImageView chooseImage;
    private GridView packView;

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
    }

    public void setClickListener(Callback callback) {
        this.callback = callback;
    }

    private void init() {
        Button cancelBt = findViewById(R.id.cancel);
        Button sureBt = findViewById(R.id.sure);
        chooseImage = findViewById(R.id.be_placed_item);
        packView = findViewById(R.id.pack);
        AbstractAdapter<Good> mAbstractAdapter = new AbstractAdapter<Good>(GoodSelfInfo.getInstance().getGoods(), R.layout.pack_item) {
            @Override
            public void bindView(ViewHolder holder, Good obj) {
                holder.setImageResource(R.id.itemSrc, obj.getGoodImagePath());
                holder.setText(R.id.itemNumber, String.valueOf(obj.getNumber()));
            }
        };
        packView.setAdapter(mAbstractAdapter);
        packView.setOnItemClickListener((parent, view, position, id) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String goodPath = GoodSelfInfo.getInstance().getGoods().get(position).getGoodImagePath();
                Glide.with(mContext).load(goodPath).into(chooseImage);
                chooseImage.invalidate();
                return;
            }
            Toast.makeText(mContext, "您的API版本低于23，不能使用本功能，请升级版本后重新尝试", Toast.LENGTH_SHORT).show();
        });

        /*
         * 给确认和取消的按钮同时设置监听
         */
        sureBt.setOnClickListener(new ClickListener());
        cancelBt.setOnClickListener(new ClickListener());

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

    public View getChooseView() {
        return chooseImage;
    }

    /**
     * 清空选择框中的物品
     */
    public void doClear() {
        if (isGoodInChooseFramework()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                chooseImage.setImageDrawable(null);
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
     * 用来判断当前选择框里是否有物品
     *
     * @return 是就返回true，不是就返回false
     */
    private boolean isGoodInChooseFramework() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Drawable drawable = chooseImage.getDrawable();
            return drawable != null;
        }
        Toast.makeText(mContext, "您当前的api等级小于23，不能使用该功能，请升级系统版本", Toast.LENGTH_SHORT).show();
        return false;
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
}

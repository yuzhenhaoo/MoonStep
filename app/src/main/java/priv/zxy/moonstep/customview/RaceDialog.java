package priv.zxy.moonstep.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.data.application.Application;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/13
 * 描述:用来在用户创建了账户后显示当前种族信息
 **/
public class RaceDialog extends Dialog {

    private ImageView raceHeadImageIV;

    private TextView raceNameTV;

    private ImageView raceIconIV;

    private ImageView luckyIconIV;

    private TextView raceDescriptionTV;

    private String raceHeadPath;

    private String raceName;

    private String raceIconPath;

    private String luckyIconPath;

    private String raceDescription;

    private Button click;

    private onClickListener onClickListener = null;

    public RaceDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    public RaceDialog(Context context, String raceName, String raceDescription, String raceHeadPath, String raceIconPath){
        super(context, R.style.MyDialog);
        this.raceHeadPath = raceHeadPath;
        this.raceIconPath = raceIconPath;
        this.raceName = raceName;
        this.raceDescription = raceDescription;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_race);

        setCanceledOnTouchOutside(false);//按空白处不能取消动画

        //初始化界面空间
        initView();

        //初始化界面数据
        initData();

        //初始化界面控件的事件
        initEvent();
    }

    private void initView() {
        raceHeadImageIV = (ImageView) findViewById(R.id.raceHeadImage);
        raceNameTV = (TextView) findViewById(R.id.raceName);
        raceIconIV = (ImageView) findViewById(R.id.raceIcon);
        luckyIconIV = (ImageView) findViewById(R.id.luckyIcon);
        raceDescriptionTV = (TextView) findViewById(R.id.raceDescription);
        click = (Button) findViewById(R.id.click);
    }

    private void initData(){
        Glide.with(Application.getContext()).load(raceHeadPath).into(raceHeadImageIV);
        Glide.with(Application.getContext()).load(raceIconPath).into(raceIconIV);
        raceNameTV.setText(raceName);
        raceDescriptionTV.setText(raceDescription);
    }

    private void initEvent(){
        click.setOnClickListener(v -> {
            if (onClickListener != null){
                onClickListener.onClick();
            }
        });
    }

    public void setOnClickListener(onClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface onClickListener{
        void onClick();
    }
}

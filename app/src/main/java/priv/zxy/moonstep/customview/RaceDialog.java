package priv.zxy.moonstep.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import priv.zxy.moonstep.R;

/**
 * 调用的方法：（写在onClick里面）
 *                 final MyDialog myDialog = new MyDialog(Activity.this);
                    myDialog.setOnClickListener(new MyDialog.onClickListener() {
                    @Override
                    public void onClick() {
                        Toast.makeText(Activity.this, "恭喜你已经成功", Toast.LENGTH_SHORT).show();
                        myDialog.dismiss();//让dialog消失
                    }
                    });
                    myDialog.show();//让dialog显示
 */

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/13
 * 描述:用来在用户创建了账户后显示当前种族信息
 **/
public class RaceDialog extends Dialog {

    private ImageView raceHeadImage;

    private TextView raceName;

    private ImageView raceIcon;

    private ImageView luckyIconIV;

    private TextView raceDescription;

    private Button click;

    private onClickListener onClickListener = null;

    public RaceDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        setCanceledOnTouchOutside(false);//按空白处不能取消动画

        //初始化界面空间
        initView();

        //初始化界面数据
        initData();

        //初始化界面控件的事件
        initEvent();
    }

    private void initView() {
        raceHeadImage = (ImageView) findViewById(R.id.raceHeadImage);
        raceName = (TextView) findViewById(R.id.raceName);
        raceIcon = (ImageView) findViewById(R.id.raceIcon);
        luckyIconIV = (ImageView) findViewById(R.id.luckyIcon);
        raceDescription = (TextView) findViewById(R.id.raceDescription);
        click = (Button) findViewById(R.id.click);
    }

    private void initData(){

    }

    private void initEvent(){
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null){
                    onClickListener.onClick();
                }
            }
        });
    }

    public void setOnClickListener(onClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public interface onClickListener{
        public void onClick();
    }
}

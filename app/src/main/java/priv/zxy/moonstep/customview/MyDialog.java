package priv.zxy.moonstep.customview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.utils.LogUtil;

public class MyDialog extends Dialog {

    private TextView title;
    private TextView content;
    private Button negativeButton;
    private Button positiveButton;
    private String titletxt;
    private String contenttxt;
    private String negativetxt;
    private String positivetxt;

    /*---------------------接口监听----------------------*/
    private onPostiveClickListener onpostiveClickListener;
    private onNegativeClickListener onnegativeClickListener;

    public interface onPostiveClickListener{
        void onPositiveClick();
    }

    public interface onNegativeClickListener{
        void onNegativeClick();
    }

    public void setPositiveClickLister(String str, onPostiveClickListener onpostiveClickListener){
        if(str != null){
            this.positivetxt = str;
            this.onpostiveClickListener = onpostiveClickListener;
        }
    }

    public void setNegativeClickLister(String str, onNegativeClickListener onnegativeClickListener){
        if(str != null){
            this.negativetxt = str;
            this.onnegativeClickListener = onnegativeClickListener;
        }
    }
    /*----------------------事件处理-----------------------*/
    public MyDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        //按空白处不能取消动画
//        setCanceledOnTouchOutside(false);

        initView();

        initData();

        initEvent();
    }

    private void initView() {
        LogUtil.i("TAG","initView");
        title = (TextView) findViewById(R.id.title);
        content = (TextView) findViewById(R.id.content);
        negativeButton = (Button) findViewById(R.id.negativeButton);
        positiveButton = (Button) findViewById(R.id.positiveButton);
    }

    /**
     * 初始化控件的显示数据
     */
    private void initData(){
        LogUtil.i("TAG","initData");
        LogUtil.i("TAG",titletxt);
        LogUtil.i("TAG",contenttxt);
        //如果用户自定义了title和message
        if(titletxt != null){
            title.setText(titletxt);
        }
        if (contenttxt != null){
            content.setText(contenttxt);
        }
        //设置按钮上的文字
        if(positivetxt != null){
            positiveButton.setText(positivetxt);
        }
        if(negativetxt != null){
            negativeButton.setText(negativetxt);
        }
    }

    /**
     * 初始化界面的确定与取消监听器
     * 向外提供监听
     */
    @SuppressLint("ClickableViewAccessibility")
    private void initEvent(){
        LogUtil.i("TAG","initEvent");
        positiveButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        positiveButton.setTextSize(19);
                        if(onpostiveClickListener != null){
                            onpostiveClickListener.onPositiveClick();//调用接口中的方法
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        positiveButton.setTextSize(18);
                        break;
                }
                return true;
            }
        });
        negativeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        negativeButton.setTextSize(19);
                        if(onnegativeClickListener != null){
                            onnegativeClickListener.onNegativeClick();//调用接口中的方法
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        negativeButton.setTextSize(18);
                        break;
                }
                return true;
            }
        });
//        positiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                positiveButton.setTextSize(19);
//                if(onpostiveClickListener != null){
//                    onpostiveClickListener.onPositiveClick();//调用接口中的方法
//                }
//            }
//        });

//        negativeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(onnegativeClickListener != null){
//                    onnegativeClickListener.onNegativeClick();
//                }
//            }
//        });
    }

    /*--------------------setter方法传值---------------------------------*/
    public void setTitle(String myTitle){
        this.titletxt = myTitle;
    }

    public void setContent(String mContent){
        this.contenttxt = mContent;
    }
}

package priv.zxy.moonstep.start_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.TitanicTextView.Titanic;
import priv.zxy.moonstep.TitanicTextView.TitanicTextView;
import priv.zxy.moonstep.custom_textView.FontCache;
import priv.zxy.moonstep.login.view.UserLoginActivity;

public class StartActivity extends AppCompatActivity {

    private TitanicTextView titanicTextView;
    private Button bt;
    private boolean isStarted = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        initView();
    }

    public void initView(){
        final MyThread mThread = new MyThread();
        String MoonStep = "圆月行";
        bt = this.findViewById(R.id.clickJump);
        titanicTextView = this.findViewById(R.id.titanicTextView);
//        titanicTextView.setTypeface(Typefaces.get(this, "/fonts/font_style0.ttf"));
        titanicTextView.setTypeface(FontCache.getTypeface("/fonts/font_style1.ttf", this.getApplicationContext()));
        titanicTextView.setText(MoonStep);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jump_to_login_page();
                isStarted = true;
            }
        });
        new Titanic().start(titanicTextView);
        mThread.start();
    }

    class MyThread extends Thread{
        public void run(){
            try{
                Thread.sleep(10000);
            }catch (InterruptedException e){
                Log.i("TAG","终止信息为:"+e.getMessage());
            }
            if(!isStarted)
                jump_to_login_page();
        }
    }

    public  void jump_to_login_page(){
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
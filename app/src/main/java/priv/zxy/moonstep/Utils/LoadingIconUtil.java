package priv.zxy.moonstep.Utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;

public class LoadingIconUtil extends View {

    private int height, width;
    private long duration = 3000;
    int startAngle = 180;
    int stroke;
    int spacing;
    boolean strokeChanged = false;
    boolean spacingChanged = false;
    private boolean inverted = false;
    private BaseInterpolator interpolator = new LinearInterpolator();
    String color[] = {
            "#06BDC7",
            "#1E80C6",
            "#0E40E3",
            "#0404A7",
            "#6910B0",
            "#7E0F89",
            "#DF09E3",
            "#D317A8",
            "#E8136B",
            "#DA0124",
            "#A30200",
            "#F96514",
            "#EFC218",
            "#C0D237",
            "#64E53D",
            "#1AE617",
            "#18F15B",
            "#0AF3A3",
            "#38DCC2"
    };
    String bgColor = "#000000";
    Paint bgPaint;
    int lineCount = 19;
    RectF[] mOval = new RectF[lineCount];
    Paint[] paint = new Paint[lineCount];
    int[] angles = new int[lineCount];


    public LoadingIconUtil(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < lineCount; i++) {
            canvas.drawArc(mOval[i], startAngle, angles[i], false, paint[i]);
            canvas.drawArc(mOval[i], startAngle, -angles[i], false, paint[i]);
        }
    }


    private void startAnimation() {
        ValueAnimator anim = getValueAnimator();
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setInterpolator(interpolator);
        anim.setDuration(duration);
        anim.start();

    }

    ValueAnimator getValueAnimator(){
        final ValueAnimator vAnim = ValueAnimator.ofFloat(0, 1);
        vAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int j;
                for (int i = 0; i < lineCount; i++) {
                    if (inverted) j = i;
                    else j = lineCount - i;
                    int angle = Math.round(
                            (adjustFraction(animation.getAnimatedFraction() + (1f / lineCount * j)))
                                    * 360);
                    if (angle > 180) {
                        angle = 180 - (angle - 180);
                    }
                    angles[i] = angle;
                }

                invalidate();
            }

        });
        return vAnim;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        width = MeasureSpec.getSize(widthMeasureSpec);
        height =  MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        if (!strokeChanged)
            stroke = width / (lineCount * 4);
        if (!spacingChanged)
            spacing = stroke;

        setBackgroundColor(Color.parseColor(bgColor));
        int j =0;
        for (int i = 0; i < lineCount; i++) {
            if (j >= color.length) j = 0;
            paint[i] = new Paint();
            paint[i].setColor(Color.parseColor(color[j]));
            paint[i].setStyle(Paint.Style.STROKE);
            paint[i].setStrokeWidth(stroke);
            int fromEdge = stroke / 2 + stroke * i  + spacing * i;
            mOval[i] = new RectF(fromEdge, fromEdge, width - fromEdge, height - fromEdge);
            j++;
        }
        startAnimation();
    }






    private float adjustFraction(float fraction){
        if (fraction > 1f)
            return fraction - 1f;
        else return fraction;
    }

    public void setIconDuration(long duration){
        this.duration = duration;
    }
    public void setIconColor(String[] color){
        this.color = new String[color.length];
        this.color = color;
    }

    public void setIconInverted(boolean inverted){ this.inverted = inverted;}
    public void setIconBgColor(String color){ this.bgColor = color;}
    public void setIconStroke(int stroke){
        this.stroke = stroke;
        strokeChanged = true;
    }

    public void setIconLines(int lineCount){
        this.lineCount = lineCount;
        mOval = new RectF[lineCount];
        paint = new Paint[lineCount];
        angles = new int[lineCount];

    }

    public void setIconSpacing(int spacing){
        this.spacing = spacing;
        spacingChanged = true;

    }

    public void setIconInterpolator(BaseInterpolator interpolator){
        this.interpolator = interpolator;
    }



}
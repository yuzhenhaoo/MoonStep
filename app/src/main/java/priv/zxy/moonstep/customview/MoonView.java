package priv.zxy.moonstep.customview;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 创建人: LYJ
 * 创建时间: 2019/2/17
 * 描述:自定义View——太极圆
 **/
public class MoonView extends View {

    /**
     * View颜色
     */
    private int color1;
    private int color2;
    private int colorBg;

    /**
     * 需要的画笔
     */
    private Paint moonPaint;
    private Paint maskPaint;
    private Paint bgPaint;
    private Paint pointPaint;
    private Paint footprintPaint1;
    private Paint footprintPaint2;
    private Paint footprintPaint3;
    private Paint footprintPaint4;
    private Paint footprintPaint5;
    private Paint footprintPaint6;
    private Paint footprintPaint7;
    private Paint footprintPaint8;

    /**
     * 绘制参数
     */
    private int width;
    private int height;
    private int centerX;
    private int centerY;
    private int radius;
    private int maskCenterX;
    private int maskCenterY;

    private float animatedValue;
    private ValueAnimator valueAnimator1;
    private ValueAnimator valueAnimator2;
    private ValueAnimator valueAnimator3;
    private ValueAnimator valueAnimator4;
    private ValueAnimator valueAnimator5;
    private ValueAnimator valueAnimator6;
    private ValueAnimator valueAnimator7;
    private ValueAnimator valueAnimator8;

    private float[] points;
    private static final int POINT_NUM = 400;
    private static final int POINT_LIST_SIZE = 800;

    /**
     * 初始化标志
     */
    private boolean initFlag = false;

    public MoonView(Context context){
        super(context);
    }

    public MoonView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }

    /**
     * 初始化参数
     */
    private void init() {
        colorBg = Color.rgb(0, 0, 0);
        color1 = Color.rgb(255, 245, 247);
        color2 = Color.rgb(228, 228, 255);

        moonPaint = new Paint();

        maskPaint = new Paint();
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        maskPaint.setAntiAlias(true);
        maskPaint.setDither(true);

        pointPaint = new Paint();
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(Color.WHITE);

        footprintPaint1 = new Paint();
        footprintPaint2 = new Paint();
        footprintPaint3 = new Paint();
        footprintPaint4 = new Paint();
        footprintPaint5 = new Paint();
        footprintPaint6 = new Paint();
        footprintPaint7 = new Paint();
        footprintPaint8 = new Paint();
        initFootprintPaint(footprintPaint1);
        initFootprintPaint(footprintPaint2);
        initFootprintPaint(footprintPaint3);
        initFootprintPaint(footprintPaint4);
        initFootprintPaint(footprintPaint5);
        initFootprintPaint(footprintPaint6);
        initFootprintPaint(footprintPaint7);
        initFootprintPaint(footprintPaint8);

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(colorBg);
    }

    /**
     * 初始化参数(需要用到getWidth()、getHeight())
     */
    public void initVariable(){
        if(!initFlag){
            initFlag = true;
            width = getWidth();
            height = getHeight();
            centerX = width / 2;
            centerY = height / 2;
            radius = (centerX > centerY? centerY:centerX) / 3 * 2;
            RadialGradient radialGradient = new RadialGradient(centerX, centerY, radius, new int[]{color1, color2, colorBg}, new float[]{0.4f, 0.9f, 1f}, Shader.TileMode.CLAMP);
            moonPaint.setShader(radialGradient);
            moonPaint.setAntiAlias(true);
            moonPaint.setDither(true);

            initValueAnimator();
            initPoints();
        }
    }

    /**
     * 初始化动画控制器
     */
    private void initValueAnimator(){
        int duration = 5000, maxAlphaValue = 255;
        initFootprintValueAnimator(duration, maxAlphaValue);

        ValueAnimator animator = ValueAnimator.ofFloat(0, 3.14f);
        animator.setDuration(10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (Float)animation.getAnimatedValue();
                maskCenterX = centerX + (int)(radius * Math.sin(animatedValue));
                maskCenterY = centerY - radius + (int)(radius * Math.cos(animatedValue));
                invalidate();
            }
        });

        ValueAnimator controlAnimator = ValueAnimator.ofInt(1, 9);
        controlAnimator.setDuration(8000);
        controlAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                startFootprintValueAnimator(value);
                invalidate();
            }
        });

        animator.start();
        controlAnimator.start();
    }

    /**
     * 初始化脚印画笔
     */
    private void initFootprintPaint(Paint paint){
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(0, 228, 228, 255);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    /**
     * 初始化脚印值动画控制器
     */
    private void initFootprintValueAnimator(int duration, int maxAlphaValue){
        valueAnimator1 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator1.setDuration(duration);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint1.setAlpha(value);
            }
        });

        valueAnimator2 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator2.setDuration(duration);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint2.setAlpha(value);
                invalidate();
            }
        });

        valueAnimator3 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator3.setDuration(duration);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint3.setAlpha(value);
                invalidate();
            }
        });

        valueAnimator4 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator4.setDuration(duration);
        valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint4.setAlpha(value);
                invalidate();
            }
        });

        valueAnimator5 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator5.setDuration(duration);
        valueAnimator5.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint5.setAlpha(value);
                invalidate();
            }
        });

        valueAnimator6 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator6.setDuration(duration);
        valueAnimator6.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint6.setAlpha(value);
                invalidate();
            }
        });

        valueAnimator7 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator7.setDuration(duration);
        valueAnimator7.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint7.setAlpha(value);
                invalidate();
            }
        });

        valueAnimator8 = ValueAnimator.ofInt(0, maxAlphaValue, 0);
        valueAnimator8.setDuration(duration/2);
        valueAnimator8.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                footprintPaint8.setAlpha(value);
                invalidate();
            }
        });

    }

    /**
     * 控制脚印动画控制器开始
     */
    private void startFootprintValueAnimator(int value){
        if(value == 1){
            valueAnimator1.start();
        }
        else if(value == 2){
            valueAnimator2.start();
        }
        else if(value == 3){
            valueAnimator3.start();
        }
        else if(value == 4){
            valueAnimator4.start();
        }else if(value == 6){
            valueAnimator6.start();
        }else if(value == 7){
            valueAnimator7.start();
        }else if(value == 8){
            valueAnimator8.start();
        }
    }

    /**
     * 初始化点数组
     */
    private void initPoints(){
        List<Float> pointsList = new ArrayList<>();
        points = new float[POINT_LIST_SIZE];
        Random random = new Random();
        int i;
        float pointX, pointY;
        pointsList.clear();
        for(i = 0; i < POINT_NUM; i++){
            pointX = random.nextInt(width);
            pointY = random.nextInt(height);
            pointsList.add(pointX);
            pointsList.add(pointY);
        }
        for(i = 0; i < POINT_LIST_SIZE; i++){
            points[i] = pointsList.get(i);
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initVariable();
        drawBg(canvas);
        drawFootprints(canvas);
        drawMoon(canvas);
        drawMoonMask(canvas);
        drawPoints(canvas);
    }

    /**
     * 绘制全部脚印
     */
    private void drawFootprints(Canvas canvas){
        drawFootprint(canvas, 0, height - 100, footprintPaint1, footprintPaint2);
        drawFootprint(canvas, 200, height - 300, footprintPaint3, footprintPaint4) ;
        drawFootprint(canvas, 400, height - 500, footprintPaint5, footprintPaint6);
        drawFootprint(canvas, 550, height - 700, footprintPaint7, footprintPaint8);
    }

    /**
     * 绘制左右脚印 160 * 250
     */
    private void drawFootprint(Canvas canvas, int x, int y, Paint leftPaint, Paint rightPaint){
        canvas.save();
        canvas.rotate(45, x - 50, y + 100);
        drawLeftFootprint(canvas, x, y, leftPaint);
        drawRightFootprint(canvas, x + 80, y - 150, rightPaint);
        canvas.restore();
    }

    /**
     * 绘制左脚印
     */
    private void drawLeftFootprint(Canvas canvas, int x, int y, Paint paint) {
        canvas.drawCircle(x, y, 6, paint);
        canvas.drawCircle(x + 25, y - 12, 8, paint);
        canvas.drawCircle(x + 50, y, 10, paint);
        canvas.drawOval(new RectF(x, y, x + 50, y + 80), paint);
    }

    /**
     * 绘制右脚印
     */
    private void drawRightFootprint(Canvas canvas, int x, int y, Paint paint) {
        canvas.drawCircle(x, y, 10, paint);
        canvas.drawCircle(x + 25, y - 12, 8, paint);
        canvas.drawCircle(x + 50, y, 6, paint);
        canvas.drawOval(new RectF(x, y, x + 50, y + 80), paint);
    }

    /**
     * 绘制背景的星星
     */
    private void drawPoints(Canvas canvas){
        canvas.drawPoints(points, pointPaint);
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas canvas){
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), bgPaint);
    }

    /**
     * 绘制月亮
     */
    private void drawMoon(Canvas canvas){
        canvas.drawCircle(centerX, centerY, radius, moonPaint);
    }

    /**
     * 绘制月亮遮罩
     */
    private void drawMoonMask(Canvas canvas){
        canvas.drawCircle(maskCenterX, maskCenterY, radius-2, maskPaint);
    }
}

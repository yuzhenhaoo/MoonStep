package priv.zxy.moonstep.customview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 创建人: Administrator
 * 创建时间: 2018/10/21
 * 描述: 设立可拖拽消息提示气泡，在消息产生的时候，在MoonFriendFragment进行消息提示
 **/
public class BubbleView extends View {
  private int mWidth, mHeight, paintextSize = 20;
  //相关坐标
  private float[] startSmall = new float[2], endBig = new float[2], startBig = new float[2],
          endSmall = new float[2], controlXY = new float[2], activeXY = new float[2], lastXY = new float[2];
  //相关路径
  private Path pathSmallC, pathBigC, pathStick, pathLine, pathText;
  //画笔
  private Paint paintNormal, paintText;
  //矩阵
  private Matrix matrix;
  //区域的裁定
  private Region regionClip, regionBubble;
  //一些参数
  private float radioSmall = 10f, radioBig = 25f, sin = 2, cos = 2, len = 0, maxLen = 200f, varySet = 1;
  private TYPE type = TYPE.NORMAL;
  private ValueAnimator animatorBack, animatorGone;
  private ValueAnimator.AnimatorUpdateListener uplistner;
  //测量
  private PathMeasure measure;

  private int number = 0;
  private int bubbleColor = Color.parseColor("#FFA02CF5");
  private int textColor = Color.parseColor("#FF84E2FF");

  enum TYPE {
    //四种状态：正常状态，拖拽状态，超出状态，恢复状态
    NORMAL, DRAG, BEYOUND, COMEBACK
  }

  public BubbleView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    initPaint();
    initValuAnimator();
  }

  /**
   * 设置气泡的颜色
   * @param bubbleColor 气泡颜色
   */
  public void setBubbleColor(int bubbleColor){
    this.bubbleColor = bubbleColor;
    invalidate();
  }

  /**
   * 设置文字的颜色
   * @param textColor 文字颜色
   */
  public void setTextColor(int textColor){
    this.textColor = textColor;
    invalidate();
  }

  /**
   * 设定显示的内容
   * @param num 显示数字
   */
  public void setNumber(int num){
    this.number = num;
  }

  /**
   * 初始化画笔
   */
  private void initPaint() {
    paintNormal = new Paint();
    paintNormal.setStyle(Paint.Style.FILL);
    paintNormal.setAntiAlias(true);
    paintNormal.setColor(bubbleColor);

    paintText = new Paint();
    paintText.setTextSize(paintextSize);
    paintText.setAntiAlias(true);
    paintText.setColor(textColor);
    paintText.setStyle(Paint.Style.FILL);
    paintText.setTextAlign(Paint.Align.CENTER);
    matrix = new Matrix();

    measure = new PathMeasure();

  }

  /**
   * 初始化路径
   */
  private void initPath() {
    if (type == TYPE.NORMAL) {
      activeXY[0] = 0;
      activeXY[1] = 0;
    }

    pathSmallC = new Path();
    pathSmallC.addCircle(0, 0, radioSmall, Path.Direction.CW);

    pathLine = new Path();
    pathLine.lineTo(activeXY[0], activeXY[1]);


    if (type == TYPE.COMEBACK) {
      measure.setPath(pathLine, false);
      measure.getPosTan(measure.getLength() * varySet, activeXY, null);
    }


    pathBigC = new Path();

    pathText = new Path();


    if (type == TYPE.BEYOUND) {
      pathBigC.addCircle(activeXY[0], activeXY[1], radioBig * varySet, Path.Direction.CW);
      pathText.moveTo(activeXY[0] - radioBig, activeXY[1] + 4 * varySet);
      pathText.lineTo(activeXY[0] + radioBig, activeXY[1] + 4 * varySet);
      paintText.setTextSize(paintextSize * varySet);
    } else {
      pathBigC.addCircle(activeXY[0], activeXY[1], radioBig, Path.Direction.CW);
      pathText.moveTo(activeXY[0] - radioBig, activeXY[1] + 4);
      pathText.lineTo(activeXY[0] + radioBig, activeXY[1] + 4);
      paintText.setTextSize(paintextSize);
    }


    regionBubble = new Region();
    regionBubble.setPath(pathBigC, regionClip);

    pathStick = new Path();


    controlXY[0] = activeXY[0] / 2;
    controlXY[1] = activeXY[1] / 2;
    sin = (0 - activeXY[1]) / len;
    cos = (0 - activeXY[0]) / len;
    startSmall[0] = 0 - radioSmall * sin;
    startSmall[1] = 0 + radioSmall * cos;
    endBig[0] = activeXY[0] - radioBig * sin;
    endBig[1] = activeXY[1] + radioBig * cos;
    startBig[0] = activeXY[0] + radioBig * sin;
    startBig[1] = activeXY[1] - radioBig * cos;
    endSmall[0] = 0 + radioSmall * sin;
    endSmall[1] = 0 - radioSmall * cos;

    pathStick.moveTo(startSmall[0], startSmall[1]);
    pathStick.quadTo(controlXY[0], controlXY[1], endBig[0], endBig[1]);
    pathStick.lineTo(startBig[0], startBig[1]);
    pathStick.quadTo(controlXY[0], controlXY[1], endSmall[0], endSmall[1]);
    pathStick.close();
    pathStick.setFillType(Path.FillType.WINDING);

  }

  /**
   * 初始化动画
   */
  private void initValuAnimator() {
    uplistner = new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator valueAnimator) {
        varySet = (float) valueAnimator.getAnimatedValue();
        invalidate();
      }
    };
    Animator.AnimatorListener listener = new Animator.AnimatorListener() {
      @Override
      public void onAnimationStart(Animator animator) {
      }

      @Override
      public void onAnimationEnd(Animator animator) {
        type = TYPE.NORMAL;
        varySet = 1;
      }

      @Override
      public void onAnimationCancel(Animator animator) {
      }

      @Override
      public void onAnimationRepeat(Animator animator) {
      }
    };

    animatorBack = ValueAnimator.ofFloat(1.0000f, 0.0000f);
    animatorBack.setInterpolator(new LinearInterpolator());
    animatorBack.setRepeatCount(0);
    animatorBack.setDuration(1000);
    animatorBack.addUpdateListener(uplistner);
    animatorBack.addListener(listener);


    animatorGone = ValueAnimator.ofFloat(1.0000f, 0.0000f);
    animatorGone.setInterpolator(new LinearInterpolator());
    animatorGone.setRepeatCount(0);
    animatorGone.setDuration(1000);
    animatorGone.addUpdateListener(uplistner);
    animatorGone.addListener(listener);

  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    getParent().requestDisallowInterceptTouchEvent(true);
    return super.dispatchTouchEvent(event);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        if (type == TYPE.NORMAL) {
          activeXY[0] = event.getRawX();
          activeXY[1] = event.getRawY();
          matrix.mapPoints(activeXY);
          if (regionBubble.contains((int) activeXY[0], (int) activeXY[1])) {
            type = TYPE.DRAG;
            lastXY[0] = activeXY[0];
            lastXY[1] = activeXY[1];
          }
        }
        break;
      case MotionEvent.ACTION_MOVE:
        activeXY[0] = event.getRawX();
        activeXY[1] = event.getRawY();
        matrix.mapPoints(activeXY);
        if (type == TYPE.DRAG) {
          len = (float) Math.hypot(activeXY[0], activeXY[1]);
          if (len <= maxLen) {
            lastXY[0] = activeXY[0];
            lastXY[1] = activeXY[1];
          } else if (len > maxLen) {
            type = TYPE.BEYOUND;
          }
        }
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        if (type == TYPE.DRAG) {
          activeXY[0] = lastXY[0];
          activeXY[1] = lastXY[1];
          //开始动画
          type = TYPE.COMEBACK;
          animatorBack.start();
        } else if (type == TYPE.BEYOUND) {

          //开始动画
          animatorGone.start();
        }
        break;
      default:
        break;
    }
    return true;
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = w;
    mHeight = h;
    regionClip = new Region(-mWidth, -mHeight, mWidth, mHeight);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    //平移画布
    canvas.translate(mWidth / 2, mHeight / 2);
    matrix.reset();
    if (matrix.isIdentity()) {
      canvas.getMatrix().invert(matrix);
    }
    initPath();
    switch (type) {
      case NORMAL:
        drawBigC(canvas);
        break;
      case DRAG:
        drawStick(canvas);
        drawSmallC(canvas);
        drawBigC(canvas);
        break;
      case BEYOUND:
        drawBigC(canvas);
        break;
      case COMEBACK:
        drawStick(canvas);
        drawSmallC(canvas);
        drawBigC(canvas);
        break;
      default:
        break;
    }
    drawText(canvas);
  }

  /**
   * 画小圆
   *
   * @param canvas
   */
  private void drawSmallC(Canvas canvas) {
    canvas.drawPath(pathSmallC, paintNormal);
  }

  /**
   * 画拖拽
   *
   * @param canvas
   */
  private void drawStick(Canvas canvas) {
    canvas.drawPath(pathStick, paintNormal);
  }

  /**
   * 画大圆
   *
   * @param canvas
   */
  private void drawBigC(Canvas canvas) {
    canvas.drawPath(pathBigC, paintNormal);
  }

  /**
   * 画文字
   *
   * @param canvas
   */
  private void drawText(Canvas canvas) {
    canvas.drawTextOnPath(String.valueOf(number), pathText, 0, 0, paintText);
  }

}
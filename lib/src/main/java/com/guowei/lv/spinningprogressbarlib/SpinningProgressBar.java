package com.guowei.lv.spinningprogressbarlib;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


public class SpinningProgressBar extends View {

    private static final int DEFAULT_CYCLE_DURATION = 800;
    private static final int MODE_LINEAR = 0;
    private static final int MODE_DECELERATE = 1;

    private Bitmap image;

    private int cycleDuration;

    private boolean clockwise;

    private int mode;

    private Paint paint;

    private ObjectAnimator animation;

    public SpinningProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public SpinningProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.default_image);
        cycleDuration = DEFAULT_CYCLE_DURATION;
        clockwise = true;
        mode = MODE_LINEAR;
        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SpinningProgressBar, 0, 0);
            int resId = array.getResourceId(R.styleable.SpinningProgressBar_srcImg, R.drawable.default_image);
            image = BitmapFactory.decodeResource(getResources(), resId);
            cycleDuration = array.getInteger(R.styleable.SpinningProgressBar_cycleDuration, DEFAULT_CYCLE_DURATION);
            clockwise = array.getBoolean(R.styleable.SpinningProgressBar_clockwise, true);
            mode = array.getInt(R.styleable.SpinningProgressBar_mode, MODE_LINEAR);
            array.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = image.getWidth();
        int height = image.getHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(image, 0, 0, paint);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAnimation();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startAnimation();
        } else {
            stopAnimation();
        }
    }

    private void startAnimation() {
        if (animation == null) {
            if (clockwise) {
                animation = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
            } else {
                animation = ObjectAnimator.ofFloat(this, "rotation", 360f, 0f);
            }
            animation.setDuration(cycleDuration);
            Interpolator interpolator;
            if (mode == MODE_LINEAR) {
                interpolator = new LinearInterpolator();
            } else if (mode == MODE_DECELERATE) {
                interpolator = new DecelerateInterpolator();
            } else {
                interpolator = new LinearInterpolator();
            }
            animation.setInterpolator(interpolator);
            animation.setRepeatCount(ObjectAnimator.INFINITE);
            animation.setRepeatMode(ObjectAnimator.RESTART);
        }
        animation.start();
    }

    private void stopAnimation() {
        animation.end();
    }
}

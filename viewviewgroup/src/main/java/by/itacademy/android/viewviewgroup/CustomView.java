package by.itacademy.android.viewviewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    public interface onMeasureListener {
        void onSizeChanged(int width, int height);
    }

    private Paint paint;
    private int centerX;
    private int centerY;

    private final int SIZE = 50;

    private onMeasureListener listener;

    public void setListener(onMeasureListener listener) {
        this.listener = listener;
    }

    public CustomView(Context context) {
        super(context);
        initPaint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        if (listener != null) {
            listener.onSizeChanged(w, h);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int size = SIZE / 2;
        canvas.drawRect(centerX - size, centerY - size, centerX + size, centerY + size, paint);
        super.onDraw(canvas);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
    }
}

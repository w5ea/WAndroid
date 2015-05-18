package cn.way.wandroid.views.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class CircleShader extends ShaderHelper {
//    private final static int ALPHA_MAX = 255;
    private float center;
    private float bitmapCenterX;
    private float bitmapCenterY;
    private float borderRadius;
    private int bitmapRadius;
    private Context context;
    private Paint secondBorder;

    public CircleShader() {
    }

    @Override
    public void init(Context context, AttributeSet attrs, int defStyle) {
        super.init(context, attrs, defStyle);
        this.context = context;
        square = true;

        secondBorder = new Paint();
        secondBorder.setStyle(Paint.Style.STROKE);
        //secondBorder.setAntiAlias(true);
        secondBorder.setColor(Color.WHITE);
        secondBorder.setStrokeWidth((this.context.getResources().getDisplayMetrics().density * 2));
    }

    @Override
    public void draw(Canvas canvas, Paint imagePaint, Paint borderPaint) {
    	borderPaint.setShadowLayer((this.context.getResources().getDisplayMetrics().density * 2), 0, 0, Color.BLACK);
    	//画红圈
        canvas.drawCircle(center, center, borderRadius-(this.context.getResources().getDisplayMetrics().density * 2), borderPaint);

        //画白圈
        canvas.drawCircle(center, center, borderRadius-(this.context.getResources().getDisplayMetrics().density * 4), secondBorder);
        canvas.save();
        canvas.concat(matrix);
        canvas.drawCircle(bitmapCenterX, bitmapCenterY, bitmapRadius-(this.context.getResources().getDisplayMetrics().density * 4), imagePaint);
        canvas.restore();
    }

    @Override
    public void onSizeChanged(int width, int height) {
        super.onSizeChanged(width, height);
        center = Math.round(viewWidth / 2f);
        borderRadius = Math.round((viewWidth - borderWidth) / 2f);
    }

    @Override
    public void calculate(int bitmapWidth, int bitmapHeight,
                          float width, float height,
                          float scale,
                          float translateX, float translateY) {
        bitmapCenterX = Math.round(bitmapWidth / 2f);
        bitmapCenterY = Math.round(bitmapHeight / 2f);
        bitmapRadius = Math.round(width / scale / 2f + 0.5f);
    }

    @Override
    public void reset() {
        bitmapRadius = 0;
        bitmapCenterX = 0;
        bitmapCenterY = 0;
    }
}
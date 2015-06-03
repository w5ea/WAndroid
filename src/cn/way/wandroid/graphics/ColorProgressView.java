package cn.way.wandroid.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class ColorProgressView extends View {
	
	private Paint paint = null;
	private float progress = 0;
	private float strokeWidth = 0;
	private float marginLR;
	public ColorProgressView(Context context) {
		super(context);
	}

	public ColorProgressView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setMarginLR(10);
	}
	public ColorProgressView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}
	public void updateView(float progress){
		if (progress<0) {
			progress = 0;
		}
		if (progress>1) {
			progress = 1;
		}
		this.progress = progress;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		invalidate();
//		postInvalidateOnAnimation();
		if(getHeight()>0){
			float maxMargin = ((float)getWidth())/2-1;
			if (getMarginLR()>maxMargin) {
				setMarginLR(maxMargin/getResources().getDisplayMetrics().density);
			}
			float strokenW = getStrokeWidth();
			float maxSW = getWidth()/2-getMarginLR();
			if (getStrokeWidth()>maxSW) {
				strokenW = maxSW;
				setStrokeWidth(maxSW);
			}
//			WLog.d(getHeight()+"====="+progress);
			canvas.drawRect(0+getMarginLR()+strokenW, getHeight()-progress*getHeight(), getWidth()-getMarginLR()-strokenW, getHeight(), getPaint());
		}
	}
	
	public void setColorRGB(int r,int g,int b){
		setColorRGBA(r, g, b, 255);
	}
	public void setColorRGBA(int r,int g,int b,int a){
		getPaint().setARGB(a, r, g, b);
	}
	public float getProgress() {
		return progress;
	}
	public void setProgress(float progress) {
		updateView(progress);
	}
	public Paint getPaint() {
		if (paint==null) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			paint = new Paint();
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setAntiAlias(true);
			paint.setDither(false);
			
			paint.setStyle(Style.FILL_AND_STROKE);
			paint.setStrokeCap(Cap.ROUND);
			paint.setStrokeJoin(Join.ROUND);
			paint.setStrokeWidth(getStrokeWidth());
		}
		return paint;
	}

	public float getMarginLR() {
		return marginLR;
	}

	/**
	 * 设置左右的间距
	 * @param marginLR 单位为dpi
	 */
	public void setMarginLR(float marginLR) {
		this.marginLR = marginLR*getResources().getDisplayMetrics().density;
	}

	public float getStrokeWidth() {
		return strokeWidth;
	}
	/**
	 * 
	 * @param width 单位为像素
	 */
	public void setStrokeWidth(float width){
		if (width>0) {
			this.strokeWidth = width;
			getPaint().setStrokeWidth(width);
		}
	}
}

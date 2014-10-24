package cn.way.wandroid.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class AnnularProgressView extends View {
	
	private Paint paint = null;
	private float strokeWidth = 20;
	private float progress = 1;
	private RectF rect;

	public AnnularProgressView(Context context) {
		super(context);
	}

	public AnnularProgressView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	public AnnularProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		strokeWidth = strokeWidth*getResources().getDisplayMetrics().density;
		rect = new RectF(strokeWidth/2,strokeWidth/2,getWidth()-strokeWidth/2,getHeight()-strokeWidth/2);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		rect = new RectF(strokeWidth/2,strokeWidth/2,getWidth()-strokeWidth/2,getHeight()-strokeWidth/2);
	}
	public void updateView(float progress){
		if (progress<0||progress>1) {
			return;
		}
		this.progress = progress;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		invalidate();
//		canvas.drawColor(Color.WHITE);
//		canvas.clipRect(rect, Op.XOR);
//		canvas.drawColor(Color.WHITE, Mode.XOR);

//		canvas.drawPaint(paint);
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
		canvas.drawArc(rect, 0-90, 360*progress, false, getPaint());
	}
	
	public void setStrokeWidth(float width){
		if (width>0) {
			this.strokeWidth = width*getResources().getDisplayMetrics().density;
			rect = new RectF(strokeWidth/2,strokeWidth/2,getWidth()-strokeWidth/2,getHeight()-strokeWidth/2);
			getPaint().setStrokeWidth(width);
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
	@SuppressLint("NewApi")
	public Paint getPaint() {
		if (paint==null) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			paint = new Paint();
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setAntiAlias(true);
			paint.setDither(false);
			
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(strokeWidth);
			paint.setStrokeCap(Cap.ROUND);
			paint.setStrokeJoin(Join.ROUND);
//			paint.setShadowLayer(getWidth()/8, getWidth()/2, getHeight()/2, Color.CYAN);
		}
		return paint;
	}
}

package cn.way.wandroid.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class TagCoverView extends FrameLayout {
	private Bitmap mBitmap;//the bitmap hold the draw result
	private Canvas mCanvas;//the canvas to draw things on;
	private RectF clearRect;//the area for drawing
	private Paint mPaint = new Paint();
	private OnClickListener onClickTargetListener;
	private View targetView;
	public TagCoverView(Context context) {
		super(context);
		//set a background, why no this no work?
		setBackgroundColor(Color.argb(0, 0, 0, 0));
	}
	public TagCoverView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		init();
	}
	/**
	 * 
	 * @param rect use x,y,width,height;
	 */
	public void setTagRect(Rect rect,boolean addTargetView){
		clearRect = new RectF(new Rect(rect.left, rect.top, rect.right+rect.left, rect.bottom+rect.top));
		if (addTargetView) {
			if (targetView==null) {
				targetView = new View(getContext());
//				targetView.setBackgroundColor(Color.argb(100, 0, 255, 0));
				setupTargetView(rect);
				addView(targetView);
			}else{
				setupTargetView(rect);
			}
		}
	}
	private void setupTargetView(Rect rect){
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				rect.right, rect.bottom);
		params.leftMargin = rect.left;
		params.topMargin = rect.top;
		targetView.setLayoutParams(params);
	}
	private void init(){
		if (mCanvas==null) {
			int width = getWidth(),height = getHeight();
			mBitmap = Bitmap.createBitmap(width, height,
					Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			mCanvas.drawColor(Color.argb(100, 0, 0, 0));
			if (clearRect==null) {
				float density = getResources().getDisplayMetrics().density;
				float rectSize = 150*density;
				float posX = 0*density;
				float posY = 0*density;
				clearRect = new RectF(posX, posY, posX+rectSize, posY+rectSize);//the area for drawing
			}
			
			mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
			mPaint.setStyle(Style.FILL);
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
//		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
			mPaint.setColor(Color.argb(255, 0, 0, 255));
//			setBackground(new BitmapDrawable(getResources(), mBitmap));
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
//		init();
//		canvas.drawColor(Color.argb(100, 0, 0, 0));
//		canvas.drawColor(Color.RED);
		
		mCanvas.drawOval(clearRect, mPaint);
//		mCanvas.drawCircle(150, 150, 100, mPaint);
//		mCanvas.clipRect(clearRect);
//		canvas.drawRoundRect(clearRect, 10, 10, paint);
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}
	public OnClickListener getOnClickTargetListener() {
		return onClickTargetListener;
	}
	public void setOnClickTargetListener(OnClickListener onClickTargetListener) {
		this.onClickTargetListener = onClickTargetListener;
		if (targetView!=null) {
			targetView.setOnClickListener(onClickTargetListener);
		}
	}
}

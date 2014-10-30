package cn.way.wandroid.graphics;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;

public class RoundtableView extends FrameLayout {
	public interface RotationListener{
		/**
		 * 在state == StateSpeedConstant 时可以做请求网络数据的操作
		 * @param state
		 */
		void onStateChange(State state);
		void onSlowdownPerStep();
	}
	
	private PanView pan;
	private PointerView pointerView;
	public FrameLayout getCenterLayout() {
		return pointerView.getCenterFrameLayout();
	}

	public PointerView getPointerView() {
		return pointerView;
	}

	private ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
	private float maxSpeed = 8.0f;
	private float speedChangeValue = 0.10f;
	private final float adjustingAngle = 360-(computeTotalRotation()%360);
	
	private float currentSpeed;
	private float leftAdjustingAngle;
	private float constantAngle ;//均速运动过程中转过的总角度
	private float speedDownAngle;//减速过程中转过的角度

	private RotationListener rotationListener;
	
	public enum State {
		StateStoped,StateSpeedConstant, StateSpeedup, StateSlowdown
	}

	private State state = State.StateStoped;

	public RoundtableView(Context context) {
		super(context);
		init();
	}

	public RoundtableView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundtableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	@Override
	protected void onDetachedFromWindow() {
//		setRotationListener(null);
//		stop();
		super.onDetachedFromWindow();
	}
	private void init() {

		if (pan == null) {
			pan = new PanView(getContext());
			// pan.setLayoutParams(new LayoutParams(getWidth(), getWidth()));
			addView(pan);
			// pan.setBackgroundColor(Color.GREEN);
		}

		animator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				if (pan != null) {
					if (state == State.StateSpeedup) {
						currentSpeed += speedChangeValue;
						if (currentSpeed >= maxSpeed) {
							currentSpeed = maxSpeed;
							state = State.StateSpeedConstant;
							stateChanged();
//							state = State.StateSlowdown;
//							ii+=1;
//							ii = ii>7?0:ii;
//							slowdown(ii);
						}
					}
					if (state == State.StateSpeedConstant) {
						constantAngle += maxSpeed;
					}
					ViewHelper.setRotation(pan,ViewHelper.getRotation(pan)+currentSpeed);
					if (state == State.StateSlowdown) {
						if (constantAngle>0) {
							leftAdjustingAngle += 360-(constantAngle%360);
							constantAngle = 0;
						}
						if (Math.floor(leftAdjustingAngle)>0) {
							leftAdjustingAngle -= maxSpeed;
						}else{
							currentSpeed -= speedChangeValue;
							if (currentSpeed <= 0) {
								stop();
								getRotationListener().onSlowdownPerStep();
//								Toast.makeText(getContext(), adjustingAngle+" -- "+(ViewHelper.getRotation(pan)), 0).show();
							}else{
								speedDownAngle += currentSpeed;
								if (speedDownAngle>=360/8.f&&getRotationListener()!=null) {
									speedDownAngle = 0;
									getRotationListener().onSlowdownPerStep();
								}
							}
						}
					}
//					ViewHelper.setRotation(pan,ViewHelper.getRotation(pan)+currentSpeed);
//					pan.setRotation(pan.getRotation() + currentSpeed);
				}
			}
		});
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setDuration(3000);
//		Toast.makeText(getContext(), adjustingAngle+" -- "+computeTotalRotation(), 0).show();
		
		if (pointerView == null) {
			pointerView = new PointerView(getContext());
			addView(pointerView);
			pointerView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					start();
				}
			});
		}
	}
	public void stop(int index){
		slowdown(index);
	}
	public TextView getTextView(int index){
		if (index>=pan.getTextViews().size()) {
			return null;
		}
		return pan.getTextViews().get(index);
	}
	public ImageView getImageView(int index){
		if (index>=pan.getImageViews().size()) {
			return null;
		}
		return pan.getImageViews().get(index);
	}
	private void stateChanged(){
		if (getRotationListener()!=null) {
			getRotationListener().onStateChange(state);
		}
	}
	private void reset(){
		leftAdjustingAngle = adjustingAngle;
		constantAngle = 0;
		speedDownAngle = 0;
		ViewHelper.setRotation(pan,0);
	}
	public void start(){
		if (state!=State.StateStoped||!isEnabled()) {
			return;
		}
		if (currentSpeed > 0) {
			return;
		}
		reset();
		state = State.StateSpeedup;
		stateChanged();
		animator.start();
	}
	private void slowdown(int index){
		leftAdjustingAngle += getAngleByIndex(index);
		if (index>1||index==0) {
			float adjustingValue = index/5f*4;
//			if(index==0)adjustingValue = 3.6f;
			leftAdjustingAngle -= adjustingValue;
		}
		state = State.StateSlowdown;
		stateChanged();
	}
	private void stop(){
		currentSpeed = 0;
		state = State.StateStoped;
		stateChanged();
		animator.end();
	}
	private float computeTotalRotation(){
		float rotation = 0;
		float speed = 0;
		for (int i = 0; i < (int)(maxSpeed/speedChangeValue); i++) {
			speed += speedChangeValue;
			rotation += speed;
		}
		return rotation*2;
	}
//	private int getIndexByAngle(float angle){
//		return (int) Math.floor((angle%360)/(360/8));
//	}
	private float getAngleByIndex(int index){
		return (360-index * (360/8.f)+180)%360;
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// WLog.d("onSizeChanged oldH"+oldh+"  newH"+h +"w="+w);

		LayoutParams lp = (LayoutParams) pan.getLayoutParams();
		lp.width = getWidth();
		lp.height = getWidth();
		lp.gravity = Gravity.CENTER;

		LayoutParams params = (LayoutParams) pointerView.getLayoutParams();
		params.width = getWidth();
		params.height = getWidth();
		params.gravity = Gravity.CENTER;
	}

	
	private static final int INVALID_POINTER = -1;
	private float moveDistance;
	private int mActivePointerId;
	private float mLastMotionX;
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();

		switch (action & MotionEventCompat.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			// Remember where the motion event started
			int index = MotionEventCompat.getActionIndex(ev);
			mActivePointerId = MotionEventCompat.getPointerId(ev, index);
			mLastMotionX = ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:{
				// Scroll to follow the motion event
				final int activePointerIndex = getPointerIndex(ev, mActivePointerId);
				if (mActivePointerId == INVALID_POINTER)
					break;
				final float x = MotionEventCompat.getX(ev, activePointerIndex);
				moveDistance = mLastMotionX - x;
				mLastMotionX = x;
		}break;
		case MotionEvent.ACTION_UP:
//			Toast.makeText(getContext(), ""+moveDistance, 0).show();
			float minDeltaxX = 5;
			if (moveDistance>minDeltaxX) {//flip to left
				RoundtableView.this.start();
			}
			if (-moveDistance>minDeltaxX) {
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}
	private int getPointerIndex(MotionEvent ev, int id) {
		int activePointerIndex = MotionEventCompat.findPointerIndex(ev, id);
		if (activePointerIndex == -1)
			mActivePointerId = INVALID_POINTER;
		return activePointerIndex;
	}
	
	
	public RotationListener getRotationListener() {
		return rotationListener;
	}

	public void setRotationListener(RotationListener rotationListener) {
		this.rotationListener = rotationListener;
	}


	public class PanView extends FrameLayout {
		private Paint paint;
		private float strokeWidth = 4;
		private float innerStrokeWidth = 8;
		private float lineWidth = 3f;
		private RectF rect = null;
		private RectF innerRect;
		private RectF innerBgRect;
		// private float centerRadius = 50;
		private int boderColor = Color.rgb(254, 178, 25);
		private int innerColor = Color.rgb(216, 139, 30);
		private int innerBgColor = Color.rgb(249, 225, 161);
		// private int centerColor = Color.RED;

		private ArrayList<ImageView> imageViews = null;
		private ArrayList<TextView> textViews = null;

		public ArrayList<ImageView> getImageViews() {
			return imageViews;
		}

		public ArrayList<TextView> getTextViews() {
			return textViews;
		}

		private ArrayList<Point> ps = new ArrayList<Point>();
		private ArrayList<Point> inSidePs = new ArrayList<Point>();
		private ArrayList<Point> inSideTitlePs = new ArrayList<Point>();
		private float density;

		public PanView(Context context) {
			super(context);
			init();
		}

		public PanView(Context context, AttributeSet attrs) {
			this(context, attrs, 0);
		}

		public PanView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init();
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			updateRect();
			super.onSizeChanged(w, h, oldw, oldh);
		}

		@Override
		protected void onLayout(boolean changed, int left, int top, int right,
				int bottom) {
			super.onLayout(changed, left, top, right, bottom);
		}

		private void updateRect() {

			rect = new RectF(strokeWidth / 2, strokeWidth / 2, getWidth()
					- strokeWidth / 2, getWidth() - strokeWidth / 2);
			innerRect = new RectF(strokeWidth + innerStrokeWidth / 2,
					strokeWidth + innerStrokeWidth / 2, getWidth()
							- strokeWidth - innerStrokeWidth / 2, getWidth()
							- strokeWidth - innerStrokeWidth / 2);
			innerBgRect = new RectF(strokeWidth + innerStrokeWidth, strokeWidth
					+ innerStrokeWidth, getWidth() - strokeWidth
					- innerStrokeWidth, getWidth() - strokeWidth
					- innerStrokeWidth);
			if (ps.size() > 0) {
				ps.clear();
			}
			float rectSize = GraphicsUtil.getWithOfRectF(innerRect);
			for (int i = 0; i < 8; i++) {
				Point p = GraphicsUtil.getPointThatAroundTheRectByAngle(360
						/ 16 + 360 / 8 * i, rectSize);
				p.x += strokeWidth + innerStrokeWidth / 2;
				p.y += strokeWidth + innerStrokeWidth / 2;
				ps.add(p);
			}
			float inSize = getWidth() / 5 * 3;
			float reSize = (getWidth() - inSize) / 2;
			float inSizeTitle = getWidth()/5*4.3f;
			float reSizeTitle = (getWidth() - inSizeTitle) / 2;
			for (int i = 0; i < 8; i++) {
				Point p = GraphicsUtil.getPointThatAroundTheRectByAngle(
						360 / 8 * i, inSize);
				p.x += reSize;
				p.y += reSize;
				inSidePs.add(p);
				
				Point pTitle = GraphicsUtil.getPointThatAroundTheRectByAngle(
						360 / 8 * i, inSizeTitle);
				pTitle.x += reSizeTitle;
				pTitle.y += reSizeTitle;
				inSideTitlePs.add(pTitle);
			}

			int width = (int) (getWidth() / 7);
			int height = (int) (getWidth() / 5);

			for (ImageView iv : imageViews) {
				int pos = imageViews.indexOf(iv);
				LayoutParams params = (LayoutParams) iv.getLayoutParams();
				params.width = width;
				params.height = height;
				params.setMargins(inSidePs.get(pos).x - width / 2,
						inSidePs.get(pos).y - height / 2, -1, -1);
				ViewHelper.setRotation(iv, 360 / 8 * pos + 180);
			}
			
			width = (int) (getWidth() / 3.3);
			height = (int) (width / 5);
			for (TextView tv : textViews) {
				int pos = textViews.indexOf(tv);
				LayoutParams params = (LayoutParams) tv.getLayoutParams();
				params.width = width;
				params.height = height;
				params.setMargins(inSideTitlePs.get(pos).x - width / 2,
						inSideTitlePs.get(pos).y - height / 2, -1, -1);
				ViewHelper.setRotation(tv, 360 / 8 * pos + 180);
			}
		}

		private void init() {
			setBackgroundColor(Color.TRANSPARENT);

			density = getResources().getDisplayMetrics().density;
			strokeWidth *= density;
			innerStrokeWidth *= density;
			// centerRadius *= density;
			lineWidth *= density;

			paint = new Paint();

			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setAntiAlias(true);
			paint.setDither(false);

			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(strokeWidth);
			paint.setStrokeCap(Cap.ROUND);
			paint.setStrokeJoin(Join.ROUND);

			if (imageViews == null) {
				imageViews = new ArrayList<ImageView>();
				for (int i = 0; i < 8; i++) {
					ImageView iv = new ImageView(getContext());
					// iv.setLayoutParams(new FrameLayout.LayoutParams(100,
					// LayoutParams.WRAP_CONTENT));
//					iv.setImageResource(R.drawable.ic_launcher);
//					 iv.setBackgroundColor(Color.BLUE);
					imageViews.add(iv);
//					iv.setVisibility(View.GONE);
					addView(iv);
				}
//				images.get(0).setVisibility(View.VISIBLE);
			}
			if (textViews == null) {
				textViews = new ArrayList<TextView>();
				for (int i = 0; i < 8; i++) {
					TextView tv = new TextView(getContext());
					tv.setGravity(Gravity.CENTER);
//					tv.setText(""+i);
					tv.setTextSize(11);
					tv.setTypeface(null, Typeface.BOLD);
					tv.setTextColor(Color.rgb(145, 94, 79));
					tv.setSingleLine();
					textViews.add(tv);
					addView(tv);
//					tv.setBackgroundColor(Color.YELLOW);
				}
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			if (paint==null||rect == null||getWidth()==0) {
				return;
			}
			// draw border
			paint.setColor(boderColor);
			canvas.drawArc(rect, 0 - 90, 360, false, paint);

			// draw innerBorder
			paint.setStrokeWidth(innerStrokeWidth);
			paint.setColor(innerColor);
			canvas.drawArc(innerRect, 0 - 90, 360, false, paint);
			// draw innerBg
			paint.setStyle(Style.FILL);
			paint.setColor(innerBgColor);
			canvas.drawOval(innerBgRect, paint);

			// draw innerLine
			paint.setColor(innerColor);
			paint.setStrokeWidth(lineWidth);
			
			float rectSize = getWidth() / 2;
			for (Point p : ps) {
				canvas.drawLine(p.x, p.y, rectSize, rectSize, paint);
			}
//			 for (Point p : inSidePs) {
//				 paint.setColor(Color.MAGENTA);
//				 paint.setTextSize(30);
//				 canvas.drawCircle(p.x, p.y,20, paint);
//				 canvas.drawText(""+(inSidePs.indexOf(p)), p.x, p.y, paint);
//			 }
			//
			// paint.setColor(centerColor);
			// canvas.drawCircle(getWidth()/2, getWidth()/2, centerRadius,
			// paint);
		}
	}

	public class PointerView extends FrameLayout {
		private Paint paint;
		private Path trianglePath;
		private FrameLayout centerFrameLayout;
		public FrameLayout getCenterFrameLayout() {
			return centerFrameLayout;
		}

		private int fillColor = Color.RED;
		private int pointerSize = 30;
		private int centerRadius = 100;
		private int centerLayoutRadius = 100;
		float density;

		public PointerView(Context context) {
			super(context);
			init();
		}
		@Override
		public void setOnClickListener(OnClickListener l) {
			centerFrameLayout.setOnClickListener(l);
		}
		private void init() {
			density = getResources().getDisplayMetrics().density;
			pointerSize *= density;
			centerLayoutRadius *= density;

			setBackgroundColor(Color.TRANSPARENT);

			paint = new Paint();

			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			// paint.setAntiAlias(true);
			paint.setDither(false);
			paint.setStyle(Style.FILL_AND_STROKE);
			paint.setColor(fillColor);
			// paint.setStrokeWidth(50);

			centerFrameLayout = new FrameLayout(getContext());
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
					centerLayoutRadius, centerLayoutRadius, Gravity.CENTER);
			centerFrameLayout.setLayoutParams(lp);
//			centerFrameLayout.setBackgroundColor(Color.BLUE);
			addView(centerFrameLayout);
		}
		
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			centerRadius = getWidth() / 2 / 3;
			centerLayoutRadius = (int) (centerRadius*0.9f);
			ViewGroup.LayoutParams lp = centerFrameLayout.getLayoutParams();
			lp.width = centerLayoutRadius*2;
			lp.height = lp.width;
			
			if (trianglePath == null) {
				trianglePath = new Path();
			}else{
				trianglePath.reset();
			}
			int startX = getWidth() / 2;
			int startY = (int) (getHeight() / 2 + centerRadius + pointerSize * 0.9f);
			trianglePath.moveTo(startX, startY);
			int xMove = pointerSize / 2;
			int yMove = (int) (Math.sin(45) * pointerSize);
			trianglePath.lineTo(startX - xMove, startY - yMove);
			trianglePath.lineTo(startX + xMove, startY - yMove);
			trianglePath.close();
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, centerRadius,
					paint);
			// canvas.drawLines(new float[]{ 100,100,
			// 100,300,
			// 100,100,
			// 300,300,
			// 300,300,
			// 100,300
			// }, paint);
			canvas.drawPath(trianglePath, paint);
		}
	}
}

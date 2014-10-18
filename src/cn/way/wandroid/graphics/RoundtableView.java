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
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import cn.way.wandroid.R;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;

public class RoundtableView extends FrameLayout {
	private PanView pan;
	private PointerView pv;
	private ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
	private float maxSpeed = 8;
	private float speedChangeValue = 0.05f;
	private float currentSpeed;

	public enum State {
		StateSpeedConstant, StateSpeedUp, StateStateSpeedDown
	}

	private State state = State.StateSpeedConstant;

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
					if (state == State.StateSpeedUp) {
						currentSpeed += speedChangeValue;
						if (currentSpeed >= maxSpeed) {
							state = State.StateStateSpeedDown;
						}
					}

					if (state == State.StateStateSpeedDown) {
						currentSpeed -= speedChangeValue;
						if (currentSpeed <= 0) {
							currentSpeed = 0;
							state = State.StateSpeedConstant;
							animator.end();
							Toast.makeText(getContext(), ""+(ViewHelper.getRotation(pan)), 0).show();
						}
					}
					ViewHelper.setRotation(pan,ViewHelper.getRotation(pan)+currentSpeed);
//					pan.setRotation(pan.getRotation() + currentSpeed);
				}
			}
		});
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setDuration(3000);
		Toast.makeText(getContext(), ""+computeTotalRotation(), 0).show();
	}
	private float computeTotalRotation(){
		float rotation = 0;
		float speed = 0;
		for (int i = 0; i < (int)(maxSpeed/speedChangeValue); i++) {
			speed += speedChangeValue;
			rotation += speed;
		}
		return (rotation - maxSpeed)*2;
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

		if (pv == null) {
			pv = new PointerView(getContext());
			addView(pv);
			pv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (currentSpeed <= 0) {
						state = State.StateSpeedUp;
						ViewHelper.setRotation(pan,0);
						animator.start();
					}
				}
			});
		}
		LayoutParams params = (LayoutParams) pv.getLayoutParams();
		params.width = lp.width;
		params.height = lp.width;
		// requestLayout();
	}

	public class PanView extends FrameLayout {
		private Paint paint;
		private float strokeWidth = 4;
		private float innerStrokeWidth = 8;
		private float lineWidth = 3f;
		private RectF rect;
		private RectF innerRect;
		private RectF innerBgRect;
		// private float centerRadius = 50;
		private int boderColor = Color.rgb(254, 178, 25);
		private int innerColor = Color.rgb(216, 139, 30);
		private int innerBgColor = Color.rgb(249, 225, 161);
		// private int centerColor = Color.RED;

		private ArrayList<ImageView> images = null;

		private ArrayList<Point> ps = new ArrayList<Point>();
		private ArrayList<Point> inSidePs = new ArrayList<Point>();
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
			for (int i = 0; i < 8; i++) {
				Point p = GraphicsUtil.getPointThatAroundTheRectByAngle(
						360 / 8 * i, inSize);
				p.x += reSize;
				p.y += reSize;
				inSidePs.add(p);
			}

			int width = (int) (getWidth() / 4);
			int height = (int) (getWidth() / 3);

			for (ImageView iv : images) {
				int pos = images.indexOf(iv);
				LayoutParams params = (LayoutParams) iv.getLayoutParams();
				params.width = width;
				params.height = height;
				params.setMargins(inSidePs.get(pos).x - width / 2,
						inSidePs.get(pos).y - height / 2, -1, -1);
				ViewHelper.setRotation(iv, 360 / 8 * pos + 180);
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

			if (images == null) {
				images = new ArrayList<ImageView>();
				for (int i = 0; i < 8; i++) {
					ImageView iv = new ImageView(getContext());
					// iv.setLayoutParams(new FrameLayout.LayoutParams(100,
					// LayoutParams.WRAP_CONTENT));
					iv.setImageResource(R.drawable.ic_launcher);
					// iv.setBackgroundColor(Color.BLUE);
					images.add(iv);
					iv.setVisibility(View.GONE);
					addView(iv);
				}
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);

			if (rect == null) {
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
			 for (Point p : inSidePs) {
				 paint.setColor(Color.MAGENTA);
				 paint.setTextSize(30);
//				 canvas.drawCircle(p.x, p.y,20, paint);
				 canvas.drawText(""+(inSidePs.indexOf(p)), p.x, p.y, paint);
			 }
			//
			// paint.setColor(centerColor);
			// canvas.drawCircle(getWidth()/2, getWidth()/2, centerRadius,
			// paint);
		}
	}

	public class PointerView extends FrameLayout {
		private Paint paint;
		private Path trianglePath;
		private ImageView pointerView;
		private int fillColor = Color.RED;
		private int pointerSize = 30;
		private int centerRadius = 100;
		private int centerImageRadius = 90;
		float density;

		public PointerView(Context context) {
			super(context);
			init();
		}

		private void init() {
			density = getResources().getDisplayMetrics().density;
			pointerSize *= density;
			centerImageRadius *= density;

			setBackgroundColor(Color.TRANSPARENT);

			paint = new Paint();

			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			// paint.setAntiAlias(true);
			paint.setDither(false);
			paint.setStyle(Style.FILL_AND_STROKE);
			paint.setColor(fillColor);
			// paint.setStrokeWidth(50);

			pointerView = new ImageView(getContext());
			FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
					centerImageRadius, centerImageRadius, Gravity.CENTER);
			pointerView.setLayoutParams(lp);
			// pointerView.setScaleType(ScaleType.CENTER);
			pointerView.setImageResource(R.drawable.ic_launcher);
			// pointerView.setBackgroundColor(Color.BLUE);
			addView(pointerView);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			centerRadius = getWidth() / 2 / 3;
			if (trianglePath == null) {
				trianglePath = new Path();
				int startX = getWidth() / 2;
				int startY = (int) (getHeight() / 2 + centerRadius + pointerSize * 0.9f);
				trianglePath.moveTo(startX, startY);
				int xMove = pointerSize / 2;
				int yMove = (int) (Math.sin(45) * pointerSize);
				trianglePath.lineTo(startX - xMove, startY - yMove);
				trianglePath.lineTo(startX + xMove, startY - yMove);
				trianglePath.close();
			}
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

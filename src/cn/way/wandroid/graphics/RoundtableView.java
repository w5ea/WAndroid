package cn.way.wandroid.graphics;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import cn.way.wandroid.R;

public class RoundtableView extends FrameLayout {
	private Paint paint = null;
	private float strokeWidth = 4;
	private float innerStrokeWidth = 8;
	private float lineWidth = 3f;
	private RectF rect;
	private RectF innerRect;
	private RectF innerBgRect;
	private float centerRadius = 50;
	private int boderColor = Color.rgb(254, 178, 25);
	private int innerColor = Color.rgb(216, 139, 30);
	private int innerBgColor = Color.rgb(249, 225, 161);
	private int centerColor = Color.RED;
	
	private ImageView pointerView;
	private ArrayList<ImageView> images;
	
	private ArrayList<Point> ps = new ArrayList<Point>();
	private ArrayList<Point> inSidePs = new ArrayList<Point>();
	float density;
	public RoundtableView(Context context) {
		super(context);
		init();
	}
	
	public RoundtableView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public RoundtableView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		updateRect();
	}
	
	@SuppressLint("NewApi")
	private void updateRect(){
		rect = new RectF(strokeWidth/2,strokeWidth/2,getWidth()-strokeWidth/2,getWidth()-strokeWidth/2);
		innerRect = new RectF(strokeWidth+innerStrokeWidth/2,strokeWidth+innerStrokeWidth/2,getWidth()-strokeWidth-innerStrokeWidth/2,getWidth()-strokeWidth-innerStrokeWidth/2);
		innerBgRect = new RectF(strokeWidth+innerStrokeWidth,strokeWidth+innerStrokeWidth,getWidth()-strokeWidth-innerStrokeWidth,getWidth()-strokeWidth-innerStrokeWidth);
		if (ps.size()>0) {
			ps.clear();
		}
		float rectSize = GraphicsUtil.getWithOfRectF(innerRect);
		for (int i = 0; i < 8; i++) {
			Point p = GraphicsUtil.getPointThatAroundTheRectByAngle(360/16+360/8*i, rectSize);
			p.x += strokeWidth+innerStrokeWidth/2;
			p.y += strokeWidth+innerStrokeWidth/2;
			ps.add(p);
		}
		float inSize = getWidth()/5*3;
		float reSize = (getWidth()-inSize)/2;
		for (int i = 0; i < 8; i++) {
			Point p = GraphicsUtil.getPointThatAroundTheRectByAngle(360/8*i, inSize);
			p.x += reSize;
			p.y += reSize;
			inSidePs.add(p);
		}
		
		LayoutParams lp = (LayoutParams) pointerView.getLayoutParams();
		int width = (int) (getWidth()/4);
		int height = (int) (getWidth()/3);
		lp.width = width;
		lp.height = height;
//		Toast.makeText(getContext(), pointerView.getWidth()+"", 0).show();
		int pox = 7;
		lp.setMargins(inSidePs.get(pox).x-width/2, inSidePs.get(pox).y-height/2, -1, -1);
		pointerView.setRotation(360/8*pox);
	}
	private void init(){
		setBackgroundColor(Color.TRANSPARENT);
		
		density = getResources().getDisplayMetrics().density;
		strokeWidth *= density;
		innerStrokeWidth *= density;
		centerRadius *= density;
		lineWidth *= density;
		
		paint = new Paint();
		
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setAntiAlias(true);
		paint.setDither(false);
		
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(strokeWidth);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		
		pointerView = new ImageView(getContext());
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pointerView.setLayoutParams(lp);
//		pointerView.setScaleType(ScaleType.CENTER);
		pointerView.setImageResource(R.drawable.ic_launcher);
		pointerView.setBackgroundColor(Color.BLUE);
		addView(pointerView);
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//draw border
		paint.setColor(boderColor);
		canvas.drawArc(rect, 0-90, 360, false, paint);
		
		//draw innerBorder
		paint.setStrokeWidth(innerStrokeWidth);
		paint.setColor(innerColor);
		canvas.drawArc(innerRect, 0-90, 360, false, paint);
		//draw innerBg
		paint.setStyle(Style.FILL);
		paint.setColor(innerBgColor);
		canvas.drawOval(innerBgRect, paint);
		
		//draw innerLine
		paint.setColor(innerColor);
		paint.setStrokeWidth(lineWidth);
		float rectSize = getWidth()/2;
		for (Point p : ps) {
			canvas.drawLine(p.x, p.y, rectSize, rectSize, paint);
		}
		for (Point p : inSidePs) {
			paint.setColor(Color.MAGENTA);
			canvas.drawCircle(p.x, p.y,20, paint);
		}
		//
		paint.setColor(centerColor);
		canvas.drawCircle(getWidth()/2, getWidth()/2, centerRadius, paint);
	}
	
}

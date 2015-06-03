package cn.way.wandroid.views;

import cn.way.wandroid.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * StrokeTextView
 * 
 * @author Wayne
 * @2015年6月3日 TODO: strokeColor strokeColorPressed 这两个属性用一个strokeColor来代替
 */
public class StrokeTextView extends TextView {
	private int strokeColor = Color.TRANSPARENT;
	private int strokeColorPressed = strokeColor;
	private int strokeSize = 2;

	public StrokeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public StrokeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.StrokeTextView);
		strokeColor = a.getColor(R.styleable.StrokeTextView_textStrokeColor,
				strokeColor);
		strokeColorPressed = a.getColor(
				R.styleable.StrokeTextView_textStrokeColorPressed, strokeColor);
		strokeSize = a.getDimensionPixelSize(
				R.styleable.StrokeTextView_textStrokeSize, strokeColor);
		a.recycle();
		init();
	}

	public StrokeTextView(Context context) {
		super(context);
		init();
	}

	private void init() {
	}

	@Override
	protected void onDraw(Canvas canvas) {

		ColorStateList textColor = getTextColors();
		TextPaint paint = this.getPaint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeMiter(10);

		setTextColor(isPressed() ? strokeColorPressed : strokeColor);
		paint.setStrokeWidth(strokeSize);

		// draw stroke
		super.onDraw(canvas);

		paint.setStyle(Paint.Style.FILL);
		setTextColor(textColor);
		// draw text
		super.onDraw(canvas);

	}

	@Override
	public void setTextColor(ColorStateList colors) {
		super.setTextColor(colors);
		updateTextStrokeColors();
	}

	@Override
	public void setTextColor(int color) {
		super.setTextColor(color);
		updateTextStrokeColors();
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		if (mTextStrokeColor != null && mTextStrokeColor.isStateful()) {
			updateTextStrokeColors();
		}
	}

	private ColorStateList mTextStrokeColor;
	private int mCurTextStrokeColor;

	private void updateTextStrokeColors() {
		if (mTextStrokeColor == null) {
			return;
		}
		boolean inval = false;
		int color = mTextStrokeColor.getColorForState(getDrawableState(), 0);
		if (color != mCurTextStrokeColor) {
			mCurTextStrokeColor = color;
			inval = true;
		}
		if (inval) {
			invalidate();
		}
	}

}

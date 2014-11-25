package cn.way.wandroid.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * @author Wayne
 *
 */
public class WLabel extends View {
	private String text;
	private float textSize;
	private int textColor = Color.BLACK;
	private TextPaint textPaint;
	private WLabel(Context context) {
		super(context);
		init();
	}

	public WLabel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		if (textPaint==null) {
			textPaint = new TextPaint();
			textPaint.setColor(textColor);
			textPaint.setAntiAlias(true);
		}
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		updateView();
	}
	private void updateView(){
		if (textPaint==null||text==null) {
			return;
		}
		float width = textPaint.measureText(text);
		float height = textSize;
		ViewGroup.LayoutParams lp = getLayoutParams();
		if (lp.width == LayoutParams.WRAP_CONTENT) {
			lp.width = (int) width;
		}
		lp.height = (int) height;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (text==null||textPaint==null) {
			return;
		}
		canvas.drawText(text, 0, 0, textPaint);
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		if (textSize<0||textSize==this.textSize) {
			return;
		}
		this.textSize = textSize;
		if (textPaint!=null) {
			textPaint.setTextSize(textSize);
		}
		updateView();
	}
}

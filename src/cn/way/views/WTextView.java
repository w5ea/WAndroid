package cn.way.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 *自定义的TextView
 *@author Wayne 2012-8-18
 */
public class WTextView extends FrameLayout {
	public static final int STYLE_YELLO_STROKE = 0;
	
	public TextView strokeTextView;
	public TextView textView;
	private String text;
	private Typeface typeface;
	private float textSize;
	private int textColor;
	private int strokeColor;
	private int strokeWidth;
	public WTextView(Context context) {
		super(context);
		this.setupView(0);
	}
	
	public WTextView(Context context,int Style) {
		super(context);
		this.setupView(Style);
	}
	public WTextView(Context context, AttributeSet attrs){
		super(context,attrs);
		this.setupView(0);
	}
	public void setupView(int STYLE){
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.strokeTextView = new TextView(getContext());
		this.strokeTextView.setLayoutParams(lp);
		
		this.textView = new TextView(getContext());
		this.textView.setLayoutParams(lp);
		
		TextPaint tp = this.strokeTextView.getPaint();
		switch (STYLE) {
		case 0:
			tp.setStrokeWidth(2); 
			tp.setStrokeMiter(0.1f);
			tp.setStyle(Style.FILL_AND_STROKE);
			//tp.setFakeBoldText(true);
//			tp.setShadowLayer(3, 2, 2, Color.BLACK);
			this.strokeTextView.setTextColor(Color.DKGRAY);
			
			this.textView.setTextColor(Color.WHITE);
			break;

		default:
			break;
		}
		
		
		
		
//		Typeface face = Typeface.createFromAsset(getContext().getAssets(),
//				"fonts/W5.ttc");
//		this.setTypeface(face);
		
		this.addView(strokeTextView);
		this.addView(textView);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		this.strokeTextView.setText(text);
		this.textView.setText(text);
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
		this.strokeTextView.setTextSize(textSize);
		this.textView.setTextSize(textSize);
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int color) {
		this.textColor = color;
		this.textView.setTextColor(color);
	}

	public Typeface getTypeface() {
		return typeface;
	}

	public void setTypeface(Typeface typeface) {
		this.typeface = typeface;
		this.strokeTextView.setTypeface(typeface);
		this.textView.setTypeface(typeface);
	}

	public int getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(int strokeColor) {
		this.strokeColor = strokeColor;
		this.strokeTextView.setTextColor(strokeColor);
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
		TextPaint tp = this.strokeTextView.getPaint();
		tp.setStrokeWidth(strokeWidth); 
	}

}

package cn.way.wandroid.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import cn.way.wandroid.utils.WTimer;

public class WSteper extends FrameLayout {
	
	// if YES, value change events are sent any time the value changes during interaction. default = YES
//	private boolean continuous; 
	// if YES, press & hold repeatedly alters value. default = YES
//	private boolean autorepeat;                     
	// if YES, value wraps from min <-> max. default = NO
//	private boolean wraps;                          

	// default is 0. sends UIControlEventValueChanged. clamped to min/max
	private double value;                        
	// default 0. must be less than maximumValue
	private double minimumValue;                 
	// default 100. must be greater than minimumValue
	private double maximumValue;                 
	private double stepValue;

	private ImageView minusButton;
	private ImageView plusButton;
	private TextView valueLabel;
	
	private final long TimeInterval = 100;
	
	private boolean isMinusTouchDown;
    private boolean isPlusTouchDown;
    private double holdingMillisecond;
    private WTimer timer;
	
    private ValueChangeListener valueChangeListener;
    
    @Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (timer!=null) {
			timer.cancel();
		}
	}
    
	public WSteper(Context context) {
		this(context,null);
	}
	public WSteper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(getMinusButton()==null)setupView();//TODO why this method called twice
	}
	
	private void setupView(){
		stepValue = 1;
	    maximumValue = 99;
	    
	    setMinusButton(new ImageView(getContext()));
	    getMinusButton().setLayoutParams(new FrameLayout.LayoutParams(getWidth()/3, getHeight(),Gravity.LEFT|Gravity.CENTER_VERTICAL));
	    addView(getMinusButton());
//	    getMinusButton().setBackgroundColor(Color.GREEN);
	    getMinusButton().setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					WSteper.this.touchDownAction(getMinusButton());
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					WSteper.this.touchUpAction(getMinusButton());
					break;
				}
				return true;
			}
		});
	    
	    setValueLabel(new TextView(getContext()));
	    getValueLabel().setLayoutParams(new FrameLayout.LayoutParams(getWidth()/3, getHeight(),Gravity.CENTER));
	    getValueLabel().setTextSize(20);
	    getValueLabel().setTypeface(Typeface.DEFAULT,Typeface.BOLD);
	    getValueLabel().setGravity(Gravity.CENTER);
	    getValueLabel().setTextColor(Color.rgb(54, 52, 53));
	    addView(getValueLabel());
//	    getValueLabel().setBackgroundColor(Color.RED);
	    
	    setPlusButton(new ImageView(getContext()));
	    getPlusButton().setLayoutParams(new FrameLayout.LayoutParams(getWidth()/3, getHeight(),Gravity.RIGHT|Gravity.CENTER_VERTICAL));
	    addView(getPlusButton());
	    getPlusButton().setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					WSteper.this.touchDownAction(getPlusButton());
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
					WSteper.this.touchUpAction(getPlusButton());
					break;
				}
				return true;
			}
		});
//	    getPlusButton().setBackgroundColor(Color.BLUE);
	    updateView();
	}
	
	private void updateView(){
		getValueLabel().setText(String.valueOf((int)getValue()));
	}
	
	private void timeGoesBy(){
	    if (holdingMillisecond<=4*1000) {
	        holdingMillisecond+=TimeInterval*2;
	        int holdSec = (int)(holdingMillisecond*10/1000);
	        if (holdSec%10==0) {
	            if (isPlusTouchDown) {
	                setValue(getValue() + 1);
	            }
	            if (isMinusTouchDown){
	                setValue(getValue() - 1);
	            }
	        }
	    }else{
	        if (isPlusTouchDown) {
	            setValue(getValue() + 1);
	        }
	        if (isMinusTouchDown) {
	            setValue(getValue() - 1);
	        }
	    }
	}

	private void touchDownAction(View sender){
	    holdingMillisecond = 0;
	    if (sender == getMinusButton()) {
	        isMinusTouchDown = true;
	    }else{
	        isPlusTouchDown = true;
	    }
	    if (timer==null) {
	        timer = new WTimer() {
				@Override
				protected void onTimeGoesBy(long totalTimeLength) {
					WSteper.this.timeGoesBy();
				}
			};
			timer.schedule(TimeInterval, null, null);
	    }
	}
	private void touchUpAction(View sender){
	    if (isPlusTouchDown) {
	        setValue(getValue() + 1);
	    }
	    if (isMinusTouchDown) {
	        setValue(getValue() - 1);
	    }
	    isMinusTouchDown = false;
	    isPlusTouchDown = false;
	    holdingMillisecond = 0;
	}
	
	public double getStepValue() {
		return stepValue;
	}

	public void setStepValue(double stepValue) {
		double maxStepValue = getMaximumValue()-getMinimumValue();
	    if (stepValue>maxStepValue) {
	    	this.stepValue = maxStepValue;
	    }else{
	        this.stepValue = stepValue;
	    }
	}

	public double getMinimumValue() {
		return minimumValue;
	}

	public void setMinimumValue(double minimumValue) {
		this.minimumValue = minimumValue;
	    if (minimumValue>getValue()) {
	        setValue(minimumValue);
	    }
	}

	public double getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(double maximumValue) {
		if (maximumValue>=getMinimumValue()) {
	        this.maximumValue = maximumValue;
	        if (maximumValue<getValue()) {
	        	setValue(maximumValue);
	        }
	    }
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		if (value>getMaximumValue()) {
	        value = getMaximumValue();
	    }
	    if (value<getMinimumValue()) {
	        value = getMinimumValue();
	    }
	    this.value = value;
	    updateView();
	    if (this.valueChangeListener!=null) {
			this.valueChangeListener.onValueChanged(value);
		}
	}

	public ValueChangeListener getValueChangeListener() {
		return valueChangeListener;
	}

	public void setValueChangeListener(ValueChangeListener valueChangeListener) {
		this.valueChangeListener = valueChangeListener;
	}

	public ImageView getMinusButton() {
		return minusButton;
	}

	public void setMinusButton(ImageView minusButton) {
		this.minusButton = minusButton;
	}

	public ImageView getPlusButton() {
		return plusButton;
	}

	public void setPlusButton(ImageView plusButton) {
		this.plusButton = plusButton;
	}

	public TextView getValueLabel() {
		return valueLabel;
	}

	public void setValueLabel(TextView valueLabel) {
		this.valueLabel = valueLabel;
	}

	public interface ValueChangeListener{
		void onValueChanged(double value);
	}
}

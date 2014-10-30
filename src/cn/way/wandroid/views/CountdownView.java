package cn.way.wandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import cn.way.wandroid.utils.WTimer;

/**
 * <h3>倒计时组件</h3>
 * <pre>
 * e.g.
 * &lt;cn.way.wandroid.views.CountdownView 
 *       android:id="@+id/countdownView"
 *       android:layout_width="wrap_content"
 *       android:layout_height="wrap_content"
 *       android:textSize="30sp"
 *       android:textColor="#FF0"
 *       android:textStyle="bold"
 *       android:layout_gravity="center_horizontal"/&gt;
 * final CountdownView cv = (CountdownView) findViewById(R.id.countdownView);
 *		cv.start(5);//开始计时
 *		cv.setCountdownListener(new CountdownListener() {
 *			<code>@Override</code>
 *			public void onCountdown(int leftTimeSeconds) {
 *				WLog.d("wwwww");
 *				if (leftTimeSeconds==0) {//计时结束
 *					cv.stop();
 *				}
 *			}
 *		});
 * </pre>
 * @author Wayne
 * 
 */
public class CountdownView extends TextView {
	private WTimer timer;
	private int leftTimeSeconds;
	private CountdownListener countdownListener;

	public CountdownView(Context context) {
		super(context);
		init();
	}

	public CountdownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CountdownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		updateView();
	}

	public void start() {
		if (timer == null) {
			timer = new WTimer() {
				@Override
				protected void onTimeGoesBy(long totalTimeLength) {
					leftTimeSeconds--;
					if (leftTimeSeconds <= 0) {
						leftTimeSeconds = 0;
					}
					updateView();
				}
			};
			timer.schedule(1000L, null, null);
		}
	}

	public void start(int leftTimeSeconds) {
		setLeftTimeSeconds(leftTimeSeconds);
		start();
	}

	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	private void updateView() {
		if (countdownListener != null) {
			countdownListener.onCountdown(leftTimeSeconds);
		}
		setText(String.format("%02d:%02d:%02d", getLeftTimeSeconds() / 60 / 60,
				getLeftTimeSeconds() / 60 % 60, getLeftTimeSeconds() % 60));
	}

	@Override
	protected void onDetachedFromWindow() {
		stop();
		super.onDetachedFromWindow();
	}

	public int getLeftTimeSeconds() {
		return leftTimeSeconds;
	}

	public void setLeftTimeSeconds(int leftTimeSeconds) {
		if (leftTimeSeconds < 0) {
			return;
		}
		this.leftTimeSeconds = leftTimeSeconds;
		updateView();
	}

	public CountdownListener getCountdownListener() {
		return countdownListener;
	}

	public void setCountdownListener(CountdownListener countdownListener) {
		this.countdownListener = countdownListener;
	}

	public interface CountdownListener {
		void onCountdown(int leftTimeSeconds);
	}
}

package cn.way.wandroid.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
/* e.g.
	AsyncTimer timer = new AsyncTimer(activity) {
		@Override
		protected void onTimeGoesBy(long totalTimeLength) {
			if(totalTimeLength>1000l*5){
				this.cancel();
			}
			tv.setTextColor(Color.RED);
			tv.setText(totalTimeLength+"");
			util.log("totalTimeLength= "+totalTimeLength);
		}
	}; 
	timer.schedule(1000l, 1000l*10, 1000l*3);
*/
/**
 * 计时工具类
 * @author Wayne
 *
 */
public abstract class WAsyncTimer {
	// TODO 通过 Timer 来做时间的更新 新在每次更新时调用 ITimeGoesByCallback接口方法，(何时停止任务由调用方决定)
	private Long timeInterval = 1L;// 时间变化间隔，默认为1/1000秒
	private Long timeLimit = timeInterval;// 最大时长限制，默认为timeInterval的值
	private Timer timer ;
	private long totalTimeLength = 0;
	private Long pausedTimeLenght = 0L;
	private Long delay;
	private Handler handler ;
	private WAsyncTimer self;
	public WAsyncTimer(Activity activity) {
		handler = new Handler(activity.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				onTimeGoesBy(totalTimeLength);
			}
		};
		self = this;
	}

	public void pause(long timeLength) {
		this.pausedTimeLenght += timeLength;
	}

	public boolean isPaused() {
		return this.pausedTimeLenght > 0;
	}

	public void schedule(Long timeInterval, Long timeLimit, Long delay) {
		setTimeInterval(timeInterval);
		setTimeLimit(timeLimit);
		setDelay(delay);
		if (timer==null) {
			timer = new Timer();
		}else{
			cancel();
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (pausedTimeLenght > 0) {
					pausedTimeLenght -= self.timeInterval;
					return;
				}
				if (self.timeLimit != null && totalTimeLength >= self.timeLimit) {
					cancel();
				} else {
					totalTimeLength += self.timeInterval;
					handler.sendEmptyMessage(0);
				}
			}
		}, timeInterval, timeInterval);
	}

	protected abstract void onTimeGoesBy(long totalTimeLength);
	
	public void cancel() {
		if (timer!=null) {
			timer.cancel();
		}
	}
	
	public float getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Long timeInterval) {
		if (timeInterval != null)
			this.timeInterval = timeInterval;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Long getDelay() {
		return delay;
	}

	public void setDelay(Long delay) {
		this.delay = delay;
		if (delay != null)
			this.pausedTimeLenght = delay;
	}

}
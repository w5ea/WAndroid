package cn.way.wandroid.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * 计时工具类
 * @author Wayne
 *
 */
@SuppressLint("HandlerLeak")
public abstract class WAsyncTimer {
	// TODO 通过 Timer 来做时间的更新 新在每次更新时调用 ITimeGoesByCallback接口方法，(何时停止任务由调用方决定)
	private Long timeInterval = 1L;// 时间变化间隔，默认为1/1000秒
	private Long timeLimit = timeInterval;// 最大时长限制，默认为timeInterval的值
	private Timer timer ;
	private long totalTimeLength = 0;
	private Long pausedTimeLenght = 0L;
	private Long delay;
	private Handler handler ;
	
	public WAsyncTimer(Long timeInterval, Long timeLimit, Long delay) {
		setTimeInterval(timeInterval);
		setTimeLimit(timeLimit);
		setDelay(delay);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				onTimeGoesBy(totalTimeLength);
			}
		};
	}

	public void pause(long timeLength) {
		this.pausedTimeLenght += timeLength;
	}

	public boolean isPaused() {
		return this.pausedTimeLenght > 0;
	}

	public void schedule() {
		if (timer!=null) {
			cancel();
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (pausedTimeLenght > 0) {
					pausedTimeLenght -= timeInterval;
					return;
				}
				if (timeLimit != null && totalTimeLength >= timeLimit) {
					cancel();
				} else {
					totalTimeLength += timeInterval;
					handler.sendEmptyMessage(0);
				}
			}
		}, timeInterval, timeInterval);
	}

	protected abstract void onTimeGoesBy(long totalTimeLength);
	
	public void cancel() {
		if (timer!=null) {
			timer.cancel();
			timer = null;
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
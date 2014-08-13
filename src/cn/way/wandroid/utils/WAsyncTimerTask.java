package cn.way.wandroid.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.os.AsyncTask;

/**
 * 计时任务工具类
 * 
 * @author Wayne 2012-8-21
 */
/* e.g.
final TextView tv = (TextView) findViewById(R.id.editText1);
WAsyncTimerTask timer = new WAsyncTimerTask(100l,100l*1000){
	@Override
	protected void onProgressUpdate(Long... values) {
		tv.setText(values[0]+"");
	}
};
timer.execute();
*/
public class WAsyncTimerTask extends AsyncTask<Void, Long, Long> {
	// TODO 通过 Timer 来做时间的更新 新在每次更新时调用 ITimeGoesByCallback接口方法，(何时停止任务由调用方决定)
	private Long timeInterval = 1L;// 时间变化间隔，默认为1/1000秒
	private Long timeLimit = timeInterval;// 最大时长限制，默认为timeInterval的值
	private Timer timer = new Timer();
	public long totalTimeLength = 0;
	public Long pausedTimeLenght = 0L;

	public WAsyncTimerTask(Long timeInterval, Long timeLimit, Long delay) {
		if (timeInterval != null)
			this.timeInterval = timeInterval;
		this.timeLimit = timeLimit;
		if (delay != null)
			this.pausedTimeLenght = delay;
	}

	public void pause(long timeLength) {
		this.pausedTimeLenght += timeLength;
	}

	public boolean isPaused() {
		return this.pausedTimeLenght > 0;
	}

	@Override
	protected Long doInBackground(Void... params) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (pausedTimeLenght > 0) {
					pausedTimeLenght -= timeInterval;
					return;
				}
				if (timeLimit != null && totalTimeLength >= timeLimit) {
					timer.cancel();
					timer = null;
					// 在TIMER中无法成功调用AsyncTask的CANCEL方法
				} else {
					totalTimeLength += timeInterval;
					publishProgress(totalTimeLength);
				}
			}
		}, timeInterval, timeInterval);
		return totalTimeLength;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Long result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		timer.cancel();
		timer = null;
	}

	public float getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(long timeInterval) {
		this.timeInterval = timeInterval;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

}
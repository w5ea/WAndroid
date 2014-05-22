package cn.way.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.os.AsyncTask;

/**
 * ��ʱ���񹤾���
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
	// TODO ͨ�� Timer ����ʱ��ĸ��� ����ÿ�θ���ʱ���� ITimeGoesByCallback�ӿڷ�����(��ʱֹͣ�����ɵ��÷�����)
	private Long timeInterval = 1l;// ʱ��仯�����Ĭ��Ϊ1/1000��
	private Long timeLimit = timeInterval;// ���ʱ�����ƣ�Ĭ��ΪtimeInterval��ֵ
	private Timer timer = new Timer();
	public long totalTimeLength = 0;
	public Long pausedTimeLenght;
	

	public WAsyncTimerTask(Long timeInterval, Long timeLimit,Long delay) {
		if(timeInterval!=null)this.timeInterval = timeInterval;
		this.timeLimit = timeLimit;
		if(delay!=null)this.pausedTimeLenght = delay;
	}

	public void pause(long timeLength){
		this.pausedTimeLenght+=timeLength;
	}
	public boolean isPaused(){
		return this.pausedTimeLenght>0;
	}
	
	@Override
	protected Long doInBackground(Void... params) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(pausedTimeLenght>0){
					pausedTimeLenght-=timeInterval;
					return;
				}
				if (timeLimit!=null&&totalTimeLength >= timeLimit) {
					timer.cancel();
					timer = null;
					// ��TIMER���޷��ɹ�����AsyncTask��CANCEL����
				}else{
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
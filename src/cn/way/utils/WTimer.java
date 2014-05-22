package cn.way.utils;


/**
 *Ê±¼äÀà
 *@author Wayne 2012-8-22
 */
/* e.g.
 		WTimer wt = new WTimer() {
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
		wt.schedule(1000l, 1000l*10, 1000l*3);
 */
public abstract class WTimer {
	private WAsyncTimerTask task;
	public WTimer schedule(Long timeInterval, Long timeLimit,Long delay){
		task = new WAsyncTimerTask(timeInterval,timeLimit,delay){
			@Override
			protected void onProgressUpdate(Long... values) {
				WTimer.this.onTimeGoesBy(totalTimeLength);
			}
		};
		task.execute();
		return this;
	}
	protected abstract void onTimeGoesBy(long totalTimeLength) ;
	
	public boolean cancel(){
		boolean result = task.cancel(true);
		if (result) {
			task = null;
		}else{//may it never happen
			task.setTimeLimit(0);
		}
		return result;
	}
	
}

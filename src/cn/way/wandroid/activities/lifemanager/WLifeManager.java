package cn.way.wandroid.activities.lifemanager;

import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import cn.way.wandroid.utils.WTimer;

public class WLifeManager {
	private int maxLifeCount;// 可恢复到的最大生命值
	private int lifePlusTimeInterval;// 恢复生命的时间间隔
	private int lifePlusLeftTimeSeconds;// 恢复一条生命剩余秒数
	private int lifeCount;// 当前生命值，目前不设置上限
	private long totalLeftTimeSeconds;// 恢复到最大值一共要用的时间
	private Context context;
	private WTimer timer;
	WLifeManagerListener listener;

	public WLifeManager(Context context) {
		this.context = context;
	}

	// 调用这个方法会启动一个计时器并通过倒计时来动态减少回复生命时间
	/** 启动但使用本地保存的数据,使用前应该确认之前保存过数据 */
	public void start() {
		maxLifeCount = readMaxLifeCount();
		lifePlusTimeInterval = readLifePlusTimeInterval();
		totalLeftTimeSeconds = readTotalLeftTimeSeconds();

		if (totalLeftTimeSeconds > 0) {
			lifeCount = lifeCountWithTotalLeftTimeSeconds(totalLeftTimeSeconds);

			if (totalLeftTimeSeconds > lifePlusTimeInterval) {
				lifePlusLeftTimeSeconds = (int) (totalLeftTimeSeconds % lifePlusTimeInterval);
			} else {
				lifePlusLeftTimeSeconds = (int) totalLeftTimeSeconds;
			}
		} else {
			lifeCount = readLifeCount();
			lifePlusLeftTimeSeconds = 0;
		}
		startTimer();
	}

	/**
	 * 启动但使用传入的数据，并更新本地保存的数据
	 * 
	 * @param maxLifeCount
	 *            可恢复到的最大生命值
	 * @param lifeCount
	 *            当前生命值
	 * @param lifePlusTimeInterval
	 *            生命增加一条的间隔秒数
	 * @param lifePlueLeftTimeSeconds
	 *            增加一条生命剩余秒数
	 */
	public void start(int maxLifeCount, int lifeCount,
			int lifePlusTimeInterval, int lifePlueLeftTimeSeconds) {
		sync(maxLifeCount, lifeCount, lifePlusTimeInterval,
				lifePlueLeftTimeSeconds);
		startTimer();
	}
	
	/**
	 * 暂停停倒计时
	 */
	public void pause() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}
	/**
	 * 恢复倒计时
	 */
	public void resume() {
		if (timer == null){
			start();
		}
	}

	/**
	 * 停止倒计时
	 */
	public void stop() {
		pause();
	}

	/**
	 * 增加一条生命
	 */
	public void lifeMinusOne() {
		if (lifeCount == 0) {
			return;
		}

		if (lifeCount > maxLifeCount) {
			lifeCount -= 1;
			lifeCountChanged();
		} else {

			if (isLifeFull()) {
				lifePlusLeftTimeSeconds = lifePlusTimeInterval;
			} else {

			}
			lifeCount -= 1;
			lifeCountChanged();

			totalLeftTimeSeconds += lifePlusTimeInterval;
			lifePlusLeftTimeChanged();
		}
	}
	/**
	 * 减少一条生命
	 */
	public void lifePlusOne() {
		if (isLifeFull()) {
			lifeCount += 1;
			lifeCountChanged();
		} else {
			lifeCount += 1;
			lifeCountChanged();

			totalLeftTimeSeconds -= lifePlusTimeInterval;
			// 增加一次生命，并重新设置停止记时时间（把之前的时间改变为LIVF_PLUS_SECONDS之前）
			if (isLifeFull()) {
				totalLeftTimeSeconds = 0;
				lifePlusLeftTimeSeconds = 0;
				lifePlusLeftTimeChanged();
			}
		}
	}
	/**
	 * 是否生命已经恢复到最大值
	 * @return true 表示已经到最大值
	 */
	public boolean isLifeFull() {
		return lifeCount >= maxLifeCount;
	}
	
	// ----------------------------------------------
	/**同步数据到本地*/
	private void sync(int maxLifeCount, int lifeCount,
			int lifePlusTimeInterval, int lifePlusLeftTimeSeconds) {
		// 保存最大可恢复生命数，和恢复时间间隔
		persistMaxLifeCount(maxLifeCount);
		persistLifePlusTimeInterval(lifePlusTimeInterval);
		// 如果生命数大于可恢复生命最大值，则不需要恢复生命
		if (lifeCount >= maxLifeCount) {
			totalLeftTimeSeconds = 0;
			lifePlusLeftTimeSeconds = 0;
			persistLifeCount(lifeCount);
		} else {
			totalLeftTimeSeconds = (maxLifeCount - lifeCount - 1)
					* lifePlusTimeInterval + lifePlusLeftTimeSeconds;
		}
		// 保存结束日期通过前面计算得到的总剩余时间
		persistFinishDateWithTimeIntervalSince1970(new Date().getTime() / 1000
				+ totalLeftTimeSeconds);

		this.maxLifeCount = maxLifeCount;
		this.lifePlusTimeInterval = lifePlusTimeInterval;
		this.lifeCount = lifeCount;
		this.lifePlusLeftTimeSeconds = lifePlusLeftTimeSeconds;
		lifeCountChanged();
		lifePlusLeftTimeChanged();
	}

	private void startTimer() {
		lifeCountChanged();
		lifePlusLeftTimeChanged();

		// 无论是否计时都开一个计时器。这样当生命减少时不用做额外设置
		if (timer != null) {
			pause();
		}
		timer = new WTimer() {
			@Override
			protected void onTimeGoesBy(long totalTimeLength) {
				doTimeGoesBy();
			}
		};
		timer.schedule(1000l, null, null);
	}

	private void doTimeGoesBy() {
//		DataManager.log("ttttttttt:" + totalLeftTimeSeconds);
		if (totalLeftTimeSeconds > 0) {

			totalLeftTimeSeconds--;

			lifePlusLeftTimeSeconds--;

			if (lifePlusLeftTimeSeconds <= 0) {

				lifeCount += 1;
				lifeCountChanged();

				if (isLifeFull()) {
					lifePlusLeftTimeSeconds = 0;
					totalLeftTimeSeconds = 0;
				} else {
					lifePlusLeftTimeSeconds = lifePlusTimeInterval;
				}
			}

			lifePlusLeftTimeChanged();
		}
	}

	private void lifePlusLeftTimeChanged() {
		if (listener != null) {
			listener.lifePlusLeftTimeChanged(lifePlusLeftTimeSeconds);
		}
	}

	private void lifeCountChanged() {
		if (listener != null) {
			listener.lifeCountChanged(lifeCount,maxLifeCount);
		}
		if(isLifeFull()){
			persistLifeCount(lifeCount);
		}
	}

	private int lifeCountWithTotalLeftTimeSeconds(long totalLeftTimeSeconds) {
		// 如果生命已经满了，读取本地保存的生命数
		int maxLifeCount = readMaxLifeCount();
		if (totalLeftTimeSeconds <= 0) {
			return maxLifeCount;
		}
		// 否则计算出当前生命数量
		int lifePlusTimeInterval = readLifePlusTimeInterval();
		int weakLifeCount = 1;
		if (totalLeftTimeSeconds > lifePlusTimeInterval) {
			weakLifeCount = (int) Math.ceil(totalLeftTimeSeconds
					/ (float)lifePlusTimeInterval);
		}
		return maxLifeCount - weakLifeCount;
	}

	// ----------------------------------------------
	private static String NAME_OF_SP = "LIFE_MANAGER_SHAREDPREFERENCES";

	private SharedPreferences getSp() {
		return context.getSharedPreferences(NAME_OF_SP, Context.MODE_PRIVATE);
	}

	private long readTotalLeftTimeSeconds() {
		long finishDateTimeIntervalSince1970 = readFinishDateTimeIntervalSince1970();
		if (finishDateTimeIntervalSince1970 > 0) {
			long left = finishDateTimeIntervalSince1970 - new Date().getTime()
					/ 1000;
			return left > 0 ? left : 0;
		}
		return 0;
	}

	private static String KEY_MAX_LIFE_COUNT = "KEY_MAX_LIFE_COUNT";

	private int readMaxLifeCount() {
		return getSp().getInt(KEY_MAX_LIFE_COUNT, 10);
	}

	private void persistMaxLifeCount(int maxLifeCount) {
		getSp().edit().putInt(KEY_MAX_LIFE_COUNT, maxLifeCount).commit();
	}
	
	private static String KEY__LIFE_COUNT = "KEY__LIFE_COUNT";
	
	private int readLifeCount() {
		return getSp().getInt(KEY__LIFE_COUNT, 10);
	}
	
	private void persistLifeCount(int lifeCount) {
		getSp().edit().putInt(KEY__LIFE_COUNT, lifeCount).commit();
	}

	private static String KEY_LIFEPLUSTIMEINTERVAL = "KEY_LIFEPLUSTIMEINTERVAL";

	private int readLifePlusTimeInterval() {
		return getSp().getInt(KEY_LIFEPLUSTIMEINTERVAL, 60 * 5);
	}

	private void persistLifePlusTimeInterval(int lifePlusTimeInterval) {
		getSp().edit().putInt(KEY_LIFEPLUSTIMEINTERVAL, lifePlusTimeInterval)
				.commit();
	}

	private static String KEY_FINISH_DATE_TIMEINTERVAL_SINCE_1970 = "KEY_FINISH_DATE";

	/**
	 * 在同步时调用一次来记录结束时间，并通过这个结束时间来取得剩余总时间
	 * @param timeIntervalSince1970
	 */
	private void persistFinishDateWithTimeIntervalSince1970(
			long timeIntervalSince1970) {
		getSp().edit()
				.putLong(KEY_FINISH_DATE_TIMEINTERVAL_SINCE_1970,
						timeIntervalSince1970).commit();
	}

	private long readFinishDateTimeIntervalSince1970() {
		return getSp().getLong(KEY_FINISH_DATE_TIMEINTERVAL_SINCE_1970, 0);
	}

	// private Date readFinishDate(){
	// long timeInterval = readFinishDateTimeIntervalSinceNow();
	// if (timeInterval>0) {
	// return new Date(timeInterval*1000);
	// }
	// return null;
	// }
	// ----------------------------------------------
	public int getMaxLifeCount() {
		return maxLifeCount;
	}

	public int getLifePlusLeftTimeSeconds() {
		return lifePlusLeftTimeSeconds;
	}

	public int getLifeCount() {
		return lifeCount;
	}

	public long getTotalLeftTimeSeconds() {
		return totalLeftTimeSeconds;
	}

	public void setListener(WLifeManagerListener listener) {
		this.listener = listener;
	}

	public static interface WLifeManagerListener {
		void lifePlusLeftTimeChanged(int lifePlusLeftTimeSeconds);

		void lifeCountChanged(int currentLifeCount,int maxLifeCount);
	}
}

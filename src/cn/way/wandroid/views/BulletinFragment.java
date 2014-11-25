package cn.way.wandroid.views;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.way.wandroid.utils.AppGuider;
import cn.way.wandroid.utils.WTimer;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.ViewHelper;

public class BulletinFragment extends Fragment {
	private ViewGroup bulletinView;
	private TextView bulletinTextView;
//	private static final String kBulletinIndex = "kBulletinIndex";
	private ArrayList<String> bulletins = new ArrayList<String>();
	private int bulletinIndex;
//	private float mDensity;
	private WTimer timer;
	
	private final long ChangeDur = 20;
	private ValueAnimator animator;
	private long speed;
	private BulletinState state;
	private final long SPEED = 3;
	private final long SHOWING_SPEED = 10;
	private final long WAIT_DURATION = 1000*3;
	private float bulletinWidth;
	
	private enum BulletinState{
		Showing,Waiting,Hiding;
	}
	public void startAnimation() {
		if (bulletins != null && bulletins.size() > 0) {
			if(bulletinTextView!=null){
				setBeShowingState();
				if(animator!=null)animator.start();
			};
		}
	}

	public void stopAnimation(){
		if (animator!=null) {
			animator.cancel();
//			animator.end();
//			animator = null;
		}
	}
	
	private void setBeShowingState(){
		bulletinIndex++;
		if (bulletinIndex + 1 >= bulletins.size()) {
			bulletinIndex = 0;
		}
		String text = " ";
		if(bulletins.size()>0)text = bulletins.get(bulletinIndex);
		TextPaint fontPaint = bulletinTextView.getPaint();
		bulletinWidth = (float) Math.ceil(fontPaint.measureText(text)*1.1f);
		bulletinTextView.getLayoutParams().width = (int) bulletinWidth;
		bulletinTextView.setText(text);
		
		ViewHelper.setX(bulletinTextView,getView().getWidth());
		speed = SHOWING_SPEED;
		state = BulletinState.Showing;
	}
	private void setBeHidingState(){
		speed = SPEED;
		state = BulletinState.Hiding;
	}
	private long lastTime = -1;
	private boolean isWaitTimeup(){
		long currentTime = System.currentTimeMillis();
		if (lastTime == -1) {
			lastTime = currentTime;
		}
		if (currentTime-lastTime<WAIT_DURATION) {
			return false;
		}else{
			lastTime = -1;
			return true;
		}
	}
	private void updateX(){
		float x = ViewHelper.getX(bulletinTextView);
		if (state == BulletinState.Showing) {
			if (x<0) {
				ViewHelper.setX(bulletinTextView, 0);
				state = BulletinState.Waiting;
			}
		}
		if (state == BulletinState.Waiting) {
			if (!isWaitTimeup()) {
				return;
			}else{
				setBeHidingState();
			}
		}
		if (state  == BulletinState.Hiding) {
			if (x <= -bulletinWidth) {
				setBeShowingState();
			}
		}
		ViewHelper.setX(bulletinTextView, x-speed);
	}
	
	private long requestTimeInterval = 1000*60*5;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (timer==null) {
			timer = new WTimer() {
				@Override
				protected void onTimeGoesBy(long totalTimeLength) {
					requestNewBulletin();
				}
			};
			timer.schedule(requestTimeInterval, null, requestTimeInterval);
		}
		requestNewBulletin();
	}
	
	
	public void requestNewBulletin(){
		if (AppGuider.isGuideMode()) {
			return;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		bulletinView = new FrameLayout(getActivity());
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		bulletinView.setLayoutParams(lp);

		bulletinTextView = new TextView(getActivity());
//		bulletinTextView.setBackgroundColor(Color.GREEN);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		bulletinTextView.setLayoutParams(lp);
		bulletinTextView.setGravity(Gravity.CENTER_VERTICAL);
		bulletinTextView.setTextColor(Color.rgb(224, 192, 115));
		bulletinTextView.setTextSize(getTextSize());
		bulletinTextView.setSingleLine();
		bulletinView.addView(bulletinTextView);
		
//		Toast.makeText(getActivity(), "onCreateViewonCreateViewonCreateView", 0).show();
		
		return bulletinView;
	}
	private float getTextSize(){
		return 14;//getResources().getDimension(R.dimen.bulletin_text);
	}
	@Override
	public void onResume() {
		super.onResume();
		if (animator == null) {
			animator = ValueAnimator.ofFloat(0, ChangeDur);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator arg0) {
					updateX();
				}
			});
			animator.setRepeatCount(ValueAnimator.INFINITE);
			animator.setDuration(3000);
		}
		startAnimation();
	}
	@Override
	public void onPause() {
		super.onPause();
		stopAnimation();
	}
	@Override
	public void onStop() {
		super.onStop();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (animator!=null) {
			animator.end();
			animator = null;
		}
		if (timer!=null) {
			timer.cancel();
			timer = null;
		}
	}
}

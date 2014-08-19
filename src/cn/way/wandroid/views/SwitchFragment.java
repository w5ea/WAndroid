package cn.way.wandroid.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import cn.way.wandoird.R;

public class SwitchFragment extends Fragment {
	private static final String TAG = "MTAB";
	private View switchView ;
	private float originalSwitchY;
	private boolean isReseting;
	private boolean isOn;
	private SwithListener switchListener;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_switch, container, false);
	}
	
	private float lastY;
	private final float SwitchMaxY = 0;
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		switchView =  view.findViewById(R.id.view_switch);
		switchView.setClickable(true);
		switchView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	        @Override
	        public void onGlobalLayout() {
	            // Ensure you call it only once :
	            switchView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	            originalSwitchY = switchView.getY();
	    		Log.d(TAG,"originalSwitchY="+originalSwitchY);
	        }
	    });
		
		switchView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction()==MotionEvent.ACTION_DOWN) {
					lastY = event.getY();
				}
				if (event.getAction()==MotionEvent.ACTION_MOVE&&!isReseting) {
					float newY = event.getY();
					float moveDistance = newY - lastY;
					lastY = newY;
//					Log.d(TAG,"MOVE="+moveDistance);
					float originalY = v.getY();
					if (moveDistance>0&&originalY<SwitchMaxY) {
						float moveToY = originalY+moveDistance;
						if (moveToY>=SwitchMaxY) {
							moveToY = SwitchMaxY;
							isOn = !isOn;
							resetSwitch(false);
							if (switchListener!=null) {
								switchListener.onSwitch(isOn);
							}
						}
						v.setY(moveToY);
					}
				}
				if (event.getAction()==MotionEvent.ACTION_UP||event.getAction()==MotionEvent.ACTION_CANCEL) {
					resetSwitch(false);
				}
				return false;
			}
		});
	}
	
	void resetSwitch(boolean animated){
		if(!animated){
			switchView.setY(originalSwitchY);
			return;
		}
		
		if (isReseting) {
			return;
		}
		isReseting = true;
		TranslateAnimation ani =  new TranslateAnimation(0,0,switchView.getY(), originalSwitchY);
		ani.setDuration(8500);
		ani.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				switchView.setY(originalSwitchY);
				isReseting = false;
			}
		});
		switchView.startAnimation(ani);
	}
	public SwithListener getSwitchListener() {
		return switchListener;
	}

	public void setSwitchListener(SwithListener switchListener) {
		this.switchListener = switchListener;
	}
	public interface SwithListener{
		void onSwitch(boolean isOn);
	}
}

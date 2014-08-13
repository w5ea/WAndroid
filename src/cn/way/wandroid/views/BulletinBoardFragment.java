package cn.way.wandroid.views;

import java.util.ArrayList;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.way.wandroid.DataManager;

public class BulletinBoardFragment extends Fragment {
	private ViewGroup bulletinView;
	private TextView bulletinTextView;
	private static final String kBulletinIndex = "kBulletinIndex";
	private ArrayList<String> bulletins = new ArrayList<String>();
	private int bulletinIndex;
	private float mDensity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String text = "this is bulletin : ";
		for (int i = 0; i < 10; i++) {
			text += text;
			bulletins.add(text+(i+1));
		}
		if(savedInstanceState!=null){
			bulletinIndex = savedInstanceState.getInt(kBulletinIndex);
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mDensity = getResources().getDisplayMetrics().density;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState==null) {
			bulletinView = new FrameLayout(getActivity());
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (60*mDensity));
			bulletinView.setLayoutParams(lp);
			
			bulletinTextView = new TextView(getActivity());
			lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
			bulletinTextView.setLayoutParams(lp);
			bulletinTextView.setTextColor(Color.RED);
			bulletinView.addView(bulletinTextView);
			bulletinTextView.setText("sdfsadfasdfasdfasdfasdfasdfs");
		}
		return bulletinView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		doAnimationForView(bulletinTextView);
	}
	@Override
	public void onStop() {
		super.onStop();
		bulletinTextView.clearAnimation();
	}
	
	private void doAnimationForView(final View targetView){
		final float viewWidth = targetView.getWidth();
		float startX = 0;
		targetView.setX(startX);
		TranslateAnimation leaveAnimation = new TranslateAnimation(startX, -viewWidth, targetView.getTop(), targetView.getTop());
		leaveAnimation.setDuration(10000);
		leaveAnimation.setStartOffset(2000);
		leaveAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				targetView.setX(viewWidth);
				
				bulletinIndex++;
				if (bulletinIndex+1>=bulletins.size()) {
					bulletinIndex = 0;
				}
				String text = bulletins.get(bulletinIndex);
				TextPaint FontPaint = new TextPaint();
			    FontPaint.setTextSize(bulletinTextView.getTextSize());
			    float width = FontPaint.measureText(text);
			    LayoutParams lp = bulletinTextView.getLayoutParams();
			    lp.width = (int) width;
			    bulletinTextView.setLayoutParams(lp);
				bulletinTextView.setText(text);
				
				TranslateAnimation turnOutAnimation = new TranslateAnimation(viewWidth, 0, targetView.getTop(), targetView.getTop());
				turnOutAnimation.setDuration(1000);
				turnOutAnimation.setInterpolator(new DecelerateInterpolator(1.0f));
				turnOutAnimation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					@Override
					public void onAnimationEnd(Animation animation) {
						doAnimationForView(targetView);
					}
				});
				targetView.startAnimation(turnOutAnimation);
			}
		});
		targetView.startAnimation(leaveAnimation);
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(kBulletinIndex, bulletinIndex);
	}
}

package cn.way.wandroid.views;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.way.wandoird.R;

public class BulletinBoardFragment extends Fragment {
	private ViewGroup bulletinView;
	private TextView bulletinTextView;
//	private static final String kBulletinIndex = "kBulletinIndex";
	private ArrayList<String> bulletins = new ArrayList<String>();
	private int bulletinIndex;
	private boolean isPaused;
//	private float mDensity;

	public void updateBulletins(ArrayList<String> bulletins) {
		this.bulletins.clear();
		bulletinIndex = -1;
		this.bulletins.addAll(bulletins);
	}

	public void startAnimation() {
		if (bulletins != null && bulletins.size() > 0) {
			stopAnimation();
			isPaused = false;
			doAnimationForView(bulletinTextView);
		}
	}

	public void stopAnimation(){
		isPaused = true;
		bulletinTextView.clearAnimation();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		if (savedInstanceState != null) {
//			bulletinIndex = savedInstanceState.getInt(kBulletinIndex);
//		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		 mDensity = getResources().getDisplayMetrics().density;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState == null) {
			bulletinView = new FrameLayout(getActivity());
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			bulletinView.setLayoutParams(lp);

			bulletinTextView = new TextView(getActivity());
			lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			bulletinTextView.setLayoutParams(lp);
			bulletinTextView.setTextColor(Color.WHITE);
			bulletinTextView.setTextSize(getResources().getDimension(R.dimen.bulletin_text));
			bulletinView.addView(bulletinTextView);
		}
		return bulletinView;
	}

	@Override
	public void onResume() {
		super.onResume();
		startAnimation();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		stopAnimation();
	}

	private static final long SPEED = 85;

	private void doAnimationForView(final View targetView) {
		final float viewWidth = targetView.getWidth();
		float startX = 0;
		FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) targetView
				.getLayoutParams();
		lp.setMargins((int) startX, lp.topMargin, lp.rightMargin,
				lp.bottomMargin);
		// targetView.setX(startX);//api11 use this
		targetView.setLayoutParams(lp);
		TranslateAnimation leaveAnimation = new TranslateAnimation(startX,
				-viewWidth, targetView.getTop(), targetView.getTop());
		leaveAnimation.setDuration((long) (viewWidth / SPEED * 1000));
		leaveAnimation.setStartOffset(3000);
		leaveAnimation.setInterpolator(new LinearInterpolator());
		leaveAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (!isPaused) {
					FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) targetView
							.getLayoutParams();
					// lp.setMargins(0, lp.topMargin, lp.rightMargin,
					// lp.bottomMargin);
					// targetView.setLayoutParams(lp);
					// targetView.setX(viewWidth);
					bulletinIndex++;
					if (bulletinIndex + 1 >= bulletins.size()) {
						bulletinIndex = 0;
					}
					String text = " ";
					if(bulletins.size()>0)text = bulletins.get(bulletinIndex);
					TextPaint fontPaint = new TextPaint();
					fontPaint.setTextSize(bulletinTextView.getTextSize());
					float width = fontPaint.measureText(text);
					lp.width = (int) Math.ceil(width);
					bulletinTextView.setLayoutParams(lp);
					bulletinTextView.setText(text);
					TranslateAnimation turnOutAnimation = new TranslateAnimation(
							bulletinView.getWidth(), 0, targetView.getTop(),
							targetView.getTop());
					turnOutAnimation.setDuration(750);
					turnOutAnimation
							.setInterpolator(new DecelerateInterpolator(1.0f));
					turnOutAnimation
							.setAnimationListener(new AnimationListener() {
								@Override
								public void onAnimationStart(Animation animation) {
								}

								@Override
								public void onAnimationRepeat(
										Animation animation) {
								}

								@Override
								public void onAnimationEnd(Animation animation) {
									if (!isPaused) {
										doAnimationForView(targetView);
									}
								}
							});
					targetView.startAnimation(turnOutAnimation);
				}
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
//		super.onSaveInstanceState(outState);
//		outState.putInt(kBulletinIndex, bulletinIndex);
	}
}

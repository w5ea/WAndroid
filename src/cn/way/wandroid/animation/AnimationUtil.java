package cn.way.wandroid.animation;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;

public class AnimationUtil {
	public static void doExchangeAnimation(final View viewToShow, final View viewToHide) {
		long dur = 5000;
		float scaleValue = 0.75f;
		FlipAnimation fa1 = new FlipAnimation(90, 0, viewToHide.getWidth() / 2,
				0, scaleValue, FlipAnimation.ScaleUpDownEnum.SCALE_UP);
		fa1.setDuration(dur);
		fa1.setDirection(FlipAnimation.ROTATION_X);
		fa1.setInterpolator(new AccelerateInterpolator(0.75f));
		viewToHide.startAnimation(fa1);

		FlipAnimation fa2 = new FlipAnimation(0, -90,
				viewToShow.getWidth() / 2, viewToShow.getHeight(), scaleValue,
				FlipAnimation.ScaleUpDownEnum.SCALE_DOWN);
		fa2.setDuration(dur);
		fa2.setDirection(FlipAnimation.ROTATION_X);
		viewToShow.startAnimation(fa2);

		fa2.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				doExchangeAnimation(viewToShow,viewToHide);
			}
		});
	}
	public static void doFlipAnimation(final long dur,final View viewToShow, final View viewToHide,final AnimationListener l) {
		final float scaleValue = 1;
		viewToShow.setVisibility(View.INVISIBLE);
		viewToHide.setVisibility(View.VISIBLE);
		FlipAnimation fa1 = new FlipAnimation(0, -90, viewToHide.getWidth() / 2,
				viewToHide.getHeight()/2, scaleValue, FlipAnimation.ScaleUpDownEnum.SCALE_NONE);
		fa1.setDuration(dur);
		fa1.setDirection(FlipAnimation.ROTATION_Y);
		fa1.setInterpolator(new AccelerateInterpolator(0.75f));
		
		viewToHide.startAnimation(fa1);
		
		fa1.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if(l!=null)l.onAnimationStart(animation);
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				viewToShow.setVisibility(View.VISIBLE);
				viewToHide.setVisibility(View.INVISIBLE);
				FlipAnimation fa2 = new FlipAnimation(90, 0,
						viewToShow.getWidth() / 2, viewToShow.getHeight()/2, scaleValue,
						FlipAnimation.ScaleUpDownEnum.SCALE_NONE);
				fa2.setDuration(dur);
				fa2.setDirection(FlipAnimation.ROTATION_Y);
				fa2.setInterpolator(new DecelerateInterpolator(0.75f));
				viewToShow.startAnimation(fa2);
				
				fa2.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						if(l!=null)l.onAnimationEnd(animation);
					}
				});
			}
		});
		
		
	}
}

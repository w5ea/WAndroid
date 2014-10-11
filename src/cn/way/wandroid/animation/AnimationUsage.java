package cn.way.wandroid.animation;

import java.util.ArrayList;
import java.util.Random;

import android.annotation.TargetApi;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.animation.nineold.FlakeView;
import cn.way.wandroid.imageloader.Utils;
import cn.way.wandroid.utils.WTimer;

public class AnimationUsage extends BaseFragmentActivity {
	private WTimer timer ;
	private ArrayList<View> views = new ArrayList<View>();
//	FlakeView flakeView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewGroup viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_animation_usage, null);
		setContentView(viewGroup);
		
//		flakeView = new FlakeView(this);
//		flakeView.setLayoutParams(new LayoutParams(500, 500));
//		viewGroup.addView(flakeView);
//		flakeView.setShowFps(true);
//		flakeView.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @SuppressWarnings("deprecation")
//					@TargetApi(VERSION_CODES.JELLY_BEAN)
//                    @Override
//                    public void onGlobalLayout() {
//                        if (Utils.hasJellyBean()) {
//                        	flakeView.getViewTreeObserver()
//                                    .removeOnGlobalLayoutListener(this);
//                        } else {
//                        	flakeView.getViewTreeObserver()
//                                    .removeGlobalOnLayoutListener(this);
//                        }
//                            
//                    }
//                });
//		viewGroup.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				flakeView.addFlakes(32, R.drawable.ic_launcher);
//				flakeView.addFlakes(32, R.drawable.empty_photo);
//			}
//		});
//		flakeView.start();
//		View iv1 =  findViewById(R.id.imageView1);
//		iv1.setVisibility(View.INVISIBLE);
//		views.add(iv1);
//		View iv2 = findViewById(R.id.imageView2);
//		iv2.setVisibility(View.INVISIBLE);
//		views.add(iv2);
//		View iv3 = findViewById(R.id.imageView3);
//		iv3.setVisibility(View.INVISIBLE);
//		views.add(iv3);
//		View iv4 = findViewById(R.id.imageView4);
//		iv4.setVisibility(View.INVISIBLE);
//		views.add(iv4);
//		View iv5 = findViewById(R.id.imageView5);
//		iv5.setVisibility(View.INVISIBLE);
//		views.add(iv5);
//		
//		for (View view : views) {
//			doFlashAnimation(view);
//		}
//		if (timer==null) {
//			timer = new WTimer() {
//				@Override
//				protected void onTimeGoesBy(long totalTimeLength) {
//					for (View view : views) {
//						doDropAnimation(view);
//					}
//				}
//			};
//			timer.schedule(dur+maxOffset+stay, null, null);
//		}
	}
	private final long dur = 2000*1;
	private final long maxOffset = 1000*4;
	private final long stay = 500;
	private void doFlashAnimation(View view){
		AnimationSet as = new AnimationSet(false);
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(dur);
		AlphaAnimation aar = new AlphaAnimation(1, 0);
		aar.setDuration(dur);
		aar.setStartOffset(stay);
		as.addAnimation(aa);
		as.addAnimation(aar);
		as.setStartOffset((long)(maxOffset*new Random().nextFloat()));
		view.startAnimation(as);
	}
	
	private void doDropAnimation(View view){
		AnimationSet as = new AnimationSet(false);
		
		AlphaAnimation aar = new AlphaAnimation(0, 1);
		aar.setDuration(100);
		as.addAnimation(aar);
		
		float dropDistance = 960*getResources().getDisplayMetrics().density;
		TranslateAnimation aa = new TranslateAnimation(view.getLeft(),view.getLeft(),view.getTop(),view.getTop()+dropDistance);
		aa.setDuration(dur);
		as.addAnimation(aa);
		
//		RotateAnimation ra = new RotateAnimation(0, 180*new Random().nextFloat(), view.getWidth()/2, view.getHeight()/2);
//		float fdur = dur/2*new Random().nextFloat();
//		ra.setDuration((long)(dur/2+fdur));
//		as.addAnimation(ra);
		
		as.setStartOffset((long)(maxOffset*new Random().nextFloat()));
		view.startAnimation(as);
	}
	
	@Override
    protected void onPause() {
        super.onPause();
//        flakeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        flakeView.resume();
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
//    	flakeView.cancel();
    }
}

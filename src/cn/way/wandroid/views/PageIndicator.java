package cn.way.wandroid.views;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageIndicator extends LinearLayout {
	
	private int numberOfPages = 1+9;
	private int currentPageIndex;
	private int resourceId;
	private int dotWidth;
	private int dotHeight;
	private ArrayList<ImageView> views;
	private boolean isFirstLayout = true;
	public PageIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PageIndicator(Context context) {
		super(context);
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		isFirstLayout = true;
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (isFirstLayout) {
			isFirstLayout = false;
			updateView();
		}
	}
	
	protected void updateView() {
		if (views==null) {
			views = new ArrayList<ImageView>();
		}else{
			removeAllViews();
			views.clear();
		}
//		int numberOfPages = getNumberOfPages();
//		int dotWidth = getWidth()/numberOfPages;
//		if (dotWidth==0) {
//			dotWidth = getHeight();
//		}
		int dotWidth = getHeight();
		
		int marginLR = 0;
		if (this.dotWidth>0&&this.dotWidth<dotWidth) {
			marginLR = dotWidth/2-this.dotWidth/2;
			dotWidth = this.dotWidth;
		}
		int dotHeight = getHeight();
		int marginTB = 0;
		if (this.dotHeight>0&&this.dotHeight<dotHeight) {
			marginTB = dotHeight/2-this.dotHeight/2;
			dotHeight = this.dotHeight;
		}
		int weight = 1;
		Log.d("test", dotWidth+"==="+dotHeight);
		for (int i = 0; i < getNumberOfPages(); i++) {
			ImageView iv = new ImageView(getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dotWidth, dotHeight, weight);
			params.leftMargin = marginLR;
			params.rightMargin = marginLR;
			params.topMargin = marginTB;
			params.bottomMargin = marginTB;
			iv.setLayoutParams(params);
//			iv.setScaleType(ScaleType.CENTER_INSIDE);
			iv.setImageResource(resourceId);
			views.add(iv);
			addView(iv);
		}
		setCurrentPageIndex(getCurrentPageIndex());
	}
	
	public void init(int resourceId,int numberOfPages,int dotWidth,int dotHeight){
		//先设置resourceId 再设置numberOfPages
		this.dotWidth = dotWidth;
		this.dotHeight = dotHeight;
		setSelector(resourceId);
		setNumberOfPages(numberOfPages);
	}
	
	public void setSelector(int resourceId){
		if (this.resourceId==resourceId) {
			return;
		}
		this.resourceId = resourceId;
		if (views!=null) {
			for (ImageView iv : views) {
				iv.setImageResource(resourceId);
			}
		}
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		if (numberOfPages<1) {
			return;
		}
		this.numberOfPages = numberOfPages;
		if (!isFirstLayout) updateView();
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		if (currentPageIndex<0) {
			return;
		}
		int lastPageIndex = getNumberOfPages()-1;
		if (currentPageIndex > lastPageIndex) {
			currentPageIndex = lastPageIndex;
		}
		this.currentPageIndex = currentPageIndex;
		if (views!=null) {
			ImageView currentView = views.get(currentPageIndex);
			for (ImageView iv : views) {
				iv.setSelected(currentView==iv);
			}
		}
	}

}

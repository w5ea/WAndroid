package cn.way.wandroid.views;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.toast.Toaster;
/**
 * 一个内容由DialogContentController提供的对话框
 * @author Wayne
 * @2015年5月19日
 */
public class ContentControlDialog extends Dialog{
	public ContentControlDialog(Context context){
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}
	public ContentControlDialog(Context context,int theme){
		super(context, theme);
	}
	/**
	 * 创建一个只能显示标题和自定义的YJDialogInnerViewController子类提供的View的对话框
	 * @param context
	 * @param controller
	 */
	public ContentControlDialog(Context context,DialogContentController controller) {
		this(context, android.R.style.Theme_Translucent_NoTitleBar, controller);
	}
	public ContentControlDialog(Context context,int theme,DialogContentController controller) {
		super(context,theme);
		setContentView(controller);
		setCanceledOnTouchOutside(false);
		setCancelable(true);
	}
	public void setContentView(DialogContentController controller) {
		if(controller!=null){
			ViewGroup contentView = controller.getContentView();
			if (contentView!=null) {
				setContentView(contentView);
			}
		}
	}
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		cancelToast();
	}

	public void toast(String title) {
		Toaster.instance(getContext()).setup(title).show();
	}
	public void cancelToast() {
		Toaster.instance(getContext()).cancel();
	}
	public static abstract class DialogContentController{
		private Activity activity;
		private Fragment parent;
		private ViewGroup contentView;//对话框的主界面
		private ViewGroup holderView;//对话框可以改变VIEW的父VIEW
		protected View view;//对话框可以改变VIEW
		protected ContentControlDialog dialog;
		public DialogContentController(Activity activity,ContentControlDialog dialog) {
			super();
			this.setActivity(activity);
			this.dialog = dialog;
			contentView = this.inflateContentView();
			dialog.setContentView(contentView);
			holderView = (ViewGroup) contentView.findViewById(android.R.id.content);
			view = this.inflateReplaceableView(holderView);
			if (holderView!=null&&view!=null) {
				holderView.addView(view);
			}
			this.inflateSubView();//解析子视图
		}
		
		public DialogContentController(Fragment parent,ContentControlDialog dialog) {
			this(parent.getActivity(),dialog);
			this.setParent(parent);
		}
		/**
		 * 返回对话框内容视图，要求视图中包含一个ID为android.R.id.content的ViewGroup
		 * @return
		 */
		protected abstract ViewGroup inflateContentView ();
		protected abstract View inflateReplaceableView (ViewGroup parent);
		/**
		 * 在这个方法中初始化子控件,直接使用类变量 view
		 * e.g. aChildView =  view.findViewById(R.id.aResourceId);
		 */
		protected abstract void inflateSubView ();
		
		public abstract String getTitle();
		
		public Activity getActivity() {
			return activity;
		}
		public void setActivity(Activity activity) {
			this.activity = activity;
		}

		public Fragment getParent() {
			return parent;
		}

		public void setParent(Fragment parent) {
			this.parent = parent;
		}

		public ViewGroup getContentView() {
			return contentView;
		}

		public ViewGroup getHolderView() {
			return holderView;
		}
		
		public View getView() {
			return view;
		}
		
		public ContentControlDialog getDialog() {
			return dialog;
		}
	}
}

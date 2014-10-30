package cn.way.wandroid.views;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.toast.Toaster;

public class WDialog extends Dialog{
	public WDialog(Context context){
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}
	public WDialog(Context context,int theme){
		super(context, theme);
	}
	/**
	 * 创建一个只能显示标题和自定义的YJDialogInnerViewController子类提供的View的对话框
	 * @param context
	 * @param controller
	 */
	public WDialog(Context context,DialogContentController controller) {
		this(context, android.R.style.Theme_Translucent_NoTitleBar, controller);
	}
	public WDialog(Context context,int theme,DialogContentController controller) {
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

	protected void toast(String title) {
		Toaster.instance(getContext()).setup(title).show();
	}
	protected void cancelToast() {
		Toaster.instance(getContext()).cancel();
	}
	public static abstract class DialogContentController{
		private FragmentActivity activity;
		private Fragment parent;
		private ViewGroup contentView;//对话框的主界面
		private ViewGroup holderView;//对话框可以改变VIEW的父VIEW
		protected View view;//对话框可以改变VIEW
		protected WDialog dialog;
		public DialogContentController(FragmentActivity activity,WDialog dialog) {
			super();
			this.setActivity(activity);
			this.dialog = dialog;
			contentView = this.inflateContentView();
			dialog.setContentView(contentView);
			holderView = this.inflateHolderView();
			view = this.inflateView();
			if (holderView!=null&&view!=null) {
				holderView.addView(view);
			}
			this.inflateSubView();//解析子视图
		}
		
		public DialogContentController(Fragment parent,WDialog dialog) {
			this(parent.getActivity(),dialog);
			this.setParent(parent);
		}
		protected abstract ViewGroup inflateContentView ();
		protected abstract ViewGroup inflateHolderView ();
		protected abstract View inflateView ();
		/**
		 * 在这个方法中初始化子控件,直接使用类变量 view
		 * e.g. aChildView =  view.findViewById(R.id.aResourceId);
		 */
		protected abstract void inflateSubView ();
		
		public abstract String getTitle();
		
		public FragmentActivity getActivity() {
			return activity;
		}
		public void setActivity(FragmentActivity activity) {
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
		
		
		protected void toast(String title) {
			Toaster.instance(getActivity()).setup(title).show();
		}
		protected void cancelToast() {
			Toaster.instance(getActivity()).cancel();
		}

		public Dialog getDialog() {
			return dialog;
		}
	}
}

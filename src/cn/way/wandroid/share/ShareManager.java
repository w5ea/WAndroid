package cn.way.wandroid.share;

import android.app.Activity;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMShareBoardListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class ShareManager {
	private Activity activity;
	final UMSocialService controller = UMServiceFactory.getUMSocialService("com.umeng.share");
	
	public ShareManager(Activity activity) {
		setActivity(activity);
		// 是否缓存图片
		controller.getConfig().setCacheValidStatus(false);
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(getActivity(),"xxx","xxx");
		// 支持微信朋友圈
		wxHandler.setToCircle(true);
//		wxHandler.addToSocialSDK();
		controller.getConfig().setSsoHandler(wxHandler);
		// 新浪微博SSO
		controller.getConfig().setSsoHandler(new SinaSsoHandler());
		controller.getConfig().setSinaCallbackUrl("http://wandroid.qiniudn.com");
		// 腾讯微博SSO
		controller.getConfig().setSsoHandler(new TencentWBSsoHandler());
		// QQ空间SSO
		controller.getConfig().setSsoHandler(new QZoneSsoHandler(getActivity(), "xxx", "xxx"));
		getController().getConfig().setPlatforms(
				SHARE_MEDIA.WEIXIN_CIRCLE,
				SHARE_MEDIA.SINA,
				SHARE_MEDIA.TENCENT, 
				SHARE_MEDIA.QZONE);
		// 分享面板中的平台将按照如下顺序进行排序
//		getController().getConfig().setPlatformOrder(
//				SHARE_MEDIA.WEIXIN_CIRCLE,
//				SHARE_MEDIA.SINA,
//				SHARE_MEDIA.TENCENT, 
//				SHARE_MEDIA.QZONE);
	}
	public UMSocialService getController() {
		return controller;
	}
	public void share(String text,UMImage image,SnsPostListener listener){
		
		if (text!=null) {
			controller.setShareContent(text);
		}
		if (image!=null) {
			controller.setShareImage(image);
		}
		
		// 设置分享内容
//		controller.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
		// 设置分享图片, 参数2为图片的url地址
//		controller.setShareMedia(new UMImage(activity, 
//		                                      "http://www.umeng.com/images/pic/banner_module_social.png"));
		// 设置分享图片，参数2为本地图片的资源引用
		//mController.setShareMedia(new UMImage(getActivity(), R.drawable.icon));
		// 设置分享图片，参数2为本地图片的路径(绝对路径)
		//mController.setShareMedia(new UMImage(getActivity(), 
//		                                BitmapFactory.decodeFile("/mnt/sdcard/icon.png")));

		// 设置分享音乐
		//UMusic uMusic = new UMusic("http://sns.whalecloud.com/test_music.mp3");
		//uMusic.setAuthor("GuGu");
		//uMusic.setTitle("天籁之音");
		// 设置音乐缩略图
		//uMusic.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
		//mController.setShareMedia(uMusic);

		// 设置分享视频
		//UMVideo umVideo = new UMVideo(
//		          "http://v.youku.com/v_show/id_XNTE5ODAwMDM2.html?f=19001023");
		// 设置视频缩略图
		//umVideo.setThumb("http://www.umeng.com/images/pic/banner_module_social.png");
		//umVideo.setTitle("友盟社会化分享!");
		//mController.setShareMedia(umVideo);
//		controller.openShare(activity, new SnsPostListener() {
//			@Override
//			public void onStart() {
//			}
//			@Override
//			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//			
//			}
//		});
		controller.setShareBoardListener(new UMShareBoardListener() {
			@Override
			public void onShow() {
				// 获取用户点击的平台
				//controller.getConfig().getSelectedPlatfrom();
			}
			
			@Override
			public void onDismiss() {
				
			}
		});
		controller.registerListener(listener);
		controller.openShare(getActivity(), false);
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}

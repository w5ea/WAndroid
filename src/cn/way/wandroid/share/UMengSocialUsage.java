package cn.way.wandroid.share;

import android.os.Bundle;
import android.view.View;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;

public class UMengSocialUsage extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_umeng_social_usage);
	}
	
	public void shareAction(View view){
		shareManager.shareToWeixin("wwwww",new UMImage(this, R.drawable.ic_launcher),new SnsPostListener() {
			@Override
			public void onStart() {
				
			}
			@Override
			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
				//分享结束，eCode==200代表分享成功，非200代表分享失败
				if (eCode==200) {
					
				}else{
					
				}
				if(platform == SHARE_MEDIA.TENCENT){
					
				}
			}
		});
		
//		shareManager.share("wwwww",new UMImage(this, R.drawable.ic_launcher),new SnsPostListener() {
//			@Override
//			public void onStart() {
//				
//			}
//			@Override
//			public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
//				//分享结束，eCode==200代表分享成功，非200代表分享失败
//				if (eCode==200) {
//					
//				}else{
//					
//				}
//				if(platform == SHARE_MEDIA.TENCENT){
//					
//				}
//			}
//		});
	}
}

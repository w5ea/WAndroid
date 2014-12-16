package cn.way.wandroid.openapi;

import android.content.Context;



public class OAuthManager {
	
	private static String ACCESS_TOKEN = "";
	private static String UID = "";
	private static String REFRESH_TOKEN = "";
	private static String EXPIRES_IN = "";
	
	private static String APP_KEY = "";
	private static String REDIRECT_URL = "";
	private static String SCOPE = "";
	
	private Context context;
	private Configration configration;
	private static OAuthManager oAuthManager;
	public static OAuthManager instance(Context context,Configration configration){
		if (oAuthManager==null) {
			oAuthManager = new OAuthManager(context,configration);
		}
		return oAuthManager;
	}
	
	private OAuthManager(Context context,Configration configration) {
		super();
		setContext(context);
		this.configration = configration;
	}

	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context.getApplicationContext();
	}
	
	public class Configration{
		//授权应用包名 例如： cn.way.wandroid
		private String authAppPackage;
		//授权Activity的完整类名 例如：cn.way.wandroid.openapi.AuthActivity
		private String authAcitvity;
		
		//获取未授权的Request Token服务地址；
		private String requestTokenURL;
		
		//获取用户授权的Request Token服务地址；
		private String userAuthorizationURL;
		//用授权的Request Token换取Access Token的服务地址；
		private String accessTokenURL;
		
		private Configration(String authAppPackage, String authAcitvity,
				String requestTokenURL, String userAuthorizationURL,
				String accessTokenURL) {
			super();
			this.authAppPackage = authAppPackage;
			this.authAcitvity = authAcitvity;
			this.requestTokenURL = requestTokenURL;
			this.userAuthorizationURL = userAuthorizationURL;
			this.accessTokenURL = accessTokenURL;
		}
		public boolean isAvailable(){
			//TODO 检测安装包和授权Activity是否存在
			return requestTokenURL!=null&&userAuthorizationURL!=null&&accessTokenURL!=null;
		}
		public String getAuthAppPackage() {
			return authAppPackage;
		}
		public String getAuthAcitvity() {
			return authAcitvity;
		}
		public String getRequestTokenURL() {
			return requestTokenURL;
		}
		public String getUserAuthorizationURL() {
			return userAuthorizationURL;
		}
		public String getAccessTokenURL() {
			return accessTokenURL;
		}
	}
	public class OAuthAPI{
		
	}
	public class SsoAPI{
		public static final String AuthAppPackage = "cn.way.wandroid"; 
		public static final String AuthAcitvity = "cn.way.wandroid.openapi.AuthActivity";
	}
}

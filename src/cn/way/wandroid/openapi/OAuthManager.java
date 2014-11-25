package cn.way.wandroid.openapi;



public class OAuthManager {
	//获取未授权的Request Token服务地址；
	private String RequestTokenURL = "";
	//获取用户授权的Request Token服务地址；
	private String UserAuthorizationURL = "";
	//用授权的Request Token换取Access Token的服务地址；
	private String AccessTokenURL = "";
	
	
	
	private static String ACCESS_TOKEN = "";
	private static String UID = "";
	private static String REFRESH_TOKEN = "";
	private static String EXPIRES_IN = "";
	
	private static String APP_KEY = "";
	private static String REDIRECT_URL = "";
	private static String SCOPE = "";
	
}

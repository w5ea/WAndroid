package cn.way.wandroid.openapi;

public class YJClient {
	
	private String appKey;
	private String apiSecret;
	private String callback;
	
	public YJClient(String appKey, String apiSecret, String callback) {
		super();
		this.appKey = appKey;
		this.apiSecret = apiSecret;
		this.callback = callback;
	}
	
	public void begainSsoAuthorization(){
		
	}
	
	public void finishSsoAuthorization(){
		
	}
	
	private final String AuthAppPackage = "cn.way.wandroid"; 
	private final String AuthAcitvity = "cn.way.wandroid.openapi.AuthActivity";
	private String accessToken;
	
	public String getAccessToken() {
		return accessToken;
	}
	public String getAppKey() {
		return appKey;
	}
	public String getApiSecret() {
		return apiSecret;
	}
	public String getCallback() {
		return callback;
	}
	
}

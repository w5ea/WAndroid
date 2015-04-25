package cn.way.wandroid.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneUtils {
	public static String parsePhoneNumber11(String phoneNumber){
		if (phoneNumber==null) {
			return null;
		}
		int standL = 11;
		int length = phoneNumber.length();
		if (length>standL) {
			return phoneNumber.substring(length-standL);
		}
		return phoneNumber;
	}
	public static String readPhoneNumber(Context context){
		TelephonyManager mTelephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
		return parsePhoneNumber11(mTelephonyManager.getLine1Number());
	}
	public static String getProvidersName(Context context) {  
        String ProvidersName = null;  
        String IMSI = ((TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();  
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。  
        System.out.println(IMSI);  
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {  
            ProvidersName = "中国移动";  
        } else if (IMSI.startsWith("46001")) {  
            ProvidersName = "中国联通";  
        } else if (IMSI.startsWith("46003")) {  
            ProvidersName = "中国电信";  
        }  
        return ProvidersName;  
    }  
}

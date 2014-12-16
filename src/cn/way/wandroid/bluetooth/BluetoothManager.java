package cn.way.wandroid.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

/**
 * 1.add permissions.
 * 	<uses-permission android:name="android.permission.BLUETOOTH" />
 * 	<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
 * @author Wayne
 */
public class BluetoothManager {
	/**
	 * Device does not support Bluetooth
	 * @author Wayne
	 */
	public static class BluetoothSupportException extends Exception{
		private static final long serialVersionUID = -5168381347847106511L;
		@Override
		public String getMessage() {
			return "Device does not support Bluetooth.";
		}
	}
	/**
	 * Bluetooth is  disabled
	 * @author Wayne
	 */
	public static class BluetoothEnableException extends Exception{
		private static final long serialVersionUID = -5168381347847106511L;
		@Override
		public String getMessage() {
			return "Bluetooth is  disabled,Please enable it.";
		}
	}
	private static BluetoothManager manager;
	public static BluetoothManager instance() throws BluetoothSupportException, BluetoothEnableException {
		if (!BluetoothManager.isBluetoothSupported()) {
			throw new BluetoothSupportException();
		}else{
			if (!BluetoothManager.isBluetoothEnabled()) {
				throw new BluetoothEnableException();
			}
		}
		if (manager == null) {
			manager = new BluetoothManager();
		}
		return manager;
	}
	private BluetoothManager() {
		super();
	}
	public static boolean isBluetoothSupported(){
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
		    return false; 
		}else{
			return true;
		}
	}
	public static boolean isBluetoothEnabled(){
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
		    return false;
		}
		return true;
	}
	public static void showBluetoothConfigActivity(Activity activity,int resultCode){
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		activity.startActivityForResult(enableBtIntent, resultCode);
	}
}

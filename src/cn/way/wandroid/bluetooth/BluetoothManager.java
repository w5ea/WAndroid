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
	public static int REQUEST_ENABLE_BT = 1001;
	public static int REQUEST_DISCOVERABLE_BT = 1002;
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
	public static void requestEnable(Activity activity){
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	}
	public static void requestDiscoverable(Activity activity){
		Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		//maximum duration is 300 seconds,default is 120 seconds.
		discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		activity.startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE_BT);
	}
	public static interface StateChangeListener{
		
	}
}

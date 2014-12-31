package cn.way.wandroid.bluetooth;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 1.add permissions. <uses-permission
 * android:name="android.permission.BLUETOOTH" /> <uses-permission
 * android:name="android.permission.BLUETOOTH_ADMIN" />
 * 
 * @author Wayne
 */
public class BluetoothManager {
	public static int REQUEST_ENABLE_BT = 1001;
	public static int REQUEST_DISCOVERABLE_BT = 1002;
	private static BluetoothManager manager;

	public static BluetoothManager instance(Context context)
			throws BluetoothSupportException {
		if (!BluetoothManager.isBluetoothSupported()) {
			throw new BluetoothSupportException();
		}
		if (context == null) {
			return null;
		}
		if (manager == null) {
			manager = new BluetoothManager(context.getApplicationContext());
		}
		return manager;
	}
	
	public static boolean isBluetoothSupported() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (mBluetoothAdapter == null) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isBluetoothEnabled() {
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (!mBluetoothAdapter.isEnabled()) {
			return false;
		}
		return true;
	}

	public static void requestEnable(Activity activity) {
		Intent enableBtIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_ENABLE);
		activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
	}

	public static void requestDiscoverable(Activity activity) {
		Intent discoverableIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
		// maximum duration is 300 seconds,default is 120 seconds.
		discoverableIntent.putExtra(
				BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		activity.startActivityForResult(discoverableIntent,
				REQUEST_DISCOVERABLE_BT);
	}

	public static String getBondState(BluetoothDevice bd){
		/**
		 * {@link #BOND_NONE},
	     * {@link #BOND_BONDING},
	     * {@link #BOND_BONDED}.
		 */
		String state = "未配对";
		if (bd.getBondState()==BluetoothDevice.BOND_BONDING) {
			state = "正在配对...";
		}
		if (bd.getBondState()==BluetoothDevice.BOND_BONDED) {
			state = "已配对";
		}
		return state;
	}
	// //////////////////////////////////////////////////////////////////////////////////////////

	private BluetoothAdapter mBluetoothAdapter;
	private Context context;
	private DeviceStateListener deviceStateListener;

	public void release() {
		// Make sure we're not doing discovery anymore
		cancelDiscovery();
		// Unregister broadcast listeners
		if(this.context!=null&&mReceiver!=null)this.context.unregisterReceiver(mReceiver);
	}

	/**
	 * 
	 * @param context
	 */
	private BluetoothManager(Context context) {
		super();
		// 监听蓝牙设置的开关状态
		IntentFilter filter = new IntentFilter(
				BluetoothAdapter.ACTION_STATE_CHANGED);
		context.registerReceiver(mReceiver, filter);

		// Register for broadcasts when a device is discovered
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		context.registerReceiver(mReceiver, filter);

		// Register for broadcasts when discovery has finished
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		context.registerReceiver(mReceiver, filter);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}

	public enum ConnectionState{
		NONE,// we're doing nothing
	    LISTENING,// now listening for incoming connections
	    CONNECTING,// now initiating an outgoing connection
	    CONNECTED,// now connected to a remote device
	}

	public enum DeviceState{
		OFF,TURNING_ON,ON,TURNING_OFF
	}
	public interface DeviceStateListener {
		/**
	     * @param state
	     * {@link #STATE_OFF},
	     * {@link #STATE_TURNING_ON},
	     * {@link #STATE_ON},
	     * {@link #STATE_TURNING_OFF},
	     */
		void onStateChanged(DeviceState state);
	}
	private void onDeviceStateChanged(int state){
		if (deviceStateListener!=null) {
			DeviceState ds = DeviceState.OFF;
			if (state==BluetoothAdapter.STATE_TURNING_ON) {
				ds = DeviceState.TURNING_ON;
			}
			if (state==BluetoothAdapter.STATE_ON) {
				ds = DeviceState.ON;
			}
			if (state==BluetoothAdapter.STATE_TURNING_OFF) {
				ds = DeviceState.TURNING_OFF;
			}
			deviceStateListener.onStateChanged(ds);
		}
	}

	public boolean isEnabled() {
		if (!mBluetoothAdapter.isEnabled()) {
			return false;
		}
		return true;
	}
	
	public void enable() {
		if (!mBluetoothAdapter.isEnabled()) {
			mBluetoothAdapter.enable();
		}else{
			onDeviceStateChanged(BluetoothAdapter.STATE_ON);
		}
	}

	/**
	 * scan the nearby devices
	 * 
	 * @return
	 */
	public void startDiscovery(DiscoveryListener l) {
		this.discoveryListener = l;
		cancelDiscovery();
		// Request discover from BluetoothAdapter
		mBluetoothAdapter.startDiscovery();
	}

	public void cancelDiscovery() {
		if (mBluetoothAdapter!=null&&mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}
	}

	public boolean isDiscovering(){
		return mBluetoothAdapter.isDiscovering();
	}
	/**
	 * Get a set of currently paired devices
	 * 
	 * @return
	 */
	public Set<BluetoothDevice> getBondedDevices() {
		return BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	}

	public DiscoveryListener getDiscoveryListener() {
		return discoveryListener;
	}

	public void setDiscoveryListener(DiscoveryListener discoveryListener) {
		this.discoveryListener = discoveryListener;
	}

	// The BroadcastReceiver that listens for discovered devices and
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			//Device Adapter state changed
			if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
				int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
				onDeviceStateChanged(state);
			}

			
			
			// When discovery finds a device
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// Get the BluetoothDevice object from the Intent
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// If it's already paired, skip it, because it's been listed
				// already
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					addDevice(device);
					if (discoveryListener != null) {
						discoveryListener.onDevicesChanged(devices.values());
					}
				}
			}
			// discovery is finished
			if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				if (discoveryListener != null) {
					discoveryListener.onFinished();
				}
			}
		}
	};

	public interface DiscoveryListener {
		/**
		 * the list of devices changed, update view should be done.
		 * @param devices
		 */
		void onDevicesChanged(Collection<BluetoothDevice> devices);

		void onFinished();
	}

	private DiscoveryListener discoveryListener;
	private Hashtable<String, BluetoothDevice> devices = new Hashtable<String, BluetoothDevice>();

	private void addDevice(BluetoothDevice device) {
		if (device != null) {
			devices.put(device.getAddress(), device);
		}
	}

	public DeviceStateListener getDeviceStateListener() {
		return deviceStateListener;
	}

	public void setDeviceStateListener(DeviceStateListener deviceStateListener) {
		this.deviceStateListener = deviceStateListener;
	}

	/**
	 * Device does not support Bluetooth
	 * 
	 * @author Wayne
	 */
	public static class BluetoothSupportException extends Exception {
		private static final long serialVersionUID = -5168381347847106511L;

		@Override
		public String getMessage() {
			return "Device does not support Bluetooth.";
		}
	}
}

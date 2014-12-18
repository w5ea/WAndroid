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
 * 1.add permissions.
 * 	<uses-permission android:name="android.permission.BLUETOOTH" />
 * 	<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
 * @author Wayne
 */
public class BluetoothManager {
	
	private BluetoothAdapter mBluetoothAdapter;
	private Context context;
	
	public static int REQUEST_ENABLE_BT = 1001;
	public static int REQUEST_DISCOVERABLE_BT = 1002;
	
	private static BluetoothManager manager;
	
	public void release(){
		// Make sure we're not doing discovery anymore
        if (mBluetoothAdapter != null) {
        	mBluetoothAdapter.cancelDiscovery();
        }
        // Unregister broadcast listeners
        this.context.unregisterReceiver(mReceiver);
	}
	
	public static BluetoothManager instance(Context context) throws BluetoothSupportException, BluetoothEnableException {
		if (!BluetoothManager.isBluetoothSupported()) {
			throw new BluetoothSupportException();
		}else{
			if (!BluetoothManager.isBluetoothEnabled()) {
				throw new BluetoothEnableException();
			}
		}
		if (context == null) {
			return null;
		}
		if (manager == null) {
			manager = new BluetoothManager(context.getApplicationContext());
		}
		return manager;
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
	
	
	
	/**
	 * 
	 * @param context
	 */
	private BluetoothManager(Context context) {
		super();
		// Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(mReceiver, filter);
        
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	/**
	 * scan the nearby devices
	 * @return
	 */
	public void discoveryDevices(DiscoveryListener l){
		this.discoveryListener = l;
		cancelDiscovery();
        // Request discover from BluetoothAdapter
        mBluetoothAdapter.startDiscovery();
	}
	public void cancelDiscovery(){
		if (mBluetoothAdapter.isDiscovering()) {
        	mBluetoothAdapter.cancelDiscovery();
        }
	}
	
	/**
	 * Get a set of currently paired devices
	 * @return
	 */
	public Set<BluetoothDevice> getBondedDevices(){
		return BluetoothAdapter.getDefaultAdapter().getBondedDevices();
	}
	
	
	
	
	
	
	public DiscoveryListener getDiscoveryListener() {
		return discoveryListener;
	}
	public void setDiscoveryListener(DiscoveryListener discoveryListener) {
		this.discoveryListener = discoveryListener;
	}
	public static interface StateChangeListener{
		
	}
	
	// The BroadcastReceiver that listens for discovered devices and
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                	addDevice(device);
                	if (discoveryListener!=null) {
						discoveryListener.onDevicesChanged(devices.values());
					}
                }
            // discovery is finished
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            	if (discoveryListener!=null) {
					discoveryListener.onFinished();
				}
            }
        }
    };
    
    public interface DiscoveryListener{
		/**
		 * the list of devices changed, update view should be done.
		 * @param devices
		 */
		void onDevicesChanged(Collection<BluetoothDevice>devices);
		void onFinished();
	}
	private DiscoveryListener discoveryListener;
	private Hashtable<String,BluetoothDevice> devices = new Hashtable<String, BluetoothDevice>();
	private void addDevice(BluetoothDevice device){
		if (device!=null) {
			devices.put(device.getAddress(), device);
		}
	}
	
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
}

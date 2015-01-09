package cn.way.wandroid.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

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

	public static String getBondState(BluetoothDevice bd) {
		/**
		 * {@link #BOND_NONE}, {@link #BOND_BONDING}, {@link #BOND_BONDED}.
		 */
		String state = "未配对";
		if (bd.getBondState() == BluetoothDevice.BOND_BONDING) {
			state = "正在配对...";
		}
		if (bd.getBondState() == BluetoothDevice.BOND_BONDED) {
			state = "已配对";
		}
		return state;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	private BluetoothAdapter mBluetoothAdapter;
	private Context context;
	private DeviceStateListener deviceStateListener;
	private BluetoothConnectionListener connectionListener;
	private BluetoothConnection connection;
	/**
	 * @return the connection
	 */
	public BluetoothConnection getConnection() {
		return connection;
	}

	public void release() {
		// Make sure we're not doing discovery anymore
		cancelDiscovery();
		// Unregister broadcast listeners
		if (this.context != null && mReceiver != null)
			this.context.unregisterReceiver(mReceiver);
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
		
		connection = new BluetoothConnection();
	}

	public enum DeviceState {
		OFF, TURNING_ON, ON, TURNING_OFF
	}

	public interface DeviceStateListener {
		/**
		 * @param state
		 *            {@link #STATE_OFF}, {@link #STATE_TURNING_ON},
		 *            {@link #STATE_ON}, {@link #STATE_TURNING_OFF},
		 */
		void onStateChanged(DeviceState state);
	}

	private void onDeviceStateChanged(int state) {
		if (deviceStateListener != null) {
			DeviceState ds = DeviceState.OFF;
			if (state == BluetoothAdapter.STATE_TURNING_ON) {
				ds = DeviceState.TURNING_ON;
			}
			if (state == BluetoothAdapter.STATE_ON) {
				ds = DeviceState.ON;
			}
			if (state == BluetoothAdapter.STATE_TURNING_OFF) {
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
		} else {
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
		if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}
	}

	public boolean isDiscovering() {
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

			// Device Adapter state changed
			if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
				int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
						BluetoothAdapter.STATE_OFF);
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
		 * 
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

	public BluetoothConnectionListener getConnectionListener() {
		return connectionListener;
	}

	public void setConnectionListener(BluetoothConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
		this.connection.setBluetoothConnectionListener(connectionListener);
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
			return "当前设备不支持蓝牙";
		}
	}

	public interface BluetoothConnectionListener {
		void onConnectionStateChanged(ConnectionState state);
	}

	public enum ConnectionState {
		DISCONNECTED, // 未连接或连接断开
		LISTENING, // 正在等待对方来连接
		CONNECTING, // 正在试图去连接对方
		CONNECTED// 已经连接
	}

	@SuppressLint("HandlerLeak")
	public class BluetoothConnection {
		private final Handler mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				getBluetoothConnectionListener()
				.onConnectionStateChanged(state);
			}
		};
		
		private static final String NAME_SECURE = "BluetoothSecure";
		private static final String NAME_INSECURE = "BluetoothInsecure";
		private UUID uuid;
		private boolean isSecure;// true is secure or insecure
		private ConnectionState state = ConnectionState.DISCONNECTED;

		private BluetoothDevice remoteDevice;// 准备或已经连接的远程设备
		private BluetoothConnectionListener bluetoothConnectionListener;

		private BluetoothSocket socket;
		private BluetoothServerSocket serverSocket;
		private InputStream inStream;
		private OutputStream outStream;

		private Thread listeningThread;// 等待客户端连接线程
		private Thread connectingThread;// 连接服务器线程
		private Thread connectedThread;// 连接后的通讯线程

		private Object lock = BluetoothConnection.this;

		/**
		 * 创建一个连接
		 * 
		 * @param device 可为空。为空是则创建ServerSocket，并进入等待连接状态（
		 *            ConnectionState.LISTENING）,不为空则通过device创建Socket并尝试连接（ConnectionState.CONNECTING）
		 * @param isSecure
		 *            true 创建安全连接，false 创建不安全连接
		 */
		public void connect(UUID uuid, BluetoothDevice device, boolean isSecure) {
			cannel();
			this.uuid = uuid;
			this.remoteDevice = device;
			if (device == null) {
				doListening();
			} else {
				doConnecting();
			}
		}

		public void cannel() {
			try {
				if (serverSocket != null)
					serverSocket.close();
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
			}
			changeState(ConnectionState.DISCONNECTED);
		}

		private void doListening() {
			try {
				if (isSecure) {
					this.serverSocket = mBluetoothAdapter
							.listenUsingRfcommWithServiceRecord(NAME_SECURE,
									uuid);
				} else {
					this.serverSocket = mBluetoothAdapter
							.listenUsingInsecureRfcommWithServiceRecord(
									NAME_INSECURE, uuid);
				}
			} catch (IOException e) {
				this.serverSocket = null;
				changeState(ConnectionState.DISCONNECTED);
			}
			changeState(ConnectionState.LISTENING);
			listeningThread = new Thread(new Runnable() {
				@Override
				public void run() {
					// 不断尝试连接直到连接完成
					while (state != ConnectionState.CONNECTED) {
						try {
							socket = serverSocket.accept();
						} catch (IOException e) {
							// 尝试连接异常退出
							socket = null;
							changeState(ConnectionState.DISCONNECTED);
							break;
						}
						if (socket != null) {
							synchronized (lock) {
								switch (state) {
								case CONNECTING:
									//连接完成，进入交换数据状态
									doConnected();
									break;
								case CONNECTED:
									try {
										socket.close();
										socket = null;
									} catch (IOException e) {
										//关闭失败
									}
									// 已经连接则关闭当前得到的Socket
									changeState(ConnectionState.DISCONNECTED);
									break;
								default:
									break;
								}
							}
						}
					}
				}
			});
			listeningThread.start();
		}

		private void doConnecting() {
			try {
				if (isSecure) {
					this.socket = remoteDevice
							.createRfcommSocketToServiceRecord(uuid);
				} else {
					this.socket = remoteDevice
							.createInsecureRfcommSocketToServiceRecord(uuid);
				}
			} catch (IOException e) {
				changeState(ConnectionState.DISCONNECTED);
			}
			changeState(ConnectionState.CONNECTING);
			mBluetoothAdapter.cancelDiscovery();
			connectingThread = new Thread(new Runnable() {
				@Override
				public void run() {
		            try {
		            	//这个连接操作是同步的。所以在线程中执行
		                socket.connect();
		            } catch (IOException e) {
		                try {
		                    socket.close();
		                } catch (IOException e2) {
		                    //未成功关闭
		                }
		                changeState(ConnectionState.DISCONNECTED);
		                return;
		            }
		            //如果连接未出现异常，则说明连接成功，可以进入数据交换了
		            synchronized (lock) {
		            	connectingThread = null;
		            }
		            doConnected();
				}
			});
			connectingThread.start();
		}

		private void doConnected() {
			changeState(ConnectionState.CONNECTED);
			mBluetoothAdapter.cancelDiscovery();
			InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                //数据流打开失败
            }

            inStream = tmpIn;
            outStream = tmpOut;
		}

		private synchronized void changeState(ConnectionState state) {
			this.state = state;
			if (getBluetoothConnectionListener() != null) {
				mHandler.sendEmptyMessage(0);
			}
		}
		public synchronized ConnectionState getState() {
			return state;
		}
		
		public BluetoothConnectionListener getBluetoothConnectionListener() {
			return bluetoothConnectionListener;
		}

		public void setBluetoothConnectionListener(
				BluetoothConnectionListener bluetoothConnectionListener) {
			this.bluetoothConnectionListener = bluetoothConnectionListener;
		}
	}
}

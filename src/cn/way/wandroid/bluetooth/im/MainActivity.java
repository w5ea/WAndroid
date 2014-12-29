package cn.way.wandroid.bluetooth.im;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.BluetoothManager;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothSupportException;
import cn.way.wandroid.bluetooth.BluetoothManager.DeviceState;
import cn.way.wandroid.bluetooth.BluetoothManager.DeviceStateListener;
import cn.way.wandroid.toast.Toaster;

public class MainActivity extends BaseFragmentActivity {
	private BluetoothManager bm;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bm!=null) {
			bm.release();
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_page_main);

		try {
			bm = BluetoothManager.instance(this);
			bm.setDeviceStateListener(new DeviceStateListener() {
				@Override
				public void onStateChanged(DeviceState state) {
					switch (state) {
					case TURNING_ON:
						break;
					case ON:
						FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
						HomepageFragment hf = new HomepageFragment();
						hf.setManager(bm);
						ft.replace(R.id.bluetooth_page_main_root, hf);
						ft.commit();
						break;
					case OFF:
						break;
					case TURNING_OFF:
						break;
					}
				}
			});
			bm.enable();
		} catch (BluetoothSupportException e) {
			Toaster.instance(this).setup(e.toString()).show();
		}
	}
}

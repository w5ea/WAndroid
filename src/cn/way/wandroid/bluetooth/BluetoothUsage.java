package cn.way.wandroid.bluetooth;

import android.os.Bundle;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothEnableException;
import cn.way.wandroid.bluetooth.BluetoothManager.BluetoothSupportException;
import cn.way.wandroid.toast.Toaster;

public class BluetoothUsage extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(this.getClass().getSimpleName());
		try {
			BluetoothManager bm = BluetoothManager.instance();
		} catch (BluetoothSupportException e) {
			Toaster.instance(this).setup(e.toString()).show();
		} catch (BluetoothEnableException e) {
			Toaster.instance(this).setup(e.toString()).show();
		}
	}
}

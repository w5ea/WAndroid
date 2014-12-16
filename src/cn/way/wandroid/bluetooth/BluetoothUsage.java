package cn.way.wandroid.bluetooth;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
			Dialog d = new Dialog(this);
			d.show();
			d.setOnCancelListener(new Dialog.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					BluetoothManager.requestDiscoverable(BluetoothUsage.this);
				}
			});
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			Toaster.instance(this).setup("OK").show();
		}else if(resultCode == Activity.RESULT_CANCELED){
			
		}else{
			
		}
	}
}

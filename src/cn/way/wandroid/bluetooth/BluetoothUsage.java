package cn.way.wandroid.bluetooth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build.VERSION_CODES;
import android.view.View;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.im.MainActivity;
@SuppressLint("InlinedApi") @TargetApi(VERSION_CODES.HONEYCOMB)
public class BluetoothUsage extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(this.getClass().getSimpleName());
//		try {
//			BluetoothManager bm = BluetoothManager.instance(this);
//		} catch (BluetoothSupportException e) {
//			Toaster.instance(this).setup(e.toString()).show();
//		} catch (BluetoothEnableException e) {
//			BluetoothManager.requestDiscoverable(BluetoothUsage.this);
//			BluetoothManager.requestDiscoverable(BluetoothUsage.this);
//		}
		setContentView(R.layout.bluetooth_page_usage);
		findViewById(R.id.bluetooth_im_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
			}
		}); 
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
}

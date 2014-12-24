package cn.way.wandroid.bluetooth.im;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import cn.way.wandroid.BaseFragmentActivity;
import cn.way.wandroid.R;

public class MainActivity extends BaseFragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth_page_main);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.bluetooth_page_main_root,new HomepageFragment());
		ft.commit();
	}
}

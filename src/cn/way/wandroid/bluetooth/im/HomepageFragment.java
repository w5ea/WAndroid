package cn.way.wandroid.bluetooth.im;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.BaseFragment;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.BluetoothManager;

public class HomepageFragment extends BaseFragment{
	private BluetoothManager manager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bluetooth_page_home, container, false);
		
		view.findViewById(R.id.addFriendBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
				DiscoveriesFragment df = new DiscoveriesFragment();
				df.setBluetoothManager(manager);
				ft.replace(R.id.bluetooth_page_main_root,df);
				ft.addToBackStack(null);
				ft.commit();
				
			}
		});
		return view;
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (getManager()!=null) {
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
			FriendsFragment ff = new FriendsFragment();
			ff.setManager(manager);
			ft.replace(R.id.im_fragment_friends, ff);
			ft.commit();
		}
	}
	public BluetoothManager getManager() {
		return manager;
	}
	public void setManager(BluetoothManager manager) {
		this.manager = manager;
	}
}

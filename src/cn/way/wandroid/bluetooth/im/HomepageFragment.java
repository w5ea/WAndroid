package cn.way.wandroid.bluetooth.im;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.BaseFragment;
import cn.way.wandroid.R;

public class HomepageFragment extends BaseFragment{
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
				ft.replace(R.id.bluetooth_page_main_root,new DiscoveriesFragment());
				ft.addToBackStack(null);
				ft.commit();
			}
		});
		return view;
	}
}

package cn.way.wandroid.bluetooth.im;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cn.way.wandroid.BaseFragment;
import cn.way.wandroid.R;
import cn.way.wandroid.utils.WLog;

public class DiscoveriesFragment extends BaseFragment {
	private ListView lv ;
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		WLog.d("listView="+lv.toString());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bluetooth_im_page_discoveries, container,false);
		lv = (ListView) view.findViewById(R.id.im_discoveries_list);
		view.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		return view;
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}

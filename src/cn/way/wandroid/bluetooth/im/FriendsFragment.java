package cn.way.wandroid.bluetooth.im;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.way.wandroid.BaseFragment;

public class FriendsFragment extends BaseFragment {
	private FrameLayout view;//主视图
	private ListView lv;//列表
	private ArrayAdapter<BluetoothDevice> adapter;
	private ArrayList<BluetoothDevice> friends = new ArrayList<BluetoothDevice>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new FrameLayout(getActivity());
		lv = new ListView(getActivity());
		view.addView(lv);
		adapter = new ArrayAdapter<BluetoothDevice>(getActivity(), 0){
			@Override
			public int getCount() {
				return friends.size();
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LinearLayout view = (LinearLayout) convertView;
				if (view == null) {
					view = new LinearLayout(getContext());
					
				}
				return view;
			}
			class ViewHolder{
				TextView nameTV;//可设置备注
				TextView stateTV;//对好友可见|对附近所有人可见
			}
		};
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view!=null&&view.getParent()!=null) {
			((ViewGroup)view.getParent()).removeView(view);
		}
		return view;
	}
}

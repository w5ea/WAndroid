package cn.way.wandroid.bluetooth.im;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.way.wandroid.BaseFragment;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.BluetoothManager;

@SuppressLint("InflateParams")
public class FriendsFragment extends BaseFragment {
	private ViewGroup view;//主视图
	private ListView lv;//列表
	private ArrayAdapter<BluetoothDevice> adapter;
	private ArrayList<BluetoothDevice> friends = new ArrayList<BluetoothDevice>();
	private BluetoothManager manager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.bluetooth_im_page_friends, null);
		lv = (ListView) view.findViewById(R.id.im_friends_list);
		adapter = new ArrayAdapter<BluetoothDevice>(getActivity(), 0){
			@Override
			public int getCount() {
				return friends.size();
			}
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if (view == null) {
					view = getActivity().getLayoutInflater().inflate(R.layout.bluetooth_im_list_friends_cell, null);
				}
				ViewHolder holder = (ViewHolder) view.getTag();
				if (holder==null) {
					holder = new ViewHolder();
					holder.nameTV = (TextView) view.findViewById(R.id.name);
					holder.stateTV = (TextView) view.findViewById(R.id.state);
					view.setTag(holder);
				}
				BluetoothDevice bd = friends.get(position);
				holder.nameTV.setText(bd.getName());
				holder.stateTV.setText(BluetoothManager.getBondState(bd));
				return view;
			}
			class ViewHolder{
				TextView nameTV;//可设置备注
				TextView stateTV;//对好友可见|对附近所有人可见
			}
		};
		lv.setAdapter(adapter);
		
		updateView();	
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view!=null&&view.getParent()!=null) {
			((ViewGroup)view.getParent()).removeView(view);
		}
		return view;
	}
	public void updateView(){
		friends.clear();
		if (getManager()!=null) {
			friends.addAll(getManager().getBondedDevices());
		}
		adapter.notifyDataSetChanged();
	}
	public BluetoothManager getManager() {
		return manager;
	}
	public void setManager(BluetoothManager manager) {
		this.manager = manager;
	}
}

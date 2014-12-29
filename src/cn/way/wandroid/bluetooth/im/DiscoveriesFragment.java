package cn.way.wandroid.bluetooth.im;

import java.util.ArrayList;
import java.util.Collection;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.way.wandroid.BaseFragment;
import cn.way.wandroid.R;
import cn.way.wandroid.bluetooth.BluetoothManager;
import cn.way.wandroid.bluetooth.BluetoothManager.DiscoveryListener;

public class DiscoveriesFragment extends BaseFragment {
	private ListView lv ;
	private BluetoothManager bluetoothManager;
	private ArrayList<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
	private ArrayAdapter<BluetoothDevice> adapter;
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
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bluetooth_im_page_discoveries, container,false);
		lv = (ListView) view.findViewById(R.id.im_discoveries_list);
		adapter = new ArrayAdapter<BluetoothDevice>(getActivity(), 0){
			@Override
			public int getCount() {
				return devices.size();
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
				BluetoothDevice bd = devices.get(position);
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
		view.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().popBackStack();
			}
		});
		view.findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getBluetoothManager()!=null) {
					getBluetoothManager().startDiscovery(new DiscoveryListener() {
						@Override
						public void onFinished() {
							Toast.makeText(getActivity(), "Finish", 0).show();
						}
						
						@Override
						public void onDevicesChanged(Collection<BluetoothDevice> devices) {
							devices.clear();
							devices.addAll(devices);
							adapter.notifyDataSetChanged();
						}
					});
				}
			}
		});
		return view;
	}
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	public BluetoothManager getBluetoothManager() {
		return bluetoothManager;
	}
	public void setBluetoothManager(BluetoothManager bluetoothManager) {
		this.bluetoothManager = bluetoothManager;
	}
}

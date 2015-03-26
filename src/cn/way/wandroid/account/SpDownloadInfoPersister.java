package cn.way.wandroid.account;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SpDownloadInfoPersister implements Persister<DownloadInfo> {

	private static final String NAME_OF_DownloadInfo_PERSISTER_DEFAULT = "cn.way.wandroid.defaultDownloadInfopersister";
	
	public static SpDownloadInfoPersister instance;
	public static SpDownloadInfoPersister defaultInstance(Context context){
		if (instance==null) {
			instance = new SpDownloadInfoPersister(context);
		}
		return instance;
	}
	public static SpDownloadInfoPersister instance(Context context,String spName){
		if (instance==null) {
			instance = new SpDownloadInfoPersister(context,spName);
		}
		return instance;
	}
	private SpDownloadInfoPersister(Context context) {
		this(context,NAME_OF_DownloadInfo_PERSISTER_DEFAULT);
	}
	private String spName;
	private SharedPreferences sp;
	private Gson gson = new Gson();
	private SpDownloadInfoPersister(Context context,String spName) {
		this.spName = spName;
		sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
	}
	@Override
	public boolean add(DownloadInfo obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(DownloadInfo obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update(DownloadInfo obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public DownloadInfo read(String objId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<DownloadInfo> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}

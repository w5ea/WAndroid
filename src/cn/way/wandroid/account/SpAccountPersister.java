package cn.way.wandroid.account;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class SpAccountPersister implements AccountPersister {
	
	private static final String NAME_OF_ACCOUNT_PERSISTER_DEFAULT = "cn.way.wandroid.defaultaccountpersister";
	
	public static SpAccountPersister instance;
	public static SpAccountPersister defaultInstance(Context context){
		if (instance==null) {
			instance = new SpAccountPersister(context);
		}
		return instance;
	}
	public static SpAccountPersister instance(Context context,String spName){
		if (instance==null) {
			instance = new SpAccountPersister(context,spName);
		}
		return instance;
	}
	private SpAccountPersister(Context context) {
		this(context,NAME_OF_ACCOUNT_PERSISTER_DEFAULT);
	}
	private String spName;
	private SharedPreferences sp;
	private Gson gson = new Gson();
	private SpAccountPersister(Context context,String spName) {
		this.spName = spName;
		sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
	}
	
	@Override
	public boolean add(Account account) {
		if (account==null) {
			return false;
		}
		ArrayList<Account> accounts = readAll();
		String identity = String.valueOf(System.currentTimeMillis());
		if (account.getIdentity()==null) {
			account.setIdentity(identity);
		}
		//TODO if existed update it
		if (accounts!=null) {
			accounts.add(account);
			return persistAll(accounts);
		}
		return false;
	}

	@Override
	public boolean delete(Account account) {
		if (account==null) {
			return false;
		}
		ArrayList<Account> accounts = readAll();
		if (accounts!=null&&accounts.size()>0&&accounts.contains(account)) {
			accounts.remove(account);
			return persistAll(accounts);
		}
		return false;
	}

	@Override
	public boolean update(Account account) {
		if (account==null) {
			return false;
		}
		ArrayList<Account> accounts = readAll();
		if (accounts!=null&&accounts.size()>0&&accounts.contains(account)) {
			Account ac = new Account();
			ac.setIdentity(account.getIdentity());
			accounts.remove(ac);
			accounts.add(account);
			return persistAll(accounts);
		}
		return false;
	}

	@Override
	public Account read(String accountId) {
		if (accountId==null) {
			return null;
		}
		ArrayList<Account> accounts = readAll();
		if (accounts!=null&&accounts.size()>0) {
			for (Account account : accounts) {
				if (account.getIdentity().equals(accountId)) {
					return account;
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<Account> readAll() {
		Account[] accounts = null;
		ArrayList<Account> accountList = null;
		String jsonStr = sp.getString(spName, null);
		if (jsonStr!=null) {
			accounts = gson.fromJson(jsonStr,Account[].class);
			Log.d("test", accounts.toString());
		}
		if (accounts==null) {
			accountList = new ArrayList<Account>(Arrays.asList(accounts));
			if(persistAll(accountList)){
				return accountList;
			}else{
				return null;
			}
		}
		return null;
	}
	private boolean persistAll(ArrayList<Account> accounts){
		if (accounts!=null) {
			String accountsStr = gson.toJson(accounts);
			Log.w("test","saved accounts:"+ accountsStr);
			return sp.edit().putString(spName, accountsStr).commit();
		}
		return false;
	}
}

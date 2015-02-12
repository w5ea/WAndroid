package cn.way.wandroid.account.usage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.way.wandroid.R;
import cn.way.wandroid.account.AccountManager;
import cn.way.wandroid.account.SpAccountPersister;
import cn.way.wandroid.activityadapter.Piece;

public class AccountUsagePiece extends Piece<AccountUsagePageAdapter> {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AccountManager am = AccountManager.instance(SpAccountPersister.defaultInstance(getActivity()));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.account_usage_piece, container, false);
		return view;
	}
	@Override
	public void onPageReady() {
		// TODO Auto-generated method stub
		
	}

}

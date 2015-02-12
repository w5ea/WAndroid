package cn.way.wandroid.account;

import java.util.List;

public interface AccountListener {
	void onSuccess(List<Account> accounts);
	void onfailure();
}

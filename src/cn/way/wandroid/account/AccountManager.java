package cn.way.wandroid.account;

public class AccountManager {
	private static AccountManager instance;
	private AccountPersister persister;
	private AccountManager(AccountPersister persister) {
		this.persister = persister;
	}
	public static AccountManager instance(AccountPersister persister){
		if (instance==null) {
			instance = new AccountManager(persister);
		}
		return instance;
	}
	
	
	public interface AccountListener{
		void onSucess(Account account);
		void onFailure(int errorCode);
	}
}

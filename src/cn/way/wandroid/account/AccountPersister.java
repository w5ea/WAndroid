package cn.way.wandroid.account;

import java.util.ArrayList;

public interface AccountPersister {
	boolean add(Account account);
	boolean delete(Account account);
	boolean update(Account account);
	Account read(String accountId);
	ArrayList<Account> readAll();
}

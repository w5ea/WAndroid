package cn.way.wandroid.account;

public class Account {
	private String identity;
	private User user;
	@Override
	public String toString() {
		return identity==null?"account":identity;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public boolean equals(Object o) {
		if (o==null||(!Account.class.isInstance(o))) {
			return false;
		}
		Account account = (Account) o;
		String identity = account.getIdentity();
		if (identity!=null) {
			return identity.equals(this.identity);
		}
		return false;
	}
}

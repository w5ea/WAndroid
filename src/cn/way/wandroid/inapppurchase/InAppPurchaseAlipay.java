package cn.way.wandroid.inapppurchase;

public class InAppPurchaseAlipay extends InAppPurchaser {

	
	public InAppPurchaseAlipay() {
		super();
	}

	@Override
	protected void transactionCompletedWithReceipt(Object receipt) {
		// TODO Auto-generated method stub

	}
	@Override
	public void purchaseProduct(Product product) {
		super.purchaseProduct(product);
		
	}
	@Override
	public boolean canMakePayments() {
		// TODO Auto-generated method stub
		return super.canMakePayments();
	}
	
	@Override
	public String getLocaleCurrencySymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restoreCompletedTransactions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void retry() {
		// TODO Auto-generated method stub

	}

}

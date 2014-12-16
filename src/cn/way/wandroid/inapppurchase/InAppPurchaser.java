package cn.way.wandroid.inapppurchase;

import java.math.BigDecimal;
import java.util.Locale;

public abstract class InAppPurchaser{
	private InAppPurchaseListener inAppPurchaseListener;
	private Product currentProduct;//正在交易中的商品
	private Object currentReceipt;//当前交易得到的收据

	protected abstract void transactionCompletedWithReceipt(Object receipt);
	
	public abstract String getLocaleCurrencySymbol();
	
	public boolean canMakePayments(){return true;};
	/**
	 * 注意：如果不传入商品，监听器返回的Error对象是空的
	 * @param product
	 */
	public void purchaseProduct(Product product){
		if (product == null) {
	        if (this.inAppPurchaseListener!=null) {
				this.inAppPurchaseListener.onFailed(this, null);
			}
	        return;
	    }
	    if (product != this.currentProduct) {
	        this.currentProduct = product ;
	    }
	};
	public abstract void restoreCompletedTransactions();
	public abstract void retry();
	
	public Product getCurrentProduct() {
		return currentProduct;
	}
	public Object getCurrentReceipt() {
		return currentReceipt;
	}
	public InAppPurchaseListener getInAppPurchaseListener() {
		return inAppPurchaseListener;
	}
	public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
		this.inAppPurchaseListener = inAppPurchaseListener;
	}
	
	/**
	 * 商品对象
	 * @author Wayne
	 */
	public static class Product{
		public Product() {
			super();
		}
		private String remoteId;
		private String identifier;
		private String localizedDescription;
		private String localizedTitle;
		private BigDecimal price;
		private Locale priceLocale;
		private String priceLocaleString;
		private Integer quantity;
		private Object extraData;
		public String getRemoteId() {
			return remoteId;
		}
		public void setRemoteId(String remoteId) {
			this.remoteId = remoteId;
		}
		public String getIdentifier() {
			return identifier;
		}
		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}
		public String getLocalizedDescription() {
			return localizedDescription;
		}
		public void setLocalizedDescription(String localizedDescription) {
			this.localizedDescription = localizedDescription;
		}
		public String getLocalizedTitle() {
			return localizedTitle;
		}
		public void setLocalizedTitle(String localizedTitle) {
			this.localizedTitle = localizedTitle;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public Locale getPriceLocale() {
			return priceLocale;
		}
		public void setPriceLocale(Locale priceLocale) {
			this.priceLocale = priceLocale;
		}
		public String getPriceLocaleString() {
			return priceLocaleString;
		}
		public void setPriceLocaleString(String priceLocaleString) {
			this.priceLocaleString = priceLocaleString;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public Object getExtraData() {
			return extraData;
		}
		public void setExtraData(Object extraData) {
			this.extraData = extraData;
		}
	}
	
	/**
	 * 应用内购买的事件监听器接口
	 * @author Wayne
	 */
	public interface InAppPurchaseListener{
		void onStarted(InAppPurchaser purchaser);//内购开始
		void onSuccess(InAppPurchaser purchaser);//内购成功
		void onRestored(InAppPurchaser purchaser);//恢复购买成功
		void onFailed(InAppPurchaser purchaser ,Error error);//交易失败
	}
}


package eu.michalbuda.offerGenerator.model;

public class Offer {
	private int offerId;
	private String productCode;
	private String oldHtmlText;
	private String text;
	private String title;
	
	public Offer(int offerId) {
		super();
		this.offerId = offerId;
	}

	public Offer() {
		super();
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOldHtmlText() {
		return oldHtmlText;
	}

	public void setOldHtmlText(String oldHtmlText) {
		this.oldHtmlText = oldHtmlText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}

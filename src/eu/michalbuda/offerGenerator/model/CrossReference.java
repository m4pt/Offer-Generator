package eu.michalbuda.offerGenerator.model;

public class CrossReference {
	private String product;
	private String manufacturer;
	
	public CrossReference(String product, String manufacturer) {
		super();
		this.product = product;
		this.manufacturer = manufacturer;
	}
	
	public CrossReference(){
		this.product = null;
		this.manufacturer = null;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
}

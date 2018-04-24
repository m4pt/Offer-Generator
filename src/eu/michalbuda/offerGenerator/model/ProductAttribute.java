package eu.michalbuda.offerGenerator.model;

public class ProductAttribute {
	private String attribName;
	private String attribValue = "";
	private String attribValue2 = "";
	
	public ProductAttribute() {
		super();
	}

	public ProductAttribute(String attribName, String attribValue) {
		super();
		this.attribName = attribName;
		this.attribValue = attribValue;
	}
	
	public ProductAttribute(String attribName, String attribValue, String attribValue2) {
		super();
		this.attribName = attribName;
		this.attribValue = attribValue;
		this.attribValue2 = attribValue2;
	}

	public String getAttribValue2() {
		return attribValue2;
	}

	public void setAttribValue2(String attribValue2) {
		this.attribValue2 = attribValue2;
	}

	public String getAttribName() {
		return attribName;
	}
	public void setAttribName(String attribName) {
		this.attribName = attribName;
	}
	public String getAttribValue() {
		return attribValue;
	}
	public void setAttribValue(String attribValue) {
		this.attribValue = attribValue;
	}
	
	
}

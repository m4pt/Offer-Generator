package eu.michalbuda.offerGenerator.model;

import java.util.ArrayList;

public class OfferTextTemplate {
	private final String template = "<S><F>3</F><W>300</W><T>[FIRST]</T><Z1>0</Z1><Z2>0</Z2></S><S><F>0</F><W>300</W><T>[SECOND]</T><Z1>-1</Z1><Z2>-1</Z2></S><S><F>0</F><W>300</W><T>[THIRD]</T><Z1>-1</Z1><Z2>-1</Z2></S>";
	private final String templateList = "&lt;UL&gt;[ELEMENTS]&lt;/UL&gt;";
	private final String templatePointList = "&lt;OL&gt;[ELEMENTS]&lt;/OL&gt;";
	private final String templateListElement = "&lt;LI&gt;[LI]&lt;/LI&gt;";
	private final String templateParagraph = "&lt;P&gt;[P]&lt;/P&gt;";
	private final String templateH1 = "&lt;H1&gt; [H1]&lt;/H1&gt;";
	private final String templateH2 = "&lt;H1&gt;[H2]&lt;/H1&gt;";
	private final String templateBold = "&lt;B&gt;[B]&lt;/B&gt;";
	
	private final boolean allowAdditionalText = false;
	
	private String title = ""; //first Section title
	private String secondSectionTitle = "";
	private String thirdSectionTitle = " ";
	
	private ArrayList<String> additionalTextLines = new ArrayList<String>();
	
	private String manufacturer = "";
	private String productCode = "";
	private ArrayList<ProductAttribute> attribs = new ArrayList<ProductAttribute>();
	private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	private ArrayList<CrossReference> refs = new ArrayList<CrossReference>();
	private ArrayList<String> vehicleStrings = new ArrayList<String>();
	 
	public OfferTextTemplate(String title, String manufacturer, String productId, ArrayList<ProductAttribute> attribs,
			ArrayList<Vehicle> vehicles, ArrayList<CrossReference> refs) {
		super();
		this.title = title;
		this.manufacturer = manufacturer;
		this.productCode = productId;
		this.attribs = attribs;
		this.vehicles = vehicles;
		this.refs = refs;
	}
	
	public OfferTextTemplate(String title, ArrayList<ProductAttribute> attribs, ArrayList<Vehicle> vehicles,
			ArrayList<CrossReference> refs) {
		super();
		this.title = title;
		this.attribs = attribs;
		this.vehicles = vehicles;
		this.refs = refs;
	}

	public OfferTextTemplate(String title) {
		super();
		this.title = title;
	}

	public OfferTextTemplate() {
		super();
	}

	@Override
	public String toString() {
		
		// ---------------------------------------------------- FIRST SECTION ------------------------------------------------------
		String first = templateH1.replace("[H1]", this.title);
		
		
		if(!this.manufacturer.isEmpty()){
			first = first + templateH2.replace("[H2]", "Producent: " + this.manufacturer);
		}
		
		if(!this.productCode.isEmpty()){
			first = first + templateParagraph.replace("[P]", "Indeks towaru: " + this.productCode);
		}
		
		if(this.attribs.size() > 0){
			String elements = "";
			for(ProductAttribute attrib : this.attribs){
				elements = elements + templateListElement.replace("[LI]", templateBold.replace("[B]",attrib.getAttribName()) 
						+ " " + attrib.getAttribValue() 
						+ " " + attrib.getAttribValue2());
				
			}
			first = first + templateList.replace("[ELEMENTS]", elements);
		}
		
		// ---------------------------------------------------- SECOND SECTION ---------------------------------------------------- 
		String second = templateH2.replace("[H2]", secondSectionTitle);
		
		if(this.vehicles.size() > 0){
			for(Vehicle v : this.vehicles){
				second = second + templateParagraph.replace("[P]", v.getMarka() + " " + templateBold.replace("[B]",v.getModel()) + "  " + v.getCcm() + "ccm  " + templateBold.replace("[B]",v.getYears()) + "  " + v.getEngineCode().replace(" ", "").replace(",", " "));
				
			}
		}
		
		if(this.vehicleStrings.size()>0) {
			for(String v : this.vehicleStrings) {
				second = second + templateParagraph.replace("[P]", v);
			}
		}
		
		// ---------------------------------------------------- THIRD SECTION ---------------------------------------------------- 
		String third = templateH2.replace("[H2]", thirdSectionTitle);
		
		if(this.refs.size()>0){
			for(CrossReference cr : this.refs){
				third = third + templateParagraph.replace("[P]", cr.getManufacturer() + " " + templateBold.replace("[B]",cr.getProduct()));
			}
			
		}
		
		if (allowAdditionalText) {
			//additional text
			if (additionalTextLines.size() > 0) {
				for (String add : this.additionalTextLines) {
					third = third + templateParagraph.replace("[P]", add);
				}
			} 
		}
		// ---------------------------------------------- PUT SECTIONS IN A TEMPLATE ----------------------------------------------
		String opis = template.replace("[FIRST]", first);
		opis = opis.replace("[SECOND]", second);
		opis = opis.replace("[THIRD]", third);
		return opis;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getProductId() {
		return productCode;
	}

	public void setProductId(String productCode) {
		this.productCode = productCode;
	}

	public ArrayList<ProductAttribute> getAttribs() {
		return attribs;
	}

	public void setAttribs(ArrayList<ProductAttribute> attribs) {
		this.attribs = attribs;
	}

	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public ArrayList<CrossReference> getRefs() {
		return refs;
	}

	public void setRefs(ArrayList<CrossReference> refs) {
		this.refs = refs;
	}

	public String getSecondSectionTitle() {
		return secondSectionTitle;
	}

	public void setSecondSectionTitle(String secondSectionTitle) {
		this.secondSectionTitle = secondSectionTitle;
	}

	public String getThirdSectionTitle() {
		return thirdSectionTitle;
	}

	public void setThirdSectionTitle(String thirdSectionTitle) {
		this.thirdSectionTitle = thirdSectionTitle;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public ArrayList<String> getAdditionalTextLines() {
		return additionalTextLines;
	}

	public void setAdditionalTextLines(ArrayList<String> additionalTextLines) {
		this.additionalTextLines = additionalTextLines;
	}

	public void addAttrib(ProductAttribute attrib) {
		this.attribs.add(attrib);
	}
	
	public void addVehicle(Vehicle vehicle) {
		this.vehicles.add(vehicle);
	}
	
	public void addAdditionalText(String string) {
		this.additionalTextLines.add(string);
	}

	public void addVehicleString(String newLine) {
		this.vehicleStrings .add(newLine);
		
	}

	public ArrayList<String> getVehicleStrings() {
		return vehicleStrings;
	}

	public void setVehicleStrings(ArrayList<String> vehicleStrings) {
		this.vehicleStrings = vehicleStrings;
	}
	
	
	
	
}

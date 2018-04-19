package eu.michalbuda.offerGenerator.model;

public class Vehicle {
	private String type;
	private int id;
	private String marka;
	private String model;
	private int mockW;
	private int mocKM;
	private int ccm;
	private int cyl;
	private String description;
	private String autoDescription;
	private String years;
	private String engineCode;
	
	
	
	public Vehicle() {
	}

	public Vehicle(String type, int id) {
		super();
		this.type = type;
		this.id = id;
	}

	public Vehicle(String type, int id, String marka, String model, int mockW, int mocKM, int ccm, int cyl,
			String years) {
		this.type = type;
		this.id = id;
		this.marka = marka;
		this.model = model;
		this.mockW = mockW;
		this.mocKM = mocKM;
		this.ccm = ccm;
		this.cyl = cyl;
		this.years = years;
	}

	public Vehicle(String type, int id, String marka, String model, int mockW, int mocKM, int ccm, int cyl,
			String years, String engineCode) {
		this.type = type;
		this.id = id;
		this.marka = marka;
		this.model = model;
		this.mockW = mockW;
		this.mocKM = mocKM;
		this.ccm = ccm;
		this.cyl = cyl;
		this.years = years;
		this.engineCode = engineCode;
	}

	
	
	
	@Override
	public String toString() {
		return this.marka + " " + this.model + " " + this.mocKM + "KM " + this.ccm + "ccm " + this.years + " " + this.engineCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAutoDescription() {
		return autoDescription;
	}

	public void setAutoDescription(String autoDescription) {
		this.autoDescription = autoDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getMockW() {
		return mockW;
	}

	public void setMockW(int mockW) {
		this.mockW = mockW;
	}

	public int getMocKM() {
		return mocKM;
	}

	public void setMocKM(int mocKM) {
		this.mocKM = mocKM;
	}

	public int getCcm() {
		return ccm;
	}

	public void setCcm(int ccm) {
		this.ccm = ccm;
	}

	public int getCyl() {
		return cyl;
	}

	public void setCyl(int cyl) {
		this.cyl = cyl;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getEngineCode() {
		return engineCode;
	}

	public void setEngineCode(String engineCode) {
		this.engineCode = engineCode;
	}
	
	
}

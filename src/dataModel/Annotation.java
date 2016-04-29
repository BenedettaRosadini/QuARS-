package dataModel;

public class Annotation {
	
	public Annotation(String indicator, String defect) {
		super();
		Indicator = indicator;
		this.defect = defect;
	}
	private String Indicator;
	private String defect;
	public String getIndicator() {
		return Indicator;
	}
	public void setIndicator(String indicator) {
		Indicator = indicator;
	}
	public String getDefect() {
		return defect;
	}
	public void setDefect(String defect) {
		this.defect = defect;
	}

}

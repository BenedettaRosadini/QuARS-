package GUI;

public class ReportTable {

	
	public ReportTable(String text, String indicator, int rank, boolean defect,String Description) {
		super();
		Text = text;
		Indicator = indicator;
		this.rank = rank;
		this.defect = defect;
		this.Description = Description;
	}
	
	private String Text;
	private String Indicator;
	private int rank;
	private boolean defect;
	private String Description;
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public String getIndicator() {
		return Indicator;
	}
	public void setIndicator(String indicator) {
		Indicator = indicator;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public boolean isDefect() {
		return defect;
	}
	public void setDefect(boolean defect) {
		this.defect = defect;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}

	
	
}

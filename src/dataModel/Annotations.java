package dataModel;

import javax.swing.JTextArea;

public class Annotations {
	
	public Annotations(String indicator, String defect, int rank) {
		super();
		Indicator = indicator;
		this.text = defect;
		this.rank = rank;
	}
	private String Indicator;
	private String text;
	private int rank;
	JTextArea jt;
	public String getIndicator() {
		return Indicator;
	}
	public void setIndicator(String indicator) {
		Indicator = indicator;
	}
	public String getDefect() {
		return text;
	}
	public void setDefect(String defect) {
		this.text = defect;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public JTextArea getJt() {
		return jt;
	}
	public void setJt(JTextArea jt) {
		this.jt = jt;
	}
	
	public String getIndicatorName() {
		String res = null;
		if(this.Indicator.equals("AnaphoricAmbiguity"))
		{
			res = "Anaphoric";
		}
		if(this.Indicator.equals("CoordAmbiguity"))
		{
			res = "Coordination";
		}	
		if(this.Indicator.equals("Vagueness"))
		{
			res = "Vagueness";
		}
		if(this.Indicator.equals("Excessive_length_token"))
		{
			res = "Excessive Length";
		}
		if(this.Indicator.equals("Adverbs_detected"))
		{
			res = "Adverbs";
		}
		if(this.Indicator.equals("Passive"))
		{
			res = "Passive Verbs";
		}
		if(this.Indicator.equals("Unknownacronyms"))
		{
			res = "Unknown Acronyms";
		}	
		if(this.Indicator.equals("MissingElse"))
		{
			res = "Missing Requirement";
		}
		if(this.Indicator.equals("MissingMeasure"))
		{
			res = "Missing Unit of Measure";
		}
		if(this.Indicator.equals("MissingReference"))
		{
			res = "Missing Reference";
		}

		return res;
	}

}

package dataModel;

import javax.swing.JTextArea;

public class Annotations {
	
	public Annotations(String indicator, String text, int rank, String defect) {
		super();
		Indicator = indicator;
		this.text = text;
		this.rank = rank;
		this.defect = defect;
		if(this.Indicator.equals("AnaphoricAmbiguity"))
		{
			this.Explanation =  "Anaphoric Ambiguity: \""+this.defect+"\" refers to a previous part of the text";
		}
		if(this.Indicator.equals("CoordAmbiguity"))
		{
			this.Explanation =  "\""+this.defect+"\" Coordination Ambiguity:  too much coordinating conjunction  are present in the requirement.";
		}
		if(this.Indicator.equals("Vagueness"))
		{
			this.Explanation =  "\""+this.defect+"\" is a vague term.";
		}
		if(this.Indicator.equals("Excessive_length_phrase"))
		{
			this.Explanation =  "Too long requirement.";
		}
		if(this.Indicator.equals("Adverbs_detected"))
		{
			this.Explanation =  "Modal adverbs should not be used.";
		}
		if(this.Indicator.equals("Passive"))
		{
			this.Explanation =  "Passive Verbs should not be used.";
		}
		if(this.Indicator.equals("Unknownacronyms"))
		{
			this.Explanation =  "Unknown Acronyms present in the requirement please check.";
		}
		if(this.Indicator.equals("Missing Requirement"))
		{
			this.Explanation =  "Else condition missing.";
		}
		if(this.Indicator.equals("MissingMeasure"))
		{
			this.Explanation =  "Missing Unit of Measure.";
		}
		if(this.Indicator.equals("MissingReference"))
		{
			this.Explanation =  "Unknown Reference present in the requirement please check.";
		}
		
		
	}
	
	public Annotations(String indicator, String text, int rank) {
		super();
		Indicator = indicator;
		this.text = text;
		this.rank = rank;
	
	}
	private String Indicator;
	private String text;
	private int rank;
	private String defect;
	private String Explanation;

	public String getIndicator() {
		return Indicator;
	}
	public void setIndicator(String indicator) {
		Indicator = indicator;
	}
	public String getText() {
		return text;
	}
	public void setText(String defect) {
		this.text = defect;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
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
		if(this.Indicator.equals("Excessive_length_phrase"))
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
	public String getDefect() {
		return defect;
	}
	public void setDefect(String defect) {
		this.defect = defect;
	}

	public String getExplanation() {
		return Explanation;
	}

	public void setExplanation(String explanation) {
		Explanation = explanation;
	}

}

package dataModel;

public class AnnotationGeneral {

	public AnnotationGeneral(String indicator, String text, int rank,
			String explanation) {
		super();
		Indicator = indicator;
		this.text = text;
		this.rank = rank;
		Explanation = explanation;
	}
	private String Indicator;
	private String text;
	private int rank;
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
	public void setText(String text) {
		this.text = text;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getExplanation() {
		return Explanation;
	}
	public void setExplanation(String explanation) {
		Explanation = explanation;
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
}

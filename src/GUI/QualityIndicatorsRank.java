package GUI;

public class QualityIndicatorsRank {

	 public QualityIndicatorsRank(boolean anaphoric, boolean coordination,
			boolean passiveverbs, boolean adverbs, boolean vagueness,
			boolean excessiveLength, boolean unknownreference,
			boolean unknownacronyms, boolean missingrequirement,
			boolean missingMesure) {
		super();
		this.anaphoric = anaphoric;
		this.coordination = coordination;
		this.passiveverbs = passiveverbs;
		this.adverbs = adverbs;
		this.vagueness = vagueness;
		this.excessiveLength = excessiveLength;
		this.unknownreference = unknownreference;
		this.unknownacronyms = unknownacronyms;
		this.missingrequirement = missingrequirement;
		this.missingMeasure = missingMesure;
	}
	 private boolean anaphoric;
	 private boolean coordination ;
	 private boolean passiveverbs ;
	 private boolean adverbs;
	 private boolean vagueness;
	 private boolean excessiveLength;
	 private boolean unknownreference;
	 private boolean unknownacronyms;
	 private boolean missingrequirement;
	 private boolean missingMeasure;
	 
	public boolean isAnaphoric() {
		return anaphoric;
	}
	public void setAnaphoric(boolean anaphoric) {
		this.anaphoric = anaphoric;
	}
	public boolean isCoordination() {
		return coordination;
	}
	public void setCoordination(boolean coordination) {
		this.coordination = coordination;
	}
	public boolean isPassiveverbs() {
		return passiveverbs;
	}
	public void setPassiveverbs(boolean passiveverbs) {
		this.passiveverbs = passiveverbs;
	}
	public boolean isAdverbs() {
		return adverbs;
	}
	public void setAdverbs(boolean adverbs) {
		this.adverbs = adverbs;
	}
	public boolean isVagueness() {
		return vagueness;
	}
	public void setVagueness(boolean vagueness) {
		this.vagueness = vagueness;
	}
	public boolean isExcessiveLength() {
		return excessiveLength;
	}
	public void setExcessiveLength(boolean excessiveLength) {
		this.excessiveLength = excessiveLength;
	}
	public boolean isUnknownreference() {
		return unknownreference;
	}
	public void setUnknownreference(boolean unknownreference) {
		this.unknownreference = unknownreference;
	}
	public boolean isUnknownacronyms() {
		return unknownacronyms;
	}
	public void setUnknownacronyms(boolean unknownacronyms) {
		this.unknownacronyms = unknownacronyms;
	}
	public boolean isMissingrequirement() {
		return missingrequirement;
	}
	public void setMissingrequirement(boolean missingrequirement) {
		this.missingrequirement = missingrequirement;
	}
	public boolean isMissingMeasure() {
		return missingMeasure;
	}
	public void setMissingMeasure(boolean missingMesure) {
		this.missingMeasure = missingMesure;
	}
	
	public void print_cofiguration() {

		System.out.println("*********************************");
		System.out.println("anaphoric: "+this.anaphoric);
		System.out.println("coordination: "+this.coordination);
		System.out.println("passive verbs: "+this.passiveverbs);
		System.out.println("adverbs: "+this.adverbs);
		System.out.println("vagueness: "+this.vagueness);
		System.out.println("excessive Length: "+this.excessiveLength);
		System.out.println("unknown reference: "+this.unknownreference);
		System.out.println("unknown acronyms: "+this.unknownacronyms);
		System.out.println("missing requirement: "+this.missingrequirement);
		System.out.println("missing Measure: "+this.missingMeasure);
		System.out.println("*********************************");
	}
}

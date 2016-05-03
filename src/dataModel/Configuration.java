package dataModel;

import java.io.IOException;
import java.util.ArrayList;

public class Configuration {

	private static Configuration instance = null;
	private String DocumentPath;
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
	private int anaphoric_rank;
	private int coordination_rank ;
	private int passiveverbs_rank ;
	private int adverbs_rank;
	private int vagueness_rank;
	private int excessiveLength_rank;
	private int unknownreference_rank;
	private int unknownacronyms_rank;
	private int missingrequirement_rank;
	private int missingMeasure_rank;
	
	private Configuration(){	
		super();
	}
	
	
	//singleton Design Pattern
	public static Configuration getInstance()
	{
		if(instance == null)
		{
			instance = new Configuration();
		}
		return instance;
	}


	public void setDocumentPath(String documentPath) {
		DocumentPath = documentPath;
	}


	public void setAnaphoric(boolean anaphoric) {
		this.anaphoric = anaphoric;
	}


	public void setCoordination(boolean coordination) {
		this.coordination = coordination;
	}


	public void setPassiveverbs(boolean passiveverbs) {
		this.passiveverbs = passiveverbs;
	}


	public void setAdverbs(boolean adverbs) {
		this.adverbs = adverbs;
	}


	public void setVagueness(boolean vagueness) {
		this.vagueness = vagueness;
	}


	public void setExcessiveLength(boolean excessiveLength) {
		this.excessiveLength = excessiveLength;
	}


	public void setUnknownreference(boolean unknownreference) {
		this.unknownreference = unknownreference;
	}


	public void setUnknownacronyms(boolean unknownacronyms) {
		this.unknownacronyms = unknownacronyms;
	}


	public void setMissingrequirement(boolean missingrequirement) {
		this.missingrequirement = missingrequirement;
	}


	public void setMissingMeasure(boolean missingMeasure) {
		this.missingMeasure = missingMeasure;
	}


	public String getDocumentPath() {
		return DocumentPath;
	}


	public boolean isAnaphoric() {
		return anaphoric;
	}


	public boolean isCoordination() {
		return coordination;
	}


	public boolean isPassiveverbs() {
		return passiveverbs;
	}


	public boolean isAdverbs() {
		return adverbs;
	}


	public boolean isVagueness() {
		return vagueness;
	}


	public boolean isExcessiveLength() {
		return excessiveLength;
	}


	public boolean isUnknownreference() {
		return unknownreference;
	}


	public boolean isUnknownacronyms() {
		return unknownacronyms;
	}


	public boolean isMissingrequirement() {
		return missingrequirement;
	}


	public boolean isMissingMeasure() {
		return missingMeasure;
	}


	public String getDocumentPath1() {
		// TODO Auto-generated method stub
		return null;
	}


	public int getAnaphoric_rank() {
		return anaphoric_rank;
	}


	public void setAnaphoric_rank(int anaphoric_rank) {
		this.anaphoric_rank = anaphoric_rank;
	}


	public int getCoordination_rank() {
		return coordination_rank;
	}


	public void setCoordination_rank(int coordination_rank) {
		this.coordination_rank = coordination_rank;
	}


	public int getPassiveverbs_rank() {
		return passiveverbs_rank;
	}


	public void setPassiveverbs_rank(int passiveverbs_rank) {
		this.passiveverbs_rank = passiveverbs_rank;
	}


	public int getAdverbs_rank() {
		return adverbs_rank;
	}


	public void setAdverbs_rank(int adverbs_rank) {
		this.adverbs_rank = adverbs_rank;
	}


	public int getVagueness_rank() {
		return vagueness_rank;
	}


	public void setVagueness_rank(int vagueness_rank) {
		this.vagueness_rank = vagueness_rank;
	}


	public int getExcessiveLength_rank() {
		return excessiveLength_rank;
	}


	public void setExcessiveLength_rank(int excessiveLength_rank) {
		this.excessiveLength_rank = excessiveLength_rank;
	}


	public int getUnknownreference_rank() {
		return unknownreference_rank;
	}


	public void setUnknownreference_rank(int unknownreference_rank) {
		this.unknownreference_rank = unknownreference_rank;
	}


	public int getUnknownacronyms_rank() {
		return unknownacronyms_rank;
	}


	public void setUnknownacronyms_rank(int unknownacronyms_rank) {
		this.unknownacronyms_rank = unknownacronyms_rank;
	}


	public int getMissingrequirement_rank() {
		return missingrequirement_rank;
	}


	public void setMissingrequirement_rank(int missingrequirement_rank) {
		this.missingrequirement_rank = missingrequirement_rank;
	}


	public int getMissingMeasure_rank() {
		return missingMeasure_rank;
	}


	public void setMissingMeasure_rank(int missingMeasure_rank) {
		this.missingMeasure_rank = missingMeasure_rank;
	}
	
	
	
}

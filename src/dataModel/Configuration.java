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
	
	
	
}

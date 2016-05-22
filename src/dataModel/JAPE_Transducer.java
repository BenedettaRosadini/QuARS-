package dataModel;

import java.util.ArrayList;

public class JAPE_Transducer {

	private boolean specific;
	private ArrayList<String> Jape_Path;
	private String Path;
	private String annotation;
	private int rank;
	public JAPE_Transducer(boolean specific, String name, String annotation, int rank)
	{
		this.specific= specific;
		Jape_Path = new ArrayList<String>();
		this.Path=name;
		this.annotation = annotation;
		this.rank = rank;
	}

	public void setSpecific(boolean specific) {
		this.specific = specific;
	}

	public void addJape_Path(String jape) {
		Jape_Path.add(jape);
	}

	public boolean isSpecific() {
		return specific;
	}

	public ArrayList<String> getJape_Path() {
		return Jape_Path;
	}

	public String getName() {
		return Path;
	}

	public void setName(String name) {
		this.Path = name;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}

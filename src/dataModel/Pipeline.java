package dataModel;

import java.util.ArrayList;

public class Pipeline {

	private boolean specific;
	private ArrayList<String> Jape_Path;
	private String Path;
	private String annotation;
	public Pipeline(boolean specific, String name, String annotation)
	{
		this.specific= specific;
		Jape_Path = new ArrayList<String>();
		this.Path=name;
		this.annotation = annotation;
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
}

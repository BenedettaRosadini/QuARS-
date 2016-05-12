package dataModel;

import java.util.ArrayList;

public class Requirement {
	
	public Requirement(String iD, String text) {
		super();
		ID = iD;
		this.text = text;
//		this.last_index = index;
//		this.length = length;
		this.result = "PASS";
		annotation_list = new ArrayList <Annotations>();
	}
	private String ID;
	private String text;
	private String result;
//	private long last_index;
//	private long length;
	private ArrayList <Annotations> annotation_list;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	/*public long getLast_index() {
		return last_index;
	}
	public void setLast_index(int last_index) {
		this.last_index = last_index;
	}
	public long getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}*/
	public ArrayList<Annotations> getAnnotation_list() {
		return annotation_list;
	}
	
	public void add_annotation(Annotations ann) {
		annotation_list.add(ann);
	}
	
	public void EvaluateRequirement(){
		if(annotation_list.size() == 0)
		{
			this.result = "PASS";
		}else
		{
			this.result = "FAIL";
		}
	}

}

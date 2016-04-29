package dataModel;

import java.util.ArrayList;
import java.util.Map;

public class Document {

	private Map<String,Requirement> list;
	public Document(Map<String,Requirement> list){
		super();
		this.list = list;
		//print(list);
	}
	
	
	static void print(Map<String,Requirement> test_specification)
	{
		for (Map.Entry<String, Requirement> entry : test_specification.entrySet()) {
			System.out.println("***Key : " + entry.getKey() );
			System.out.println("**Value : " + entry.getValue().getID()+ " "+entry.getValue().getText());
		}
	}
	
//	public void Annoatate(AnnotationSet annset)
//	{
//		
//	}

}

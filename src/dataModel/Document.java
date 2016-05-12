package dataModel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Document {

	private ArrayList<Requirement> general_list = new ArrayList<Requirement>();
	private ArrayList<AnnotationGeneral> general_ann = new ArrayList<AnnotationGeneral>();
	private ArrayList<String> ind = new ArrayList<String>();
	private static  Map<String,Requirement> list =new Hashtable<>();
	public Document(Map<String,Requirement> list){
		super();
		this.list = list;
		//print();
	}
	
	
	static void print()
	{
		for (Map.Entry<String, Requirement> entry : list.entrySet()) {
			System.out.println("***Key : " + entry.getKey() );
			System.out.println("**Value : " + entry.getValue().getID()+ " "+entry.getValue().getText());
		}
	}
	
	public void evaluate_req( Annotations ann)
	{

		for (Map.Entry<String, Requirement> entry : this.list.entrySet()) {
			if(ann.getText().equals(entry.getKey()))
			{
				//System.out.println("trovata corrispondenza");
				entry.getValue().add_annotation(ann);	
			}
		}

		
	}
	
	public void evaluate( )
	{
		for (Map.Entry<String, Requirement> entry : list.entrySet()) {
			if(entry.getValue().getAnnotation_list().size() > 0)
			{
				general_list.add(entry.getValue());
				//System.out.println("Added Element");

			}
		}	
		//System.out.println("******"+general_list.size());
	}
	
	
	public ArrayList<AnnotationGeneral> CreateGeneral()
	{
		
		//System.out.println("******"+general_list.size());
		String Indicator="";
		int rank = 0;
		String Explanation="";
		Iterator <Requirement> it = general_list.iterator();
		while(it.hasNext())
		{
			Requirement req = it.next();
			if(req.getAnnotation_list().size()>0)
			{
				Iterator <Annotations> ita = req.getAnnotation_list().iterator();
				while(ita.hasNext())
				{
					Annotations an = ita.next();
					ind.add(an.getDefect());
					Indicator =Indicator +" - " + an.getIndicatorName();
					rank = rank + an.getRank();
					Explanation = Explanation + " - " +an.getExplanation();
					
				}
			}
			//System.out.println("Indicator "+ Indicator+" rank "+rank+" Explanation "+Explanation);
			AnnotationGeneral ann_genn = new AnnotationGeneral(Indicator,req.getText(), rank, Explanation);
			general_ann.add(ann_genn);
			Indicator = "";
			rank = 0;
			Explanation = "";
		}
		//System.out.println(general_ann.size());
		return general_ann;
		
	}


	public Map<String, Requirement> getList() {
		return list;
	}


	public void setList(Map<String, Requirement> list) {
		this.list = list;
	}


	public ArrayList<AnnotationGeneral> getGeneral_ann() {
		return general_ann;
	}


	public void setGeneral_ann(ArrayList<AnnotationGeneral> general_ann) {
		this.general_ann = general_ann;
	}


	public ArrayList<String> getInd() {
		return ind;
	}


	public void setInd(ArrayList<String> ind) {
		this.ind = ind;
	}
	


}

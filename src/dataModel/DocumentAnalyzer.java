package dataModel;

import gate.util.GateException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import GUI.ReportTable;

public class DocumentAnalyzer {

//	private static DocumentAnalyzer instance = null;
	private Document Document;
	private String Corpora;
	private RequirementDocumentManager extractreq;
	private ArrayList<Indicator> indicator_list;
	private ArrayList<Pipeline> pipelineElement;
	private  ArrayList<Annotations> ann_list = new ArrayList<Annotations>();
	private Configuration conf ;
	public DocumentAnalyzer(Configuration conf){	
		indicator_list = new ArrayList<Indicator>();
		pipelineElement = new ArrayList<Pipeline>();
		extractreq = new RequirementDocumentManager();		
		this.conf = conf;
	}
	

	//Requirement Extraction
	public void ExtractRequirements() throws IOException{
		Map<String,Requirement> Requirements_list;	
		Requirements_list = extractreq.ExtractRequirements(this.conf.getDocumentPath());
		Document document = new Document(Requirements_list);
		this.Corpora = this.extractreq.getCorpora();
	}
	
	//Run Pipeline
	public ArrayList<Annotations> Run() throws IOException, GateException, XMLStreamException{
		this.ExtractRequirements();
		this.resetPipelines();
		this.ConfigurePipelines();
		this.readPipelineList();
		GateInterface gi = new GateInterface();
		gi.runPipeline(this.pipelineElement, this.Corpora);
		ann_list = gi.getAnn();
//  	Iterator <Annotations> it = ann_list.iterator();
//		while(it.hasNext())
//		{
//			Annotations ann = it.next();
//			System.out.println("Text: "+ann.getDefect());
//			System.out.println("Defect: "+ann.getIndicator());
//			System.out.println("Defect: "+ann.getRank());
//		}
		return ann_list;
	}

	public Document getDocument() {
		return Document;
	}

	public ArrayList<Indicator> getIndicator_list() {
		return indicator_list;
	}

	public ArrayList<Pipeline> getPipelineElement() {
		return pipelineElement;
	}


	public void setIndicator_list(ArrayList<Indicator> indicator_list) {
		this.indicator_list = indicator_list;
	}

	public void setPipelineElement(ArrayList<Pipeline> pipelineElement) {
		this.pipelineElement = pipelineElement;
	}

	
	
	//Pipeline Configuration
	public void ConfigurePipelines()
	{

		if(conf.isAnaphoric() == true)
		{
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"anaphoric");
			Pipeline pipeline = new Pipeline(false, "Anaphoric", "AnaphoricAmbiguity", conf.getAnaphoric_rank());
			File[] list = directory.listFiles();
			for(int i = 0; i < list.length;i++)
			{
				if(list[i].getName().contains(".jape"))
				{
					
					pipeline.addJape_Path(list[i].getPath());
				}
			}
			pipelineElement.add(pipeline);
		}
		if(conf.isCoordination() == true)
		{
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"coordination");
			Pipeline pipeline = new Pipeline(false, "Coordination", "CoordAmbiguity", conf.getCoordination_rank());
			File[] list = directory.listFiles();
			for(int i = 0; i < list.length;i++)
			{
				if(list[i].getName().contains(".jape"))
				{
					
					pipeline.addJape_Path(list[i].getPath());
				}
			}
			pipelineElement.add(pipeline);
		}
		if(conf.isPassiveverbs() == true)
		{
		
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"passive_verb");
			Pipeline pipeline = new Pipeline(false, "PassiveVerbs","Passive", conf.getPassiveverbs_rank());
			File[] list = directory.listFiles();
			for(int i = 0; i < list.length;i++)
			{
				if(list[i].getName().contains(".jape"))
				{
					
					pipeline.addJape_Path(list[i].getPath());
				}
			}
			pipelineElement.add(pipeline);
		}
		if(conf.isAdverbs() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"adverbs");
			Pipeline pipeline = new Pipeline(false, "Adverbs","Adverbs_detected", conf.getAdverbs_rank());                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                         
					                                                              
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		} 
		if(conf.isExcessiveLength() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"Length");
			Pipeline pipeline = new Pipeline(true, "ExcessiveLength","Excessive_length_token", conf.getExcessiveLength_rank());                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                         
					                                                              
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		}
		if(conf.isUnknownacronyms() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"acronyms");
			Pipeline pipeline = new Pipeline(false, "UnknownAcronyms", "Unknownacronyms", conf.getUnknownacronyms_rank());                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                         
					                                                             
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		}
		if(conf.isMissingrequirement() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"Missingrequirement");
			Pipeline pipeline = new Pipeline(false, "MissingRequirement","MissingElse", conf.getMissingrequirement_rank());                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                         
					                                                             
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		}
		if(conf.isMissingMeasure() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"Missingrequirement");
			Pipeline pipeline = new Pipeline(false, "MissingMeasure", "MissingUnit", conf.getMissingMeasure_rank());                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                         
					                                                          
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		}
		
		if(conf.isVagueness() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"vagueness");
			Pipeline pipeline = new Pipeline(false,"Vagueness", "Vagueness", conf.getVagueness_rank() );                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                                                                                      
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		}
		if(conf.isUnknownreference() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"MissingReference");
			Pipeline pipeline = new Pipeline(false, "UnknownReference", "MissingReference", conf.getMissingrequirement_rank());                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                                                                                      
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		}
	}
	
	
	public void resetPipelines(){
		if(pipelineElement.size() >0)
		{
			pipelineElement.clear();
			System.out.println("deleted");
		}else{
			//System.out.println("already empty");
		}
	}
//	public void AnnotateRequirements(AnnotationSet annset)
//	{
//		
//	}
	
	public void readPipelineList()
	{
		Iterator <Pipeline> it = pipelineElement.iterator();
		
		while(it.hasNext())
		{
			Pipeline p = it.next();
			System.out.println(p.getName()+ ": ");
			Iterator <String> it1 = p.getJape_Path().iterator();
			while(it1.hasNext())
			{
				String s = it1.next();
				System.out.println(s);
			}
		}
	}


	public ArrayList<Annotations> getAnn_list() {
		return ann_list;
	}


	public void setAnn_list(ArrayList<Annotations> ann_list) {
		this.ann_list = ann_list;
	}
	

	public String GenerateReport(ArrayList<ReportTable> clarity_defect, ArrayList<ReportTable> nonambiguity_defect, ArrayList<ReportTable> completeness_defect) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		return this.extractreq.Report(clarity_defect,nonambiguity_defect, completeness_defect);
	}
}

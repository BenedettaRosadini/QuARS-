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

	private ArrayList<JAPE_Transducer> pipelineElement;
	private  ArrayList<Annotations> ann_list = new ArrayList<Annotations>();
	private  ArrayList<Requirement> general_list = new ArrayList<Requirement>();
	private Configuration conf ;
	public DocumentAnalyzer(Configuration conf){	

		pipelineElement = new ArrayList<JAPE_Transducer>();
		extractreq = new RequirementDocumentManager();		
		this.conf = conf;
	}
	

	//Requirement Extraction
	public void ExtractRequirements() throws IOException{
		Map<String,Requirement> Requirements_list;	
		Requirements_list = extractreq.ExtractRequirements(this.conf.getDocumentPath());
		Document document = new Document(Requirements_list);
		this.setDocument(document);
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
		Iterator <Annotations> it = ann_list.iterator();
		while(it.hasNext())
		{
			Annotations cc = it.next();
			//System.out.println("***"+cc.getText());
			this.Document.evaluate_req(cc);			
		}
		this.Document.evaluate();
		return ann_list;
	}
	
	public ArrayList<AnnotationGeneral> RunGeneral(){
		ArrayList<AnnotationGeneral> ann_gen = new ArrayList<AnnotationGeneral>();
		ann_gen = this.Document.CreateGeneral();
		return ann_gen;
	}

	public Document getDocument() {
		return Document;
	}
	


	public ArrayList<JAPE_Transducer> getPipelineElement() {
		return pipelineElement;
	}



	public void setPipelineElement(ArrayList<JAPE_Transducer> pipelineElement) {
		this.pipelineElement = pipelineElement;
	}

	
	
	//Pipeline Configuration
	public void ConfigurePipelines()
	{

		if(conf.isAnaphoric() == true)
		{
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"anaphoric");
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "Anaphoric", "AnaphoricAmbiguity", conf.getAnaphoric_rank());
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
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "Coordination", "CoordAmbiguity", conf.getCoordination_rank());
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
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "PassiveVerbs","Passive", conf.getPassiveverbs_rank());
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
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "Adverbs","Adverbs_detected", conf.getAdverbs_rank());                                                                      
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
			JAPE_Transducer pipeline = new JAPE_Transducer(true, "ExcessiveLength","Excessive_length_phrase", conf.getExcessiveLength_rank());                                                                      
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
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "UnknownAcronyms", "Unknownacronyms", conf.getUnknownacronyms_rank());                                                                      
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
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "MissingRequirement","MissingElse", conf.getMissingrequirement_rank());                                                                      
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
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"MissingMeasure");
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "MissingMeasure", "MissingUnit", conf.getMissingMeasure_rank());                                                                      
			File[] list = directory.listFiles();                                                                          
			for(int i = 0; i < list.length;i++)                                                                           
			{                                                                                                             
				if(list[i].getName().contains(".jape"))                                                                    
				{                                                                                                         
					                                                          
					System.out.println(list[i].getName());
					pipeline.addJape_Path(list[i].getPath());                                                             
				}                                                                                                         
			}                                                                                                             
			pipelineElement.add(pipeline);                                                                                
		}
		
		if(conf.isVagueness() == true)                                                                                 
		{                                                                                                                 
		                                                                                                                  
			File directory = new File(System.getProperty("user.dir")+File.separator+"jape"+File.separator+"vagueness");
			JAPE_Transducer pipeline = new JAPE_Transducer(false,"Vagueness", "Vagueness", conf.getVagueness_rank() );                                                                      
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
			JAPE_Transducer pipeline = new JAPE_Transducer(false, "UnknownReference", "MissingReference", conf.getMissingrequirement_rank());                                                                      
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
		Iterator <JAPE_Transducer> it = pipelineElement.iterator();
		
		while(it.hasNext())
		{
			JAPE_Transducer p = it.next();
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
	

	public String GenerateReport(ArrayList<ReportTable> clarity_defect, ArrayList<ReportTable> nonambiguity_defect, ArrayList<ReportTable> completeness_defect,ArrayList<ReportTable> general_defect) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		return this.extractreq.Report(clarity_defect,nonambiguity_defect, completeness_defect,general_defect);
	}
	
	public String GenerateReportAll(ArrayList<ReportTable> clarity_defect, ArrayList<ReportTable> nonambiguity_defect, ArrayList<ReportTable> completeness_defect,ArrayList<ReportTable>general_defect) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		return this.extractreq.ReportAll(clarity_defect,nonambiguity_defect, completeness_defect,general_defect);
	}


	public void setDocument(Document document) {
		Document = document;
	}
}

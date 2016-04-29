package dataModel;

import gate.util.GateException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class DocumentAnalyzer {

//	private static DocumentAnalyzer instance = null;
	private Document Document;
	private String Corpora;
	private RequirementDocumentManager extractreq;
	private ArrayList<Indicator> indicator_list;
	private ArrayList<Pipeline> pipelineElement;
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
	public void Run() throws IOException, GateException{
		this.ExtractRequirements();
		this.resetPipelines();
		this.ConfigurePipelines();
		this.readPipelineList();
		GateInterface gi = new GateInterface();
		gi.runPipeline(this.pipelineElement, this.Corpora);
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
			Pipeline pipeline = new Pipeline(false, "Anaphoric", "AnaphoricAmbiguity");
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
			Pipeline pipeline = new Pipeline(false, "Coordination", "CoordAmbiguity");
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
			Pipeline pipeline = new Pipeline(false, "Passiveverbs","Passive");
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
			Pipeline pipeline = new Pipeline(false, "Adverbs","Adverbs_detected");                                                                      
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
			Pipeline pipeline = new Pipeline(true, "ExcessiveLength","Excessive_length_token");                                                                      
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
			Pipeline pipeline = new Pipeline(false, "Unknownacronyms", "Unknownacronyms");                                                                      
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
			Pipeline pipeline = new Pipeline(false, "Missingrequirement","MissingElse");                                                                      
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
			Pipeline pipeline = new Pipeline(false, "MissingMeasure", "MissingUnit");                                                                      
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
			Pipeline pipeline = new Pipeline(false,"isVagueness", "Vagueness" );                                                                      
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
			Pipeline pipeline = new Pipeline(false, "Unknownreference", "MissingReference");                                                                      
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

}

package dataModel;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.corpora.DocumentStaxUtils;
import gate.creole.ANNIEConstants;
import gate.creole.SerialAnalyserController;
import gate.relations.Relation;
import gate.relations.RelationSet;
import gate.util.GateException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;








import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FileUtils;



public class GateInterface {

	private SerialAnalyserController pipeline;
	static public ArrayList <Annotations> ann_list = new ArrayList <Annotations>();
	static public ArrayList <String> false_pos = new ArrayList <String>();
	public GateInterface()
	{
		super();
		//jape_rules = new ArrayList <LanguageAnalyser>();
		false_pos.add("logical and");
		false_pos.add("logical or");
		false_pos.add("green LED");
		false_pos.add("Short circuit");
		false_pos.add("short circuit");
		false_pos.add("Raw data");
		false_pos.add("raw data");
		false_pos.add("Hard disk");
		false_pos.add("flashing ligth");
		false_pos.add("hard disk");
		false_pos.add("also");
		false_pos.add("even");
		false_pos.add("thereafter");
		false_pos.add("once");
		false_pos.add(" as");
		false_pos.add(" well");
		false_pos.add("not");
		false_pos.add("Information purposes only");
	}
	
	public void runPipeline(ArrayList<JAPE_Transducer> Pipe, String DocPath) throws GateException, XMLStreamException, IOException{
			
		ann_list.clear();
		File f1 = new File("Annotation");
		f1.delete();
		String new_name;
		File filefolder = new File("Annotation");
    	filefolder.mkdir();
		Iterator <JAPE_Transducer>  ite= Pipe.iterator();
		Iterator <String>  it1;
		JAPE_Transducer pipe;
		Iterator<Document> corpIterator;
		String s;
		File f = new File(DocPath);
		URL sourceUrl = f.toURI().toURL();
		Properties props = System.getProperties();
		props.setProperty("gate.site.config", "gate.xml");
		org.apache.log4j.BasicConfigurator.configure();
		System.out.println("Initialising GATE...");
		Gate.init();	
		Gate.getCreoleRegister().registerDirectories(
		           new File(Gate.getPluginsHome(), "ANNIE").toURI().toURL());
		while(ite.hasNext())
		{
			pipe = ite.next();
			SerialAnalyserController pipeline =
			          (SerialAnalyserController)gate.Factory.createResource(
			             "gate.creole.SerialAnalyserController");
			LanguageAnalyser annotationreset = (LanguageAnalyser)gate.Factory.createResource("gate.creole.annotdelete.AnnotationDeletePR");
			LanguageAnalyser tokeniser = (LanguageAnalyser)gate.Factory.createResource("gate.creole.tokeniser.DefaultTokeniser");
			LanguageAnalyser sentencesp = (LanguageAnalyser)gate.Factory.createResource("gate.creole.splitter.SentenceSplitter");
			LanguageAnalyser Gazetteer = (LanguageAnalyser)gate.Factory.createResource("gate.creole.gazetteer.DefaultGazetteer");
			LanguageAnalyser pos = (LanguageAnalyser)gate.Factory.createResource("gate.creole.POSTagger");
			LanguageAnalyser NPChunker = (LanguageAnalyser)gate.Factory.createResource("mark.chunking.GATEWrapper");
			
			pipeline.add(annotationreset);
			pipeline.add(tokeniser);
			pipeline.add(sentencesp);
			pipeline.add(Gazetteer);
			pipeline.add(pos);
			pipeline.add(NPChunker);
			
			
			it1= pipe.getJape_Path().iterator();
			while(it1.hasNext())
			{
				s = it1.next();
				System.out.println(s);
				LanguageAnalyser jape = (LanguageAnalyser)gate.Factory.createResource(
				          "gate.creole.Transducer", gate.Utils.featureMap("grammarURL", new File(s).toURI().toURL(),
				              "encoding", "UTF-8"));
				pipeline.add(jape);
			}
			
			// feature map for creating documents
			FeatureMap params = Factory.newFeatureMap();
			params.put(Document.DOCUMENT_URL_PARAMETER_NAME, sourceUrl);
			params.put(Document.DOCUMENT_ENCODING_PARAMETER_NAME, "UTF-8");

			FeatureMap features = Factory.newFeatureMap();
			features.put("createdOn", new Date());			
			// increment i to name each doc and corpus uniquely	
			// create document with specified params, features and unique name
			Document doc=(Document) Factory.createResource("gate.corpora.DocumentImpl", params, features, f.getName()+" TestDoc");
			Corpus corpus = gate.Factory.newCorpus(null);    
			corpus.add(doc);
			pipeline.setCorpus(corpus);
			pipeline.execute();
			System.out.println("Found annotations of the following types: " +
			          doc.getAnnotations().getAllTypes());
			//System.out.println("*****"+pipe.getAnnotation());
			for(corpIterator = corpus.iterator(); corpIterator.hasNext();){
				
				
				Document corpDoc = corpIterator.next(); 
				// getting default set of annotations 
				AnnotationSet defaultSet = corpDoc.getAnnotations();
				// get annotations of type COL_NAME and VALUES from default annotation set
				AnnotationSet annotations = defaultSet.get(pipe.getAnnotation());
				new_name="Annotation"+File.separator+pipe.getAnnotation()+".xml";
			
				// System.out.println(gate.Utils.stringFor(doc, annotations));
				 File outputFile = new File(new_name);
		         DocumentStaxUtils.writeDocument(doc, outputFile);
		         FileUtils.write(outputFile,doc.toXml(doc.getAnnotations().get(pipe.getAnnotation()), true));
				//System.out.println(gate.Utils.stringFor(doc, defaultSet));
		         extract_requirement(new_name, pipe.getAnnotation(), pipe.getRank());
		        // System.out.println("****"+ann_list.size());
			}
			
			Factory.deleteResource(doc);
			Factory.deleteResource(annotationreset);
			Factory.deleteResource(tokeniser);
			Factory.deleteResource(sentencesp);
			Factory.deleteResource(Gazetteer);
			Factory.deleteResource(pos);
			Factory.deleteResource(pipeline);
			params.clear();
			corpus.clear();
		}
		
	}
	
	public void extract_requirement(String name, String annotation, int rank)
	{		
		File filename = new File(name);
		  
	   if (filename.isFile()) 
	   {
		   try {
			   BufferedReader input = new BufferedReader(new FileReader(name));
			   StringBuffer buffer = new StringBuffer();
			   String req = null;
			   String text;
			   while ((text = input.readLine()) != null)
			   {
				   buffer.append(text + "\n");

			   }
			   input.close(); 
			   
			   req = buffer.toString();
			   //System.out.println(req);
			   req=req.replaceAll("&lt;", "<");
			   req=req.replaceAll("&gt;", ">");
			   req=req.replaceAll("&quot;", "\"");			  
			   req=req.replaceAll(">/requirement", "></requirement");
			   req=req.replaceAll("<<", "<");
			   String [] ann = req.split("</requirement>");
			   String regular = null;
			   for(int i = 0; i < ann.length ; ++i)
			   {
				   ann[i]=ann[i].replace("<requirement>", "");
				  
				   if(ann[i].contains(annotation))
				   {
					  
					   ann[i]= ann[i].replaceAll("[\r\n]", "");
					   System.out.println(ann[i]);
					   System.out.println("******************");
					   if(annotation.equals("Excessive_length_phrase"))
					   {
						   String []list = ann[i].split("[<>]");
//						   for(int j= 0; j < list.length ; ++j)
//						   {
//							   System.out.println("**"+list[j]);
//						   } 
						   Annotations a = new Annotations(annotation, list[2], rank, list[2]);
						   ann_list.add(a);
					   }else
					   {
						   extractInformation(ann[i], annotation, rank); 
						  // System.out.println("****"+ann_list.size());
					   }
					   
				   }
			   }
			   
			  
			   

		   } catch (IOException ioException) {
	
		   }
	   }
	}

	public static ArrayList<Annotations> getAnn() {
		return ann_list;
	}

	public static void setAnn(ArrayList<Annotations> ann) {
		GateInterface.ann_list = ann;
	}
	
	
	public void extractInformation(String info, String annotation, int rank)
	{
	   if(!annotation.equals("Excessive_length_phrase"))
	   {
		   String regular = null ;
		   String text = info;
		  // System.out.println("+++++"+info);
		   String regex = "<"+annotation+".*>.*</"+annotation+">";
		   Pattern pattern = Pattern.compile(regex);
		   Matcher matcher = pattern.matcher(info);
		   while (matcher.find())
		   {
			   regular = matcher.group();			  
		   }
		  // System.out.println(""+regular);
		   String []list = regular.split("[<>]");
		  // System.out.println(""+list.length);
		   for(int i = 0; i < list.length ; ++i)
		   {
			   if(((i+1) %2)==0)
			   {
				   text = text.replace("<"+list[i]+">", "") ;
			   }
		   } 
		   
		   if(annotation.equals("Adverbs_detected"))
		   {
			   for(int i = 0; i < list.length ; ++i)
			   {
				  //System.out.println("**"+list[i]);
				   if(((i %2) == 0) && i!=0)
				   {		
					  
					   if(contaParole(list[i]) == 1)
					   {
						   Annotations a = new Annotations(annotation, text, rank, list[i]);
						  // System.out.println("****"+list[i]+"--"+text);
						   boolean check = check_amb(list[i], text);
						   if(check == true)
						   {
							   ann_list.add(a);   
						   }

					   }
	  
					   
				   }
			   }
		   }
		   else if(annotation.equals("Passive"))
		   {
			   for(int i = 0; i < list.length ; ++i)
			   {
				  System.out.println("**"+list[i]);
				   if(((i %2) == 0) && i!=0)
				   {		
					  
					   if(contaParole(list[i]) >1 && contaParole(list[i]) < 5)
					   {
						   Annotations a = new Annotations(annotation, text, rank, list[i]);
						  // System.out.println("****"+list[i]+"--"+text);
						   boolean check = check_amb(list[i], text);
						   if(check == true)
						   {
							   ann_list.add(a);   
						   }

					   }
	  
					   
				   }
			   }
		   }
		   else
		   {
			   for(int i = 0; i < list.length ; ++i)
			   {
				   //System.out.println("**"+list[i]);
				   if(((i %2) == 0) && i!=0)
				   {					   
					   Annotations a = new Annotations(annotation, text, rank, list[i]);
					   //System.out.println("****"+list[i]+"--"+text);
					   if(annotation.equals("Vagueness"))
					   {
						  // System.out.println("Analyzing"+list[i]);
						   boolean check = check_amb(list[i], text);
						   if(check == true)
						   {
							   ann_list.add(a);  
							   //System.out.println("OK*****"+list[i]);
						   }
						   
					   }else{
						   ann_list.add(a);  
					   }
					  
					   
				   }
			   }
			   //System.out.println("**"+text);
		   }
	   }
	}
	
	public boolean check_amb(String word, String sentence)
	{
		boolean res = true;
		Iterator <String> it = false_pos.iterator();
	
	    while(it.hasNext())
	    {
	    	String hh = it.next();
	    	
	    	if(word.contains(hh))
	    	{
	    		res= false;
	    	}
	    }
	    if(sentence.contains("Information purposes only"))
	    {
	    	return false;
	    }
		return res;
	}
	
	private int contaParole(String frase) {
	    int nc = frase.length();
	    int indice = 0, numeroParole = 0;

	    while (indice < nc) {
	      // ignora gli spazi bianchi
	      while ((indice < nc) && (frase.charAt(indice) == ' ')) {
	        indice++;
	      }
	      // trova la fine della parola
	      while ((indice < nc) && (frase.charAt(indice) != ' ')) {
	        indice++;
	      }
	      // un'altra parola trovata, incremento del contatore
	      numeroParole++;
	    }
	    return numeroParole;
	  }

}


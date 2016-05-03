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








import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FileUtils;



public class GateInterface {

	private SerialAnalyserController pipeline;
	static public ArrayList <Annotations> ann_list = new ArrayList <Annotations>();
	public GateInterface()
	{
		super();
		//jape_rules = new ArrayList <LanguageAnalyser>();
	}
	
	public void runPipeline(ArrayList<Pipeline> Pipe, String DocPath) throws GateException, XMLStreamException, IOException{
			
		ann_list.clear();
		File f1 = new File("Annotation");
		f1.delete();
		String new_name;
		File filefolder = new File("Annotation");
    	filefolder.mkdir();
		Iterator <Pipeline>  ite= Pipe.iterator();
		Iterator <String>  it1;
		Pipeline pipe;
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
			
			
			pipeline.add(annotationreset);
			pipeline.add(tokeniser);
			pipeline.add(sentencesp);
			pipeline.add(Gazetteer);
			pipeline.add(pos);
			
			
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
			
			for(corpIterator = corpus.iterator(); corpIterator.hasNext();){
				
				
				Document corpDoc = corpIterator.next(); 
				// getting default set of annotations 
				AnnotationSet defaultSet = corpDoc.getAnnotations();
				// get annotations of type COL_NAME and VALUES from default annotation set
				//AnnotationSet annotations = defaultSet.get(pipe.getAnnotation());
				 new_name="Annotation"+File.separator+pipe.getAnnotation()+".xml";
				 System.out.println(defaultSet.get(pipe.getAnnotation()).size());
				 File outputFile = new File(new_name);
		         DocumentStaxUtils.writeDocument(doc, outputFile);
		         FileUtils.write(outputFile,doc.toXml(doc.getAnnotations().get(pipe.getAnnotation()), true));
				//System.out.println(gate.Utils.stringFor(doc, defaultSet));
		         extract_requirement(new_name, pipe.getAnnotation(), pipe.getRank());
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
	
	public void extract_requirement(String name, String annotation, int rank){
		
		
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
			   String [] ann = req.split("</requirement>");
			   for(int i = 0; i < ann.length ; ++i)
			   {
				   ann[i]=ann[i].replace("<requirement>", "");
				  
				   if(ann[i].contains(annotation))
				   {
					   //System.out.println("**"+ann[i]);
					   ann[i]= ann[i].replaceAll("[\r\n]", "");
					   Annotations a = new Annotations(annotation, ann[i], rank);
					   ann_list.add(a);
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
	
	
}


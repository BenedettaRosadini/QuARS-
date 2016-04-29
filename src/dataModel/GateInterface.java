package dataModel;

import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.LanguageAnalyser;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class GateInterface {

	private SerialAnalyserController pipeline;

	
	public GateInterface()
	{
		super();
		//jape_rules = new ArrayList <LanguageAnalyser>();
	}
	
	public void runPipeline(ArrayList<Pipeline> Pipe, String DocPath) throws GateException, MalformedURLException{
			
		Iterator <Pipeline>  ite= Pipe.iterator();
		Iterator <String> it1 ;
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
				AnnotationSet colTypeAnnotations = defaultSet.get(pipe.getAnnotation());
				System.out.println("****+++"+colTypeAnnotations.size());
				System.out.println("****+++"+ colTypeAnnotations);
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
	
	
}

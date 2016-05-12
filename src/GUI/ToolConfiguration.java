package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ToolConfiguration {

	public ToolConfiguration() {

	}

	 private int lengththres ;
	 private int anaphoric ;
	 private int coordination ;
	 private int passiveverbs ;
	 private int adverbs ;
	 private int vagueness ;
	 private int excessiveLength ;
	 private int unknownreference ;
	 private int unknownacronyms ;
	 private int missingrequirement ;
	 private int missingMesure ;
	 
	 
	public int getLengththres() {
		return lengththres;
	}
	public void setLengththres(int lengththres) {
		this.lengththres = lengththres;
	}
	public int getAnaphoric() {
		return anaphoric;
	}
	public void setAnaphoric(int anaphoric) {
		this.anaphoric = anaphoric;
	}
	public int getCoordination() {
		return coordination;
	}
	public void setCoordination(int coordination) {
		this.coordination = coordination;
	}
	public int getPassiveverbs() {
		return passiveverbs;
	}
	public void setPassiveverbs(int passiveverbs) {
		this.passiveverbs = passiveverbs;
	}
	public int getAdverbs() {
		return adverbs;
	}
	public void setAdverbs(int adverbs) {
		this.adverbs = adverbs;
	}
	public int getVagueness() {
		return vagueness;
	}
	public void setVagueness(int vagueness) {
		this.vagueness = vagueness;
	}
	public int getExcessiveLength() {
		return excessiveLength;
	}
	public void setExcessiveLength(int excessiveLength) {
		this.excessiveLength = excessiveLength;
	}
	public int getUnknownreference() {
		return unknownreference;
	}
	public void setUnknownreference(int unknownreference) {
		this.unknownreference = unknownreference;
	}
	public int getUnknownacronyms() {
		return unknownacronyms;
	}
	public void setUnknownacronyms(int unknownacronyms) {
		this.unknownacronyms = unknownacronyms;
	}
	public int getMissingrequirement() {
		return missingrequirement;
	}
	public void setMissingrequirement(int missingrequirement) {
		this.missingrequirement = missingrequirement;
	}
	public int getMissingMesure() {
		return missingMesure;
	}
	public void setMissingMesure(int missingMesure) {
		this.missingMesure = missingMesure;
	}
	
	public void updateThreshold(int lengththres) 
   	{
   		File file = new File("jape"+File.separator+"Length"+File.separator+"Template"+File.separator+"partial.jape");
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader input = new BufferedReader(fr);
		StringBuffer buffer = new StringBuffer();
		String text;
		String jape = "";
		try {
			while ((text = input.readLine()) != null)
			{
				jape = jape+"\r\n" + text;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jape);
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File file1 = new File("jape"+File.separator+"Length"+File.separator+"sentence_length_0.jape");
		file1.delete();
		file = new File("jape"+File.separator+"Length"+File.separator+"sentence_length_0.jape");
		try {
			FileWriter fw = new FileWriter(file1);
			fw.write(jape);
			String number = Integer.toString(lengththres);
			fw.write("if (tokensOrdered.size() > "+number+")  {");
			fw.write("\r\n");
			fw.write("int token_len = tokensOrdered.size();");
			fw.write("\r\n");
			fw.write("features.put(\"rule\", token_len);");
			fw.write("\r\n");
			fw.write("outputAS.add(tokens.firstNode(), tokens.lastNode(), \"Excessive_length_phrase\", features);");
			fw.write("\r\n");
			fw.write("}}}");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    	JFrame f = null;
	    	JOptionPane.showMessageDialog(f,
	    		    "Error in Length Threshold settings",
	    		    "Configuration error",
	    		    JOptionPane.ERROR_MESSAGE);
	   
		}
		
   	}
}

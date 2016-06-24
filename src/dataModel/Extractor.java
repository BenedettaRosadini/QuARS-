package dataModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Extractor {

	ArrayList <String> acronyms = new ArrayList <String>();
	ArrayList <String> reference = new ArrayList <String>();
	public void extractor(){}
	public void Extract() throws IOException
	{
		FileReader f;
	    f=new FileReader("Acronyms"+ File.separator+"list.txt");
	    StringBuffer buffer = new StringBuffer();
	    BufferedReader b;
	    b=new BufferedReader(f);

	    String s;
	    while((s=b.readLine())!=null) 
	    {
	       
	        acronyms.add(s);
	    }
	    FileReader f1;
	    f1=new FileReader("Reference"+File.separator+"list.txt");
	    StringBuffer buffer1 = new StringBuffer();
	    BufferedReader b1 = null;
	    b=new BufferedReader(f1);

	    String s1;
	    while((s1=b.readLine())!=null) 
	    {
	        reference.add(s1);
	    }
	}
	public ArrayList<String> getAcronyms() {
		return acronyms;
	}
	public void setAcronyms(ArrayList<String> acronyms) {
		this.acronyms = acronyms;
	}
	public ArrayList<String> getReference() {
		return reference;
	}
	public void setReference(ArrayList<String> reference) {
		this.reference = reference;
	}
	
}

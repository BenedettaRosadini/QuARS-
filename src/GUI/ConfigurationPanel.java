package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import static javax.swing.GroupLayout.Alignment.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.text.*;

public class ConfigurationPanel  implements  ActionListener{
	JFrame mainFrame = new JFrame();
	static int error = 0;
	private ToolConfiguration t;
	private JLabel anaphoriclabel ;
    private JTextField anaphoricText ;	 
	private JLabel coordinationlabel ;
	private JTextField coordinationText ;
	private JLabel passivelabel ;
	private JTextField passivetext ; 
	private JLabel adverbslabel ;
	private JTextField adverbsText  ;
	private JLabel lengthlabel ;
	private JTextField lengthText   ;
	private JLabel vaguenesslabel;
	private JTextField vaguenessText  ;
	private JLabel unreferencelabel ;
	private JTextField unreferenceText ;
	private JLabel unacronymslabel  ;
	private JTextField unacronymsText ;
	private JLabel missingrequirementlabel;
	private JTextField missingrequirementText  ;
	private JLabel unitlabel  ;
	private JTextField unitText;
	private JLabel label;
	private JTextField textField;
	private JButton saveButton;
	ToolConfiguration toolConf;

	
	public ToolConfiguration getConfiguration(){
		return toolConf;
	}
	public ConfigurationPanel() {		
		
	    JPanel rank = RankPanel();
	    toolConf = new ToolConfiguration();
		label = new JLabel("Requirement Threshold Length:");;
		textField = new JTextField();
		saveButton = new JButton("Save Configuration");
		saveButton.addActionListener(this);
        // remove redundant default border of check boxes - they would hinder
        // correct spacing and aligning (maybe not needed on some look and feels)

 
        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        mainFrame.getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(label)
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(textField)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(LEADING)
                           .addComponent(rank)
                          ) ))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(saveButton)
                )
        );
        
        layout.linkSize(SwingConstants.HORIZONTAL, saveButton);
 
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(label)
                .addComponent(textField)
                .addComponent(saveButton))
            .addGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(BASELINE)
                    		.addComponent(rank)                    		
                        ))
                )
        );
        mainFrame.setVisible(true);
        mainFrame.setTitle("Configuration Panel");
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
	
	public JPanel RankPanel()
    {
		 anaphoriclabel = new JLabel("Anaphoric");
		//JTextField anaphoricText = new JTextField(3) ;	
		anaphoricText = new JTextField(new NumberLimiter(),"",10);	 
		coordinationlabel = new JLabel("Coordination") ;
		coordinationText = new JTextField(new NumberLimiter(),"",10);;
		passivelabel = new JLabel("Passive Verb");
		passivetext = new JTextField(new NumberLimiter(),"",10); 
		adverbslabel = new JLabel("Adverbs");
		adverbsText = new JTextField(new NumberLimiter(),"",10) ;
		lengthlabel = new JLabel("Excessive Length");
		lengthText = new JTextField(new NumberLimiter(),"",10);  ;
		vaguenesslabel = new JLabel("Vagueness");
		vaguenessText = new JTextField(new NumberLimiter(),"",10); ;
		unreferencelabel = new JLabel("Undefined Reference");
		unreferenceText = new JTextField(new NumberLimiter(),"",10); ;
		unacronymslabel  = new JLabel("Undefined Acronyms");
		unacronymsText = new JTextField(new NumberLimiter(),"",10);;
		missingrequirementlabel = new JLabel("Missing Requirement");
		missingrequirementText = new JTextField(new NumberLimiter(),"",10); ;
		unitlabel = new JLabel("Missing Unit of Measure") ;
		unitText = new JTextField(new NumberLimiter(),"",10);

	
		
	    JPanel panel = new JPanel();
		panel.add(anaphoriclabel);
		panel.add(anaphoricText);
		panel.add(coordinationlabel);
		panel.add(coordinationText);
		panel.add(passivelabel);
		panel.add(passivetext);
		panel.add(adverbslabel);
		panel.add(adverbsText);
		panel.add(lengthlabel);
		panel.add(lengthText);
		panel.add(vaguenesslabel);
		panel.add(vaguenessText);
		panel.add(unreferencelabel);
		panel.add(unreferenceText);
		panel.add(unacronymslabel);
		panel.add(unacronymsText);
		panel.add(missingrequirementlabel);
		panel.add(missingrequirementText);
		panel.add(unitlabel);
		panel.add(unitText);
		panel.setSize(300, 120);
		panel.setBorder((Border) new TitledBorder(new EtchedBorder(), "Rank"));
    	//panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
		panel.setLayout(new GridLayout(0,2));
    	return panel; 
    } 
	
	class NumberLimiter extends PlainDocument
	  {
	    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
	    {
	      if(str.length() > 1 || "0123456789".indexOf(str) < 0)
	      {
	        java.awt.Toolkit.getDefaultToolkit().beep();
	        return;
	      }
	      super.insertString(offs, str, (javax.swing.text.AttributeSet) a);
	    }
	  }
	

	
   	 public void actionPerformed(ActionEvent e) {
   		if(e.getActionCommand() == "Save Configuration")
   		{
		    try 
		    {
		    	 error = 0 ;
		    	 int lengththres = 0;
		    	 int anaphoric = 0;
	        	 int coordination = 0;
	        	 int passiveverbs = 0;
	        	 int adverbs = 0;
	        	 int vagueness = 0;
	        	 int excessiveLength = 0;
	        	 int unknownreference = 0;
	        	 int unknownacronyms = 0;
	        	 int missingrequirement = 0;
	        	 int missingMesure = 0;
	        	 String anaphoricext = anaphoricText.getText();
	        	 String coordinationext =  coordinationText.getText();
	        	 String passiveverbsext = passivetext.getText();
	        	 String adverbsext = adverbsText.getText();
	        	 String vaguenessext = vaguenessText.getText();
	        	 String excessiveLengthext = lengthText.getText();
	        	 String unknownreferenceext = unreferenceText.getText();
	        	 String unknownacronymsext = unacronymsText.getText();
	        	 String missingrequirementext = missingrequirementText.getText();
	        	 String missingMesureext = missingrequirementText.getText();
	        	 String lengththresext = textField.getText();
	        	 //anaphoric
	        	 if(!anaphoricext.isEmpty())
	        	 {
	        		 anaphoric = Integer.parseInt(anaphoricext);
	        		 if(anaphoric > 10 ||  anaphoric < 0)
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 //coordination
	        	 if(!coordinationext.isEmpty())
	        	 {
	        		 coordination = Integer.parseInt(coordinationext);
	        		 if(coordination > 10 || coordination < 0)
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 //passive verbs        	 
	        	 if(!passiveverbsext.isEmpty())
	        	 {
	        		 passiveverbs = Integer.parseInt(passiveverbsext);
	        		 if(passiveverbs > 10 || passiveverbs < 0 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 
	        	 //adverbs        	 
	        	 if(!adverbsext.isEmpty())
	        	 {
	        		 adverbs = Integer.parseInt(adverbsext);
	        		 if(adverbs > 10 || adverbs < 0 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 
	        	 //vagueness
	        	 if(!vaguenessext.isEmpty())
	        	 {
	        		 vagueness = Integer.parseInt(vaguenessext);
	        		 if(vagueness > 10 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 // excessiveLength
	        	 
	        	 if(!excessiveLengthext.isEmpty())
	        	 {
	        		 excessiveLength = Integer.parseInt(excessiveLengthext);
	        		 if(excessiveLength > 10 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 
	        	 //unknownreference
	        	 if(!unknownreferenceext.isEmpty())
	        	 {
	        		 unknownreference = Integer.parseInt(unknownreferenceext);
	        		 if(unknownreference > 10 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 
	        	 //unknownacronyms
	        	 if(!unknownacronymsext.isEmpty())
	        	 {
	        		 unknownacronyms = Integer.parseInt(unknownacronymsext);
	        		 if(unknownacronyms > 10 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 //missingrequirement       	 
	        	 if(!missingrequirementext.isEmpty())
	        	 {
	        		 missingrequirement = Integer.parseInt(missingrequirementext);
	        		 if(missingrequirement > 10 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 // missingMesure
	        	 
	        	 if(!missingMesureext.isEmpty())
	        	 {
	        		 missingMesure = Integer.parseInt(missingMesureext);
	        		 if(missingMesure > 10 )
	        		 {
	        			 JFrame f = null;
	        		     JOptionPane.showMessageDialog(f,
	        		    		   "Please provide a number in the range [1,10]",
	        		    		   "Configuration error",
	        		    		   JOptionPane.ERROR_MESSAGE);
	        		     error = 1;
	        		 }
	        	 }
	        	 
	        	 //lengththresext
	        	 if(!lengththresext.isEmpty())
	        	 {
	        		 lengththres = Integer.parseInt(lengththresext);
	        		
	        	 }
		    	 
		    	 if(lengththres == 0)
		    	 {
		    		 lengththres = 60;
		    	 }
		    	 if(anaphoric == 0)
		    	 {
		    		 anaphoric = 5;
		    	 }
		    	 if(coordination == 0)
		    	 {
		    		 coordination = 5;
		    	 }
		    	 if(passiveverbs == 0)
		    	 {
		    		 passiveverbs = 5;
		    	 }
		    	 if(adverbs == 0)
		    	 {
		    		 adverbs = 5;
		    	 }
		    	 if(vagueness == 0)
		    	 {
		    		 vagueness = 5;
		    	 }
		    	 if(excessiveLength == 0)
		    	 {
		    		 excessiveLength = 5;
		    	 }
		    	 if(unknownreference == 0)
		    	 {
		    		 unknownreference = 5;
		    	 }
		    	 if(unknownacronyms == 0)
		    	 {
		    		 unknownacronyms = 5;
		    	 }
		    	 if(missingrequirement == 0)
		    	 {
		    		 missingrequirement = 5;
		    	 }
		    	 if(missingMesure == 0)
		    	 {
		    		 missingMesure = 5;
		    	 }

	        	if(error == 0){
	        		JFrame f = null;
	        		JOptionPane.showMessageDialog(f,
	        			    "Configuration Successfully acquired.");
	        		
	        		mainFrame.setVisible(false);
	        		toolConf.setLengththres(lengththres);
	        		toolConf.setAdverbs(adverbs);
	        		toolConf.setAnaphoric(anaphoric);
	        		toolConf.setCoordination(coordination);
	        		toolConf.setPassiveverbs(passiveverbs);
	        		toolConf.setVagueness(vagueness);
	        		toolConf.setExcessiveLength(excessiveLength);
	        		toolConf.setUnknownreference(unknownreference);
	        		toolConf.setUnknownacronyms(unknownacronyms);
	        		toolConf.setMissingMesure(missingMesure);
	        		toolConf.setMissingrequirement(missingrequirement);
	        	}
	        	 
	        	
		   	 
		    } catch (Exception ex) {
		    	JFrame f = null;
		    	JOptionPane.showMessageDialog(f,
		    		    "Please provide a number in the range [1,10]",
		    		    "Configuration error",
		    		    JOptionPane.ERROR_MESSAGE);
		    	error = 1;
		    }
	  }
   	}
   	 
   	
}
	
	
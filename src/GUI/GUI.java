package GUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import static javax.swing.GroupLayout.Alignment.*;
 
public class GUI extends JFrame {
	
	//graphic interface
	private JTextArea textArea ;
	private JCheckBox Anaphoric ;
	private JCheckBox PassiveVerbs ;
	private JCheckBox Adverbs ;
	private JCheckBox Vagueness ;
	private JCheckBox EscessiveLength ;
	private JCheckBox unknownReference ;
	private JCheckBox Coordination ;
	private JCheckBox unknownAcronyms ;
	private JCheckBox MissingRequirement ;
	private JCheckBox MissingMesure ;
	private JButton findButton;
	private JButton RunButton;
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JMenuItem menuItemnew;
	private JMenuItem menuItemsave;
	private JMenuItem menuItemsaveas;
	private JMenuItem menuItemopen;
	private JMenuItem menuItemclose;
	private JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	
	//data 
	private ToolConfiguration toolconf = new ToolConfiguration();
	private String DocPath;
	private QualityIndicatorsRank QItoolconfiguration;
	
	
    public GUI() {
    	//Create the menu bar.
    	menuBar = new JMenuBar();

    	//Build the first menu.
    	menu = new JMenu("File");
    	menu.setMnemonic(KeyEvent.VK_A);
    	menu.getAccessibleContext().setAccessibleDescription(
    	        "The only menu in this program that has menu items");
    	menuBar.add(menu);

    	//a group of JMenuItems
    	menuItemnew = new JMenuItem("New",
    	                         KeyEvent.VK_T);
    	menuItemnew.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_1, ActionEvent.ALT_MASK));
    	menuItemnew.addActionListener(new MenuActionListener());
    	menuItemsave = new JMenuItem("Save",
                KeyEvent.VK_T);
    	menuItemsave.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_2, ActionEvent.ALT_MASK));
    	menuItemsave.addActionListener(new MenuActionListener());
    	menuItemsaveas = new JMenuItem("Save as",
                KeyEvent.VK_T);
    	menuItemsaveas.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_3, ActionEvent.ALT_MASK));
    	menuItemsaveas.addActionListener(new MenuActionListener());
    	menuItemopen = new JMenuItem("Open",
                KeyEvent.VK_T);
    	menuItemopen.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_4, ActionEvent.ALT_MASK));
    	menuItemopen.addActionListener(new MenuActionListener());
    	menuItemclose = new JMenuItem("Close",
                KeyEvent.VK_T);
    	menuItemclose.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_5, ActionEvent.ALT_MASK));
    	menuItemclose.addActionListener(new MenuActionListener());
    	menu.add(menuItemnew);
    	menu.add(menuItemsave);
    	menu.add(menuItemsaveas);
    	menu.add(menuItemopen);
    	menu.add(menuItemclose);
    	
    	
    	//a submenu
    	menu.addSeparator();
    	submenu = new JMenu("Configuration");
    	submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("Options");
    	menuItem.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_6, ActionEvent.ALT_MASK));
    	menuItem.addActionListener(new MenuActionListener());
    	submenu.add(menuItem);
    	menu.add(submenu);
    	menuBar.add(menu);
    	this.setJMenuBar(menuBar);
    	
    	
    	//panel

    	JPanel NonAmbiguity = createNonAmbiguityBox();
    	JPanel Clarity = createClarityBox();
    	JPanel Completeness = createCompletenessBox();
    	//this.add(frame, BorderLayout.CENTER);
        textArea = new JTextArea();
        
        RunButton = new JButton("Run");
        RunButton.addActionListener(new RunProcedure());
        findButton = new JButton("Search File");
        findButton.addActionListener(new OpenFileChooser());

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
 
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(textArea)             
                   .addGroup(layout.createSequentialGroup()
                     .addGroup(layout.createParallelGroup(LEADING)
                         .addComponent(NonAmbiguity)                    	                                    
                      )
                    .addGroup(layout.createParallelGroup(LEADING)
                    		.addComponent(Clarity)
                        )
                     .addGroup(layout.createParallelGroup(LEADING)
                    		 .addComponent(Completeness)
                       )))               
             .addGroup(layout.createParallelGroup(LEADING)
            		 .addComponent(findButton))
            .addGroup(layout.createParallelGroup(LEADING)
            		 .addComponent(RunButton))
        );
        
        layout.linkSize(SwingConstants.HORIZONTAL, findButton, RunButton);
 
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(textArea)
                .addComponent(findButton)
                .addComponent(RunButton))
            .addGroup(layout.createParallelGroup(LEADING)
               .addGroup(layout.createSequentialGroup()
            		.addGroup(layout.createParallelGroup(BASELINE)
            				.addComponent(NonAmbiguity)
            				.addComponent(Clarity)
            				.addComponent(Completeness))
                    ))            
        );
 
        setTitle("QuARS++");
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public JPanel createNonAmbiguityBox()
    {
    	Anaphoric = new JCheckBox("Anaphoric");
    	Anaphoric.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	Coordination=new JCheckBox("Coordination"); 
    	Coordination.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	JPanel panel = new JPanel();
    	panel.add(Anaphoric);
    	panel.add(Coordination);
    	panel.setBorder
    	((Border) new TitledBorder(new EtchedBorder(), "Non Ambiguity"));
    	SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
    	panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
    	return panel; 
    } 
    
    
    public JPanel createClarityBox()
    {
    	Vagueness = new JCheckBox("Vagueness");
        EscessiveLength = new JCheckBox("Excessive Length");
        PassiveVerbs = new JCheckBox("Passive Verbs");
    	Adverbs = new JCheckBox("Adverbs");
    	Vagueness.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EscessiveLength.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        PassiveVerbs.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        Adverbs.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	JPanel panel = new JPanel();
    	panel.add(Vagueness);
    	panel.add(EscessiveLength);
    	panel.add(Adverbs);
    	panel.add(PassiveVerbs);
    	panel.setBorder
    	((Border) new TitledBorder(new EtchedBorder(), "Clarity"));
    	panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
    	return panel; 
    } 
    
    public JPanel createCompletenessBox()
    {
    	unknownReference = new JCheckBox("Unknown Reference");
    	unknownAcronyms = new JCheckBox("Unknown Acronyms");
    	MissingRequirement = new JCheckBox("MissingRequirement");
    	MissingMesure = new JCheckBox("Missing Unit of Measure");
    	unknownReference.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	unknownAcronyms.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	MissingRequirement.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	MissingMesure.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    	JPanel panel = new JPanel();
    	panel.add(unknownReference);
    	panel.add(unknownAcronyms);
    	panel.add(MissingMesure);
    	panel.add(MissingRequirement);
    	panel.setBorder
    	((Border) new TitledBorder(new EtchedBorder(), "Completeness"));
    	panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
    	return panel; 
    } 
    


    private class RunProcedure implements ActionListener {
    	 public void actionPerformed(ActionEvent e) {
             try 
             {
            	 boolean anaphoric = Anaphoric.isSelected();
            	 boolean coordination = Coordination.isSelected();
            	 boolean passiveverbs = PassiveVerbs.isSelected();
            	 boolean adverbs = Adverbs.isSelected();
            	 boolean vagueness = Vagueness.isSelected();
            	 boolean excessiveLength = EscessiveLength.isSelected();
            	 boolean unknownreference = unknownReference.isSelected();
            	 boolean unknownacronyms = unknownAcronyms.isSelected();
            	 boolean missingrequirement = MissingRequirement.isSelected();
            	 boolean missingMesure = MissingMesure.isSelected();
            	 QItoolconfiguration = new QualityIndicatorsRank( anaphoric,  coordination,
			 passiveverbs,  adverbs,  vagueness,
			 excessiveLength,  unknownreference,
			 unknownacronyms,  missingrequirement,
			 missingMesure);
            	 //toolconfiguration.print_cofiguration();
            	 
             } catch (Exception ex) {}
           }
    }
    private class OpenFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
          try {
            textArea.setText("");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new DocumentFileFilter());
            int n = fileChooser.showOpenDialog(GUI.this);
            if (n == JFileChooser.APPROVE_OPTION) {
              File f = fileChooser.getSelectedFile();
              System.out.println(f.getPath());
              textArea.append(f.getPath());
              DocPath = f.getPath();
            }
          } catch (Exception ex) {}
        }


      }
    
    class MenuActionListener implements ActionListener {
    	
    	  public void actionPerformed(ActionEvent e) {
    		  
    		  
    		if(e.getActionCommand().equals("Close"))
    		{
    			System.exit(0);
    		}
    		if(e.getActionCommand().equals("Options"))
    		{
    			
    			ConfigurationPanel cp = new ConfigurationPanel();
    			toolconf = cp.getConfiguration();
    		}
    		if(e.getActionCommand().equals("Save"))
    		{
    			System.out.println("Selected: " + toolconf.getAdverbs());
    			System.out.println("Selected: " + toolconf.getAnaphoric());
    		}
    		else
    		{
    	    System.out.println("Selected: " + e.getActionCommand());
    		}
    	  }
    	}

    private class DocumentFileFilter extends FileFilter {

        public boolean accept(File file) {
        boolean ret = false;
          if (file.isDirectory()) return true;
            String fname = file.getName().toLowerCase();
            if(fname.endsWith("docx") || fname.endsWith("xlsx")|| fname.endsWith("doc"))
            {
            	ret = true;
            }
            return ret;
        }
        

        public String getDescription() {
          return "Requirements Document";
        }



      }
     
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                                  "javax.swing.plaf.metal.MetalLookAndFeel");
                                //  "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                                //UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                new GUI().setVisible(true);

            }
        });
    }

	public ToolConfiguration getToolconf() {
		return toolconf;
	}

	public void setToolconf(ToolConfiguration toolconf) {
		this.toolconf = toolconf;
	}

	public String getDocPath() {
		return DocPath;
	}

	public void setDocPath(String docPath) {
		DocPath = docPath;
	}

	public QualityIndicatorsRank getToolconfiguration() {
		return QItoolconfiguration;
	}

	public void setToolconfiguration(QualityIndicatorsRank toolconfiguration) {
		this.QItoolconfiguration = toolconfiguration;
	}
}

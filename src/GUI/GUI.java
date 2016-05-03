package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import dataModel.Annotations;
import dataModel.Configuration;
import dataModel.DocumentAnalyzer;
import dataModel.MyComparator;
import static javax.swing.GroupLayout.Alignment.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JTextArea;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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
	private JButton ReportButton;
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JMenuItem menuItemnew;
	private JMenuItem menuItemsave;
	private JMenuItem menuItemsaveas;
	private JMenuItem menuItemopen;
	private JMenuItem menuItemclose;
	private JTabbedPane tab;
	private JRadioButtonMenuItem rbMenuItem;
	JCheckBoxMenuItem cbMenuItem;
	private JPanel mainPanel = new JPanel();
	private DefaultTableModel Table_Completness_list;
	private DefaultTableModel Table_Non_Ambiguity_list;
	private DefaultTableModel Table_Clarity_list;
	private JTable Completeness_table;
	private JTable NonAmbiguity_table;
	private JTable Clarity_table;
	//data 
	private ToolConfiguration toolconf = new ToolConfiguration();
	private String DocPath = null;
	private ArrayList<Annotations> clarity_list = new ArrayList<Annotations>();
	private ArrayList<Annotations> completeness_list = new ArrayList<Annotations>();
	private ArrayList<Annotations> nonambiguity_list = new ArrayList<Annotations>();
	
	private ArrayList<ReportTable> clarity_defect = new ArrayList<ReportTable>();
	private ArrayList<ReportTable> completeness_defect = new ArrayList<ReportTable>();
	private ArrayList<ReportTable> nonambiguity_defect = new ArrayList<ReportTable>();
    private DocumentAnalyzer da; 
	
    public GUI() {
    	
    	
    	tab = JTabbedPaneDemo();
    	tab.setVisible(false);
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
        ReportButton = new JButton("Generate Report");
        ReportButton.setVisible(false);
        ReportButton.addActionListener(new GenerateReport());
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
                   ))
                   .addComponent(tab)   )               
             .addGroup(layout.createParallelGroup(LEADING)
            		 .addComponent(findButton))
            .addGroup(layout.createParallelGroup(LEADING)
            		 .addComponent(RunButton)
            		 .addComponent(ReportButton))
            		 
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
           .addGroup(layout.createParallelGroup(LEADING)
                   .addGroup(layout.createSequentialGroup()
                		.addGroup(layout.createParallelGroup(BASELINE)
                				.addComponent(tab)                				
                				)
                				
                	.addGroup(layout.createParallelGroup(BASELINE)
                				.addComponent(ReportButton)                				
                				)
                		   ))   
        );
 
        setTitle("QuARS++");
        pack();
        setSize(1300, 200);
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
    

    public JTabbedPane JTabbedPaneDemo()
    {
    	
        JTabbedPane tabbedPane = new JTabbedPane();
        ImageIcon icon = createImageIcon("images/middle.gif");
         
        JComponent panel1 = makeTextPanelCompleteness("Completeness");
        tabbedPane.addTab("Completeness", icon, panel1,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        JComponent panel2 = makeTextPanelNonAmbiguity("NonAmbiguity");
        tabbedPane.addTab("NonAmbiguity", icon, panel2,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
         
        JComponent panel3 = Clarity("Clarity");
        tabbedPane.addTab("Clarity", icon, panel3,
                "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
         
         
        //Add the tabbed pane to this panel.
        add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        return tabbedPane;
    } 
    
    protected JComponent makeTextPanelCompleteness(String text) {
        JPanel panel = new JPanel(false);       
        Object[] columnHeaders = {"Requirement", "Type of Defect", "Relevance", "Not a Defect"};        

        
        Table_Completness_list = new DefaultTableModel() {
            Class[] types = {
            		String.class, String.class, Integer.class,  Boolean.class

            };
            // making sure that it returns boolean.class.   
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        Table_Completness_list.setColumnIdentifiers(columnHeaders);
        Completeness_table = new JTable(Table_Completness_list);  
        Completeness_table.setRowHeight(30);
        panel.add(new JScrollPane(Completeness_table));
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
    
    protected JComponent makeTextPanelNonAmbiguity(String text) {
    	JPanel panel = new JPanel(false);       
        Object[] columnHeaders = {"Requirement", "Type of Defect", "Relevance", "Not a Defect"};        

        
         Table_Non_Ambiguity_list = new DefaultTableModel() {
            Class[] types = {
            		String.class, String.class, Integer.class,  Boolean.class

            };
            // making sure that it returns boolean.class.   
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        Table_Non_Ambiguity_list.setColumnIdentifiers(columnHeaders);
        NonAmbiguity_table = new JTable(Table_Non_Ambiguity_list);   
        NonAmbiguity_table.setRowHeight(30);        
        panel.add(new JScrollPane(NonAmbiguity_table));
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
    
    
    protected JComponent Clarity(String text) {
    	JPanel panel = new JPanel(false);       
        Object[] columnHeaders = {"Requirement", "Type of Defect", "Relevance", "Not a Defect"};        
    	Table_Clarity_list = new DefaultTableModel() {
             Class[] types = {
             		String.class, String.class, Integer.class,  Boolean.class

             };
             // making sure that it returns boolean.class.   
             @Override
             public Class getColumnClass(int columnIndex) {
                 return types[columnIndex];
             }
         };
        Table_Clarity_list.setColumnIdentifiers(columnHeaders);
        Clarity_table = new JTable(Table_Clarity_list); 
        Clarity_table.setRowHeight(30);
        panel.add(new JScrollPane(Clarity_table));
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
     
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        return new ImageIcon(path);
    }
    
    private class GenerateReport implements ActionListener {
   	 public void actionPerformed(ActionEvent e) 
   	 {
   		 String text;
   		 String indicator;
   		 int rank;
   		 boolean defect;
   		 ReportTable report;
   		 
   		clarity_defect.clear();
   		nonambiguity_defect.clear();
   		completeness_defect.clear();
   		
   		if (Table_Clarity_list.getRowCount() > 0) {
    	    for (int i = 0; i < Table_Clarity_list.getRowCount(); i++) {
    	    	text = (String) Table_Clarity_list.getValueAt(i, 0);
    	    	indicator = (String) Table_Clarity_list.getValueAt(i, 1);
    	    	rank = (int) Table_Clarity_list.getValueAt(i, 2);
    	    	defect = (boolean) Table_Clarity_list.getValueAt(i, 3);
    	    	report = new ReportTable(text, indicator,rank,defect);
    	    	clarity_defect.add(report);
    	    }
    	}
   		if (Table_Non_Ambiguity_list.getRowCount() > 0) {
    	    for (int i = 0; i < Table_Non_Ambiguity_list.getRowCount(); i++) {
    	    	
    	    	text = (String) Table_Non_Ambiguity_list.getValueAt(i, 0);
    	    	indicator = (String) Table_Non_Ambiguity_list.getValueAt(i, 1);
    	    	rank = (int) Table_Non_Ambiguity_list.getValueAt(i, 2);
    	    	defect = (boolean) Table_Non_Ambiguity_list.getValueAt(i, 3);
    	    	report = new ReportTable(text, indicator,rank,defect);
    	    	nonambiguity_defect.add(report);
    	    }
    	}
   		
   		
   		if (Table_Completness_list.getRowCount() > 0) {
    	    for (int i = 0; i < Table_Completness_list.getRowCount(); i++) {
    	    	
    	    	text = (String) Table_Completness_list.getValueAt(i, 0);
    	    	indicator = (String) Table_Completness_list.getValueAt(i, 1);
    	    	rank = (int) Table_Completness_list.getValueAt(i, 2);
    	    	defect = (boolean) Table_Completness_list.getValueAt(i, 3);
    	    	report = new ReportTable(text, indicator,rank,defect);
    	    	completeness_defect.add(report);
    	    }
    	}
   		
   		try {
			String res = da.GenerateReport(clarity_defect, nonambiguity_defect, completeness_defect);
			JFrame f = null;
		     JOptionPane.showMessageDialog(f,
		    		   "Report Correctly Generated!!! Path: "+res,
		    		   "Report Generation",
		    		   JOptionPane.INFORMATION_MESSAGE);
		} catch (EncryptedDocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JFrame f = null;
		     JOptionPane.showMessageDialog(f,
		    		    e1.toString(),
		    		   "Error",
		    		   JOptionPane.ERROR_MESSAGE);

		} catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JFrame f = null;
		     JOptionPane.showMessageDialog(f,
		    		    e1.toString(),
		    		   "Error",
		    		   JOptionPane.ERROR_MESSAGE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JFrame f = null;
		     JOptionPane.showMessageDialog(f,
		    		    e1.toString(),
		    		   "Error",
		    		   JOptionPane.ERROR_MESSAGE);
		}
   		 	
      }
   }
    

    private class RunProcedure implements ActionListener {
    	 public void actionPerformed(ActionEvent e) {
    		 
    		 myAttemptActionPerformed();
           }
    }
    private class OpenFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
          try {
            textArea.setText(null);
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new DocumentFileFilter());
            int n = fileChooser.showOpenDialog(GUI.this);
            if (n == JFileChooser.APPROVE_OPTION) {
              File f = fileChooser.getSelectedFile();
              System.out.println(f.getPath());
              textArea.append(f.getPath());
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

    private void myAttemptActionPerformed() {
        Window thisWin = SwingUtilities.getWindowAncestor(mainPanel);
        final JDialog progressDialog = new JDialog(thisWin, "Waiting..");
        JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(200, 25));
        final JProgressBar bar = new JProgressBar(0, 100);
        bar.setIndeterminate(true);
        contentPane.add(bar);
        progressDialog.setContentPane(contentPane);
        progressDialog.pack();
        progressDialog.setLocationRelativeTo(null);
        final Task task = new Task("My attempt");
        task.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equalsIgnoreCase("progress")) {
                    int progress = task.getProgress();
                    if (progress == 0) {
                        bar.setIndeterminate(true);
                    } else {
                        bar.setIndeterminate(false);
                        bar.setValue(progress);
                        progressDialog.dispose();
                    }
                }
            }
        });
        task.execute();
        progressDialog.setVisible(true);
    }
    
    class Task extends SwingWorker<Void, Void> {

        private static final long SLEEP_TIME = 4000;
        private String text;

        public Task(String text) {
            this.text = text;
        }

        @Override
        public Void doInBackground() {
            setProgress(0);
            try 
            {
            	clarity_list.clear();;
            	completeness_list.clear();
            	nonambiguity_list.clear();

            	System.out.println(Table_Non_Ambiguity_list.getRowCount());
            	System.out.println(Table_Clarity_list.getRowCount());
            	System.out.println(Table_Completness_list.getRowCount());
            	
            	if (Table_Non_Ambiguity_list.getRowCount() > 0) {
            	    for (int i = Table_Non_Ambiguity_list.getRowCount() - 1; i > -1; i--) {
            	    	Table_Non_Ambiguity_list.removeRow(i);
            	    }
            	}
            	
            	if (Table_Completness_list.getRowCount() > 0) {
            	    for (int i = Table_Completness_list.getRowCount() - 1; i > -1; i--) {
            	    	Table_Completness_list.removeRow(i);
            	    }
            	}
            	
            	if (Table_Clarity_list.getRowCount() > 0) {
            	    for (int i = Table_Clarity_list.getRowCount() - 1; i > -1; i--) {
            	    	Table_Clarity_list.removeRow(i);
            	    }
            	}
            	
            	
            	System.out.println(Table_Non_Ambiguity_list.getRowCount());
            	System.out.println(Table_Clarity_list.getRowCount());
            	System.out.println(Table_Completness_list.getRowCount());

            	Configuration conf = null;
	           	conf = conf.getInstance();
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
	           	boolean pressed = (anaphoric || coordination || passiveverbs || adverbs || vagueness || excessiveLength || unknownreference || unknownacronyms || missingrequirement || missingMesure); 
	           	DocPath = textArea.getText();
	           	if((DocPath.length() == 0) || (pressed == false))
	           	{
	           		JFrame f= null;
	           		 JOptionPane.showMessageDialog(f,
	     		    		   "Document Path or Quality Indicator Missing",
	     		    		   "Run error",
	     		    		   JOptionPane.ERROR_MESSAGE);
	           		 
	           	}
	           	else
	           	{         		
	           		conf.setDocumentPath(DocPath);
	            	conf.setAdverbs(adverbs);
	            	conf.setAnaphoric(anaphoric);
	            	conf.setCoordination(coordination);
	            	conf.setPassiveverbs(passiveverbs);
	            	conf.setExcessiveLength(excessiveLength);
	            	conf.setMissingMeasure(missingMesure);
	            	conf.setUnknownacronyms(unknownacronyms);
	            	conf.setVagueness(vagueness);
	            	conf.setUnknownreference(unknownreference);
	            	conf.setMissingrequirement(missingrequirement);
	            	conf.setAdverbs_rank(toolconf.getAdverbs());
	            	conf.setAnaphoric_rank(toolconf.getAnaphoric());
	            	conf.setCoordination_rank(toolconf.getCoordination());
	            	conf.setExcessiveLength_rank(toolconf.getExcessiveLength());
	            	conf.setMissingMeasure_rank(toolconf.getMissingMesure());
	            	conf.setMissingrequirement_rank(toolconf.getMissingrequirement());
	            	conf.setPassiveverbs_rank(toolconf.getPassiveverbs());
	            	conf.setUnknownacronyms_rank(toolconf.getUnknownacronyms());
	            	conf.setUnknownreference_rank(toolconf.getUnknownreference());
	            	conf.setVagueness_rank(toolconf.getVagueness());
	            	da = new DocumentAnalyzer(conf);
	            	ArrayList<Annotations> ann_list = da.Run(); 
	            	Iterator <Annotations> it = ann_list.iterator();
	        		while(it.hasNext())
	        		{
	        			
	        			Annotations ann = it.next();
	        			if(ann.getIndicator().equals("AnaphoricAmbiguity") || ann.getIndicator().equals("CoordAmbiguity"))
	        			{
	        				//System.out.println("Adding non amb "+ann.getIndicator());
	        				nonambiguity_list.add(ann);
	        			}
	        			if(ann.getIndicator().equals("Vagueness") || ann.getIndicator().equals("Excessive_length_token") || ann.getIndicator().equals("Adverbs_detected") || ann.getIndicator().equals("Passive"))
	        			{
	        				//System.out.println("Adding clarity "+ann.getIndicator());
	        				clarity_list.add(ann);
	        			}
	        			if(ann.getIndicator().equals("Unknownacronyms") || ann.getIndicator().equals("MissingElse") || ann.getIndicator().equals("MissingMeasure") || ann.getIndicator().equals("MissingReference"))
	        			{
	        				//System.out.println("Adding compl "+ann.getIndicator());
	        				completeness_list.add(ann);
	        			}
	        		}
	        		
	        		Collections.sort(nonambiguity_list, new MyComparator());
	        		it = nonambiguity_list.iterator();
	        		while(it.hasNext())
	        		{
	        			Annotations ann = it.next();
	        			Table_Non_Ambiguity_list.addRow(new Object[]{ann.getDefect(), ann.getIndicatorName(), ann.getRank(), false});
	        		}
	        		Collections.sort(completeness_list, new MyComparator());
	        		it = completeness_list.iterator();
	        		while(it.hasNext())
	        		{
	        			Annotations ann = it.next();
	        			Table_Completness_list.addRow(new Object[]{ann.getDefect(), ann.getIndicatorName(), ann.getRank(), false});
	        		}
	        		
	        		Collections.sort(clarity_list, new MyComparator());
	        		it = clarity_list.iterator();
	        		while(it.hasNext())
	        		{
	        			Annotations ann = it.next();
	        			Table_Clarity_list.addRow(new Object[]{ann.getDefect(), ann.getIndicatorName(), ann.getRank(), false});
	        		}
	            	setSize(1300, 400);
	            	tab.setVisible(true);
	            	ReportButton.setVisible(true);
	           	}
           	 
            } catch (Exception ex) {}
            setProgress(100);
            return null;
        }

        @Override
        public void done() {
           Toolkit.getDefaultToolkit().beep();
        }
    }
}

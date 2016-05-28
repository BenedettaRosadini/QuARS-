package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import dataModel.AnnotationGeneral;
import dataModel.Annotations;
import dataModel.Configuration;
import dataModel.DocumentAnalyzer;
import dataModel.MyComparator;
import dataModel.comparator_gen;
import static javax.swing.GroupLayout.Alignment.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextArea;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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
	private DefaultTableModel Table_General_list;
	private JTable Completeness_table;
	private JTable NonAmbiguity_table;
	private JTable Clarity_table;
	private JTable General_table;
	//data 
	private ToolConfiguration toolconf = new ToolConfiguration();
	private String DocPath = null;
	private ArrayList<Annotations> clarity_list = new ArrayList<Annotations>();
	private ArrayList<Annotations> completeness_list = new ArrayList<Annotations>();
	private ArrayList<Annotations> nonambiguity_list = new ArrayList<Annotations>();
	private ArrayList<Annotations> nonambiguity_General = new ArrayList<Annotations>();
	
	private ArrayList<ReportTable> clarity_defect = new ArrayList<ReportTable>();
	private ArrayList<ReportTable> completeness_defect = new ArrayList<ReportTable>();
	private ArrayList<ReportTable> nonambiguity_defect = new ArrayList<ReportTable>();
	private ArrayList<ReportTable> general_defect = new ArrayList<ReportTable>();
	
    private DocumentAnalyzer da; 
    private ArrayList<String> highligth_clarity = new  ArrayList<String> ();
    private ArrayList<String> highligth_completeness = new  ArrayList<String> ();
    private ArrayList<String> highligth_nonambiguity = new  ArrayList<String> ();
    private ArrayList<String> highligth_general = new  ArrayList<String> ();
    private ArrayList<AnnotationGeneral> ann_general= new  ArrayList<AnnotationGeneral> ();
    private File save_file;
    
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
    	menuItemsave.addActionListener(new SaveFileChooser());
//    	menuItemsaveas = new JMenuItem("Save as",
//                KeyEvent.VK_T);
//    	menuItemsaveas.setAccelerator(KeyStroke.getKeyStroke(
//                  KeyEvent.VK_3, ActionEvent.ALT_MASK));
//    	menuItemsaveas.addActionListener(new MenuActionListener());
    	menuItemopen = new JMenuItem("Open",
                KeyEvent.VK_T);
    	menuItemopen.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_4, ActionEvent.ALT_MASK));
    	menuItemopen.addActionListener(new OpenFileChooser());;
    	menuItemclose = new JMenuItem("Close",
                KeyEvent.VK_T);
    	menuItemclose.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_5, ActionEvent.ALT_MASK));
    	menuItemclose.addActionListener(new MenuActionListener());
    	menu.add(menuItemnew);
    	menu.add(menuItemsave);
    	//menu.add(menuItemsaveas);
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
        textArea = new JTextArea(2, 30);
        
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
        setSize(1400, 200);
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
        ImageIcon icon = new ImageIcon("images/middle.gif");
         
        JComponent panel1 = makeTextPanelCompleteness("Completeness");
        tabbedPane.addTab("Completeness", icon, panel1,
                "Completeness Quality");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        JComponent panel2 = makeTextPanelNonAmbiguity("NonAmbiguity");
        tabbedPane.addTab("NonAmbiguity", icon, panel2,
                "NonAmbiguity Quality");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
         
        JComponent panel3 = Clarity("Clarity");
        tabbedPane.addTab("Clarity", icon, panel3,
                "Clarity Quality");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        JComponent panel4= General("Ranking by Indicator");
        tabbedPane.addTab("Ranked by Indicator", icon, panel4,
                "Ranked by Indicator");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_3);
         
         
        //Add the tabbed pane to this panel.
        add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        return tabbedPane;
    } 
    
    protected JComponent makeTextPanelCompleteness(String text) {
        JPanel panel = new JPanel(false);       
        Object[] columnHeaders = {"Requirement", "Type of Defect", "Description","Relevance", "Not a Defect"};        

        
        Table_Completness_list = new DefaultTableModel() {
            Class[] types = {
            		String.class, String.class,String.class, Integer.class,  Boolean.class

            };
            // making sure that it returns boolean.class.   
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };

        Table_Completness_list.setColumnIdentifiers(columnHeaders);
        Completeness_table = new JTable(Table_Completness_list); 
        Completeness_table.getColumnModel().getColumn(0).setCellRenderer(new MyCellRenderer_Completness());   
        Completeness_table.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer_Completness());  
        Completeness_table.getColumnModel().getColumn(1).setCellRenderer(new MyCellRenderer_Completness());   

        panel.add(new JScrollPane(Completeness_table));
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
    
    protected JComponent makeTextPanelNonAmbiguity(String text) {
    	JPanel panel = new JPanel(false);       
        Object[] columnHeaders = {"Requirement", "Type of Defect", "Description","Relevance", "Not a Defect"};        

        
         Table_Non_Ambiguity_list = new DefaultTableModel() {
            Class[] types = {
            		String.class, String.class, String.class, Integer.class,  Boolean.class

            };
            // making sure that it returns boolean.class.   
            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        Table_Non_Ambiguity_list.setColumnIdentifiers(columnHeaders);
        NonAmbiguity_table = new JTable(Table_Non_Ambiguity_list);   
        NonAmbiguity_table.getColumnModel().getColumn(0).setCellRenderer(new MyCellRenderer_NonAmbiguity());       
        NonAmbiguity_table.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer_NonAmbiguity());   
        NonAmbiguity_table.getColumnModel().getColumn(1).setCellRenderer(new MyCellRenderer_NonAmbiguity());   
        panel.add(new JScrollPane(NonAmbiguity_table));
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
    
    
    protected JComponent Clarity(String text) {
    	JPanel panel = new JPanel(false);       
        Object[] columnHeaders = {"Requirement", "Type of Defect","Description", "Relevance", "Not a Defect"};        
    	Table_Clarity_list = new DefaultTableModel() {
             Class[] types = {
             		String.class, String.class, String.class, Integer.class,  Boolean.class

             };
             // making sure that it returns boolean.class.   
             @Override
             public Class getColumnClass(int columnIndex) {
                 return types[columnIndex];
             }
         };
        Table_Clarity_list.setColumnIdentifiers(columnHeaders);        
        Clarity_table = new JTable(Table_Clarity_list); 
        Clarity_table.getColumnModel().getColumn(0).setCellRenderer(new MyCellRenderer_Clarity());
        Clarity_table.getColumnModel().getColumn(1).setCellRenderer(new MyCellRenderer_Clarity());
        Clarity_table.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer_Clarity());
       
        panel.add(new JScrollPane(Clarity_table));
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
    
    
    protected JComponent General(String text) {
    	JPanel panel = new JPanel(false);       
        Object[] columnHeaders = {"Requirement", "Type of Defect","Description", "Relevance", "Not a Defect"};        
    	Table_General_list = new DefaultTableModel() {
             Class[] types = {
             		String.class, String.class, String.class, Integer.class,  Boolean.class

             };
             // making sure that it returns boolean.class.   
             @Override
             public Class getColumnClass(int columnIndex) {
                 return types[columnIndex];
             }
         };
        Table_General_list.setColumnIdentifiers(columnHeaders);        
        General_table = new JTable(Table_General_list); 
        General_table.getColumnModel().getColumn(0).setCellRenderer(new MyCellRenderer_General());
        General_table.getColumnModel().getColumn(1).setCellRenderer(new MyCellRenderer_General());
        General_table.getColumnModel().getColumn(2).setCellRenderer(new MyCellRenderer_General());
       
        panel.add(new JScrollPane(General_table));
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
    
 public class MyCellRenderer_Clarity extends JTextArea implements TableCellRenderer {

    	
    	public MyCellRenderer_Clarity() {
          setLineWrap(true);
          setWrapStyleWord(true);
       }

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			setText( (String) value);//or something in value, like value.getNote()...
			setSize(table.getColumnModel().getColumn(column).getWidth(),
	                getPreferredSize().height);
	        if (table.getRowHeight(row) != getPreferredSize().height) {
	                table.setRowHeight(row, getPreferredSize().height);
	        }
	        Font f = new Font("TimesRoman", Font.PLAIN, 14);
	        setFont(f);
	        if (column == 1)
	        {
	        	f = new Font("TimesRoman", Font.BOLD, 14);
	        	setFont(f);
	        }
	        if(column==0)
	        {
		        Iterator<String> it = highligth_clarity.iterator();
		        while(it.hasNext())
		        {
		        	String a = it.next();
		        	String string = value.toString();
		        	if(string.contains(a)){
	                    int indexOf = string.indexOf(a);
	                    try {
	                    	getHighlighter().addHighlight(indexOf,indexOf+a.length(),new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.yellow));

	                    } catch (BadLocationException e) {
	                        e.printStackTrace();
	                    }
	                }
		        }
	        }
	        return this;

		}
    }
    
    public class MyCellRenderer_General extends JTextArea implements TableCellRenderer {

    	
    	public MyCellRenderer_General() {
          setLineWrap(true);
          setWrapStyleWord(true);
       }

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			setText( (String) value);//or something in value, like value.getNote()...
			setSize(table.getColumnModel().getColumn(column).getWidth(),
	                getPreferredSize().height);
	        if (table.getRowHeight(row) != getPreferredSize().height) {
	                table.setRowHeight(row, getPreferredSize().height);
	        }
	        Font f = new Font("TimesRoman", Font.PLAIN, 14);
	        setFont(f);
	        if (column == 1)
	        {
	        	f = new Font("TimesRoman", Font.BOLD, 14);
	        	setFont(f);
	        }
	        if(column==0)
	        {
		        Iterator<String> it = highligth_general.iterator();
		        while(it.hasNext())
		        {
		        	String a = it.next();
		        	String string = value.toString();
		        	if(string.contains(a)){
	                    int indexOf = string.indexOf(a);
	                    try {
	                    	getHighlighter().addHighlight(indexOf,indexOf+a.length(),new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.yellow));

	                    } catch (BadLocationException e) {
	                        e.printStackTrace();
	                    }
	                }
		        }
	        }
	        return this;

		}
    }
    
    
   public class MyCellRenderer_NonAmbiguity extends JTextArea implements TableCellRenderer {

    	
    	public MyCellRenderer_NonAmbiguity() {
          setLineWrap(true);
          setWrapStyleWord(true);
       }

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			setText( (String) value);//or something in value, like value.getNote()...
			setSize(table.getColumnModel().getColumn(column).getWidth(),
	                getPreferredSize().height);
	        if (table.getRowHeight(row) != getPreferredSize().height) {
	                table.setRowHeight(row, getPreferredSize().height);
	        }
	        Font f = new Font("TimesRoman", Font.PLAIN, 14);
	        setFont(f);
	        if (column == 1)
	        {
	        	f = new Font("TimesRoman", Font.BOLD, 14);
	        	setFont(f);
	        }
	        if(column==0)
	        {
		        Iterator<String> it = highligth_nonambiguity.iterator();
		        while(it.hasNext())
		        {
		        	String a = it.next();
		        	String string = value.toString();
		        	if(string.contains(a)){
	                    int indexOf = string.indexOf(a);
	                    try {
	                    	getHighlighter().addHighlight(indexOf,indexOf+a.length(),new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
	
	                    } catch (BadLocationException e) {
	                        e.printStackTrace();
	                    }
	                }
		        }
	        }
	        return this;
	
		}
	}
	
public class MyCellRenderer_Completness extends JTextArea implements TableCellRenderer {

	
	public MyCellRenderer_Completness() {
      setLineWrap(true);
      setWrapStyleWord(true);
   }

	@Override	
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		// TODO Auto-generated method stub
		setText( (String) value);//or something in value, like value.getNote()...
		setSize(table.getColumnModel().getColumn(column).getWidth(),
	            getPreferredSize().height);
	    if (table.getRowHeight(row) != getPreferredSize().height) {
	            table.setRowHeight(row, getPreferredSize().height);
	    }
	    Font f = new Font("TimesRoman", Font.PLAIN, 14);
	    setFont(f);
	    if (column == 1)
	    {
	    	f = new Font("TimesRoman", Font.BOLD, 14);
	    	setFont(f);
	    }
	    if(column==0)
	    {
	        Iterator<String> it = highligth_completeness.iterator();
	        while(it.hasNext())
	        {
	        	String a = it.next();
	        	String string = value.toString();
	        	if(string.contains(a)){
	                int indexOf = string.indexOf(a);
	                try {
	                	getHighlighter().addHighlight(indexOf,indexOf+a.length(),new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
	
	                } catch (BadLocationException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	    return this;
	
	}
	}
        
    
    
    private class GenerateReport implements ActionListener {
   	 public void actionPerformed(ActionEvent e) 
   	 {
   		Object[] possibilities = {"Include only true positive cases", "Include All Defects"};
   		JFrame frame = null;
   		 Icon icon = null;
   		String s = (String)JOptionPane.showInputDialog(
   		                    frame,
   		                    "Select the type of Report\n",
   		                    "Report Dialog",
   		                    JOptionPane.PLAIN_MESSAGE,
   		                    icon,
   		                    possibilities,
   		                    "Generate only Defect");

   		//If a string was returned, say so.
   		if ((s != null) && (s.length() > 0)) {
   		    if(s.equals("Include only true positive cases"))
   		    {
   		    	 String text;
	   	   		 String indicator;
	   	   		 int rank;
	   	   		 boolean defect;
	   	   		 ReportTable report;
	   	   		 String Description;
	   	   		 
	   	   		 clarity_defect.clear();
	   	   		 nonambiguity_defect.clear();
	   	   		 completeness_defect.clear();
	   	   		
	   	   		 if (Table_Clarity_list.getRowCount() > 0) {
	   	    	    for (int i = 0; i < Table_Clarity_list.getRowCount(); i++) {
	   	    	    	text = (String) Table_Clarity_list.getValueAt(i, 0);
	   	    	    	indicator = (String) Table_Clarity_list.getValueAt(i, 1);
	   	    	    	rank = (int) Table_Clarity_list.getValueAt(i, 3);
	   	    	    	defect = (boolean) Table_Clarity_list.getValueAt(i, 4);
	   	    	    	Description = (String) Table_Clarity_list.getValueAt(i, 2);
	   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
	   	    	    	clarity_defect.add(report);
	   	    	    }
	   	    	 }
	   	   		 if (Table_Non_Ambiguity_list.getRowCount() > 0) {
	   	    	    for (int i = 0; i < Table_Non_Ambiguity_list.getRowCount(); i++) {
	   	    	    	
	   	    	    	text = (String) Table_Non_Ambiguity_list.getValueAt(i, 0);
	   	    	    	indicator = (String) Table_Non_Ambiguity_list.getValueAt(i, 1);
	   	    	    	rank = (int) Table_Non_Ambiguity_list.getValueAt(i, 3);
	   	    	    	defect = (boolean) Table_Non_Ambiguity_list.getValueAt(i, 4);
	   	    	    	Description = (String) Table_Non_Ambiguity_list.getValueAt(i,2);
	   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
	   	    	    	nonambiguity_defect.add(report);
	   	    	    }
	   	    	 }
	   	   		
	   	   		
	   	   		 if (Table_Completness_list.getRowCount() > 0) {
	   	    	    for (int i = 0; i < Table_Completness_list.getRowCount(); i++) {
	   	    	    	
	   	    	    	text = (String) Table_Completness_list.getValueAt(i, 0);
	   	    	    	indicator = (String) Table_Completness_list.getValueAt(i, 1);
	   	    	    	rank = (int) Table_Completness_list.getValueAt(i, 3);
	   	    	    	defect = (boolean) Table_Completness_list.getValueAt(i, 4);
	   	    	    	Description = (String) Table_Completness_list.getValueAt(i, 2);
	   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
	   	    	    	completeness_defect.add(report);
	   	    	    }
	   	    	}
	   	   		 
	   	   	if (Table_General_list.getRowCount() > 0) {
   	    	    for (int i = 0; i < Table_General_list.getRowCount(); i++) {
   	    	    	
   	    	    	text = (String) Table_General_list.getValueAt(i, 0);
   	    	    	indicator = (String) Table_General_list.getValueAt(i, 1);
   	    	    	rank = (int) Table_General_list.getValueAt(i, 3);
   	    	    	defect = (boolean) Table_General_list.getValueAt(i, 4);
   	    	    	Description = (String) Table_General_list.getValueAt(i, 2);
   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
   	    	    	general_defect.add(report);
   	    	    }
   	    	}
	   	   		
	   	   		 try {
	   				String res = da.GenerateReport(clarity_defect, nonambiguity_defect, completeness_defect,general_defect);
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
   		    else{
 		    	 String text;
	   	   		 String indicator;
	   	   		 int rank;
	   	   		 boolean defect;
	   	   		 ReportTable report;
	   	   		 String Description;
	   	   		 
	   	   		 clarity_defect.clear();
	   	   		 nonambiguity_defect.clear();
	   	   		 completeness_defect.clear();
	   	   		
	   	   		 if (Table_Clarity_list.getRowCount() > 0) {
	   	    	    for (int i = 0; i < Table_Clarity_list.getRowCount(); i++) {
	   	    	    	text = (String) Table_Clarity_list.getValueAt(i, 0);
	   	    	    	indicator = (String) Table_Clarity_list.getValueAt(i, 1);
	   	    	    	rank = (int) Table_Clarity_list.getValueAt(i, 3);
	   	    	    	defect = (boolean) Table_Clarity_list.getValueAt(i, 4);
	   	    	    	Description = (String) Table_Clarity_list.getValueAt(i, 2);
	   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
	   	    	    	clarity_defect.add(report);
	   	    	    }
	   	    	 }
	   	   		 if (Table_Non_Ambiguity_list.getRowCount() > 0) {
	   	    	    for (int i = 0; i < Table_Non_Ambiguity_list.getRowCount(); i++) {
	   	    	    	
	   	    	    	text = (String) Table_Non_Ambiguity_list.getValueAt(i, 0);
	   	    	    	indicator = (String) Table_Non_Ambiguity_list.getValueAt(i, 1);
	   	    	    	rank = (int) Table_Non_Ambiguity_list.getValueAt(i, 3);
	   	    	    	defect = (boolean) Table_Non_Ambiguity_list.getValueAt(i, 4);
	   	    	    	Description = (String) Table_Non_Ambiguity_list.getValueAt(i,2);
	   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
	   	    	    	nonambiguity_defect.add(report);
	   	    	    }
	   	    	 }
	   	   		
	   	   		
	   	   		 if (Table_Completness_list.getRowCount() > 0) {
	   	    	    for (int i = 0; i < Table_Completness_list.getRowCount(); i++) {
	   	    	    	
	   	    	    	text = (String) Table_Completness_list.getValueAt(i, 0);
	   	    	    	indicator = (String) Table_Completness_list.getValueAt(i, 1);
	   	    	    	rank = (int) Table_Completness_list.getValueAt(i, 3);
	   	    	    	defect = (boolean) Table_Completness_list.getValueAt(i, 4);
	   	    	    	Description = (String) Table_Completness_list.getValueAt(i, 2);
	   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
	   	    	    	completeness_defect.add(report);
	   	    	    }
	   	    	}
	   	   	if(Table_General_list.getRowCount() > 0) {
   	    	    for (int i = 0; i < Table_General_list.getRowCount(); i++) {
   	    	    	
   	    	    	text = (String) Table_General_list.getValueAt(i, 0);
   	    	    	indicator = (String) Table_General_list.getValueAt(i, 1);
   	    	    	rank = (int) Table_General_list.getValueAt(i, 3);
   	    	    	defect = (boolean) Table_General_list.getValueAt(i, 4);
   	    	    	Description = (String) Table_General_list.getValueAt(i, 2);
   	    	    	report = new ReportTable(text, indicator,rank,defect,Description);
   	    	    	general_defect.add(report);
   	    	    }
	   	   	}
	   	   		 try {
	   				String res = da.GenerateReportAll(clarity_defect, nonambiguity_defect, completeness_defect, general_defect);
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
              textArea.append(f.getPath());
              System.out.println(f.getPath());
              if(f.getPath().contains(".txt"))
              {
                  setSize(1400, 700);
              	  tab.setVisible(true);
                  this.configuration(f); 
              }

              }
          } catch (Exception ex) {}
        }
        
        public void configuration(File file) throws IOException
        {   
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
        	if (Table_General_list.getRowCount() > 0) {
        	    for (int i = Table_General_list.getRowCount() - 1; i > -1; i--) {
        	    	Table_General_list.removeRow(i);
        	    }
        	}
        	if(file.getName().contains(".txt") && file.isFile())
        	{
        		
        		FileReader f;
        	    f=new FileReader(file.getPath());
        	    StringBuffer buffer = new StringBuffer();
        	    BufferedReader b;
        	    b=new BufferedReader(f);

        	    String s;
        	    while(true) 
        	    {
        	        s=b.readLine();
        	        if(s==null)
        	          break;
        	        buffer.append(s+ "\n");
        	    }
        	   
        	    
        	    String Req =  buffer.toString();        	    
     		    //System.out.println(Req);
        	   
        	   highligth_clarity.clear();
        	   highligth_completeness.clear();
        	   highligth_general.clear();
        	   highligth_nonambiguity.clear();
        	   extractClarity(Req);
        	   extractNonAmbiguty(Req); 
        	   extractCompleteness(Req);
        	   extractGeneral(Req);
        	   b.close();
           }

        }
        
        
        public void extractClarity(String Req)
        {
           String regex = "<Clarity>.*</Clarity>";
           Pattern pattern = Pattern.compile(regex);
  		   Matcher matcher = pattern.matcher(Req);
  		   String regular = null ;
  		   while (matcher.find())
  		   {  			   
  			 regular = matcher.group();
  			 if(regular != null)
  			 {
	  			 //System.out.println("***"+regular);
	  			 String []list = regular.split("[<>]");
			     Table_Clarity_list.addRow(new Object[]{list[3], list[5], list[7], Integer.parseInt(list[9]), Boolean.parseBoolean(list[11])});     		   
  			 }
  		  }
  		  regex = "<ClarityList>.*</ClarityList>";
          pattern = Pattern.compile(regex);
		  matcher = pattern.matcher(Req);
		  while (matcher.find())
 		   {
			  regular = matcher.group();
			  System.out.println("****"+regular);
			  regular = regular.replace("<ClarityList>", "");
			  regular = regular.replace("</ClarityList>", "");
			  System.out.println("****"+regular);
			  highligth_clarity.add(regular);

 		  }

        }
        
        
        public void extractNonAmbiguty(String Req)
        {
           String regex = "<NonAmbiguity>.*</NonAmbiguity>";
           Pattern pattern = Pattern.compile(regex);
  		   Matcher matcher = pattern.matcher(Req);
  		   String regular = null ;
  		   while (matcher.find())
		   {  			   
			 regular = matcher.group();
			 if(regular != null)
			 {
	  			 //System.out.println("***"+regular);
	  			 String []list = regular.split("[<>]");
			     Table_Non_Ambiguity_list.addRow(new Object[]{list[3], list[5], list[7], Integer.parseInt(list[9]), Boolean.parseBoolean(list[11])});     		   
			 }
		   }
  		   regex = "<Non_AmbiguityList>.*</Non_AmbiguityList>";
           pattern = Pattern.compile(regex);
		   matcher = pattern.matcher(Req);
		  while (matcher.find())
		   {
			  regular = matcher.group();
			  System.out.println("****"+regular);
			  regular = regular.replace("<Non_AmbiguityList>", "");
			  regular = regular.replace("</Non_AmbiguityList>", "");
			  System.out.println("****"+regular);
			  highligth_nonambiguity.add(regular);

		  }
        }
        
        
        public void extractGeneral(String Req)
        {
           String regex = "<General>.*</General>";
           Pattern pattern = Pattern.compile(regex);
  		   Matcher matcher = pattern.matcher(Req);
  		   String regular = null ;
  		   while (matcher.find())
		   {  			   
			 regular = matcher.group();
			 if(regular != null)
			 {
	  			 //System.out.println("***"+regular);
	  			 String []list = regular.split("[<>]");
			     Table_General_list.addRow(new Object[]{list[3], list[5], list[7], Integer.parseInt(list[9]), Boolean.parseBoolean(list[11])});     		   
			 }
		   }
  		   regex = "<GeneralList>.*</GeneralList>";
           pattern = Pattern.compile(regex);
		   matcher = pattern.matcher(Req);
		  while (matcher.find())
		   {
			  regular = matcher.group();
			  System.out.println("****"+regular);
			  regular = regular.replace("<GeneralList>", "");
			  regular = regular.replace("</GeneralList>", "");
			  System.out.println("****"+regular);
			  highligth_general.add(regular);

		  }
        }
        
        public void extractCompleteness(String Req)
        {
           String regex = "<Completeness>.*</Completeness>";
           Pattern pattern = Pattern.compile(regex);
  		   Matcher matcher = pattern.matcher(Req);
  		   String regular = null ;
  		   while (matcher.find())
		   {  			   
			 regular = matcher.group();
			 if(regular != null)
			 {
	  			 //System.out.println("***"+regular);
	  			 String []list = regular.split("[<>]");
			     Table_Completness_list.addRow(new Object[]{list[3], list[5], list[7], Integer.parseInt(list[9]), Boolean.parseBoolean(list[11])});     		   
			 }
		   }
  		   regex = "<CompletenessList>.*</CompletenessList>";
           pattern = Pattern.compile(regex);
		   matcher = pattern.matcher(Req);
		  while (matcher.find())
		   {
			  regular = matcher.group();
			  System.out.println("****"+regular);
			  regular = regular.replace("<CompletenessList>", "");
			  regular = regular.replace("</CompletenessList>", "");
			  System.out.println("****"+regular);
			  highligth_completeness.add(regular);

		  }
        }
        
        
        
      }
    
    private class SaveFileChooser implements ActionListener {

        public void actionPerformed(ActionEvent e) {
          try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new DocumentFileFilter());
            int n = fileChooser.showSaveDialog(GUI.this);
            if (n == JFileChooser.APPROVE_OPTION) {
              save_file = fileChooser.getSelectedFile();
              System.out.println(save_file.getPath());
              this.save();
              JFrame f = null;
			     JOptionPane.showMessageDialog(f,
			    		   "Save Procedure Successfull: "+save_file.getPath(),
			    		   "Save Operation",
			    		   JOptionPane.INFORMATION_MESSAGE);
              }
          } catch (Exception ex) {}
        }
        
        
        public void save()
        {
			FileWriter fw;
			String text_req ;
			try {
				fw = new FileWriter(save_file);
				for (int i = 0; i < Table_Clarity_list.getRowCount(); i++) 
				{
					text_req = Table_Clarity_list.getValueAt(i, 0).toString();
					text_req = text_req.replace(">", "greater than");
					text_req = text_req.replace("<", "lower than");
					fw.write("<Clarity><"+ text_req+"><"+Table_Clarity_list.getValueAt(i, 1)+"><"+Table_Clarity_list.getValueAt(i, 2)+"><"+Table_Clarity_list.getValueAt(i, 3)+"><"+Table_Clarity_list.getValueAt(i, 4)+"></Clarity>");
					fw.write("\r\n");
   	    	    }
				Iterator <String> itcl = highligth_clarity.iterator();
				while(itcl.hasNext())
				{
					String g = itcl.next();
					fw.write("<ClarityList>"+g+"</ClarityList>");
					fw.write("\r\n");
				}
				for (int i = 0; i < Table_Completness_list.getRowCount(); i++) 
				{
					text_req = Table_Completness_list.getValueAt(i, 0).toString();
					text_req = text_req.replace(">", "greater than");
					text_req = text_req.replace("<", "lower than");
					fw.write( "<Completeness><"+text_req+"><"+Table_Completness_list.getValueAt(i, 1)+"><"+Table_Completness_list.getValueAt(i, 2)+"><"+Table_Completness_list.getValueAt(i, 3)+"><"+Table_Completness_list.getValueAt(i, 4)+"></Completeness>");
					fw.write("\r\n");
   	    	    }
				Iterator <String> itco = highligth_completeness.iterator();
				while(itco.hasNext())
				{
					String g = itco.next();
					fw.write("<CompletenessList>"+g+"</CompletenessList>");
					fw.write("\r\n");
				}
				for (int i = 0; i < Table_Non_Ambiguity_list.getRowCount(); i++) 
				{
					text_req = Table_Non_Ambiguity_list.getValueAt(i, 0).toString();
					text_req = text_req.replace(">", "greater than");
					text_req = text_req.replace("<", "lower than");
					fw.write("<NonAmbiguity><"+ text_req+"><"+Table_Non_Ambiguity_list.getValueAt(i, 1)+"><"+Table_Non_Ambiguity_list.getValueAt(i, 2)+"><"+Table_Non_Ambiguity_list.getValueAt(i, 3)+"><"+Table_Non_Ambiguity_list.getValueAt(i, 4)+"></NonAmbiguity>");
					fw.write("\r\n");
   	    	    }
				Iterator <String> itna = highligth_nonambiguity.iterator();
				while(itna.hasNext())
				{
					String g = itna.next();
					fw.write("<Non_AmbiguityList>"+g+"</Non_AmbiguityList>");
					fw.write("\r\n");
				}
				for (int i = 0; i < Table_General_list.getRowCount(); i++) 
				{
					text_req = Table_General_list.getValueAt(i, 0).toString();
					text_req = text_req.replace(">", "greater than");
					text_req = text_req.replace("<", "lower than");
					fw.write("<General><"+ text_req+"><"+Table_General_list.getValueAt(i, 1)+"><"+Table_General_list.getValueAt(i, 2)+"><"+Table_General_list.getValueAt(i, 3)+"><"+Table_General_list.getValueAt(i, 4)+"></General>");
					fw.write("\r\n");
   	    	    }
				Iterator <String> itge = highligth_general.iterator();
				while(itge.hasNext())
				{
					String g = itge.next();
					fw.write("<GeneralList>"+g+"</GeneralList>");
					fw.write("\r\n");
				}
    			fw.close();
			} catch (IOException e1) {
				JFrame f = null;
				 JOptionPane.showMessageDialog(f,
			    		    e1.toString(),
			    		   "Error",
			    		   JOptionPane.ERROR_MESSAGE);
			}
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
    		if(e.getActionCommand().equals("New"))
    		{
    			textArea.setText(""); ;
    			Anaphoric.setSelected(false); 
    			PassiveVerbs.setSelected(false);
    			Adverbs.setSelected(false);
    			Vagueness.setSelected(false);
    			EscessiveLength.setSelected(false);
    			unknownReference.setSelected(false);
    			Coordination.setSelected(false);
    			unknownAcronyms.setSelected(false);
    			MissingRequirement.setSelected(false);
    			MissingMesure.setSelected(false);
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
            	if (Table_General_list.getRowCount() > 0) {
            	    for (int i = Table_General_list.getRowCount() - 1; i > -1; i--) {
            	    	Table_General_list.removeRow(i);
            	    }
            	}
    			
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
            if(fname.endsWith("docx") || fname.endsWith("xlsx")|| fname.endsWith("doc") || fname.endsWith(".txt"))
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
            	highligth_nonambiguity.clear();
            	highligth_clarity.clear();
            	highligth_completeness.clear();
            	highligth_general.clear();
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
            	if (Table_General_list.getRowCount() > 0) {
            	    for (int i = Table_General_list.getRowCount() - 1; i > -1; i--) {
            	    	Table_General_list.removeRow(i);
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
	           	if((DocPath.length() == 0) || (pressed == false)|| DocPath.contains(".txt"))
	           	{
	           		JFrame f= null;
	           		 JOptionPane.showMessageDialog(f,
	     		    		   "Running Error. Please check: \r\n 1) DocPath is not empty \r\n 2) almost a quality indicator is selected \r\n 3) the Extension shall be .xlsx",
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
	            	CheckToolConf(toolconf);
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
	        			if(ann.getIndicator().equals("Vagueness") || ann.getIndicator().equals("Excessive_length_phrase") || ann.getIndicator().equals("Adverbs_detected") || ann.getIndicator().equals("Passive"))
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
	        		String s = null;
	        		Collections.sort(nonambiguity_list, new MyComparator());
	        		it = nonambiguity_list.iterator();
	        		while(it.hasNext())
	        		{
	        			
	        			Annotations ann = it.next();
	        			s = "";
	        			s = ann.getDefect().trim();
	        			highligth_nonambiguity.add(s);
	        		
	        			Table_Non_Ambiguity_list.addRow(new Object[]{ann.getText(), ann.getIndicatorName(), ann.getExplanation(), ann.getRank(), false});
	        			
	        		}
	        		
	        		if(Table_Non_Ambiguity_list.getRowCount() == 0)
	        		{
	        			Table_Non_Ambiguity_list.addRow(new Object[]{"no requirement detected", "no defect", "-", 0, false});
	        		}
	        		
	        		Collections.sort(completeness_list, new MyComparator());
	        		it = completeness_list.iterator();
	        		while(it.hasNext())
	        		{
	        			s = "";
	        			Annotations ann = it.next();
	        			s = ann.getDefect().trim();
	        			highligth_completeness.add(s);
	        			Table_Completness_list.addRow(new Object[]{ann.getText(), ann.getIndicatorName(),ann.getExplanation(), ann.getRank(), false});
	        			
	        		}
	        		if(Table_Completness_list.getRowCount() == 0)
	        		{
	        			Table_Completness_list.addRow(new Object[]{"no requirement detected", "no defect", "-", 0, false});
	        		}
	        		Collections.sort(clarity_list, new MyComparator());
	        		it = clarity_list.iterator();
	        		while(it.hasNext())
	        		{
	        			s = "";
	        			Annotations ann = it.next();	
	        			s = ann.getDefect().trim();
	        			if(!s.contains("even")&& !s.contains("as"))
	        			{
	        				highligth_clarity.add(s);
	        				Table_Clarity_list.addRow(new Object[]{ann.getText(), ann.getIndicatorName(), ann.getExplanation(), ann.getRank(), false});	        		
	        			}
	        		}
	        		if(Table_Clarity_list.getRowCount() == 0)
	        		{
	        			Table_Clarity_list.addRow(new Object[]{"no requirement detected", "no defect", "-", 0, false});
	        		}
	        		ann_general = da.RunGeneral();
	        		Collections.sort(ann_general, new comparator_gen());
	        		System.out.println(ann_general.size());
	        		Iterator <AnnotationGeneral> it1 = ann_general.iterator();
	        		while(it1.hasNext())
	        		{
	        			AnnotationGeneral ann_gene = it1.next();	        			
	        			Table_General_list.addRow(new Object[]{ann_gene.getText(), ann_gene.getIndicator(), ann_gene.getExplanation(), ann_gene.getRank(), false});
	        		}
	        		
	        		if(Table_General_list.getRowCount() == 0)
	        		{
	        			Table_General_list.addRow(new Object[]{"no requirement detected", "no defect", "-", 0, false});
	        		}
	        		System.out.println(ann_general.size());
	        		highligth_general = da.getDocument().getInd();
	            	setSize(1400, 700);
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
    
    public void CheckToolConf(ToolConfiguration tool)
    {
	     if(tool.getLengththres() == 0)
	   	 {
	   		 tool.setLengththres(60);
	   		 tool.updateThreshold(60);
	   	 }
	   	 if(tool.getAnaphoric() == 0)
	   	 {
	   		 tool.setAnaphoric(5);
	   	 }
	   	 if(tool.getCoordination() == 0)
	   	 {
	   		 tool.setCoordination(5); 
	   	 }
	   	 if(tool.getPassiveverbs() == 0)
	   	 {
	   		 tool.setPassiveverbs(5);
	   	 }
	   	 
	   	 if(tool.getAdverbs() == 0)
	   	 {
	   		 tool.setAdverbs(5);
	   	 }
	   	 if(tool.getVagueness() == 0)
	   	 {
	   		 tool.setVagueness(5); 
	   	 }
	   	 if(tool.getExcessiveLength() == 0)
	   	 {
	   		 tool.setExcessiveLength(5);
	   	 }
	   	 if(tool.getUnknownreference() == 0)
	   	 {
	   		 tool.setUnknownreference(5); 
	   	}
	   	 if(tool.getUnknownacronyms() == 0)
	   	 {
	   		 tool.setUnknownacronyms(5);
	   	 }
	   	 if(tool.getMissingrequirement() == 0)
	   	 {
	   		 tool.setMissingrequirement(5); 
	   	}
	   	 if(tool.getMissingMesure() == 0)
	   	 {
	   		 tool.setMissingMesure(5); 
	   	 }
    }

}

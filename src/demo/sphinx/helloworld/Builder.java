package demo.sphinx.helloworld;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class Builder extends KeyAdapter implements ActionListener,ItemListener,ChangeListener, MouseListener{
	static{
		try{
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
 		}catch(Exception qe){} 
	}
	static JFrame frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10, frame11, createFrame;
	
	static String operators[]={"--Select Operator--","=","<",">","<=",">=","IN","LIKE","WHERE","BETWEEN","NOT BETWEEN"};
	static String check_operators[]={"--Select Operator--","=","<",">","<=",">="};
	static String field_selected, temp_create, operator_selected, check_operator_selected, value_selected, query_generated, driver, url, username, pass,table_name, temp_query, selected_fields, updated_fields, temp_query1, order_fields, sequence, andor, rec, fstr, check, heading1[], entry1[][], heading[], entry[][];
	static String squote2="'";
	static String squote1=" '";
	static String status="", authenticate="", inserted="";
	static String speechStatus="";
	
	@SuppressWarnings("rawtypes")
	static JComboBox field_combo,check_operator_combo[];
	@SuppressWarnings("rawtypes")
	static JComboBox fields=new JComboBox();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	static JComboBox operator_combo=new JComboBox(operators);

	static JButton db_submit, select_fields, update_fields, fields_to_be_updated, order, alter_button, select_table, query, go, rename, reset, insert, add, create, enter, build, drop, join, lock, otherQuery;

	static JRadioButton and, or, none, delete, select, update, alter, alter_add, alter_drop, alter_alter, ascending, descending, createTable, createSeq, createUser, createIndex;
	static ButtonGroup bg=new ButtonGroup();
	static ButtonGroup bg1=new ButtonGroup();
	static ButtonGroup bg2=new ButtonGroup();
	static ButtonGroup bg3=new ButtonGroup();
	static ButtonGroup bg4=new ButtonGroup();
	static ButtonGroup bg5=new ButtonGroup();

	static JCheckBox selectedFields_checkbox[], as_cb[], checkbox_order[], update_check[], distinct, orderby, notnull_cb[];

	static JPanel panel1, panel2, panel3, panel4, panel5, panel6, panel7, panel8, panel9, panel13, panel14, panel15, panel16, panel17, panel18, panel19, panel20, panel21, panel22, panel23, panel24, panel25, panel26, intermediatePan1;

	static JLabel table_label,labels[];      

	static JTextField db_port,db_DBname,db_username,table_tf,value,text_fields[],update_tf[],field_no,f_names[],datatypes[],as_tf[];

	static JTextArea query_area;
	static JPasswordField db_pass;
	
	static JScrollPane sp;

	static JTable jt1,jt;
	
	JMenuBar menuBar;
	JMenu menu;
	static JMenu speechMenu;
	static JMenuItem passMenuItem, exitMenuItem, aboutMenuItem, lockTableMenuItem, tableDetailsMenuItem, contactUsMenyItem;
	
	static int columncount, rowcount, rowcount1, go_colcount, flag_and, flag_or, n, db_flag, where_flag, tf_count, top_where;
	
	static Connection c;
	static Statement s;
	static ResultSet rs;
	static ResultSetMetaData rsmd;
	static DatabaseMetaData dbmd;

	static ImageIcon image_icon;
	static Image image;
	
	public static CreateTable createTableObj;
	public static CreateSequence createSequenceObj;
	public static CreateUser createUserObj;
	public static CreateIndex createIndexObj;
	
	Builder(){}
	@SuppressWarnings("rawtypes")
	Builder(String title){
		frame1=new JFrame(title);
//		try{
//			System.setProperty( "Quaqua.tabLayoutPolicy","wrap");
//			UIManager.setLookAndFeel(ch.randelshofer.quaqua.QuaquaManager.getLookAndFeel());
//			SwingUtilities.updateComponentTreeUI(frame1);
//		}catch(Exception qe){} 
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		table_tf=new JTextField(10);
		value=new JTextField(7);
		table_label=new JLabel("Enter table/sequence name ");

		Icon icon1=new ImageIcon(".\\Icon\\select.png");
		select_table=new JButton("Choose",icon1);

		Icon icon2=new ImageIcon(".\\Icon\\query.png");
		query=new JButton("Query",icon2);
		
		Icon icon3=new ImageIcon(".\\Icon\\reset.png");
		reset=new JButton("Reset",icon3);
		
		Icon icon4=new ImageIcon(".\\Icon\\create16.png");
		create=new JButton("Create",icon4);

		Icon icon5=new ImageIcon(".\\Icon\\drop.png");
		drop=new JButton("Drop",icon5);

		Icon icon6=new ImageIcon(".\\Icon\\insert.png");
		insert=new JButton("Insert",icon6);

		Icon icon7=new ImageIcon(".\\Icon\\go.png");
		go=new JButton("Go",icon7);
		
		Icon icon8=new ImageIcon(".\\Icon\\rename16.png");
		rename=new JButton("Rename",icon8);
		
		Icon icon9=new ImageIcon(".\\Icon\\join16.png");
		join=new JButton("Join",icon9);
		
		Icon icon10=new ImageIcon(".\\Icon\\lock16.png");
		lock=new JButton("Lock/Unlock",icon10);
		
		Icon icon11=new ImageIcon(".\\Icon\\otherQuery.png");
		otherQuery=new JButton("Native SQL", icon11);

		delete=new JRadioButton("Delete query",false);

		select=new JRadioButton("Select query",false);

		update=new JRadioButton("Update",false);

		alter=new JRadioButton("Alter",false);

		and=new JRadioButton("AND");
		or=new JRadioButton("OR");
		none=new JRadioButton("None",true);

		orderby=new JCheckBox("Order By");

		bg.add(and);
		bg.add(or);
		bg.add(none);
		bg1.add(select);
		bg1.add(delete);
		bg1.add(update);
		bg1.add(alter);
	
		field_combo=new JComboBox();
		operator_combo.setEnabled(false);

		insert.addActionListener(new Insert());
		create.addActionListener(this);
		delete.addActionListener(new Delete());
		select.addActionListener(new Select());	
		update.addActionListener(new Update());	
		alter.addActionListener(Alter.getAlterObj());
		select_table.addActionListener(new SelectTable());
		reset.addActionListener(new Reset());
		rename.addActionListener(new Rename());
		query.addActionListener(new Query());
		go.addActionListener(new Go());
		drop.addActionListener(new Drop());
		and.addActionListener(this);
		or.addActionListener(this);
		orderby.addActionListener(new Orderby());
		join.addActionListener(new SelectJoinTables());
		lock.addActionListener(new LockUnlockUser());
		otherQuery.addActionListener(new OtherQuery());
		
		table_tf.addKeyListener(this);
	
		rename.addChangeListener(this);
		create.addChangeListener(this);
		reset.addChangeListener(this);
		query.addChangeListener(this);
		go.addChangeListener(this);
		insert.addChangeListener(this);
		delete.addChangeListener(this);
		select.addChangeListener(this);
		update.addChangeListener(this);
		alter.addChangeListener(this);
		drop.addChangeListener(this);
		select_table.addChangeListener(this);
		orderby.addChangeListener(this);
		join.addChangeListener(this);
		lock.addChangeListener(this);
		otherQuery.addChangeListener(this);

		field_combo.addItemListener(this);
		fields.addItemListener(Alter.getAlterObj());
		operator_combo.addItemListener(this);
		
		query.setEnabled(false);
		insert.setEnabled(false);
		create.setEnabled(false);
		delete.setEnabled(false);
		select.setEnabled(false);
		update.setEnabled(false);
		alter.setEnabled(false);
		drop.setEnabled(false);
		reset.setEnabled(false);
		go.setEnabled(false);
		select_table.setEnabled(false);
		//field_combo.setEnabled(false);
		//value.setEnabled(false);
		and.setEnabled(false);
		or.setEnabled(false);
		orderby.setEnabled(false);
		
		//String menuName="<html><font color=BLUE><b>Setting</b></font></html>";
		//Icon icon100=new ImageIcon(".\\Icon\\settings.png");
		menuBar=new JMenuBar();
		
		menu=new JMenu();
		image_icon=new ImageIcon(".\\Icon\\settings241.png");
		image=image_icon.getImage();
		menu.setIcon(new ImageIcon(image));
		menu.addMouseListener(this);
		menuBar.add(menu);
		
		Icon icon100=new ImageIcon(".\\Icon\\changePass24.png");
		passMenuItem=new JMenuItem("Change Password",icon100);
		passMenuItem.addActionListener(new ChangePass());
		
		Icon icon101=new ImageIcon(".\\Icon\\lockTable24.png");
		lockTableMenuItem=new JMenuItem("Lock Table", icon101);
		lockTableMenuItem.addActionListener(new LockTable());
		
		Icon icon121=new ImageIcon(".\\Icon\\tableDetails24.png");
		tableDetailsMenuItem=new JMenuItem("Table details", icon121);
		tableDetailsMenuItem.addActionListener(new TableDetails());
		
		
		Icon icon1211=new ImageIcon(".\\Icon\\email.png");
		contactUsMenyItem=new JMenuItem("Contact Us", icon1211);
		contactUsMenyItem.addActionListener(new ContactUs());
			
		Icon icon111=new ImageIcon(".\\Icon\\about24.png");
		aboutMenuItem=new JMenuItem("About",icon111);
		aboutMenuItem.addActionListener(new About());
		
		Icon icon12=new ImageIcon(".\\Icon\\exit24.png");
		exitMenuItem=new JMenuItem("<html><font color=RED>Exit</font></html>",icon12);
		exitMenuItem.addActionListener(this);
		
		menu.add(passMenuItem);
		menu.add(lockTableMenuItem);
		menu.add(tableDetailsMenuItem);
		menu.add(contactUsMenyItem);
		menu.add(aboutMenuItem);
		
		menu.addSeparator();
		
		menu.add(exitMenuItem);
		
		speechMenu=new JMenu();
		image_icon=new ImageIcon(".\\Icon\\speechOff.png");
		image=image_icon.getImage();
		speechMenu.setIcon(new ImageIcon(image));
		speechMenu.addMouseListener(this);
		menuBar.add(speechMenu);
		speechStatus="enabled";
		
		panel1=new JPanel();
		panel2=new JPanel();
		panel3=new JPanel();
		panel4=new JPanel();
		panel5=new JPanel();
		panel6=new JPanel();
		panel13=new JPanel();
		
		panel1.add(table_label);
		panel1.add(table_tf);
		panel1.add(select_table);
		panel1.add(orderby);
		
		intermediatePan1=new JPanel();
		intermediatePan1.setLayout(new BorderLayout());
		intermediatePan1.add(menuBar, BorderLayout.NORTH);
		intermediatePan1.add(panel1, BorderLayout.SOUTH);
		intermediatePan1.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.CENTER);

		JLabel fl=new JLabel("Fields");
		panel2.add(fl);
		panel2.add(field_combo);
		panel2.add(select);
		panel2.add(delete);
		panel2.add(update);
		panel2.add(alter);

		JLabel ol=new JLabel("Operators");
		panel3.add(ol);
		panel3.add(operator_combo);
		panel3.add(and);
		panel3.add(or);
		panel3.add(none);
		panel3.add(otherQuery);

		JLabel vl=new JLabel("Value");
		panel4.add(vl);
		panel4.add(value);
		panel4.add(query);
		panel4.add(go);
		panel4.add(reset);
		panel4.add(rename);
		
		panel5.setLayout(new BorderLayout());
		panel5.add(panel2,BorderLayout.NORTH);
		panel5.add(panel3,BorderLayout.CENTER);
		panel5.add(panel4,BorderLayout.SOUTH);
		
		panel6.add(create);
		panel6.add(insert);
		panel6.add(drop);
		panel6.add(join);
		panel6.add(lock);
		
		query_area=new JTextArea(6,14);
		query_area.setAutoscrolls(true);
		query_area.setForeground(Color.red);
		query_area.setFont(new Font("Cambria", 17, 17));
		sp=new JScrollPane(query_area);
		query_area.setEditable(false);

		panel13.setLayout(new BorderLayout());
		panel13.add(panel6,BorderLayout.NORTH);
		panel13.add(sp,BorderLayout.SOUTH);
		
		frame1.add(intermediatePan1,BorderLayout.NORTH);
		frame1.add(panel5,BorderLayout.CENTER);
		frame1.add(panel13,BorderLayout.SOUTH);
		
		select_table.setEnabled(true);
		create.setEnabled(true);
		drop.setEnabled(true);
		reset.setEnabled(true);

		image_icon=new ImageIcon(".\\Icon\\query.png");
		image=image_icon.getImage();
		frame1.setIconImage(image);
		frame1.pack();
		frame1.setVisible(true);
	}
	public void create(){
		createFrame=new JFrame("Create");
		ButtonGroup bgrp=new ButtonGroup();
		createTable=new JRadioButton("Create table");
		createSeq=new JRadioButton("Create sequence");
		createUser=new JRadioButton("Create user");
		createIndex=new JRadioButton("Create index");
		bgrp.add(createTable);
		bgrp.add(createSeq);
		bgrp.add(createUser);
		bgrp.add(createIndex);
		
		createTableObj=new CreateTable();
		createSequenceObj=new CreateSequence();
		createUserObj=new CreateUser();
		createIndexObj=new CreateIndex();
		createTable.addActionListener(createTableObj);
		createSeq.addActionListener(createSequenceObj);
		createUser.addActionListener(createUserObj);
		createIndex.addActionListener(createIndexObj);
		
		JPanel pan=new JPanel();
		pan.setLayout(new GridLayout(4,1));
		pan.add(createTable);
		pan.add(createSeq);
		pan.add(createUser);
		pan.add(createIndex);
		
		createFrame.add(pan);
		
		image_icon=new ImageIcon(".\\Icon\\create.png");
		image=image_icon.getImage();
		createFrame.setIconImage(image);
		createFrame.pack();
		createFrame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==create){
			create();
		}
		if(e.getSource()==and){
			value.setText("");
			query.setEnabled(true);
			go.setEnabled(false);
			flag_and++;
			where_flag++;
		}
		if(e.getSource()==or){
			value.setText("");
			query.setEnabled(true);
			go.setEnabled(false);
			flag_or++;
			where_flag++;
		}	
		
		if(e.getSource()==exitMenuItem){
			new Exit().exit();
		}
	}
	
	public void keyReleased(KeyEvent e){	
		table_name=table_tf.getText().toUpperCase();
	}

	//For Combo
	
	public void itemStateChanged(ItemEvent e){
		if(e.getSource()==operator_combo){
			operator_selected=(String)operator_combo.getSelectedItem();
			if( operator_selected.equals("WHERE") )
				top_where=1;
			if( operator_selected.equals("WHERE") && where_flag==0 )
			{
				where_flag=1;
				query_generated=query_generated+" where ";
				field_combo.setEnabled(true);
			}
			if( operator_selected.equals("LIKE") )
			{
				try 
				{ 
					Runtime.getRuntime().exec("cmd /c wildcard.jpg"); 
				}catch(Exception e989){}	
			}
			
		}
		if(e.getSource()==field_combo)
		{
			field_selected=(String)field_combo.getSelectedItem();
		}
	}
	
	// For ToolTipText
	
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource()==rename)
			rename.setToolTipText("Rename table/sequence name");
		if(e.getSource()==create)
			create.setToolTipText("Create a new table/sequence/user");
		if(e.getSource()==insert)
			insert.setToolTipText("Insert a record");
		if(e.getSource()==delete)
			delete.setToolTipText("Delete record(s)");
		if(e.getSource()==update)
			update.setToolTipText("Update record(s)");
		if(e.getSource()==go)
			go.setToolTipText("Display result");
		if(e.getSource()==query)
			query.setToolTipText("Current query");
		if(e.getSource()==reset)
			reset.setToolTipText("Clear all fields");
		if(e.getSource()==select)
			select.setToolTipText("Select record(s)");
		if(e.getSource()==drop)
			drop.setToolTipText("Delete table/sequence/user from database");
		if(e.getSource()==orderby)
			orderby.setToolTipText("Arrange records in Ascending/Descending order");
		if(e.getSource()==alter)
			alter.setToolTipText("ADD,DELETE,MODIFY the table column");
		if(e.getSource()==select_table)
			select_table.setToolTipText("Table to be queried");
		if(e.getSource()==select_fields)
			select_fields.setToolTipText("Check field(s) to be selected");
		if(e.getSource()==fields_to_be_updated)
			fields_to_be_updated.setToolTipText("Check field(s) to be updated");
		if(e.getSource()==join)
			join.setToolTipText("Join tables");
		if(e.getSource()==lock)
			lock.setToolTipText("lock/unlock user");
		if(e.getSource()==otherQuery)
			otherQuery.setToolTipText("Execute other native SQL query");
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==menu)
			menu.doClick();
		if(e.getSource()==speechMenu)
			speechMenu.setToolTipText("ON/OFF microphone");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==speechMenu){
			new HelloWorld().speechControl();
		}
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	
	}
}
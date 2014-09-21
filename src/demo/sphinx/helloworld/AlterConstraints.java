package demo.sphinx.helloworld;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

public class AlterConstraints extends Builder{

	JRadioButton alterConstraints;
	JFrame frame;
	JRadioButton addConstraint, dropConstraint, enableConstraint, disableConstraint;
	JPanel pan1;
	String constraintType;
	JComboBox<String> consTypeCombo =new JComboBox<String>(new String[] {"--select type--", "PRIMARY KEY","FOREIGN KEY","UNIQUE","CHECK"});
	String fields[], query, CommonQuery="";
	JCheckBox fieldsCb[], refFieldsCb[];
	JButton add;
	JComboBox<String> operatorCombo[];//=new JComboBox<String>(new String[]{"--select operator--",">","<",">=","<=","=","IN"});
	JTextField valueTf[];
	String []checkOperatorSelected;
	AlterConstraints alterConstraintsObj;
	public AlterConstraints(){}
	
	public AlterConstraints(JRadioButton alterConstraints){
		this.alterConstraints=alterConstraints;
	}
	public void alterConstraints(){
		Alter.getAlterObj().frame.setVisible(false);
		alterConstraintsObj=this;
		frame=new JFrame("Alter Constraints");
		ButtonGroup bg=new ButtonGroup();
		addConstraint=new JRadioButton("ADD Constraint");
		dropConstraint=new JRadioButton("DROP Constraint");
		enableConstraint=new JRadioButton("ENABLE Constraint");
		disableConstraint=new JRadioButton("DISABLE Constraint");
		bg.add(addConstraint);
		bg.add(dropConstraint);
		bg.add(enableConstraint);
		bg.add(disableConstraint);
		
		addConstraint.addActionListener(this);
		dropConstraint.addActionListener(this);
		enableConstraint.addActionListener(this);
		disableConstraint.addActionListener(this);
		
		pan1=new JPanel();
		pan1.setLayout(new GridLayout(4,1));
		pan1.add(addConstraint);
		pan1.add(dropConstraint);
		pan1.add(enableConstraint);
		pan1.add(disableConstraint);
		
		image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
		image=image_icon.getImage();
		frame.setIconImage(image);
		
		frame.add(pan1);
		frame.pack();
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==alterConstraints){
			alterConstraints();
		}
		
		// alter table consdemo add constraint other_id_prim primary key(id, phone);
		// alter table consdemo drop constraint other_id_prim;
		
		// alter table consdemo add constraint consdemo_id_uni unique(id);
		//  alter table consdemo add constraint consdemo_id_uni unique(id,phone);
		
		// alter table consdemo add constraint consdemo_id_check check(id>100);
		// alter table consdemo add constraint consdemo_id_check check(id>100 and phone>999);
		// alter table consdemo add constraint consdemo_id_check check(id IN(101,102,103));
		
		// alter table consdemo add constraint consdemo_fk foreign key(mgr_id) references other(oid);
		// alter table fkMultiTab1 add constraint fk_cons_fkMultiTab1 foreign key(eid,esno) references fkMultiTab2(aid,asno);
		// Here in table fkMultiTab1 eid and esno should be primary keys and similarly in fkMultiTab2 aid and asno should be primary keys.
		
		if(e.getSource()==addConstraint){
			query="ALTER TABLE "+table_name+" add constraint ";
			frame.setVisible(false);
			JFrame addFrame=new JFrame("Add constraint");
			JTextField consName=new JTextField(8);
			Icon icon=new ImageIcon(".\\Icon\\ok161.png");
			JButton select=new JButton("OK", icon);
			select.addActionListener(
					new ActionListener(){
						@SuppressWarnings("unchecked")
						@Override
						public void actionPerformed(ActionEvent e){
							if(e.getSource()==select){
								addFrame.setVisible(false);
								query=query+consName.getText()+" "+constraintType+"(";	// alter table consdemo add constraint consdemo_id_check check(
								
								fields=fetchFields(table_name);
								fieldsCb=new JCheckBox[fields.length];
								for(int i=0;i<fields.length;i++){
									fieldsCb[i]=new JCheckBox(fields[i]);
								}
								
								if(constraintType.equals("CHECK")){
									JFrame checkFrame=new JFrame("Add "+constraintType+"Constraint");
									JPanel pan=new JPanel();
									pan.add(new JLabel("Select Column(s)"));
									
									
									operatorCombo=new JComboBox[fields.length];
									valueTf=new JTextField[fields.length];
									JPanel pan1=new JPanel();
									pan1.setLayout(new GridLayout(fields.length, 3));
									for(int i=0;i<fields.length;i++){
										pan1.add(fieldsCb[i]);
										operatorCombo[i]=new JComboBox<String>(new String[]{"--select operator--",">","<",">=","<=","=","IN"});
										pan1.add(operatorCombo[i]);
										valueTf[i]=new JTextField(5);
										pan1.add(valueTf[i]);
										operatorCombo[i].addItemListener(alterConstraintsObj);
									}
									
									JPanel pan2=new JPanel();
									Icon icon=new ImageIcon(".\\Icon\\additem.png");
									add=new JButton("Add", icon);
									add.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent e1){
											if(e1.getSource()==add){
												System.out.println("Add");
												checkFrame.setVisible(false);
												for(int j=0;j<fields.length;j++){
													if(fieldsCb[j].isSelected()){
														if(checkOperatorSelected[j].equals("IN")){
															String str=valueTf[j].getText();
															String tokens="";
															StringTokenizer stzr=new StringTokenizer(str, ",; ");
															while(stzr.hasMoreTokens()){
																tokens=tokens+"'"+stzr.nextToken()+"',";
															}
															tokens=tokens.substring(0, tokens.length()-1);
															query=query+fields[j]+" IN("+tokens+")"+" AND ";
														}
														else
															query=query+fields[j]+" "+checkOperatorSelected[j]+"'"+valueTf[j].getText()+"'"+" AND ";
													}
												}
												query=query.substring(0,query.length()-5)+")";
												query_area.setText(query);
												try{
													s.executeUpdate(query);
													query_area.append("\nDone!!");
												}catch(Exception ex){
													ex.printStackTrace();
													Error.error(addFrame, ex);
												}
											}
										}
									});
									pan2.add(add);
									
									checkFrame.setLayout(new BorderLayout());
									checkFrame.add(pan, BorderLayout.NORTH);
									checkFrame.add(pan1, BorderLayout.CENTER);
									checkFrame.add(pan2, BorderLayout.SOUTH);
									
									image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
									image=image_icon.getImage();
									checkFrame.setIconImage(image);
									checkFrame.pack();
									checkFrame.setVisible(true);
								}
								// alter table consdemo add constraint fk_cons foreign key(mgr_id) references other(oid);
								else if(constraintType.equals("FOREIGN KEY")){
									// alter table consdemo add constraint fk_cons foreign key(
									JFrame fkFrame=new JFrame("Add Constraint");
									JPanel pan1=new JPanel();
									
									JTextField refTable=new JTextField(5);
									Icon icon=new ImageIcon(".\\Icon\\ok161.png");
									JButton selectRefTab=new JButton("OK", icon);
									selectRefTab.addActionListener(
											new ActionListener() {
												@Override
												public void actionPerformed(ActionEvent ev) {
													if(ev.getSource()==selectRefTab){
														fkFrame.setVisible(false);
														String referencedTab=refTable.getText().toUpperCase();
														String []refFields=fetchFields(referencedTab);
														refFieldsCb=new JCheckBox[refFields.length];
														
														JFrame selectKeysFrame=new JFrame("Select column(s)");
														JPanel pan1=new JPanel();
														pan1.setLayout(new GridLayout(fields.length+refFields.length+3, 1));
														
														pan1.add(new JLabel("<html><b>Current table : <font color=red>"+table_name+"</font></b></html>"));
														for(int i=0;i<fields.length;i++){
															pan1.add(fieldsCb[i]);
														}
														pan1.add(new JSeparator());
														pan1.add(new JLabel("<html><b>Referenced table : <font color=green>"+referencedTab+"</font></b></html>"));
														for(int i=0;i<refFields.length;i++){
															refFieldsCb[i]=new JCheckBox(refFields[i]);
															pan1.add(refFieldsCb[i]);
														}
														
														JPanel pan2=new JPanel();
														Icon icon=new ImageIcon(".\\Icon\\additem.png");
														add=new JButton("Add", icon);
														add.addActionListener(
															new ActionListener() {
															@Override
															public void actionPerformed(ActionEvent ev1) {
																if(ev1.getSource()==add){
																	selectKeysFrame.setVisible(false);
																	String currentKeys="", refKeys="";
																	for(int k=0; k<fields.length; k++){
																		if(fieldsCb[k].isSelected()){
																			currentKeys=currentKeys+fields[k]+",";
																		}
																	}
																	currentKeys=currentKeys.substring(0, currentKeys.length()-1);
																	
																	for(int k=0; k<refFields.length; k++){
																		if(refFieldsCb[k].isSelected()){
																			refKeys=refKeys+refFields[k]+",";
																		}
																	}
																	refKeys=refKeys.substring(0, refKeys.length()-1);
																	query=query+currentKeys+") references "+referencedTab+" ("+refKeys+")";
																	query_area.setText(query);
																	try{
																		s.executeUpdate(query);
																		query_area.append("\nDone!!");
																	}catch(Exception ex){
																		ex.printStackTrace();
																		Error.error(addFrame, ex);
																	}
																}
															}
														});
														pan2.add(add);
														
														selectKeysFrame.setLayout(new BorderLayout());
														selectKeysFrame.add(pan1, BorderLayout.NORTH);
														selectKeysFrame.add(pan2, BorderLayout.CENTER);
														
														image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
														image=image_icon.getImage();
														selectKeysFrame.setIconImage(image);
														selectKeysFrame.pack();
														selectKeysFrame.setVisible(true);
													}
												}
											}
									);
									pan1.add(new JLabel("Referenced Table"));
									pan1.add(refTable);
								
									pan1.add(selectRefTab);
									
									image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
									image=image_icon.getImage();
									fkFrame.setIconImage(image);
									
									fkFrame.add(pan1);
									fkFrame.pack();
									fkFrame.setVisible(true);
													
								}
								else{
									JFrame notCheckFrame=new JFrame("Add "+constraintType+"Constraint");
									JPanel pan=new JPanel();
									pan.setLayout(new GridLayout(fields.length+1, 1));
									pan.add(new JLabel("Select Column(s)"));
									for(int i=0;i<fields.length;i++){
										pan.add(fieldsCb[i]);
									}
									JPanel pan2=new JPanel();
									Icon icon=new ImageIcon(".\\Icon\\additem.png");
									add=new JButton("Add", icon);
									add.addActionListener(new ActionListener(){
										public void actionPerformed(ActionEvent e1){
											if(e1.getSource()==add){
												notCheckFrame.setVisible(false);
												for(int j=0;j<fields.length;j++){
													if(fieldsCb[j].isSelected()){
														query=query+fields[j]+",";
													}
												}
												query=query.substring(0,query.length()-1)+")";
												query_area.setText(query);
												try{
													s.executeUpdate(query);
													query_area.append("\nDone!!");
												}catch(Exception ex){
													ex.printStackTrace();
													Error.error(addFrame, ex);
												}
											}
										}
									});
									pan2.add(add);
									
									notCheckFrame.setLayout(new BorderLayout());
									notCheckFrame.add(pan, BorderLayout.NORTH);
									notCheckFrame.add(pan2, BorderLayout.CENTER);
									image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
									image=image_icon.getImage();
									notCheckFrame.setIconImage(image);
									notCheckFrame.pack();
									notCheckFrame.setVisible(true);
								}
							}
						}
					}
			);
			consTypeCombo.addItemListener(
					new ItemListener() {
						@Override
						public void itemStateChanged(ItemEvent e){
							if(e.getSource()==consTypeCombo){
								constraintType=(String)consTypeCombo.getSelectedItem();
							}
						}
					}		
			);
			JPanel pan1=new JPanel();
			//pan1.setLayout(new GridLayout(2,2));
			pan1.add(new JLabel("Constraint Name"));
			pan1.add(consName);
			
			pan1.add(new JLabel("Constraint Type"));
			pan1.add(consTypeCombo);
			
			pan1.add(select);
			
			//frame.setLayout(new BorderLayout());
			
			image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
			image=image_icon.getImage();
			addFrame.setIconImage(image);
			addFrame.add(pan1);
			addFrame.pack();
			addFrame.setVisible(true);
		}
		
		if(e.getSource()==dropConstraint){
			frame.setVisible(false);
			query="ALTER TABLE "+table_name+" DROP Constraint ";
			gui(query);
		}
		if(e.getSource()==enableConstraint){
			frame.setVisible(false);
			query="ALTER TABLE "+table_name+" ENABLE Constraint ";
			gui(query);
			
		}
		if(e.getSource()==disableConstraint){
			frame.setVisible(false);
			query="ALTER TABLE "+table_name+" DISABLE Constraint ";
			gui(query);
		}
	}
	
	public void itemStateChanged(ItemEvent e2) {
		for(int i=0;i<fields.length;i++){
			if(e2.getSource()==operatorCombo[i]){
				if(fieldsCb[i].isSelected()){
						checkOperatorSelected[i]=(String) operatorCombo[i].getSelectedItem();
				}
			}
		}
	}
	
	public String gui(String query){
		
		JFrame frame=new JFrame("Contraints");
		
		JTextField consNameTf=new JTextField(8);
		Icon icon=new ImageIcon(".\\Icon\\ok16.png");
		JButton butt=new JButton("OK", icon);
		butt.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ev) {
						if(ev.getSource()==butt){
							frame.setVisible(false);
							CommonQuery=CommonQuery+query+consNameTf.getText();
							query_area.setText(CommonQuery);
							try{
								s.executeUpdate(CommonQuery);
								query_area.append("\nDone!!");
							}catch(Exception ex){
								ex.printStackTrace();
								Error.error(frame, ex);
							}
						}
					}
				}
		);
		JPanel pan=new JPanel();
		pan.add(new JLabel("Constraint Name"));
		pan.add(consNameTf);
		pan.add(butt);
		
		frame.add(pan);
		image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
		image=image_icon.getImage();
		frame.setIconImage(image);
		
		frame.pack();
		frame.setVisible(true);
		return consNameTf.getText();
	}

	String[] fetchFields(String tableName){
		try {
			int columnCount=0;
			ResultSetMetaData metaData;
			String[] fieldsName;
			ResultSet result;
			result=s.executeQuery("select * from "+tableName);
		
			metaData=result.getMetaData();
			columnCount=metaData.getColumnCount();
			fieldsName=new String[columnCount];
			
			for(int t=1;t<=columnCount;t++){
				fieldsName[t-1]=metaData.getColumnName(t);
			}
			checkOperatorSelected=new String[fieldsName.length];
			return fieldsName;
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
			Error.error(frame, e1);
			return null;
		}
	}
}
package demo.sphinx.helloworld;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CreateTable extends Builder //implements ItemListener
{
	JCheckBox unique_cb[],check_cb[],primary_cb[], foreign_cb=new JCheckBox("Foreign Key");
	@SuppressWarnings("rawtypes")
	JComboBox check_operator[];
	JTextField check_tf[],ref_tableTf=new JTextField(7),foreign_keyTf=new JTextField(7);
	JPanel panel11[];
	JPanel panel12=new JPanel();
	JPanel panel14=new JPanel();
	JLabel refTab_label=new JLabel("	Referenced table : "),forKey_label=new JLabel("Key (Field) : ");
	boolean uniqueConsFlag=false,checkConsFlag=false,primaryConsFlag=false,foreignConsFlag=false;
	String checkOperatorSelected[];
	
	public void createTable(){
		int count=new LockTable().isExist(table_name);
		System.out.println(count+table_name);
		if(count==1){
			JOptionPane opane=new JOptionPane();
			JOptionPane.showMessageDialog(createFrame,"table "+table_name.toUpperCase()+" already exist","ERROR !!", JOptionPane.ERROR_MESSAGE, new ImageIcon(".\\Icon\\error64.png"));
			opane.setVisible(true);
		}
		else{
			createFrame.setVisible(false);
			frame5=new JFrame("Create Table");
			JLabel fl=new JLabel("Enter the number of fields ");
			Icon icon14=new ImageIcon(".\\Icon\\enter.png");
			enter=new JButton("Enter",icon14);
			enter.addActionListener(this);
			field_no=new JTextField(5);
			panel9=new JPanel();
			panel9.add(fl);
			panel9.add(field_no);
			panel9.add(enter);
			frame5.add(panel9);
			image_icon=new ImageIcon(".\\Icon\\create.png");
			image=image_icon.getImage();
			frame5.setIconImage(image);
			frame5.setVisible(true);
			frame5.pack();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==createTable){
			createTable();
		}
		if(e.getActionCommand().equals("Enter")){
			String no=field_no.getText();
			n=Integer.parseInt(no);
			
			panel11=new JPanel[n];

			notnull_cb=new JCheckBox[n];
			
			unique_cb=new JCheckBox[n];
			
			primary_cb=new JCheckBox[n];
			
			check_cb=new JCheckBox[n];
			check_operator=new JComboBox[n];
			check_tf=new JTextField[n];
			checkOperatorSelected=new String[n];
			
			datatypes=new JTextField[n];
			f_names=new JTextField[n];
			frame6=new JFrame("Create Table");
			frame6.setLayout(new BorderLayout());
			
			JPanel panel13=new JPanel();
			panel13.setLayout(new GridLayout(n,1));
			for(int i=1;i<=n;i++){
				panel11[i-1]=new JPanel();
				panel11[i-1].add(new JLabel("Field : "+i));
				f_names[i-1]=new JTextField(8);
				panel11[i-1].setLayout(new FlowLayout());
				panel11[i-1].add(f_names[i-1]);

				panel11[i-1].add(new JLabel("Datatype :"));
				datatypes[i-1]=new JTextField(8);
				panel11[i-1].add(datatypes[i-1]);

				notnull_cb[i-1]=new JCheckBox("NOT NULL");
				panel11[i-1].add(notnull_cb[i-1]);
				
				unique_cb[i-1]=new JCheckBox("UNIQUE");
				panel11[i-1].add(unique_cb[i-1]);
				
				primary_cb[i-1]=new JCheckBox("PRIMARY KEY");
				panel11[i-1].add(primary_cb[i-1]);
				
				check_cb[i-1]=new JCheckBox("CHECK");
				check_cb[i-1].addActionListener(this);
				
				check_operator[i-1]=new JComboBox(check_operators);
				check_operator[i-1].addItemListener(this);
				
				check_tf[i-1]=new JTextField(5);
				panel11[i-1].add(check_cb[i-1]);
				panel11[i-1].add(check_operator[i-1]);
				panel11[i-1].add(check_tf[i-1]);
				check_tf[i-1].setEnabled(false);
				
				panel13.add(panel11[i-1]);
			}
			
			foreign_cb.addActionListener(this);
			panel14.add(foreign_cb);
		
			Icon icon13=new ImageIcon(".\\Icon\\build.png");
			build=new JButton("Build",icon13);	
			build.addActionListener(this);
			panel12=new JPanel();
			panel12.add(build);
			
			frame6.add(panel13,BorderLayout.NORTH);
			frame6.add(panel14,BorderLayout.CENTER);	// foreign key
			frame6.add(panel12,BorderLayout.SOUTH);		// submit button
			
			image_icon=new ImageIcon(".\\Icon\\provide.png");
			image=image_icon.getImage();
			frame6.setIconImage(image);
			frame6.setVisible(true);
			frame5.setVisible(false);
			frame6.pack();
		}
		if(e.getActionCommand().equals("Build"))
		{
			String unique_cons="";
			String check_cons="";
			String primary_cons="";
			
			String uniqueConsName="uni_"+table_name;
			String checkConsName="chk_"+table_name;
			String primaryConsName="prim_"+table_name;
			String foreignConsName="foreign_"+table_name;
			
			temp_create="";
			rec="";
			for(int i=0;i<n;i++){
				temp_create=temp_create+f_names[i].getText()+" "+datatypes[i].getText();	
				if(notnull_cb[i].isSelected()){
					temp_create=temp_create+" NOT NULL";
				}
				temp_create=temp_create+",\n";
				if(unique_cb[i].isSelected()){
					unique_cons=unique_cons+f_names[i].getText()+",";
					uniqueConsFlag=true;
				}
				if(check_cb[i].isSelected()){
					check_cons=check_cons+f_names[i].getText()+checkOperatorSelected[i]+"'"+check_tf[i].getText()+"'"+" AND ";
					checkConsFlag=true;
				}
				if(primary_cb[i].isSelected()){
					primary_cons=primary_cons+f_names[i].getText()+",";
					primaryConsFlag=true;
				}
			}
			if(foreign_cb.isSelected()){
				foreignConsFlag=true;
			}
			
			if(uniqueConsFlag){		// CONSTRAINT uni6775 UNIQUE (Address)
				unique_cons=unique_cons.substring(0,unique_cons.length()-1);
				unique_cons="("+unique_cons+")";
				rec=rec+"\nCONSTRAINT "+uniqueConsName+" UNIQUE "+unique_cons+",";
			}
			
			if(primaryConsFlag){		// 	CONSTRAINT pk PRIMARY KEY (P_Id,LastName)
				primary_cons=primary_cons.substring(0,primary_cons.length()-1);
				primary_cons="("+primary_cons+")";
				rec=rec+"\nCONSTRAINT "+primaryConsName+" PRIMARY KEY "+primary_cons+",";
			}
			
			if(checkConsFlag){		// CONSTRAINT chk42234 CHECK (P_Id>0 AND City='Sandnes')
				check_cons=check_cons.substring(0,check_cons.length()-5);
				check_cons="("+check_cons+")";
				rec=rec+"\nCONSTRAINT "+checkConsName+" CHECK "+check_cons+",";
			}
			
			if(foreignConsFlag){		// CONSTRAINT fk24534 FOREIGN KEY (P_Id) REFERENCES Persons(P_Id)
				rec=rec+"\nCONSTRAINT "+foreignConsName+" FOREIGN KEY ("+foreign_keyTf.getText()+")"+" REFERENCES "+ref_tableTf.getText()+"("+foreign_keyTf.getText()+"),";
			}
			
			if(!rec.equals("")){
				rec=temp_create+rec.substring(0,rec.length()-1);
				System.out.println("if");
			}
			else{
				rec=temp_create.substring(0,temp_create.length()-2);
				System.out.println("else");
			}
			try{
				query_area.setText("CREATE TABLE "+table_name+"("+rec+")");
				s.executeUpdate("CREATE TABLE "+table_name+"("+rec+")");
				query_area.append("\nDone!!");
			}catch(Exception rr1){
					System.out.println(rr1);
					Error.error(frame6,rr1);
			}
			build.setEnabled(false);
			frame6.setVisible(false);
		}
		
		for(int i=1;i<=n;i++){
			if(check_cb!=null){
				if(e.getSource()==(check_cb[i-1])){
					if(check_cb[i-1].isSelected())
						check_tf[i-1].setEnabled(true);
					else
						check_tf[i-1].setEnabled(false);
				}
			}
		}
		if(e.getSource()==foreign_cb){
			if(foreign_cb.isSelected()){
				panel14.add(refTab_label);
				panel14.add(ref_tableTf);
				panel14.add(forKey_label);
				panel14.add(foreign_keyTf);
				
			}
			else{
				panel14.remove(refTab_label);
				panel14.remove(ref_tableTf);
				panel14.remove(forKey_label);
				panel14.remove(foreign_keyTf);
				
			}
			Resize.resize(panel14);
			Resize.resize(frame6);
		}
	}
	
	public void itemStateChanged(ItemEvent e){
		for(int i=1;i<=n;i++){
			if(e.getSource()==check_operator[i-1]){
				checkOperatorSelected[i-1]=(String)check_operator[i-1].getSelectedItem();
				//checkConsFlag=true;
			}
		}
	}
}
/*
 SELECT	constraint_name, constraint_type, search_condition FROM	user_constraints WHERE	table_name = 'EMPLOYEES';
 
 SELECT	constraint_name, column_name FROM	user_cons_columns WHERE table_name = 'EMPLOYEES';

SELECT , user_cons_columns.column_name, user_constraints.constraint_name , user_constraints.search_condition FROM user_constraints, user_cons_columns WHERE user_constraints.table_name = 'BOOK' AND user_cons_columns.table_name = 'BOOK' AND  user_cons_columns.constraint_name=user_constraints.constraint_name;

 */

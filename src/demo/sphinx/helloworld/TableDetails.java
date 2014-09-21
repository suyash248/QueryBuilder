package demo.sphinx.helloworld;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.*;

public class TableDetails extends Builder implements ActionListener{
	Authenticate auth=new Authenticate();
	
	ArrayList<String> colNamesCollection=new ArrayList<String>();
	ArrayList<String> colDataTypesCollection=new ArrayList<String>();
	ArrayList<Integer> colSizeCollection=new ArrayList<Integer>();
	ArrayList<String> colConsCollection=new ArrayList<String>();
	
	HashMap<String,String> colNameConsMap=new HashMap<String,String>();
	Iterator<String> itr;
	ResultSet result;
	String[][] entries;
	JTable table;
	JFrame tbFrame;
	JButton ok;
	JTextField tableTf;
	JPanel pan;
	
	String tableName;


	public void tableDetailsMenuItem(){
		try{
			colNamesCollection.clear();
			colDataTypesCollection.clear();
			colSizeCollection.clear();
			colConsCollection.clear();
			colNameConsMap.clear();
			
			tbFrame=new JFrame("Table Details");
			tableTf=new JTextField(8);
			Icon icon=new ImageIcon(".\\Icon\\select.png");
			ok=new JButton("OK", icon);
			ok.addActionListener(this);
			
			pan=new JPanel();
			pan.add(new JLabel("Table name"));
			pan.add(tableTf);
			pan.add(ok);
			tbFrame.add(pan);
			image_icon=new ImageIcon(".\\Icon\\select.png");
			image=image_icon.getImage();
			tbFrame.setIconImage(image);
			
			tbFrame.pack();
			tbFrame.setVisible(true);
			
		}catch(Exception se2){	
				se2.printStackTrace();
				table_name="";
				Error.error(frame1,se2);
			}
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==tableDetailsMenuItem){
			tableDetailsMenuItem();
		}
		
		if(e.getSource()==ok){
			tableName=tableTf.getText().toUpperCase();
			tableDetails(tableName);
			tbFrame.setVisible(false);
		}
 	}
	
	public HashMap<String,String> constraintDetails(String tableName){	// retrieving the map containing column name as key and constraintName as value
		
		try{
			String colName;
			String consName;
			String query="SELECT column_name, constraint_name from user_constraints natural join user_cons_columns where table_name ='"+tableName+"'";
			result=s.executeQuery(query);
			while(result.next()){
				colName=result.getString(1);
				consName=result.getString(2);
				colNameConsMap.put(colName, consName);
			}
		}catch(Exception exce){
			exce.printStackTrace();
			Error.error(frame1, exce);
		}
		return colNameConsMap;
	}
	
	public void tableDetails(String tableName){
		String colName;
		HashMap<String,String> colNameConsMap=constraintDetails(tableName);
		try{
			result=dbmd.getColumns(null, "SYSTEM", tableName, null);
			
			while(result.next()){
				colName=result.getString(4);
				colNamesCollection.add(colName);	// column name
				colDataTypesCollection.add(result.getString(6)); // datatype
				colSizeCollection.add(result.getInt(7)); // size
				colConsCollection.add(colNameConsMap.get(colName));	// constraint
				
			}
			entries=new String[colNamesCollection.size()][4];
			
			itr=colNamesCollection.iterator();
			int j=0;
			while(itr.hasNext()){
				entries[j][0]=itr.next();
				j++;
			}
			// heading is an array containing all column names of the table.
			
			itr=colDataTypesCollection.iterator();
			j=0;
			while(itr.hasNext()){
				entries[j][1]=itr.next();
				j++;
			}
			
			Iterator <Integer>itr1=colSizeCollection.iterator();
			j=0;
			while(itr1.hasNext()){
				entries[j][2]=itr1.next().toString();
				j++;
			}
			
			itr=colConsCollection.iterator();
			j=0;
			while(itr.hasNext()){
				entries[j][3]=itr.next();
				j++;
			}
			
			JFrame frame2=new JFrame("Table detail");
			table=new JTable(entries,new String[] {"Column Name", "Datatype" ,"Size","Constraint"});
			JScrollPane jp=new JScrollPane(table);
			frame2.add(jp);
			frame2.pack();
			image_icon=new ImageIcon(".\\Icon\\select.png");
			image=image_icon.getImage();
			frame2.setIconImage(image);
			frame2.setVisible(true);
		
		}catch(Exception ex){
			ex.printStackTrace();
			Error.error(frame1, ex);
		}
	}
}
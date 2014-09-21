package demo.sphinx.helloworld;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateIndex extends Builder {
	
	JFrame frame;
	JTextField tableNameTf,indexNameTf;
	JCheckBox fieldsCb[];
	JButton selectTable, create;
	String tableName;
	JPanel pan1, pan2, pan3;
	String fields[];
	
	public void createIndex(){
		frame=new JFrame("Create Index");
		tableNameTf=new JTextField(7);
		
		Icon icon=new ImageIcon(".\\Icon\\tableIndex16.png");
		selectTable=new JButton("OK", icon);
		selectTable.addActionListener(this);
		pan1=new JPanel();
		pan1.add(new JLabel("Table Name"));
		pan1.add(tableNameTf);
		pan1.add(selectTable);
		frame.setLayout(new BorderLayout());
		frame.add(pan1, BorderLayout.NORTH);
		
		image_icon=new ImageIcon(".\\Icon\\tableIndexFrame24.png");
		image=image_icon.getImage();
		frame.setIconImage(image);
		
		frame.pack();
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==createIndex){
			createIndex();
		}
		
		if(e.getSource()==selectTable){
			tableName=tableNameTf.getText();
			fields=fetchFields(tableName);
			fieldsCb=new JCheckBox[fields.length];
			
			pan1.removeAll();
			indexNameTf=new JTextField(7);
			pan1.add(new JLabel("Index Name"));
			pan1.add(indexNameTf);
			Resize.resize(pan1);
			
			pan2=new JPanel();
			pan2.setLayout(new GridLayout(fields.length+1,1));
			pan2.add(new JLabel("On columns"));
			for(int i=0;i<fields.length;i++){
				fieldsCb[i]=new JCheckBox(fields[i]);
				pan2.add(fieldsCb[i]);
			}
			
			pan3=new JPanel();
			Icon icon=new ImageIcon(".\\Icon\\createIndex16.png");
			create=new JButton("Create", icon);
			create.addActionListener(this);
			pan3.add(create);
			 
			frame.add(pan2, BorderLayout.CENTER);
			frame.add(pan3, BorderLayout.SOUTH);
			Resize.resize(frame);
		}
		
		if(e.getSource()==create){
			frame.setVisible(false);
			String onColumns="";
			for(int i=0;i<fieldsCb.length;i++){
				if(fieldsCb[i].isSelected()){
					onColumns=onColumns+fields[i]+",";
				}
			}
			onColumns=onColumns.substring(0, onColumns.length()-1);
			String query="CREATE INDEX "+indexNameTf.getText()+" ON "+tableName+"("+onColumns+")";			
			//CREATE INDEX employee_first_last_name_idx ON employee (first_name, last_name);
			query_area.setText(query);
			try {
				s.executeUpdate(query);
				query_area.append("\nDone!!");
			} catch (SQLException e1) {
				e1.printStackTrace();
				Error.error(frame, e1);
			}
		}
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
			return fieldsName;
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
			Error.error(frame, e1);
			return null;
		}
	}
}

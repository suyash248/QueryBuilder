package demo.sphinx.helloworld;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class Join extends Builder{
	String firstTable, secondTable, joinType;
	JFrame joinFrame, tabFrame;
	
	@SuppressWarnings("rawtypes")
	JComboBox joinOnCombo;
	
	JCheckBox[] firstTableFieldsCb, secondTableFieldsCb;
	JButton doJoin;
	
	JPanel joinOnPan, pan2, pan3, tablesPan, buttonPan;
	
	String[] firstTableFields, secondTableFields, mergedFields;
	String field, joinOnField;
	
	JTable table;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Join(String firstTable, String secondTable, String joinType){
		this.firstTable=firstTable;
		this.secondTable=secondTable;
		this.joinType=joinType;

		joinFrame=new JFrame("Join");
		
		firstTableFields=fetchFields(firstTable);
		secondTableFields=fetchFields(secondTable);
		
		mergedFields=new String[firstTableFields.length+secondTableFields.length];
		
		List<String> list1=Arrays.asList(firstTableFields);
		List<String> list2=Arrays.asList(secondTableFields);
		HashSet hashSet=new HashSet();
		hashSet.addAll(list1);
		hashSet.addAll(list2);
		
		Iterator<String> itr=hashSet.iterator();
		int j=0;
		while(itr.hasNext()){
			field= itr.next();
			mergedFields[j]=field;
			j++;
		}
		
		//hashSet.toArray(mergedFields);
		joinOnCombo=new JComboBox(mergedFields);
		joinOnCombo.addItemListener(this);
		
		firstTableFieldsCb=new JCheckBox[firstTableFields.length];
		secondTableFieldsCb=new JCheckBox[secondTableFields.length];
		
		joinOnPan=new JPanel();
		joinOnPan.add(new JLabel("Join on"));
		joinOnPan.add(joinOnCombo);
		
		pan2=new JPanel();
		pan2.setLayout(new GridLayout(firstTableFields.length+4, 1));
		pan2.add(new JLabel("<html><b>Select Fields</b></html>"));
		pan2.add(new JSeparator());
		pan2.add(new JLabel("<html><b>Table 1 : </b><font color=red>"+firstTable+"</font></html>"));
		for(int i=0;i<firstTableFields.length;i++){
			firstTableFieldsCb[i]=new JCheckBox(firstTableFields[i]);
			pan2.add(firstTableFieldsCb[i]);
		}
		pan2.add(new JSeparator());
		
		pan3=new JPanel();
		pan3.setLayout(new GridLayout(secondTableFields.length+2, 1));
		pan3.add(new JLabel("<html><b>Table 2 : </b><font color=blue>"+secondTable+"</font></html>"));
		for(int i=0;i<secondTableFields.length;i++){
			secondTableFieldsCb[i]=new JCheckBox(secondTableFields[i]);
			pan3.add(secondTableFieldsCb[i]);
		}
		pan3.add(new JSeparator());
		
		tablesPan=new JPanel();
		tablesPan.setLayout(new BorderLayout());
		tablesPan.add(pan2, BorderLayout.NORTH);
		tablesPan.add(pan3, BorderLayout.CENTER);
		
		Icon icon=new ImageIcon(".\\Icon\\link16.png");
		doJoin=new JButton("Join", icon);
		doJoin.addActionListener(this);
		
		buttonPan=new JPanel();
		buttonPan.add(doJoin);
		
		joinFrame.setLayout(new BorderLayout());
		joinFrame.add(joinOnPan, BorderLayout.NORTH);
		joinFrame.add(tablesPan, BorderLayout.CENTER);
		joinFrame.add(buttonPan, BorderLayout.SOUTH);
		
		image_icon=new ImageIcon(".\\Icon\\link24.png");
		image=image_icon.getImage();
		joinFrame.setIconImage(image);
		
		if(joinType.equals("NATURAL") || joinType.equals("CROSS")){		// This if block is for removing joinOnPan
			joinFrame.remove(joinOnPan);
			Resize.resize(joinFrame);
		}
		
		joinFrame.pack();
		joinFrame.setVisible(true);
		
	}
	
	public void itemStateChanged(ItemEvent e){
		if(e.getSource()==joinOnCombo){
			joinOnField=(String)joinOnCombo.getSelectedItem();
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==doJoin){
			String query="SELECT ", selectedFields="", joinName="", usingClause="";	
			// select empno, ename, job, dname, loc, from emp, dept where dept.deptno=emp.deptno;
			
			for(int i=0; i<firstTableFieldsCb.length; i++){
				if(firstTableFieldsCb[i].isSelected()){
					selectedFields=selectedFields+firstTableFields[i]+",";
				}
			}
			
			for(int i=0; i<secondTableFieldsCb.length; i++){
				if(secondTableFieldsCb[i].isSelected()){
					selectedFields=selectedFields+secondTableFields[i]+",";
				}
			}
			query=query+selectedFields;
			query=query.substring(0, query.length()-1);// rec=temp_create+rec.substring(0,rec.length()-1);
			
			if(joinType.equals("EQUI")){
				joinName="JOIN";
				usingClause="using("+joinOnField+")";
			}
			if(joinType.equals("NATURAL")){
				joinName="NATURAL JOIN";
				usingClause="";
			}
			if(joinType.equals("CROSS")){
				joinName="CROSS JOIN";
				usingClause="";
			}
			if(joinType.equals("LEFT OUTER")){
				joinName="LEFT OUTER JOIN";
				usingClause="using("+joinOnField+")";
			}
			if(joinType.equals("RIGHT OUTER")){
				joinName="RIGHT OUTER JOIN";
				usingClause="using("+joinOnField+")";
			}
			if(joinType.equals("FULL OUTER")){
				joinName="FULL OUTER JOIN";
				usingClause="using("+joinOnField+")";
			}
			if(joinType.equals("INNER")){
				joinName="INNER JOIN";
				usingClause="using("+joinOnField+")";
			}
			
			query=query+" FROM "+firstTable+" "+joinName+" "+secondTable+" "+usingClause;
			query_area.setText(query);
			
			try{
				int rowCount=0, columnCount=0;
				ResultSetMetaData metaData;
				ResultSet result;
				String headings[], entries[][];
			
				result=s.executeQuery(query);
				query_area.append("\nDone!!");
				metaData=result.getMetaData();
				columnCount=metaData.getColumnCount();
				headings=new String[columnCount];
				
				while(result.next()){
					rowCount++;
				}	
				result.beforeFirst();
				
				for(int t=1;t<=columnCount;t++){
					headings[t-1]=metaData.getColumnName(t);
				}
				
				entries=new String[rowCount][columnCount];
				
				for(int k=1;k<=rowCount;k++){
					result.next();
					for(int l=1;l<=columnCount;l++){
						entries[k-1][l-1]=result.getString(l);
					}
				}
				
				tabFrame=new JFrame(joinType+" JOIN");
				table=new JTable(entries,headings);
				JScrollPane jp=new JScrollPane(table);
				tabFrame.add(jp);
				tabFrame.pack();
				image_icon=new ImageIcon(".\\Icon\\select.png");
				image=image_icon.getImage();
				tabFrame.setIconImage(image);
			
				tabFrame.setVisible(true);
				
			}catch(Exception exc){
				exc.printStackTrace();
				Error.error(joinFrame, exc);
			}
		}
	}
	
	@SuppressWarnings("unused")
	String[] fetchFields(String tableName){
		try {
			int rowCount=0, columnCount=0;
			ResultSetMetaData metaData;
			String[] fieldsName;
			ResultSet result;
			result=s.executeQuery("select * from "+tableName);
		
			metaData=result.getMetaData();
			columnCount=metaData.getColumnCount();
			fieldsName=new String[columnCount];
			
			while(result.next()){
				rowCount++;
			}	
			
			result=s.executeQuery("select * from "+tableName);
			for(int t=1;t<=columnCount;t++){
				fieldsName[t-1]=metaData.getColumnName(t);
			}
			
			return fieldsName;
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
			Error.error(joinFrame, e1);
			return null;
		}
		
	}
}

package demo.sphinx.helloworld;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class OtherQuery extends Builder implements KeyListener {

	JFrame queryFrame, QueryTypeFrame;
	JTable table;
	ResultSet result;
	ResultSetMetaData metaData;
	int rowCount, columnCount;
	String queryWritten, headings[], entries[][], queryType;
	
	JRadioButton ddl, dml;
	
	public void nativeSQL(){
		QueryTypeFrame=new JFrame("Select Query Type");
		JPanel pan=new JPanel();
		ddl=new JRadioButton("DDL Query");
		dml=new JRadioButton("DML Query");
		
		ddl.addActionListener(this);
		dml.addActionListener(this);
		
		ButtonGroup bgrp=new ButtonGroup();
		bgrp.add(ddl);
		bgrp.add(dml);
		
		pan.add(ddl);
		pan.add(dml);
		
		QueryTypeFrame.add(pan);
		
		image_icon=new ImageIcon(".\\Icon\\otherQueryFrame.png");
		image=image_icon.getImage();
		QueryTypeFrame.setIconImage(image);
		QueryTypeFrame.pack();
		QueryTypeFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==otherQuery){
			nativeSQL();
		}
		
		if(e.getSource()==ddl){
			queryType="DDL";
			QueryTypeFrame.setVisible(false);
			query_area.setText("Write your SQL query here and then hit ENTER!\nNOTE:-Don't use semicolon(;)");
			query_area.setEditable(true);
			query_area.addKeyListener(this);
			query_area.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent e){
							if(e.getSource()==query_area){
								query_area.selectAll();
							}
						}
					}
			);
			
		}
		if(e.getSource()==dml){
			queryType="DML";
			QueryTypeFrame.setVisible(false);
			query_area.setText("Write your SQL query here and then hit ENTER!");
			query_area.setEditable(true);
			query_area.addKeyListener(this);
			//query_area.addMouseListener(this);
			query_area.addMouseListener(
					new MouseAdapter() {
						public void mouseClicked(MouseEvent e){
							if(e.getSource()==query_area){
								query_area.selectAll();
							}
						}
					}
			);
		}
	}
	
//	public void mouseClicked(MouseEvent e) {
//		query_area.setText("");
//	}
	
	public void keyPressed(KeyEvent e){	
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			try{
				queryWritten=query_area.getText();
				otherQuery.setEnabled(false);
				if(queryType.equals("DDL")){
				
					result=s.executeQuery(queryWritten);
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
					
					queryFrame=new JFrame("Result");
					table=new JTable(entries,headings);
					JScrollPane jp=new JScrollPane(table);
					queryFrame.add(jp);
					image_icon=new ImageIcon(".\\Icon\\otherQueryFrame.png");
					image=image_icon.getImage();
					queryFrame.setIconImage(image);
					queryFrame.pack();
					queryFrame.setVisible(true);
				}
				else{
					s.executeUpdate(queryWritten);
				}
				query_area.append("\nDone!!");
			}catch(Exception exc){
				exc.printStackTrace();
				Error.error(queryFrame, exc);
			}
			query_area.setEditable(false);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

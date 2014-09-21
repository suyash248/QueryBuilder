package demo.sphinx.helloworld;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Drop extends Builder implements ActionListener{
	JFrame dropTabSeqFrame, dropIndexFrame;
	JRadioButton dropTable, dropSeq, dropUser, dropIndex;
	JPanel pan;
	ButtonGroup bGroup=new ButtonGroup();
	JTextField droppedUserTf, droppedIndexTf;
	JButton droppedUser, droppedIndex;
	static Drop dropObj;
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==drop){
			dropObj=this;
			drop();
		}
		
		if(e.getSource()==dropTable){
			dropTable();
		}
		if(e.getSource()==dropSeq){
			dropSequence();
		}
		if(e.getSource()==dropUser){
			dropUser();
		}
		if(e.getSource()==dropIndex){
			dropIndex();
		}
		
		if(e.getSource()==droppedUser){
			try{
				query_generated="DROP USER "+droppedUserTf.getText();
				query_area.setText(query_generated);
				dropTabSeqFrame.setVisible(false);
				s.executeUpdate(query_generated);
				query_area.append("\nDone!!");
			}catch(Exception dro){
					Error.error(frame1,dro); 
			}
		}
		
		if(e.getSource()==droppedIndex){
			dropIndexFrame.setVisible(false);
			query_generated="DROP INDEX "+droppedIndexTf.getText();
			try{
				query_area.setText(query_generated);
				s.executeUpdate(query_generated);
				query_area.append("\nDone!!");
			}catch(Exception dro){
				System.out.println(dro);
				Error.error(frame1,dro); 
			}
		}
	}
	public void drop(){
		dropTabSeqFrame=new JFrame("Drop");
		pan=new JPanel();
		dropTable=new JRadioButton("Drop table");
		dropTable.addActionListener(this);
		dropSeq=new JRadioButton("Drop sequence");
		dropSeq.addActionListener(this);
		dropUser=new JRadioButton("Drop user");
		dropUser.addActionListener(this);
		dropIndex=new JRadioButton("Drop index");
		dropIndex.addActionListener(this);
		bGroup.add(dropSeq);
		bGroup.add(dropTable);
		bGroup.add(dropUser);
		bGroup.add(dropIndex);
		
		pan.setLayout(new GridLayout(4,1));
		pan.add(dropTable);
		pan.add(dropSeq);
		pan.add(dropUser);
		pan.add(dropIndex);
		dropTabSeqFrame.add(pan);
		
		image_icon=new ImageIcon(".\\Icon\\drop.png");
		image=image_icon.getImage();
		dropTabSeqFrame.setIconImage(image);
		
		dropTabSeqFrame.pack();
		dropTabSeqFrame.setVisible(true);
	}
	public void dropTable(){
		try{
			query_generated="DROP TABLE "+table_name;
			s.executeUpdate(query_generated);
			query_area.setText(query_generated);
			query_area.append("\nDone!!");
			dropTabSeqFrame.setVisible(false);
			}catch(Exception dro){
					System.out.println(dro);
					Error.error(frame1,dro);
			}
	}
	public void dropSequence(){
		try{
			query_generated="DROP SEQUENCE "+table_name;
			s.executeUpdate(query_generated);
			query_area.setText(query_generated);
			query_area.append("\nDone!!");
			dropTabSeqFrame.setVisible(false);
			}catch(Exception dro){
					Error.error(frame1,dro);
			}
	}
	public void dropUser(){
		pan.setLayout(new FlowLayout());
		droppedUserTf=new JTextField(10);
		
		dropTabSeqFrame.setName("Drop User");
		Icon icon=new ImageIcon(".\\Icon\\drop6.png");
		droppedUser=new JButton("Drop", icon);
		pan.removeAll();
		pan.add(new JLabel("Username to be dropped"));
		pan.add(droppedUserTf);
		pan.add(droppedUser);
		Resize.resize(pan);	
		Resize.resize(dropTabSeqFrame);
		
		droppedUser.addActionListener(this);
	}
	public void dropIndex(){
		dropTabSeqFrame.setVisible(false);
		dropIndexFrame=new JFrame("Drop Index");
		JPanel pan=new JPanel();
		droppedIndexTf=new JTextField(7);
		Icon icon=new ImageIcon(".\\Icon\\drop6.png");
		droppedIndex=new JButton("OK", icon);
		droppedIndex.addActionListener(this);
		pan.add(new JLabel("Index name"));
		pan.add(droppedIndexTf);
		pan.add(droppedIndex);
		dropIndexFrame.add(pan);
		
		image_icon=new ImageIcon(".\\Icon\\drop.png");
		image=image_icon.getImage();
		dropIndexFrame.setIconImage(image);
		
		dropIndexFrame.pack();
		dropIndexFrame.setVisible(true);
	}
}
package demo.sphinx.helloworld;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class CreateSequence extends Builder {
	/* 
	 
	 CREATE SEQUENCE sequence
       [INCREMENT BY n]
       [START WITH n]
       [{MAXVALUE n | NOMAXVALUE}]
       [{MINVALUE n | NOMINVALUE}]
       [{CYCLE | NOCYCLE}]
       [{CACHE n | NOCACHE}];

	  
	CREATE SEQUENCE dept_deptid_seq
    INCREMENT BY 10
    START WITH 120
    MAXVALUE 9999
    NOCACHE
    NOCYCLE; 
    
    SELECT	sequence_name, min_value, max_value, increment_by, last_number FROM	user_sequences;
    
    SELECT	dept_deptid_seq.NEXTVAL FROM	dual;
    */

	String maxVal="",minVal="",cacheVal="",cycleVal="";
	JFrame seqFrame;
	JTextField increment_tf, start_tf,max_tf,min_tf, cache_tf;
	JCheckBox max_cb,min_cb,cycle_cb,cache_cb;
	JButton ok;
	JPanel incrementPan,startPan, panSeq1, maxPan,minPan,cachePan, panSeq2 ,cycleOkPan,panSeq3;
	
	public void createSequence(){
		createFrame.setVisible(false);
		seqFrame=new JFrame("Create Sequence");
	
		incrementPan=new JPanel();
		startPan=new JPanel();
		
		start_tf=new JTextField(5);
		increment_tf=new JTextField(5);
		incrementPan.setLayout(new FlowLayout(FlowLayout.LEFT));
		incrementPan.add(new JLabel("Incement by"));
		incrementPan.add(increment_tf);
		startPan.setLayout(new FlowLayout(FlowLayout.LEFT));
		startPan.add(new JLabel("Start with"));
		startPan.add(start_tf);
		
		panSeq1=new JPanel();
		panSeq1.setLayout(new BorderLayout());
		panSeq1.add(incrementPan,BorderLayout.NORTH);
		panSeq1.add(startPan,BorderLayout.CENTER);
		
		
		maxPan=new JPanel();
		maxPan.setLayout(new FlowLayout(FlowLayout.LEFT));
		minPan=new JPanel();
		minPan.setLayout(new FlowLayout(FlowLayout.LEFT));
		cachePan=new JPanel();
		cachePan.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		max_cb=new JCheckBox("MAX VALUE");
		min_cb=new JCheckBox("MIN VALUE");
		cache_cb=new JCheckBox("CACHE");
	
		max_cb.addActionListener(this);
		min_cb.addActionListener(this);
		cache_cb.addActionListener(this);
		
		maxPan.add(max_cb);
		
		minPan.add(min_cb);
		cachePan.add(cache_cb);
		
		panSeq2=new JPanel();
		panSeq2.setLayout(new BorderLayout());
		panSeq2.add(maxPan,BorderLayout.NORTH);
		panSeq2.add(minPan,BorderLayout.CENTER);
		panSeq2.add(cachePan,BorderLayout.SOUTH);
		
		cycleOkPan=new JPanel();
		cycle_cb=new JCheckBox("CYCLE");
		cycle_cb.addActionListener(this);
		ok=new JButton("OK");
		ok.addActionListener(this);
		cycleOkPan=new JPanel();
		cachePan.setLayout(new FlowLayout(FlowLayout.LEFT));
		cycleOkPan.add(cycle_cb);
		cycleOkPan.add(ok);
		
		seqFrame.setLayout(new BorderLayout());
		seqFrame.add(panSeq1,BorderLayout.NORTH);
		seqFrame.add(panSeq2,BorderLayout.CENTER);
		seqFrame.add(cycleOkPan,BorderLayout.SOUTH);
		
		image_icon=new ImageIcon(".\\Icon\\sequence.png");
		image=image_icon.getImage();
		seqFrame.setIconImage(image);
		
		seqFrame.pack();
		seqFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==createSeq){
			createSequence();
		}
		if(e.getSource()==max_cb){
			if(max_cb.isSelected()){
				max_tf=new JTextField(5);
				maxPan.add(max_tf);
			}
			else{
				maxPan.remove(max_tf);
			}
			Resize.resize(maxPan);
			Resize.resize(panSeq2);
			Resize.resize(seqFrame);
		}
		
		if(e.getSource()==min_cb){
			if(min_cb.isSelected()){
				min_tf=new JTextField(5);
				minPan.add(min_tf);
			}
			else{
				minPan.remove(min_tf);
			}
			Resize.resize(minPan);
			Resize.resize(panSeq2);
			Resize.resize(seqFrame);
		}
		
		if(e.getSource()==cache_cb){
			if(cache_cb.isSelected()){
				cache_tf=new JTextField(5);
				cachePan.add(cache_tf);
			}
			else{
				cachePan.remove(cache_tf);
			}
			Resize.resize(cachePan);
			Resize.resize(panSeq2);
			Resize.resize(seqFrame);
		}
		
		if(e.getSource()==ok){
			System.out.println("OK");
			try {
				if(max_cb.isSelected()){
					maxVal=" MAXVALUE "+max_tf.getText();
				}
				else{
					maxVal=" NOMAXVALUE ";
				}
				
				if(min_cb.isSelected()){
					minVal=" MINVALUE "+min_tf.getText();
				}
				else{
					minVal=" NOMINVALUE ";
				}
				
				if(cache_cb.isSelected()){
					cacheVal=" CACHE "+cache_tf.getText();
				}	
				else{
					cacheVal=" NOCACHE ";
				}
				
				if(cycle_cb.isSelected()){
					cycleVal=" CYCLE";
				}
				else{
					cycleVal=" NOCYCLE";
				}
				query_generated=" CREATE SEQUENCE "+table_name+" INCREMENT BY "+increment_tf.getText()+" START WITH "+start_tf.getText()+maxVal+minVal+cacheVal+cycleVal;
				s.executeUpdate(query_generated);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			query_area.setText(query_generated);
			query_area.append("\nDone !!");
		}
	}
}

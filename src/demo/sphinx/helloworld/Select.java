package demo.sphinx.helloworld;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Select extends Builder{
	
	public void select(){
		try{
			query.setEnabled(true);
			value.setEnabled(true);
			insert.setEnabled(true);
			operator_combo.setEnabled(true);
			delete.setEnabled(false);
			update.setEnabled(false);
			alter.setEnabled(false);
			fstr="select";

			selectedFields_checkbox=new JCheckBox[columncount];
			as_cb=new JCheckBox[columncount];
			as_tf=new JTextField[columncount];
			
			frame7=new JFrame("Fields to be selected");	
			frame7.setLayout(new BorderLayout());
			panel14=new JPanel();
			panel14.setLayout(new GridLayout(columncount,3));
			panel15=new JPanel();	
			
			for(int k=0;k<heading.length;k++){
				selectedFields_checkbox[k]=new JCheckBox(heading[k]);
				
				as_cb[k]=new JCheckBox("AS");
				as_tf[k]=new JTextField(7);
				
				as_cb[k].setEnabled(false);
				as_tf[k].setEnabled(false);
				
				selectedFields_checkbox[k].addActionListener(this);
				
				panel14.add(selectedFields_checkbox[k]);
				panel14.add(as_cb[k]);
				panel14.add(as_tf[k]);
			}
			
			distinct=new JCheckBox("Distinct");
		
			panel20=new JPanel();
			panel20.add(distinct);
			
			Icon icon17=new ImageIcon(".\\Icon\\selectfields.png");
			select_fields=new JButton("Select field(s)",icon17);
			select_fields.addActionListener(this);
			select_fields.addChangeListener(new Builder());
			panel15.add(select_fields);

			frame7.add(panel14,BorderLayout.NORTH);
			frame7.add(panel20,BorderLayout.CENTER);
			frame7.add(panel15,BorderLayout.SOUTH);
			
			image_icon=new ImageIcon(".\\Icon\\selectfields.png");
			image=image_icon.getImage();
			frame7.setIconImage(image);
			
			frame7.pack();
			frame7.setVisible(true);
			}catch(Exception e89){
					e89.printStackTrace();
					Error.error(frame1,e89);
					}
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==select){
			select();
		}
		
		for(int i=0;i<columncount;i++){
			if(e.getSource()==selectedFields_checkbox[i]){
				if(selectedFields_checkbox[i].isSelected()){
					as_cb[i].setEnabled(true);
					as_tf[i].setEnabled(true);
				}
				else{
					as_cb[i].setEnabled(false);
					as_tf[i].setEnabled(false);
				}
				Resize.resize(panel14);
				Resize.resize(frame7);
			}
		}
		
		if(e.getSource()==select_fields){
			temp_query="";
			frame7.setVisible(false);
			orderby.setEnabled(true);
			for(int j=0;j<columncount;j++){
				if(selectedFields_checkbox[j].isSelected()){
					temp_query=temp_query+heading[j];
					if(as_cb[j].isSelected()){
						temp_query=temp_query+" AS "+as_tf[j].getText();
					}
					temp_query=temp_query+",";
					go_colcount++;
				}
			}
			selected_fields=temp_query.substring(0,temp_query.length()-1);
			if(distinct.isSelected()){
				query_generated="select distinct "+selected_fields+" from "+table_name;
			}
			else{
				if( !(distinct.isSelected()) )
					query_generated="select "+selected_fields+" from "+table_name;
			}
		}
	}
}
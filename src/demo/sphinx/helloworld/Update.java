package demo.sphinx.helloworld;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Update extends Builder implements ActionListener{
	
	public void update(){
		try{
			query.setEnabled(true);
			value.setEnabled(true);
			insert.setEnabled(true);
			operator_combo.setEnabled(true);
			select.setEnabled(false);
			delete.setEnabled(false);	
			alter.setEnabled(false);
			fstr="update";

			update_check=new JCheckBox[columncount];
			frame8=new JFrame("Select field(s) to be updated");	
			frame8.setLayout(new BorderLayout());
			panel16=new JPanel();
			panel16.setLayout(new GridLayout(columncount,1));
			panel17=new JPanel();

			for(int k=0;k<heading.length;k++){
				update_check[k]=new JCheckBox(heading[k]);
				panel16.add(update_check[k]);
			}	

			Icon icon19=new ImageIcon(".\\Icon\\selectfields.png");
			fields_to_be_updated=new JButton("Select field(s)",icon19);
			fields_to_be_updated.addChangeListener(new Builder());
			fields_to_be_updated.addActionListener(this);
			panel17.add(fields_to_be_updated);

			frame8.add(panel16,BorderLayout.NORTH);
			frame8.add(panel17,BorderLayout.CENTER);

			image_icon=new ImageIcon(".\\Icon\\selectfields.png");
			image=image_icon.getImage();
			frame8.setIconImage(image);
			
			frame8.pack();
			frame8.setVisible(true);
			}catch(Exception e99){		
					System.out.println(e99);
					Error.error(frame1,e99);
					}
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==update){
			update();
		}
		
		if(e.getSource()==update_fields){
			int n=0;
			frame9.setVisible(false);
			temp_query="";
			frame8.setVisible(false);
			for(int j=0;j<columncount;j++){
				if(update_check[j].isSelected()){
					//  "'"+text_fields[0].getText()+"',";
					temp_query=temp_query+heading[j]+"="; 
					temp_query=temp_query+"'"+update_tf[n].getText()+"',";
					n++;
				}
			}
			updated_fields=temp_query.substring(0,temp_query.length()-1);
			query_generated="update "+table_name+" set "+updated_fields;
		}
		if(e.getSource()==fields_to_be_updated){
			int n=0;
			tf_count=0;
			frame8.setVisible(false);
			frame9=new JFrame("Enter updated values");
			panel18=new JPanel();
			panel19=new JPanel();	
			
			for(int y=0;y<columncount;y++){
				if(update_check[y].isSelected()){
					tf_count++;
				}
			}
			panel18.setLayout(new GridLayout(tf_count,2));
			update_tf=new JTextField[tf_count];
			
			for(int z=0;z<columncount;z++){
				if(update_check[z].isSelected()){
					update_tf[n]=new JTextField();
					panel18.add(new JLabel(heading[z]));
					panel18.add(update_tf[n]);
					n++;
				}
			}
			Icon icon20=new ImageIcon(".\\Icon\\update.png");
			update_fields=new JButton("Update",icon20);
			update_fields.addActionListener(this);
			panel19.add(update_fields);

			frame9.add(panel18,BorderLayout.NORTH);
			frame9.add(panel19,BorderLayout.CENTER);

			image_icon=new ImageIcon(".\\Icon\\update.png");
			image=image_icon.getImage();
			frame9.setIconImage(image);
			
			frame9.pack();
			frame9.setVisible(true);
		}
	}
}
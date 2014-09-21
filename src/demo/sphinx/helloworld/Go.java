package demo.sphinx.helloworld;
import java.awt.event.*;

import javax.swing.*;
public class Go extends Builder{
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==go){
			if(fstr.equals("select")){
				try{
				heading1=new String[go_colcount];
				int n=0;
				for(int j=0;j<columncount;j++){
					if(selectedFields_checkbox[j].isSelected()){
						if(as_cb[j].isSelected()){
							heading1[n]=as_tf[j].getText();
						}
						else{
							heading1[n]=heading[j];
						}
						n++;
					}
				}
				
				if(orderby.isSelected()){
					query_generated=query_generated+" order by "+order_fields+" "+sequence;
				}
				query_area.setText(query_generated);
				rs=s.executeQuery(query_generated);
				frame3=new JFrame("Selected fields");
				while(rs.next()){
					rowcount1++;
				}
				//rs=s.executeQuery(query_generated);
				rs.beforeFirst();
				entry1=new String[rowcount1][go_colcount];
				for(int k=1;k<=rowcount1;k++){
					rs.next();
					for(int l=1;l<=go_colcount;l++){
						entry1[k-1][l-1]=rs.getString(l);
					}
				}
				jt1=new JTable(entry1,heading1);
				JScrollPane jp1=new JScrollPane(jt1);
				frame3.add(jp1);
				
				image_icon=new ImageIcon(".\\Icon\\select.png");
				image=image_icon.getImage();
				frame3.setIconImage(image);
				
				frame3.setVisible(true);
				frame3.pack();
				query_area.append("\nDone !!");
				}catch(Exception pe1){	
						pe1.printStackTrace();
						Error.error(frame1,pe1);
						}
			}
			if(fstr.equals("delete"))
			{
				try{
					s.executeUpdate(query_generated);
					query_area.append("\nDone !!");
				}catch(Exception pe2){
						pe2.printStackTrace();
						Error.error(frame1,pe2);
						}
			}
			if(fstr.equals("update")){
				try{
					s.executeUpdate(query_generated);
					query_area.append("\nDone !!");
				}catch(Exception pe3){
						pe3.printStackTrace();
						Error.error(frame1,pe3);
						}
			}
			if(fstr.equals("alter")){
				try{
					s.executeUpdate(query_generated);
					query_area.append("\nDone !!");
				}catch(Exception pe4){
						pe4.printStackTrace();
						Error.error(frame1,pe4);
						}
			}
			orderby.setEnabled(false);
		}
	}
}
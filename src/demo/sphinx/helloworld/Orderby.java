package demo.sphinx.helloworld;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Orderby extends Builder implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Order By")){
			if(orderby.isSelected()){
				frame10=new JFrame("Order By");
				panel21=new JPanel();
				panel21.setLayout(new GridLayout(columncount,1));

				checkbox_order=new JCheckBox[columncount];
				for(int k=0;k<heading.length;k++){
					checkbox_order[k]=new JCheckBox(heading[k]);
					panel21.add(checkbox_order[k]);
				}
				
				ascending=new JRadioButton("Ascending");
				descending=new JRadioButton("Descending");
				bg2.add(ascending);
				bg2.add(descending);
				
				panel22=new JPanel();
				panel22.add(ascending);
				panel22.add(descending);
	
				Icon icon18=new ImageIcon(".\\Icon\\orderby1.png");
				order=new JButton("OK",icon18);
				order.addActionListener(this);
			
				panel23=new JPanel();
				panel23.add(order);
			
				frame10.add(panel21,BorderLayout.NORTH);
				frame10.add(panel22,BorderLayout.CENTER);
				frame10.add(panel23,BorderLayout.SOUTH);
	
				image_icon=new ImageIcon(".\\Icon\\orderby1.png");
				image=image_icon.getImage();
				frame10.setIconImage(image);
	
				frame10.pack();
				frame10.setVisible(true);
				orderby.setEnabled(false);			
			}
		}
		if(e.getActionCommand().equals("OK")){		
			temp_query1="";
			order_fields="";
			sequence="";
			for(int j=0;j<columncount;j++){
				if(checkbox_order[j].isSelected()){
					temp_query1=temp_query1+heading[j]+",";
				}
			}
			order_fields=temp_query1.substring(0,temp_query1.length()-1);
			
			if(ascending.isSelected()){
				sequence="asc";
			}
			if(descending.isSelected()){
				sequence="desc";
			}
			//query_generated=query_generated+" order by "+order_fields+" "+sequence;
			frame10.setVisible(false);
		}
	}
}
package demo.sphinx.helloworld;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Insert extends Builder implements ActionListener{
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==insert){
			insert();		
		}
		if(e.getSource()==add){
			add();
			go.setEnabled(true);
		}	
	}
	public void insert(){
		frame4=new JFrame("Insert Record");
		text_fields=new JTextField[columncount];
		labels=new JLabel[columncount];
		panel7=new JPanel();
		panel7.setLayout(new GridLayout(columncount,2));	
		for(int t=1;t<=columncount;t++){
			try{
			String col=rsmd.getColumnName(t);
			labels[t-1]=new JLabel(col);
			panel7.add(labels[t-1]);
			text_fields[t-1]=new JTextField(8);
			panel7.add(text_fields[t-1]);
			}catch(Exception rss){
					rss.printStackTrace();
					Error.error(frame1,rss);
					}
		}
		Icon icon12=new ImageIcon(".\\Icon\\additem.png");
		add=new JButton("Add",icon12);
		add.addActionListener(this);
		panel8=new JPanel();
		panel8.add(add);
		frame4.add(panel8,BorderLayout.CENTER);
		frame4.add(panel7,BorderLayout.NORTH);
		image_icon=new ImageIcon(".\\Icon\\insert.png");
		image=image_icon.getImage();
		frame4.setIconImage(image);
		frame4.pack();	
		frame4.setVisible(true);
		inserted="inserted";
	}
	public void add(){
//		for(int p=0;p<text_fields.length;p++)
//		System.out.println(text_fields[p].getText());
		//rec="'"+text_fields[0].getText()+"',";
		rec="";
		for(int i=0;i<columncount;i++){
			rec=rec+"'"+text_fields[i].getText()+"',";
		}
		rec=rec.substring(0,rec.length()-1);
		//rec=rec+"'"+text_fields[columncount-1].getText()+"'";
		try{
			query_area.setText("insert into "+table_name+" values("+rec+")");
			s.executeUpdate("insert into "+table_name+" values("+rec+")");
			query_area.append("\n1 record inserted successfully !!");
		}catch(Exception ii){
				ii.printStackTrace();
				Error.error(frame4,ii);
				}
		frame4.setVisible(false);
		insert.setEnabled(false);
		inserted="";
	}
}
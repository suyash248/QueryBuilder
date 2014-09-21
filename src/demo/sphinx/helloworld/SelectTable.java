package demo.sphinx.helloworld;
import java.awt.event.*;

import javax.swing.*;

public class SelectTable extends Builder implements ActionListener{
	Authenticate auth=new Authenticate();

	@SuppressWarnings({ "unchecked", "static-access" })
	public void choose(){
		try{
			check="ok";
			rowcount=0;
			create.setEnabled(false);
			rs=auth.s.executeQuery("select * from "+table_name);
			rsmd=rs.getMetaData();
			columncount=rsmd.getColumnCount();
			heading=new String[columncount];
			while(rs.next()){
				rowcount++;
			}	
			rs=s.executeQuery("select * from "+table_name);
				field_combo.addItem("--select field--");
			for(int t=1;t<=columncount;t++){
				heading[t-1]=rsmd.getColumnName(t);
				field_combo.addItem(heading[t-1]);
			}
			entry=new String[rowcount][columncount];
			for(int k=1;k<=rowcount;k++){
				rs.next();
				for(int l=1;l<=columncount;l++){
					entry[k-1][l-1]=rs.getString(l);
				}
			}
			}catch(Exception se2){	
					System.out.println(se2);
					table_name="";
					Error.error(frame1,se2);
					check="exception";
				}
			if(!check.equals("exception")){
				frame2=new JFrame("Table detail");
				jt=new JTable(entry,heading);
				JScrollPane jp=new JScrollPane(jt);
				frame2.add(jp);
				frame2.pack();
				image_icon=new ImageIcon(".\\Icon\\select.png");
				image=image_icon.getImage();
				frame2.setIconImage(image);
			
				frame2.setVisible(true);
			}
			else{
				table_tf.setText("Table does not exist");
			}
			select.setEnabled(true);
			delete.setEnabled(true);
			insert.setEnabled(true);
			update.setEnabled(true);
			alter.setEnabled(true);
			select_table.setEnabled(false);
			Resize.resize(frame1);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("Choose")){
			choose();
		}
 	}
}
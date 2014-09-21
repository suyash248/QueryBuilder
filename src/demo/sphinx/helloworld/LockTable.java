package demo.sphinx.helloworld;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LockTable extends Builder{
	ResultSet rs;
	JFrame tabFrame;
	JTextField tableNameTf;
	JComboBox<String> modesCombo;
	JButton lockTable;
	JPanel pan1, pan2;
	
	String modeSelected, tableName;
	
	public void itemStateChanged(ItemEvent e){
		if(e.getSource()==modesCombo){
			modeSelected=(String)modesCombo.getSelectedItem();
		}
	}
	
	public void lockTableMenuItem(){
		tabFrame=new JFrame("Lock Table");
		tableNameTf=new JTextField(10);
		
		modesCombo=new JComboBox<String>(new String[] {"--select mode--", "SHARE" ,"EXCLUSIVE"});
		modesCombo.addItemListener(this);
		
		Icon icon=new ImageIcon(".\\Icon\\Keys-icon (2).png");
		lockTable=new JButton("Lock", icon);
		lockTable.addActionListener(this);
		
		pan1=new JPanel();
		pan1.add(new JLabel("Table name"));
		pan1.add(tableNameTf);
		pan1.add(modesCombo);
		
		pan2=new JPanel();
		pan2.add(lockTable);
		
		tabFrame.setLayout(new BorderLayout());
		tabFrame.add(pan1, BorderLayout.NORTH);
		tabFrame.add(pan2, BorderLayout.CENTER);
		
		image_icon=new ImageIcon(".\\Icon\\lock.png");
		image=image_icon.getImage();
		tabFrame.setIconImage(image);
		
		tabFrame.pack();
		tabFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==lockTableMenuItem){
			lockTableMenuItem();
		}
		if(e.getSource()==lockTable){
			tableName=tableNameTf.getText().toUpperCase();
			tabFrame.setVisible(false);
			
			if(isExist(tableName)==1){
				query_generated="LOCK TABLE "+tableName+" IN "+modeSelected+" MODE ";
				try {
					query_area.setText(query_generated);
					s.executeUpdate(query_generated);
					query_area.append("\nDONE !!");
				} catch (SQLException e1) {
					Error.error(tabFrame, e1);
					e1.printStackTrace();
				}
			}
			else{
				query_area.setText("Table does not exist !!");
			}
		}
	}
	
	public int isExist(String tableName){
		int count=0;
		try {
			query_generated="select count(*) from user_tables where table_name='"+tableName+"'";
			rs=s.executeQuery(query_generated);
			rs.next();
			count=rs.getInt(1);
			
		} catch (SQLException e) {
			Error.error(frame0, e);
			e.printStackTrace();
		}
		return count;
	}
}

package demo.sphinx.helloworld;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Rename extends Builder{
	JFrame renameFrame;
	JPanel pan;
	JTextField newTableTf;
	JButton changeButton;
	
	public void rename(){
		renameFrame=new JFrame("Rename");
		newTableTf=new JTextField(10);
		Icon icon=new ImageIcon(".\\Icon\\rename16.png");
		changeButton=new JButton("Change", icon);
		changeButton.addActionListener(this);
		pan=new JPanel();
		pan.add(new JLabel("Enter new name"));
		pan.add(newTableTf);
		pan.add(changeButton);
	
		renameFrame.add(pan);
		
		image_icon=new ImageIcon(".\\Icon\\rename32.png");
		image=image_icon.getImage();
		renameFrame.setIconImage(image);
		
		renameFrame.pack();
		renameFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==rename){
			rename();
		}
		if(e.getSource()==changeButton){
			query_generated="RENAME "+table_name+" TO "+newTableTf.getText();	// RENAME dept TO detail_dept;
			try {
				query_area.setText(query_generated);
				query_area.append("\nDone!!");
				s.executeUpdate(query_generated);
			} catch (SQLException e1) {
				e1.printStackTrace();
				}
		}
	}
}
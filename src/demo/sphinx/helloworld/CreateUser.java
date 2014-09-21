package demo.sphinx.helloworld;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateUser extends Builder {
	JFrame userFrame;
	JPanel pan1, pan2;
	JTextField uNameTf=new JTextField(10);
	JPasswordField uPass=new JPasswordField(10);
	JPasswordField confirmPass=new JPasswordField(10);
	JCheckBox grantPrivileges=new JCheckBox("Grant ALL Privelliges");
	Icon icon=new ImageIcon(".\\Icon\\user16.png");
	JButton ok=new JButton("OK", icon);
	
	public void createUser(){
		createFrame.setVisible(false);
		userFrame=new JFrame("New User");
		
		pan1=new JPanel();
		pan1.setLayout(new GridLayout(3,2));
		
		pan1.add(new JLabel("Username"));
		pan1.add(uNameTf);
		pan1.add(new JLabel("Password"));
		pan1.add(uPass);
		pan1.add(new JLabel("Confirm Password"));
		pan1.add(confirmPass);
		
		pan2=new JPanel();
		pan2.setLayout(new FlowLayout(FlowLayout.LEFT));
		pan2.add(ok);
		pan2.add(grantPrivileges);
		ok.addActionListener(this);
		
		userFrame.setLayout(new BorderLayout());
		userFrame.add(pan1,BorderLayout.NORTH);
		userFrame.add(pan2,BorderLayout.CENTER);
		
		image_icon=new ImageIcon(".\\Icon\\user.png");
		image=image_icon.getImage();
		userFrame.setIconImage(image);
		
		userFrame.pack();
		userFrame.setVisible(true);
	}
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==createUser){
			createUser();
		}
		
		if(e.getSource()==ok){
			try {
				if(uPass.getText().equals(confirmPass.getText())){
					query_generated="CREATE USER "+uNameTf.getText()+" IDENTIFIED BY "+uPass.getText();	// create user scott1 identified by tiger1
					query_area.setText(query_generated);
					s.executeUpdate(query_generated);
					query_area.append("\nUSER CREATED!!");
					if(grantPrivileges.isSelected()){
						String grantQuery="GRANT ALL PRIVILEGES TO "+uNameTf.getText();
						query_area.append("\n"+grantQuery);
						s.executeUpdate(grantQuery);		// GRANT  all privileges to scott
						query_area.append("\nALL PRIVILLEGES GRANTED!!");
					}
				}
				else{
					query_area.setText("Password does not match !!");
				}
			} catch (SQLException e1) {
				e1.printStackTrace(); }
			userFrame.setVisible(false);
		}
	}
}
/*
SELECT * FROM USER_SYS_PRIVS; 
SELECT * FROM USER_TAB_PRIVS;
SELECT * FROM USER_ROLE_PRIVS;
 */


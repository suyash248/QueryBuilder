package demo.sphinx.helloworld;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ChangePass extends Builder {
	JFrame changePassFrame;
	JTextField uNameTf;
	JPasswordField uPass, confirmPass;
	JButton change;
	
	JPanel pan1, pan2;
	
	public void passMenuItem(){
		changePassFrame=new JFrame("Change Password");
		uNameTf=new JTextField(8);
		uPass=new JPasswordField(8);
		confirmPass=new JPasswordField(8);
		
		Icon icon=new ImageIcon(".\\Icon\\key16.png");
		change=new JButton("Change", icon);
		change.addActionListener(this);
		
		pan1=new JPanel();
		pan1.setLayout(new GridLayout(3,3));
		pan1.add(new JLabel("Username"));
		pan1.add(uNameTf);
		
		pan1.add(new JLabel("New Password"));
		pan1.add(uPass);
		
		pan1.add(new JLabel("Confirm Password"));
		pan1.add(confirmPass);
		
		pan2=new JPanel();
		pan2.add(change);
		
		changePassFrame.setLayout(new BorderLayout());
		changePassFrame.add(pan1, BorderLayout.NORTH);
		changePassFrame.add(pan2, BorderLayout.CENTER);
		
		image_icon=new ImageIcon(".\\Icon\\password.png");
		image=image_icon.getImage();
		changePassFrame.setIconImage(image);
		
		changePassFrame.pack();
		changePassFrame.setVisible(true);
	}
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==passMenuItem){
			passMenuItem();
		}

		if(e.getSource()==change){
			changePassFrame.setVisible(false);
			try{
				if(uPass.getText().equals(confirmPass.getText())){
					query_generated="ALTER USER "+uNameTf.getText()+" IDENTIFIED BY "+uPass.getText();
					query_area.setText(query_generated);
					s.executeUpdate(query_generated);
					query_area.append("\nPassword changed successfully for "+"'"+uNameTf.getText()+"'");
				}
				else{
					query_area.setText("Password doesn not match!!");
				}
			}catch(Exception exc){
				Error.error(changePassFrame, exc);
			}
		}
	}
}

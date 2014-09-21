package demo.sphinx.helloworld;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



import com.petersoft.advancedswing.onoffbutton.OnOffButton;

public class LockUnlockUserAlt extends Builder {
	JFrame lockFrame;
	JPanel pan1;
	static JPanel pan2;
	
	JTextField userTf;
	JButton selectUser;
	
	JRadioButton lockTable, lockUser;
	ButtonGroup bg=new ButtonGroup();

	static JLabel status, splashImage;
	String username, accountStatus, statusString;
	
	ResultSet rs;
	int count;
	
	OnOffButton onOffButton;
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==lock){
			
			lockFrame=new JFrame("Lock/Unlock User");
			pan1=new JPanel();
			userTf=new JTextField(10);
			Icon icon=new ImageIcon(".\\Icon\\Keys-icon (2).png");
			selectUser=new JButton("OK", icon);
			selectUser.addActionListener(this);
			
			pan1.add(new JLabel("Username"));
			pan1.add(userTf);
			pan1.add(selectUser);
			
			lockFrame.setLayout(new BorderLayout());
			lockFrame.add(pan1, BorderLayout.NORTH);
			
			image_icon=new ImageIcon(".\\Icon\\lock.png");
			image=image_icon.getImage();
			lockFrame.setIconImage(image);
			
			lockFrame.pack();
			lockFrame.setVisible(true);
		}
		
		if(e.getSource()==selectUser){
			if(pan2!=null){
				pan2.removeAll();
			}
			username=userTf.getText().toUpperCase().trim();
			if(username.equals("SYSTEM")){
				JOptionPane opane=new JOptionPane();
				JOptionPane.showMessageDialog(lockFrame,"SYSTEM is default user. Perform this action with caution.","WARNING !!", JOptionPane.WARNING_MESSAGE, new ImageIcon(".\\Icon\\warning.png"));
				//opane.setIcon(new ImageIcon(".\\Icon\\query.png"));
				opane.setVisible(true);
			}
			try {
				
				// To check if user exist or not
				query_generated="select count(*) from dba_users where username='"+username+"'";
				query_area.setText(query_generated);
				rs=s.executeQuery(query_generated);
				if(rs.next())
					count=rs.getInt(1);
				
				// If user exist then
				if(count==1){
					query_generated="select account_status from dba_users where account_status like '%LOCK%' AND username='"+username+"'";
					query_area.append("\n"+query_generated);
					rs=s.executeQuery(query_generated);
					if(rs.next()){
						accountStatus=rs.getString("ACCOUNT_STATUS");
					}
					else
						accountStatus="UNLOCKED";
					
					rs=null;
					
					lockUnlock(accountStatus);
				}
				// If user does not exist then
				else
					userTf.setText("USER DOES NOT EXIST");
				
			} catch (SQLException e1) {
				Error.error(lockFrame, e1);
				e1.printStackTrace();
			}
			
		}
		
	}
	void lockUnlock(String accountStatus){
		pan2=new JPanel();
		
		if(accountStatus.equals("LOCKED")){
			statusString=".\\Icon\\red.png";
		}
		else{
			statusString=".\\Icon\\green.png";
		}
		
		splashImage = new JLabel(new ImageIcon(statusString));
		status=new JLabel(" Status : "+accountStatus);
		onOffButton = new OnOffButton();
		
		onOffButton.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent evt) {
	                onOffButtonItemStateChanged(evt);
	        }
	});
		pan2.add(splashImage);
		pan2.add(status);
		pan2.add(onOffButton);
		
		//lockFrame.remove(pan1);
		lockFrame.add(pan2, BorderLayout.CENTER);
		
		Resize.resize(pan2);
		Resize.resize(lockFrame);
	}
	
	private void onOffButtonItemStateChanged(ItemEvent evt) {
        int state = evt.getStateChange();
        if(accountStatus.equals("LOCKED")){
        	state=1;
        }
        else{
        	state=2;
        }
        	
	    if (state == ItemEvent.SELECTED) {		// if LOCKED then here
	            System.out.println("true-locked and now unlocking.....");
	            query_generated="alter user "+username+" account unlock";
	            query_area.append("\n"+query_generated);
	            accountStatus="UNLOCKED";
	            statusString=".\\Icon\\green.png";
	    }
        
        else {		// if UNLOCKED then here
                System.out.println("false-unlocked and now locking.....");
                query_generated="alter user "+username+" account lock";
                query_area.append("\n"+query_generated);
                accountStatus="LOCKED";
                statusString=".\\Icon\\red.png";
        }
	    
	    try{
	    	s.executeUpdate(query_generated);
	    	query_area.append("\n"+accountStatus+"!!");
	    	status.setText("STATUS : "+accountStatus);
	    	splashImage.setIcon(new ImageIcon(statusString));
	    }catch(Exception exc){
	    	Error.error(lockFrame, exc);
	    }
	    Resize.resize(pan2);
	}
}

//lock user - alter user scott account lock;
//unlock user - alter user scott account unlock;

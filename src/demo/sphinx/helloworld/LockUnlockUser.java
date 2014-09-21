package demo.sphinx.helloworld;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.petersoft.advancedswing.onoffbutton.OnOffButton;

public class LockUnlockUser extends Builder {
	JFrame lockFrame;
	JPanel pan1;
	static JPanel pan2;
	
	JComboBox<String> usersCombo=new JComboBox<String>();
	
	static JLabel status, splashImage;
	String username, accountStatus, statusString;
	
	ResultSet rs;
	int count;
	
	OnOffButton onOffButton;
	
	public void lock(){
		if(usersCombo.getItemCount()>0){
			usersCombo.removeItemListener(this);
			usersCombo.removeAllItems();
		}
		
		lockFrame=new JFrame("Lock/Unlock User");
		pan1=new JPanel();
		usersCombo.addItemListener(this);
		usersCombo.addItem("--select user--");
		try{
			rs=dbmd.getSchemas();// list of all users
			while(rs.next()){
				usersCombo.addItem(rs.getString(1));
			}
			usersCombo.setMaximumRowCount(5);
			usersCombo.setSelectedIndex(0);
		}catch(Exception ex){
			ex.printStackTrace();
			Error.error(lockFrame, ex);
		}
		
		pan1.add(new JLabel("Username"));
		pan1.add(usersCombo);
		
		lockFrame.setLayout(new BorderLayout());
		lockFrame.add(pan1, BorderLayout.NORTH);
		
		image_icon=new ImageIcon(".\\Icon\\lock.png");
		image=image_icon.getImage();
		lockFrame.setIconImage(image);
		
		lockFrame.pack();
		lockFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==lock){
			lock();
		}
	}
	
	public void itemStateChanged(ItemEvent e){
		
		if(e.getSource()==usersCombo){
			
			if(pan2!=null){
				pan2.removeAll();
				Resize.resize(pan2);
				lockFrame.pack();
			}
			
			username=(String) usersCombo.getSelectedItem();
			if(!username.equals("--select user--")){
				if(username.equals("SYSTEM")){
					JOptionPane opane=new JOptionPane();
					JOptionPane.showMessageDialog(lockFrame,"SYSTEM is default user. Perform this action with caution.","WARNING !!", JOptionPane.WARNING_MESSAGE, new ImageIcon(".\\Icon\\warning.png"));
					opane.setVisible(true);
				}
				try {
					
						// Fetching current account status
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
						Resize.resize(pan2);
						
				} catch (SQLException e1) {
					Error.error(lockFrame, e1);
					e1.printStackTrace();
				}
			}else{
				if(pan2!=null){
					pan2.removeAll();
					Resize.resize(pan2);
					lockFrame.pack();
				}
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
	            query_generated="alter user "+username+" account unlock";
	            query_area.append("\n"+query_generated);
	            accountStatus="UNLOCKED";
	            statusString=".\\Icon\\green.png";
	    }
        
        else {		// if UNLOCKED then here
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

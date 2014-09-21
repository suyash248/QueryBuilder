package demo.sphinx.helloworld;

import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Exit extends Builder{
	public void exit(){
		int reply = JOptionPane.showConfirmDialog(null, "Are you sure?", "Exit",  JOptionPane.YES_NO_OPTION);  
		if (reply == JOptionPane.YES_OPTION) {  
		  try {
			c.close();
		} catch (SQLException e) {
			Error.error(frame1, e);
			e.printStackTrace();
		}
	      System.exit(0);
		} else {  
		}  
	}
}
// showConfirmDialog(null, "Are you sure?", "Exit",  JOptionPane.YES_NO_OPTION, icon);  
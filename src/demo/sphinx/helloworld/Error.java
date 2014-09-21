package demo.sphinx.helloworld;
import javax.swing.*;
public class Error{
	public static void error(JFrame f,Exception se1){
		JOptionPane opane=new JOptionPane();
		JOptionPane.showMessageDialog(f,"Access Denied : "+se1.getMessage(),"ERROR !!",JOptionPane.ERROR_MESSAGE, new ImageIcon(".\\Icon\\error64.png"));
		opane.setVisible(true);
	}
}
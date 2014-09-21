package demo.sphinx.helloworld;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
class About extends Builder{
	JFrame jf;
	
	public void about(){
		jf=new JFrame("About Query Builder");	
		
		jf.setLayout(new BorderLayout());
        JLabel splashImage = new JLabel(new ImageIcon(".\\Icon\\abotQB.png"));
        jf.add(splashImage, BorderLayout.CENTER);

		jf.pack();
		//jf.setSize(400,60);
		ImageIcon image_icon=new ImageIcon(".\\Icon\\db3.jpg");
		Image image=image_icon.getImage();
		jf.setIconImage(image);
		jf.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==aboutMenuItem){
			about();
		}
	}
}

// http://dwhlaureate.blogspot.in/2012/08/joins-in-oracle.html
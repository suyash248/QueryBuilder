package demo.sphinx.helloworld;
import javax.swing.*;

import java.awt.*;

public class Index extends Thread{
	JProgressBar js;
	JFrame jf;
	static{
		try{
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
 		}catch(Exception qe){} 
	}
	Index(){
		jf=new JFrame("Query Builder");
		jf.setLayout(new BorderLayout());
        JLabel splashImage = new JLabel(new ImageIcon(".\\Icon\\qbframe.png"));
        jf.add(splashImage, BorderLayout.CENTER);

		js=new JProgressBar();
		js.setMinimum(1);
		js.setMaximum(100);
		js.setStringPainted(true);
		jf.add(js, BorderLayout.SOUTH);
		jf.pack();
		//jf.setSize(400,60);
		ImageIcon image_icon=new ImageIcon(".\\Icon\\db3.jpg");
		Image image=image_icon.getImage();
		jf.setIconImage(image);
		jf.setVisible(true);
	}
	
	public void run(){
		for(int i=1;i<=100;i++){
			js.setValue(i);
			try{
			Thread.sleep(50);
			}catch(Exception e){System.out.println(e);}
			String str=Integer.toString(i);
			js.setString(str+"%");
		}
		jf.setVisible(false);
		Main.active();
	}
	public static void main(String... w){
		new Index().start();
	}
}

// http://dwhlaureate.blogspot.in/2012/08/joins-in-oracle.html
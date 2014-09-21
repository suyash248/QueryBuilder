package demo.sphinx.helloworld;
import java.awt.*;
import javax.swing.*;
public class Resize{
	public static void resize(Component c){
		c.validate();
		c.repaint();
		if(c instanceof JFrame){
			JFrame f=(JFrame)c;
			f.pack();
		}
	}
}
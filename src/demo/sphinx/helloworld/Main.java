package demo.sphinx.helloworld;
import javax.swing.*;
public class Main extends Builder{
	static Authenticate auth;
	public static void active(){
		try
  		{
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
 		}catch(Exception qe){}
		auth=new Authenticate("Authenticate");
	}
}
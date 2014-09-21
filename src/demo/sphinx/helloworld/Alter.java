package demo.sphinx.helloworld;
import java.awt.event.*;

import javax.swing.*;
public class Alter extends Builder{
	
	JFrame frame;
	JPanel pan;
	private static Alter alterObj;
	
	
	JRadioButton alterColumns, alterConstraints;
	ButtonGroup bg=new ButtonGroup();
	
	private Alter(){}
	
	public static Alter getAlterObj(){
		if(alterObj==null){
			alterObj=new Alter();
		}
		return alterObj;
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==alter){
			alter();
		}
	}
	
	public void alter(){
		frame=new JFrame("Alter");
		pan=new JPanel();
		alterColumns=new JRadioButton("Alter Columns");
		alterConstraints=new JRadioButton("Alter Constraints");
		bg.add(alterColumns);
		bg.add(alterConstraints);
		
		alterColumns.addActionListener(new AlterColumns(alterColumns));
		alterConstraints.addActionListener(new AlterConstraints(alterConstraints));
		
		pan.add(alterColumns);
		pan.add(alterConstraints);
		
		frame.add(pan);
		
		image_icon=new ImageIcon(".\\Icon\\consFrame32.png");
		image=image_icon.getImage();
		frame.setIconImage(image);
		frame.pack();
		frame.setVisible(true);
	}
}
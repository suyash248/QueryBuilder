package demo.sphinx.helloworld;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SelectJoinTables extends Builder {
	JFrame tabsFrame;
	JTextField tabTf1, tabTf2;
	JComboBox<String> joinTypeCombo=new JComboBox<String>(new String[] {"--select type--", "EQUI", "NATURAL" ,"CROSS", "LEFT OUTER", "RIGHT OUTER", "FULL OUTER", "INNER"});
	JButton ok;
	JPanel pan1, pan2;
	String firstTable, secondTable, joinType;
	
	public void join(){
		tabsFrame=new JFrame("Select Tables");
		tabTf1=new JTextField(8);
		tabTf2=new JTextField(8);
		
		Icon icon=new ImageIcon(".\\Icon\\link161.png");
		ok=new JButton("OK", icon);
		ok.addActionListener(this);
		joinTypeCombo.addItemListener(this);
		
		pan1=new JPanel();
		pan1.setLayout(new GridLayout(3,2));
		
		pan1.add(new JLabel("Table-1"));
		pan1.add(tabTf1);
		pan1.add(new JLabel("Table-2"));
		pan1.add(tabTf2);
		
		pan1.add(new JLabel("Join Type"));
		pan1.add(joinTypeCombo);
		
		pan2=new JPanel();
		pan2.add(ok);
		
		tabsFrame.setLayout(new BorderLayout());
		tabsFrame.add(pan1, BorderLayout.NORTH);
		tabsFrame.add(pan2, BorderLayout.CENTER);
		
		image_icon=new ImageIcon(".\\Icon\\link241.png");
		image=image_icon.getImage();
		tabsFrame.setIconImage(image);
		
		tabsFrame.pack();
		tabsFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==join){
			join();
		}
		if(e.getSource()==ok){
			tabsFrame.setVisible(false);
			firstTable=tabTf1.getText().toUpperCase();
			secondTable=tabTf2.getText().toUpperCase();
			new Join(firstTable, secondTable, joinType);
		}
	}
	public void itemStateChanged(ItemEvent e){
		if(e.getSource()==joinTypeCombo){
			joinType=(String)joinTypeCombo.getSelectedItem();
		}
	}
}

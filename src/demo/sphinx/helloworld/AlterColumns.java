package demo.sphinx.helloworld;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class AlterColumns extends Builder {
	
	static String option,field_chosen;
	JTextField columnname,datatype;
	JRadioButton alterColumns;
	
	public AlterColumns(JRadioButton alterColumns) {
		this.alterColumns=alterColumns;
	}
	
	@SuppressWarnings("unchecked")
	public void alterColumns(){
		Alter.getAlterObj().frame.setVisible(false);
		select.setEnabled(false);	
		update.setEnabled(false);
		delete.setEnabled(false);
		fields.removeAllItems();
		fields.addItemListener(this);
		fields.addItem("--select field--");
		for(int t=1;t<=columncount;t++){
			fields.addItem(heading[t-1]);
		}
		
		query_generated="alter table "+table_name;
		query.setEnabled(true);
		select.setEnabled(false);
		fstr="alter";

		frame11=new JFrame("Alter Table");
		image_icon=new ImageIcon(".\\Icon\\alter1.png");
		image=image_icon.getImage();
		frame11.setIconImage(image);

		alter_add=new JRadioButton("ADD COLUMN");
		alter_drop=new JRadioButton("DROP COLUMN");
		alter_alter=new JRadioButton("ALTER COLUMN");
		bg3.add(alter_add);				
		bg3.add(alter_drop);
		bg3.add(alter_alter);

		alter_add.addActionListener(this);
		alter_drop.addActionListener(this);
		alter_alter.addActionListener(this);

		panel24=new JPanel();
		panel24.add(alter_add);
		panel24.add(alter_drop);
		panel24.add(alter_alter);

		
		Icon icon=new ImageIcon(".\\Icon\\alter2.png");
		alter_button=new JButton("Alter",icon);
		alter_button.addActionListener(this);

		panel26=new JPanel();
		panel26.add(alter_button);

		frame11.add(panel24,BorderLayout.NORTH);
		frame11.add(panel26,BorderLayout.SOUTH);
		frame11.pack();
		frame11.setVisible(true);
	
		go.setEnabled(false);

		panel25=new JPanel();
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==alterColumns){
			alterColumns();
		}
		
		if(e.getSource()==alter_button){
			if(option.equals("add")){
				query_generated=query_generated+" ADD "+columnname.getText()+" "+datatype.getText();
			}
			if(option.equals("drop")){
				query_generated=query_generated+" DROP COLUMN "+field_chosen;
			}
			if(option.equals("alter")){
				query_generated=query_generated+" MODIFY ("+field_chosen+" "+datatype.getText()+")";
			}
			frame11.setVisible(false);
			query.setEnabled(false);
			go.setEnabled(true);
			query_area.setText(query_generated);
		}
		if(e.getSource()==alter_add){
			if(alter_add.isSelected()){
				option="add";
				panel25.add(new JLabel("New column"));
				columnname=new JTextField(7);
				panel25.add(columnname);

				panel25.add(new JLabel("Datatype"));
				datatype=new JTextField(7);
				panel25.add(datatype);

				frame11.remove(panel24);
				frame11.add(panel25,BorderLayout.CENTER);
				Resize.resize(frame11);
			}
			
		}
		if(e.getSource()==alter_drop){
			if(alter_drop.isSelected()){
				option="drop";
				panel25.add(new JLabel("Select column to be deleted"));
				panel25.add(fields);

				frame11.remove(panel24);
				frame11.add(panel25,BorderLayout.CENTER);
				Resize.resize(frame11);
			}
		}
		if(e.getSource()==alter_alter){
			if(alter_alter.isSelected()){
				option="alter";
				panel25.add(new JLabel("Select column to be altered"));
				panel25.add(fields);

				panel25.add(new JLabel("New datatype"));
				datatype=new JTextField(7);
				panel25.add(datatype);
			
				frame11.remove(panel24);
				frame11.add(panel25,BorderLayout.CENTER);
				Resize.resize(frame11);
			}
		}	
	}
	public void itemStateChanged(ItemEvent e){
		if(e.getSource()==fields){
			field_chosen=(String)fields.getSelectedItem();
		}
	}	
}

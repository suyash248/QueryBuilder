package demo.sphinx.helloworld;
import java.awt.event.*;

public class Delete extends Builder{
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==delete){
			query_generated="delete from "+table_name;
			query.setEnabled(true);
			value.setEnabled(true);
			insert.setEnabled(true);
			operator_combo.setEnabled(true);
			select.setEnabled(false);
			update.setEnabled(false);
			alter.setEnabled(false);
			fstr="delete";
		}	
	}
}

















	
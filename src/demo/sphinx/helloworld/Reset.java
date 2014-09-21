package demo.sphinx.helloworld;
import java.awt.event.*;

class Reset extends Builder{
	public void reset(){
		frame1.setVisible(false);
		where_flag=0;
		go_colcount=0;
		table_name=null;
		flag_and=0;
		flag_or=0;
		query_generated=null;
		otherQuery.setEnabled(true);
		rs=null;
		rowcount1=0;
		rowcount=0;
		Authenticate.builder=new Builder("Query Builder");
	}
	public void actionPerformed(ActionEvent e){
			reset();
	}
}
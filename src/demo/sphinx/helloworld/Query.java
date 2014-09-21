package demo.sphinx.helloworld;
import java.awt.event.*;
import java.util.*;
public class Query extends Builder{
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==query){
			value_selected=value.getText();
			if( operator_selected.equals("BETWEEN") || operator_selected.equals("NOT BETWEEN") ){
				try {
					squote1="";
					squote2="";
					String temp1="";
					String temp=value.getText();
					StringTokenizer st=new StringTokenizer(temp,",;& ");
					st.hasMoreTokens();
					
					String quotes=" '"+st.nextToken()+"'";
					temp1=temp1+quotes+" and ";

					st.hasMoreTokens();
					temp1=temp1+"'"+st.nextToken()+"'";
					value_selected=temp1;
					
				}catch(Exception e955){}
			}
			else if( operator_selected.equals("IN") ){
				try { 
					squote1=" (";
					squote2=") ";
					String temp1="";
					String temp=value.getText();
					StringTokenizer st=new StringTokenizer(temp,",; ");
					while(st.hasMoreTokens()){
						String quotes="'"+st.nextToken()+"'";
						temp1=temp1+quotes+",";
					}
					value_selected=temp1.substring(0,temp1.length()-1);
				}catch(Exception e959){}	
			}
			
			else{
				squote1=" '";
				squote2="'";
			}
			try{
				if(where_flag!=0){
					if(flag_and==0&&flag_or==0)     //  " '"+value_selected+"'"
						query_generated=query_generated+field_selected+" "+operator_selected+squote1+value_selected+squote2;
					if(flag_and!=0)
						query_generated=query_generated+" AND "+field_selected+" "+operator_selected+squote1+value_selected+squote2;
					if(flag_or!=0)
						query_generated=query_generated+" OR "+field_selected+" "+operator_selected+squote1+value_selected+squote2;
				}
				
				query_area.setText(query_generated);
				
			}catch(Exception pe){
					System.out.println(pe);
					value_selected="";
					Error.error(frame1,pe);
					}
			flag_and=0;
			flag_or=0;
			go.setEnabled(true);
			and.setEnabled(true);
			or.setEnabled(true);
		}
	}
}
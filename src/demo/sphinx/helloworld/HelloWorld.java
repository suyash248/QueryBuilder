package demo.sphinx.helloworld;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.*;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.UIManager;

public class HelloWorld extends Builder{

	public static HelloWorld helloWrold=new HelloWorld();
	static String resultText;
	static Recognizer recognizer;
	static Microphone microphone;
	
	JFrame frame;
	JPanel pan1, pan2;
	JLabel youSaid=new JLabel();
	
    public static void main(String[] args) {
    	new Index().start();
		Builder.status="started";
		helloWrold.runHello();
    }
        
    public void guide(){
    	try{
			UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
 		}catch(Exception qe){}
    	frame=new JFrame("Speech commands");
    	String headings[]=new String[] {"COMMAND","DESCRIPTION"};
    	String entries[][]=new String[29][2];
    	
    		entries[0][0]="Stop query"; entries[0][1]="To close application";
    		entries[1][0]="Login"; entries[1][1]="To authenticate";
    		entries[2][0]="Pick table"; entries[2][1]="To choose table";
    		entries[3][0]="Select query"; entries[3][1]="To fire select query";
    		entries[4][0]="Update query"; entries[4][1]="To fire update query";
    		entries[5][0]="Native query"; entries[5][1]="To fire native SQL query";
    		entries[6][0]="Modify table"; entries[6][1]="To alter table";
    		entries[7][0]="Modify columns"; entries[7][1]="To alter table columns";
    		entries[8][0]="Modify constraints"; entries[8][1]="To alter table level constraints";
    		entries[9][0]="Modify password"; entries[9][1]="To change password";
    		entries[10][0]="Details"; entries[10][1]="To view table details";
    		entries[11][0]="Lock table"; entries[11][1]="To lock table";
    		entries[12][0]="Lock user"; entries[12][1]="To lock/unlock user";
    		entries[13][0]="Create table"; entries[13][1]="To create a new table";
    		entries[14][0]="Create user"; entries[14][1]="To create a new user";
    		entries[15][0]="Create sequence"; entries[15][1]="To create a new sequence";
    		entries[16][0]="Create index"; entries[16][1]="To create a new index";
    		entries[17][0]="Drop table"; entries[17][1]="To drop table";
    		entries[18][0]="Drop sequence"; entries[18][1]="To drop sequence";
    		entries[19][0]="Drop user"; entries[19][1]="To drop user";
    		entries[20][0]="Drop index"; entries[20][1]="To drop index";
    		entries[21][0]="Join"; entries[21][1]="To join tables";
    		entries[22][0]="Rename"; entries[22][1]="To rename the table/sequence";
    		entries[23][0]="Reset"; entries[23][1]="To reset the application";
    		entries[24][0]="Menu"; entries[24][1]="To pop-up setting menu";
    		entries[25][0]="Insert record"; entries[25][1]="To fire insert query";
    		entries[26][0]="Add record"; entries[26][1]="To add a record";
    		entries[27][0]="About"; entries[27][1]="To know about application";
    		entries[28][0]="Contact"; entries[28][1]="To contact us";
    	
		JTable jt=new JTable(entries,headings);
		JScrollPane jp=new JScrollPane(jt);
		
    	pan1=new JPanel();
    	pan1.add(jp);
    	
    	pan2=new JPanel();
    	frame.setLayout(new BorderLayout());
    	frame.add(pan1, BorderLayout.NORTH);
    	frame.add(new JSeparator(JSeparator.HORIZONTAL));
    	frame.add(pan2, BorderLayout.SOUTH);
    	
		ImageIcon image_icon=new ImageIcon(".\\Icon\\mic32.png");
		Image image=image_icon.getImage();
		frame.setIconImage(image);
    	frame.pack();
    	frame.setVisible(true);
    	Voice.getVoice().say(new String[] {"Start Speaking"} );
    }

	public void runHello(){
    	try {
            URL url;

             url = HelloWorld.class.getResource("helloworld.config.xml");
             ConfigurationManager cm = new ConfigurationManager(url);

 		    recognizer = (Recognizer) cm.lookup("recognizer");
 		    microphone = (Microphone) cm.lookup("microphone");

             /* allocate the resource necessary for the recognizer */
 		  
 			  recognizer.allocate();
 		
             /* the microphone will keep recording until the program exits */
 			 performActions();

         } catch (IOException e) {
             e.printStackTrace();
         } catch (PropertyException e) {
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
	public void performActions(){
		if (microphone.startRecording()){	
    	 		guide();
	      
	            while (true) {
			                    /*
			                     * This method will return when the end of speech
			                     * is reached. Note that the endpointer will determine
			                     * the end of speech.
			                     */ 
					    Result result = recognizer.recognize();
					    if (result != null){
									resultText = result.getBestFinalResultNoFiller();
									//System.out.println("You said: " + resultText + "\n");
						
									youSaid.setText("You said: " + resultText);
									
									pan2.add(youSaid);
									Resize.resize(pan2);
									Resize.resize(frame);
									
									 if (resultText.equalsIgnoreCase("stop query")){
								        try{
								        	new Exit().exit();
								        }catch(Exception ae){}
								     }
									
									else if (resultText.equalsIgnoreCase("log in")){
								        try{
								        	if(Builder.status.equals("started") && !(Builder.authenticate.equals("logged in")) ){
 								        	Main.auth.setUp();
 								        	Builder.authenticate="logged in";
								        	}
								        }catch(Exception ae){}
								     }
									
									if(Builder.authenticate.equals("logged in")){
									// For inserting
	 									if (resultText.equalsIgnoreCase("insert record") && !(inserted.equals("inserted"))){
	 								        try{
	 								        	new Insert().insert();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("add record") && inserted.equals("inserted")){
	 								        try{
	 								        	new Insert().add();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("drop table")){
	 								        try{
	 								        	Drop.dropObj=new Drop();
	 								        	Drop.dropObj.drop();
	 								        	Drop.dropObj.dropTable();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("drop sequence")){
	 								        try{
	 								        	Drop.dropObj=new Drop();
	 								        	Drop.dropObj.drop();
	 								        	Drop.dropObj.dropSequence();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("drop user")){
	 								        try{
	 								        	Drop.dropObj=new Drop();
	 								        	Drop.dropObj.drop();
	 								        	Drop.dropObj.dropUser();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("drop index")){
	 								        try{
	 								        	Drop.dropObj=new Drop();
	 								        	Drop.dropObj.drop();
	 								        	Drop.dropObj.dropIndex();
	 								        }catch(Exception ae){}
	 								     }
	 									
	 									else if (resultText.equalsIgnoreCase("create table")){
	 								        try{
	 								        	Authenticate.builder.create();
	 								        	createTableObj.createTable();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("create sequence")){
	 								        try{
	 								        	Authenticate.builder.create();
	 								        	createSequenceObj.createSequence();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("create user")){
	 								        try{
	 								        	Authenticate.builder.create();
	 								        	createUserObj.createUser();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("create index")){
	 								        try{
	 								        	Authenticate.builder.create();
	 								        	createIndexObj.createIndex();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("join")){
	 								        try{
	 								        	new SelectJoinTables().join();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("lock user")){
	 								        try{
	 								        	new LockUnlockUser().lock();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("native query")){
	 								        try{
	 								        	new OtherQuery().nativeSQL();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("Rename")){
	 								        try{
	 								        	new Rename().rename();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("Reset")){
	 								        try{
	 								        	new Reset().reset();
	 								        }catch(Exception ae){}
	 								     }
	 									
	 									else if (resultText.equalsIgnoreCase("menu")){
	 								        try{
	 								        	Authenticate.builder.menu.doClick();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("modify Password")){
	 								        try{
	 								        	new ChangePass().passMenuItem();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("About")){
	 								        try{
	 								        	new About().about();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("details")){
	 								        try{
	 								        	new TableDetails().tableDetailsMenuItem();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("lock table")){
	 								        try{
	 								        	new LockTable().lockTableMenuItem();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("pick table")){
	 								        try{
	 								        	new SelectTable().choose();
	 								        }catch(Exception ae){}
	 								     }
	 									
	 									else if (resultText.equalsIgnoreCase("select query")){
	 								        try{
	 								        	new Select().select();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("update query")){
	 								        try{
	 								        	new Update().update();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("modify table")){
	 								        try{
	 								        	Alter.getAlterObj().alter();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("modify columns")){
	 								        try{
	 								        	new AlterColumns(Alter.getAlterObj().alterColumns).alterColumns();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("modify constraints")){
	 								        try{
	 								        	new AlterConstraints(Alter.getAlterObj().alterConstraints).alterConstraints();
	 								        }catch(Exception ae){}
	 								     }
	 									else if (resultText.equalsIgnoreCase("contact")){
	 								        try{
	 								        	new ContactUs().contactUs();
	 								        }catch(Exception ae){}
	 								     }
									}
					    }
			
				    	else{
							frame.setVisible(false);
							break;
					    }
	            }
			} 
			else{
			 
			}
	}
	public void speechControl(){
		
		System.out.println("speech menu");
		if(speechStatus.equals("enabled")){		// If enabled then disable it
			microphone.stopRecording();
			speechStatus="disabled";
			speechMenu.setIcon(new ImageIcon(".\\Icon\\speechOn.png"));
			
		}
		else if(speechStatus.equals("disabled")){	// If disabled then enable it
			try {
				Thread t=new Thread(new Runnable(){
						public void run(){
							performActions();
						}
				});
				t.start();
				speechMenu.setIcon(new ImageIcon(".\\Icon\\speechOff.png"));
				speechStatus="enabled";
			} catch (Exception e1) {
				e1.printStackTrace();
				Error.error(frame1, e1);
			}
			
		}
		Resize.resize(intermediatePan1);
		Voice.getVoice().say(new String[] {"microphone " + speechStatus} );
	}
}

//public void guide(){
//	try
//		{
//		UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
//		}catch(Exception qe){}
//	frame=new JFrame("Speech commands");
//	JLabel splashImage = new JLabel(new ImageIcon(".\\Icon\\guide.png"));
//	pan1=new JPanel();
//	JScrollPane jsp=new JScrollPane(splashImage);
//	pan1.add(jsp);
//	
//	pan2=new JPanel();
//	frame.setLayout(new BorderLayout());
//	frame.add(pan1, BorderLayout.NORTH);
//	frame.add(new JSeparator(JSeparator.HORIZONTAL));
//	frame.add(pan2, BorderLayout.SOUTH);
//	
//	ImageIcon image_icon=new ImageIcon(".\\Icon\\mic32.png");
//	Image image=image_icon.getImage();
//	frame.setIconImage(image);
//	frame.pack();
//	frame.setVisible(true);
//	Voice.getVoice().say(new String[] {"Start Speaking"} );
//}
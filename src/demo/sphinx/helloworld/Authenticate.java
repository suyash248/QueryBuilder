package demo.sphinx.helloworld;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.*;
class Authenticate extends Builder implements ActionListener,ItemListener, KeyListener{
	JPanel db_panel2,db_panel3;
	JTextField db_ip;
	static Builder builder;
	Authenticate(){}
	Authenticate(String title){
		frame0=new JFrame(title);	
		frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		image_icon=new ImageIcon(".\\Icon\\auth.jpg");
		image=image_icon.getImage();
		frame0.setIconImage(image);

		JLabel db_ipLabel=new JLabel("IP");
		JLabel db_portLabel=new JLabel("Port");
		JLabel db_DBnameLabel=new JLabel("Database name");
		JLabel db_usernameLabel=new JLabel("Username");
		JLabel db_passLabel=new JLabel("Password");

		db_ip=new JTextField("localhost");
		db_port=new JTextField("1521");
		db_DBname=new JTextField("xe");
		db_username=new JTextField("system");
		db_pass=new JPasswordField(12);
		
		db_pass.addKeyListener(this);

		driver="oracle.jdbc.driver.OracleDriver";
		
		db_panel2=new JPanel();
		db_panel2.setLayout(new GridLayout(5,2));

		db_panel2.add(db_ipLabel);
		db_panel2.add(db_ip);

		db_panel2.add(db_portLabel);
		db_panel2.add(db_port);

		db_panel2.add(db_DBnameLabel);
		db_panel2.add(db_DBname);
		
		db_panel2.add(db_usernameLabel);
		db_panel2.add(db_username);

		db_panel2.add(db_passLabel);
		db_panel2.add(db_pass);

		db_panel3=new JPanel();

		Icon icon0=new ImageIcon(".\\Icon\\ok161.png");
		db_submit=new JButton("Submit",icon0);
		db_submit.setEnabled(false);

		db_panel3.add(db_submit);

		//frame0.add(db_panel1,BorderLayout.NORTH);

		frame0.add(db_panel2,BorderLayout.CENTER);
		frame0.add(db_panel3,BorderLayout.SOUTH);
		db_submit.setEnabled(true);

		db_submit.addActionListener(this);

		frame0.pack();
		frame0.setVisible(true);
	}
	@SuppressWarnings("deprecation")
	public void setUp(){
		url="jdbc:oracle:thin:@"+db_ip.getText()+":"+db_port.getText()+":"+db_DBname.getText();		//"jdbc:oracle:thin:@localhost:1521:xe";
		username=db_username.getText();
		pass=db_pass.getText();
		db_flag=0;
		Builder.status="started";
		try{	
			Class.forName(driver);  //oracle.jdbc.driver.OracleDriver
			c=DriverManager.getConnection(url,username,pass);  //"jdbc:oracle:thin:@localhost:1521:xe","system","oracle"
			dbmd=c.getMetaData();
			s=c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
		}catch(Exception se1){
					db_flag=1;
					Error.error(frame0,se1);	
					username="";
					pass="";
				}
		if(db_flag==0){
			frame0.setVisible(false);
			builder=new Builder("QUERY BUILDER - "+db_username.getText());
		}
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==db_submit){
			setUp();
			Builder.authenticate="logged in";
		}
	}
	
	public void keyReleased(KeyEvent e){	
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			setUp();
		}
	}
}

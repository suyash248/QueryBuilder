package demo.sphinx.helloworld;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactUs extends Builder {
	JFrame loginFrame, sendFrame;
	JTextField emailFrom, subject;
	JPasswordField password;
	JTextArea body;
	JButton login, send;
	
	JPanel pan1, pan2;
	
	Session session;
	
	public void contactUs(){
		loginFrame=new JFrame("Gmail Login");
		emailFrom=new JTextField(16);
		password=new JPasswordField(16);	
		
		Icon icon=new ImageIcon(".\\Icon\\login16.png");
		login=new JButton("Login", icon);
		login.addKeyListener(this);
		login.addActionListener(this);
		
		pan1=new JPanel();
		pan1.setLayout(new GridLayout(2,2));
		
		pan1.add(new JLabel("Your Email"));
		pan1.add(emailFrom);
		
		pan1.add(new JLabel("Your Password"));
		pan1.add(password);
		
		pan2=new JPanel();
		pan2.add(login);
		
		loginFrame.setLayout(new BorderLayout() );
		loginFrame.add(pan1, BorderLayout.NORTH);
		loginFrame.add(pan2, BorderLayout.CENTER);
		
		image_icon=new ImageIcon(".\\Icon\\loginSend24.png");
		image=image_icon.getImage();
		loginFrame.setIconImage(image);
		
		loginFrame.pack();
		loginFrame.setVisible(true);
		
	}
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==contactUsMenyItem){
			contactUs();
		}
		
		if(e.getSource()==login){
			try{
				//authentication code goes here
				loginFrame.setVisible(false);
				if(emailFrom.getText().equals("") || password.getText().equals("")){
					query_area.setText("Username or password can not be blank");
				}
				else{
					login(emailFrom.getText(), password.getText());
				}

			}catch(Exception e1){
				e1.printStackTrace();
				Error.error(loginFrame, e1);
			}
		}
		
		if(e.getSource()==send){
			try{
				System.out.println("Send");
				send(session);
			}catch(Exception e2){
				e2.printStackTrace();
				Error.error(sendFrame, e2);
			}
		}
	}
	
	public void login(String username, String password){
		try{
				//String to="suyash.soni248@gmail.com";//change accordingly

				//Get the session object
				 Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");
				
				session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);//change accordingly, here we made an annonymous nested class.
					}
				});
			
		}catch(Exception e1){
			e1.printStackTrace();
			Error.error(loginFrame, e1);
		}
				// If authenticated then create send GUI
				sendFrame=new JFrame("Send");
				subject=new JTextField(16);
				body=new JTextArea(8, 16);
				
				Icon icon=new ImageIcon(".\\Icon\\send16.png");
				send=new JButton("Send", icon);
				send.addActionListener(this);
				
				JPanel pan1=new JPanel();
				pan1.add(new JLabel("Subject"));
				pan1.add(subject);
				
				JPanel pan2=new JPanel();
				pan2.add(new JLabel("Body"));
				pan2.add(body);			
				
				JPanel pan3=new JPanel();
				pan3.add(send);
				
				sendFrame.setLayout(new BorderLayout() );
				sendFrame.add(pan1, BorderLayout.NORTH);
				sendFrame.add(pan2, BorderLayout.CENTER);
				sendFrame.add(pan3, BorderLayout.SOUTH);
				
				image_icon=new ImageIcon(".\\Icon\\loginSend24.png");
				image=image_icon.getImage();
				sendFrame.setIconImage(image);
				
				sendFrame.pack();
				sendFrame.setVisible(true);
	}
	public void send(Session session){
		//compose message
		  try{
			  InternetAddress to[]={
						new InternetAddress("suyash.soni248@gmail.com"),
						new InternetAddress("suyash248@gmail.com")
					     };
			  
			   MimeMessage message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(emailFrom.getText()));//change accordingly
			   message.setRecipients(Message.RecipientType.TO,to);
			   message.setSubject(subject.getText());
			   message.setText(body.getText());
			   
			//send message
	
			   Transport.send(message);
			   sendFrame.setVisible(false);
			   query_area.setText("message sent successfully");
			   System.out.println("message sent successfully");
			   session=null;
		  }catch(Exception e1){
				e1.printStackTrace();
				Error.error(sendFrame, e1);
			}
	}
	
	@SuppressWarnings("deprecation")
	public void keyPressed(KeyEvent e){	
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			try{
				loginFrame.setVisible(false);
				if(emailFrom.getText().equals("") || password.getText().equals("")){
					query_area.setText("Username or password can not be blank");
				}
				else{
					login(emailFrom.getText(), password.getText());
				}
			}catch(Exception ex){
				ex.printStackTrace();
				Error.error(sendFrame, ex);
			}
		}
	}
	
}

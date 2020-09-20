package assignment;

/*************************************
 * Filename:  EmailClient.java
 * Date:
 *************************************/

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class EmailClient extends Frame {
	/* The stuff for the verify sender address*/
	private static String user_name;
	private static InetAddress IPaddress;
	
	/* The stuff for getting content type from URL. */
	private String content_type="text/plain";
	
	/* The stuff for the GUI. */
	private Button btSend = new Button("Send");
	private Button btClear = new Button("Clear");
	private Button btQuit = new Button("Quit");
	private Label serverLabel = new Label("Local SMTP server:");
	private TextField serverField = new TextField("student.csc.liv.ac.uk", 40);
	private Label serverPortLabel = new Label("Port number of SMTP server:");
	private TextField serverPortField = new TextField("1025", 30);
	private Label fromLabel = new Label("From:");
	private TextField fromField = new TextField(user_name+"@"+IPaddress, 40);	//set the default sender address to a verified sender address
	private Label toLabel = new Label("To:");
	private TextField toField = new TextField("", 40);
	private Label CcLabel = new Label("Cc:");				//add a new label named "Cc"
	private TextField CcField = new TextField("", 40);		//add a new new text field for "Cc"
	private Label subjectLabel = new Label("Subject:");
	private TextField subjectField = new TextField("", 40);
	private Label messageLabel = new Label("Message:");
	private TextArea messageText = new TextArea(30, 80);
	private Label urlLabel = new Label("HTTP://");
	private TextField urlField = new TextField("comp211.gairing.com/nolength.php", 40);
	private Button btGet = new Button("Get");
	
	/* set the text URL field to a new URL
	 * this method will be called in following redirections */
	public void SeturlField(String newurl) {
		urlField.setText(newurl);
	}

	/**
	 * Create a new EmailClient window with fields for entering all the relevant
	 * information (From, To, Subject, and message).
	 */
	public EmailClient() {
		super("Java Emailclient");

		/*
		 * Create panels for holding the fields. To make it look nice, create an extra
		 * panel for holding all the child panels.
		 */
		Panel serverPanel = new Panel(new BorderLayout());
		Panel serverPortPanel = new Panel(new BorderLayout());
		Panel fromPanel = new Panel(new BorderLayout());
		Panel toPanel = new Panel(new BorderLayout());
		Panel CcPanel= new Panel(new BorderLayout());
		Panel subjectPanel = new Panel(new BorderLayout());
		Panel messagePanel = new Panel(new BorderLayout());
		
		serverPanel.add(serverLabel, BorderLayout.WEST);
		serverPanel.add(serverField, BorderLayout.CENTER);
		serverPortPanel.add(serverPortLabel, BorderLayout.WEST);
		serverPortPanel.add(serverPortField, BorderLayout.CENTER);
		fromPanel.add(fromLabel, BorderLayout.WEST);
		fromPanel.add(fromField, BorderLayout.CENTER);
		toPanel.add(toLabel, BorderLayout.WEST);
		toPanel.add(toField, BorderLayout.CENTER);
		CcPanel.add(CcLabel,BorderLayout.WEST);				//add CcLabel to CcPanel 
		CcPanel.add(CcField, BorderLayout.CENTER);			//add CcField to CcPanel
		subjectPanel.add(subjectLabel, BorderLayout.WEST);
		subjectPanel.add(subjectField, BorderLayout.CENTER);
		messagePanel.add(messageLabel, BorderLayout.NORTH);
		messagePanel.add(messageText, BorderLayout.CENTER);
		Panel fieldPanel = new Panel(new GridLayout(0, 1));
		fieldPanel.add(serverPanel);
		fieldPanel.add(serverPortPanel);
		fieldPanel.add(fromPanel);
		fieldPanel.add(toPanel);
		fieldPanel.add(CcPanel);							//add Ccpanel to fieldPanel
		fieldPanel.add(subjectPanel);
		

		/*
		 * Create a panel for the URL field and add listener to the GET button.
		 */
		Panel urlPanel = new Panel(new BorderLayout());
		urlPanel.add(urlLabel, BorderLayout.WEST);
		urlPanel.add(urlField, BorderLayout.CENTER);
		urlPanel.add(btGet, BorderLayout.EAST);
		fieldPanel.add(urlPanel);
		btGet.addActionListener(new GetListener());

		/*
		 * Create a panel for the buttons and add listeners to the buttons.
		 */
		Panel buttonPanel = new Panel(new GridLayout(1, 0));
		btSend.addActionListener(new SendListener());
		btClear.addActionListener(new ClearListener());
		btQuit.addActionListener(new QuitListener());
		buttonPanel.add(btSend);
		buttonPanel.add(btClear);
		buttonPanel.add(btQuit);

		/* Add, pack, and show. */
		add(fieldPanel, BorderLayout.NORTH);
		add(messagePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}

	static public void main(String argv[]) throws Exception {
		
		/* Get the current computer's user name and IP address*/
		user_name=System.getProperty("user.name");
		IPaddress= InetAddress.getLocalHost();
		
		new EmailClient();
	}

	/* Handler for the Send-button. */
	class SendListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Sending mail");

			/* Check that we have the local mailserver */
			if ((serverField.getText()).equals("")) {
				System.out.println("Need name of SMTP server!");
				return;
			}

			/* Check that we have a port number */
			if ((serverPortField.getText()).equals("")) {
				System.out.println("Need Port number of  SMTP server!");
				return;
			}

			/* Check that we have the sender and recipient. */
			if ((fromField.getText()).equals("")) {
				System.out.println("Need sender!");
				return;
			}
			if ((toField.getText()).equals("")) {
				System.out.println("Need recipient!");
				return;
			}

			/* Create the message */
			EmailMessage mailMessage;
			try {
				mailMessage = new EmailMessage(fromField.getText(), 
												toField.getText(),
												subjectField.getText(),
												messageText.getText(),
												serverField.getText(),
												Integer.parseInt(serverPortField.getText()));
			} catch (UnknownHostException e) {
				/* If there is an error, do not go further */
				return;
			}

			/*
			 * Check that the message is valid, i.e., sender and recipient addresses look
			 * ok.
			 */
			if (!mailMessage.isValid()) {
				return;
			}

			try {
				SMTPConnect connection = new SMTPConnect(mailMessage);
				connection.send(mailMessage);
				connection.close();
			} catch (IOException error) {
				System.out.println("Sending failed: " + error);
				return;
			}
			System.out.println("Email sent succesfully!");
			
			/* If CcField is not empty, a new email should be sent to that address */
			if(CcField.getText().length()!=0) {
				/* Create the message for Cc */
				EmailMessage CcmailMessage;
				try {
					CcmailMessage = new EmailMessage(fromField.getText(), 
														CcField.getText(),
														subjectField.getText(),
														messageText.getText(),
														serverField.getText(), 
														Integer.parseInt(serverPortField.getText()));
				} catch (UnknownHostException e) {
					/* If there is an error, do not go further */
					return;
				}

				/*
				 * Check that the message is valid, i.e., sender and Cc addresses look
				 * ok.
				 */
				if (!CcmailMessage.isValid()) {
					return;
				}

				try {
					CcmailMessage.setContent_type(content_type);
					SMTPConnect connection = new SMTPConnect(CcmailMessage);
					connection.send(CcmailMessage);
					connection.close();
				} catch (IOException error) {
					System.out.println("Sending failed: " + error);
					return;
				}
				System.out.println("Cc Email sent succesfully!");
			}
			
		}
	}
	
	/* Get URL if specified. */
	class GetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String receivedText;

			/* Check if URL field is empty. */
			if ((urlField.getText()).equals("")) {
				System.out.println("Need URL!");
				return;
			}
			/*
			 * Pass string from URL field to HTTPGet (trimmed); returned string is either
			 * requested object or some error message.
			 */
			/*********************************************
			 * Uncomment this for part 2
			 ********************************************* 
			 * HttpInteract request = new HttpInteract(urlField.getText().trim());
			 * 
			 * // Send http request. Returned String holds object try {
			 * receivedText=request.send(); } catch (IOException error) {
			 * messageText.setText("Downloading File failed.\r\nIOException: " + error);
			 * return; } // Change message text messageText.setText(receivedText);
			 *******************************************/
			
			HttpInteract request = new HttpInteract(urlField.getText().trim());
			// Send http request. Returned String holds object
			try {
				 receivedText=request.send(); 
				 //if the status code from server is 301 or 302, receivedText actually holds the new URL for the redirected location.
				 while(request.getStatusCode()==301||request.getStatusCode()==302) {
					 String new_location=receivedText;					//copy the new URL to new_location
					 urlField.setText(new_location);					//change the URL to in urlField to the new one
					 request=new HttpInteract(new_location.trim());		//create request again for the new URL
					 receivedText=request.send();						//get the receivedText from the new URL
				 }
				 content_type=request.getContentType();					//get the content type of the URL
				 
				// Change message text
				 messageText.setText(receivedText);						
				 
			} 
			catch (IOException error) {
				  messageText.setText("Downloading File failed.\r\nIOException: " + error);
				  return; 
			}
		}
	}

	/* Clear the fields on the GUI. */
	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Clearing fields");
			fromField.setText("");
			toField.setText("");
			CcField.setText("");
			subjectField.setText("");
			messageText.setText("");
		}
	}

	/* Quit. */
	class QuitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
	
}

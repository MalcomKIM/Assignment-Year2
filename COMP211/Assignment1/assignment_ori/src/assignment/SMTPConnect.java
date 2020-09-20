package assignment;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to mailserver and send one mail.
 * Date: 10/10/2019
 * Student Name: Shuheng Mo
 * Student ID: 201448174 
 * Student Name: Minhao Jin
 * Student ID: 201447766
 *
 */

/**
 * 
 * @author sgmjin2
 *
 */
public class SMTPConnect {
	/* Socket to the server */
	private Socket connection;

	/* Streams for reading from and writing to socket */
	private BufferedReader fromServer;
	private DataOutputStream toServer;

	private static final String CRLF = "\r\n";

	/* Are we connected? Used in close() to determine what to do. */
	private boolean isConnected = false;

	/*
	 * Create an SMTPConnect object. Create the socket and the associated streams.
	 * Initialise SMTP connection.
	 */
	public SMTPConnect(EmailMessage mailmessage) throws IOException {
		// Open a TCP client socket with hostname and portnumber specified in
		// mailmessage.DestHost and mailmessage.DestHostPort, respectively.
		connection = new Socket(mailmessage.DestHost, mailmessage.DestHostPort);

		// attach the BufferedReader fromServer to read from the socket and
		// the DataOutputStream toServer to write to the socket
		fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		toServer = new DataOutputStream(connection.getOutputStream());

		/* Fill in */
		/*
		 * Read one line from server and check that the reply code is 220. If not, throw
		 * an IOException.
		 */
		String response = fromServer.readLine();
		if (!response.startsWith("220")) {
			throw new IOException("220 reply not received from server.");
		}

		/* Fill in */

		/*
		 * SMTP handshake. We need the name of the local machine. Send the appropriate
		 * SMTP handshake command.
		 */
		String localhost = InetAddress.getLocalHost().getHostName();

		try {
			sendCommand("HELO " + mailmessage.DestHost, 250);
			sendCommand("MAIL FROM: <" + mailmessage.Sender + ">", 250);
			sendCommand("RCPT TO: <" + mailmessage.Recipient + ">", 250);
			sendCommand("DATA", 354);
		} catch (IOException error) {
			isConnected = false;
		} finally {
			isConnected = true;
		}
		
	}

	/*
	 * Send message. Write the correct SMTP-commands in the correct order. No
	 * checking for errors, just throw them to the caller.
	 */
	public void send(EmailMessage mailmessage) throws IOException {
		/* Fill in */
		/*
		 * Send all the necessary commands to send a message. Call sendCommand() to do
		 * the dirty work. Do _not_ catch the exception thrown from sendCommand().
		 */
		/* Fill in */
		
		if(isConnected) {
			String  header=mailmessage.Headers;
			String[] helper=header.split(CRLF);
			String subject=helper[2].substring(9); //?
			
			toServer.writeBytes("Subject: "+subject + CRLF);
			toServer.writeBytes(mailmessage.Body + CRLF);
			sendCommand(".",250);
		}
		
	}

	/*
	 * Close SMTP connection. First, terminate on SMTP level, then close the socket.
	 */
	public void close() {
		isConnected = false;
		try {
			sendCommand("QUIT",221);
			connection.close();
		} catch (IOException e) {
			System.out.println("Unable to close connection: " + e);
			isConnected = true;
		}
	}

	/*
	 * Send an SMTP command to the server. Check that the reply code is what is is
	 * supposed to be according to RFC 821.
	 */
	private void sendCommand(String command, int rc) throws IOException {
		/* Fill in */
		/* Write command to server and read reply from server. */
		/* Fill in */

		toServer.writeBytes(command + CRLF);
		String response = fromServer.readLine();
		if (!response.startsWith("" + rc)) {
			throw new IOException("Bad request!");
		}

		/* Fill in */
		/*
		 * Check that the server's reply code is the same as the parameter rc. If not,
		 * throw an IOException.
		 */
		/* Fill in */
	}

	/* Destructor. Closes the connection if something bad happens. */
	protected void finalize() throws Throwable {
		if (isConnected) {
			close();
		}
		super.finalize();
	}
}
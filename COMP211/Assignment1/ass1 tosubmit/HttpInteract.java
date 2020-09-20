import java.net.*;
import java.io.*;
import java.util.*;

/*************************************
 * Filename:  HttpInteract.java
 * Names:	Shuheng Mo	Minhao Jin
 * Student-IDs:	201448174	201447766
 * Date:
 *************************************/

public class HttpInteract {
	private String host;
	private String path;
	private String requestMessage;
	private int status = 0; // status code
	private int bytesRead = 0;

	private static final int HTTP_PORT = 80;
	private static final String CRLF = "\r\n";
	private static final int BUF_SIZE = 4096;
	private static final int MAX_OBJECT_SIZE = 102400;

	/* Create HttpInteract object. */
	public HttpInteract(String url) {

		/*
		 * Split "URL" into "host name" and "path name", and set host and path class
		 * variables. if the URL is only a host name, use "/" as path
		 */

		/* Fill in */

		String all[] = url.split("/");
		host = all[0];

		if (all.length == 1) {
			path = "/";
		} else {
			path = url.substring(host.length());
		}

		/*
		 * Construct requestMessage, add a header line so that server closes connection
		 * after one response.
		 */

		/* Fill in */

		/*
		 * Construct a request message by stimulate a browser. Then identified that the
		 * right message format should be "GET" code line followed by the "Host" code
		 * line.
		 */

		requestMessage = "GET " + path + " HTTP/1.1" + CRLF + "Host: " + host + CRLF
		// +"Connection: close"+CRLF
		// +"User-agent: Mozilla/5.0"+CRLF
		// +"Accept-language: en"+CRLF
				+ CRLF;

		return;
	}

	/*
	 * Send Http request, parse response and return requested object as a String (if
	 * no errors), otherwise return meaningful error message. Don't catch
	 * Exceptions. EmailClient will handle them.
	 */
	public String send() throws IOException {

		/* buffer to read object in 4kB chunks */
		char[] buf = new char[BUF_SIZE];

		/*
		 * Maximum size of object is 100kB, which should be enough for most objects.
		 * Change constant if you need more.
		 */
		char[] body = new char[MAX_OBJECT_SIZE];

		String statusLine = ""; // status line

		String headers = ""; // headers
		int bodyLength = -1; // lenghth of body

		String[] tmp;

		/* The socket to the server */
		Socket connection;

		/* Streams for reading from and writing to socket */
		BufferedReader fromServer;
		DataOutputStream toServer;

		System.out.println("Connecting server: " + host + CRLF);

		/*
		 * Connect to http server on port 80. Assign input and output streams to
		 * connection.
		 */
		connection = new Socket(host, 80);
		fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		toServer = new DataOutputStream(connection.getOutputStream());

		System.out.println("Send request:\n" + requestMessage);

		/* Send requestMessage to http server */
		/* Fill in */
		toServer.writeBytes(requestMessage);

		/* Read the status line from response message */

		statusLine = fromServer.readLine();
		System.out.println("Status Line:\n" + statusLine + CRLF);

		/*
		 * Extract status code from status line. If status code is not 200, close
		 * connection and return an error message. Do NOT throw an exception
		 */
		/* Fill in */

		String[] status_code = statusLine.split(" ");
		status = Integer.parseInt(status_code[1]);

		if (status != 200 && status != 404) {
			connection.close();
		} else {

			/*
			 * Read header lines from response message, convert to a string, and assign to
			 * "headers" variable. Recall that an empty line indicates end of headers.
			 * Extract length from "Content-Length:" (or "Content-length:") header line, if
			 * present, and assign to "bodyLength" variable.
			 */

			/* Fill in */
			// requires about 10 lines of code

			// Read the header lines one by one and assign the content-length line to
			// "bodyLength"
			String temp = null;
			while ((temp = fromServer.readLine()).length() != 0) {
				headers += temp + "\n";
				if (temp.startsWith("Content-Length:")) {
					bodyLength = Integer.parseInt(temp.substring(16));
				}

			}

			System.out.println("Headers:\n" + headers + CRLF);

			/*
			 * If object is larger than MAX_OBJECT_SIZE, close the connection and return
			 * meaningful message.
			 */

			if (bodyLength > MAX_OBJECT_SIZE) {
				connection.close();
				return ("The object is larger than MAX_OBJECT_SIZE, its size is " + bodyLength);
			}

			/*
			 * Read the body in chunks of BUF_SIZE using buf[] and copy the chunk into
			 * body[]. Stop when either we have read Content-Length bytes or when the
			 * connection is closed (when there is no Content-Length in the response). Use
			 * one of the read() methods of BufferedReader here, NOT readLine(). Make sure
			 * not to read more than MAX_OBJECT_SIZE characters.
			 */
			

			/* Fill in */ // Requires 10-20 lines of code

			if (bodyLength != -1) {
				int times = bodyLength / BUF_SIZE + 1;
				for (int i = 0; i < times; i++) {
					buf = new char[BUF_SIZE];
					bytesRead += fromServer.read(buf);
					System.arraycopy(buf, 0, body, i * BUF_SIZE, BUF_SIZE);
				}
				System.out.println(bytesRead);
			} else {
				body=read_chunk(fromServer,buf,body);
			}

			/*
			 * At this points body[] should hold to body of the downloaded object and
			 * bytesRead should hold the number of bytes read from the BufferedReader
			 */

			/* Close connection and return object as String. */
			System.out.println("Done reading file. Closing connection.");
			connection.close();
			return (new String(body, 0, bytesRead));
		}
		return "";
	}

	private char[] read_chunk(BufferedReader fromServer,char[] buf,char body[]) throws IOException {
		String chunk_size;
		int size = -1;
		while (true) {
			chunk_size = fromServer.readLine();
			try {
				size = Integer.parseInt(chunk_size, 16);
			} catch (NumberFormatException error) {
				body=read_chunk(fromServer,buf,body);
				return body;
			}
			//size = Integer.parseInt(chunk_size, 16);
			//System.out.println(size);
			if (size == 0)
				break;
			fromServer.read(buf,0, size);
			//System.out.println(buf);
			for (int i = 0; i < size; i++) {
				body[i + bytesRead] = buf[i];
			}
			bytesRead += size;
			fromServer.readLine();
		}
		return body;
	}
}
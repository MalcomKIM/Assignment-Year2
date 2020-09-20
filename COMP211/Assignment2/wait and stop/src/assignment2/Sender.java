/*************************************
 * Filename:  Sender.java
 * Names:
 * Student-IDs:
 * Date:
 *************************************/
package assignment2;
import java.util.Random;

public class Sender extends NetworkHost

{
    /*
     * Predefined Constant (static member variables):
     *
     *   int MAXDATASIZE : the maximum size of the Message data and
     *                     Packet payload
     *
     *
     * Predefined Member Methods:
     *
     *  void startTimer(double increment):
     *       Starts a timer, which will expire in
     *       "increment" time units, causing the interrupt handler to be
     *       called.  You should only call this in the Sender class.
     *  void stopTimer():
     *       Stops the timer. You should only call this in the Sender class.
     *  void udtSend(Packet p)
     *       Sends the packet "p" into the network to arrive at other host
     *  void deliverData(String dataSent)
     *       Passes "dataSent" up to application layer. Only call this in the 
     *       Receiver class.
     *  double getTime()
     *       Returns the current time of the simulator.  Might be useful for
     *       debugging.
     *  String getReceivedData()
     *       Returns a String with all data delivered to receiving process.
     *       Might be useful for debugging. You should only call this in the
     *       Sender class.
     *  void printEventList()
     *       Prints the current event list to stdout.  Might be useful for
     *       debugging, but probably not.
     *
     *
     *  Predefined Classes:
     *
     *  Message: Used to encapsulate the message coming from application layer
     *    Constructor:
     *      Message(String inputData): 
     *          creates a new Message containing "inputData"
     *    Methods:
     *      boolean setData(String inputData):
     *          sets an existing Message's data to "inputData"
     *          returns true on success, false otherwise
     *      String getData():
     *          returns the data contained in the message
     *  Packet: Used to encapsulate a packet
     *    Constructors:
     *      Packet (Packet p):
     *          creates a new Packet, which is a copy of "p"
     *      Packet (int seq, int ack, int check, String newPayload)
     *          creates a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and a
     *          payload of "newPayload"
     *      Packet (int seq, int ack, int check)
     *          chreate a new Packet with a sequence field of "seq", an
     *          ack field of "ack", a checksum field of "check", and
     *          an empty payload
     *    Methods:
     *      boolean setSeqnum(int n)
     *          sets the Packet's sequence field to "n"
     *          returns true on success, false otherwise
     *      boolean setAcknum(int n)
     *          sets the Packet's ack field to "n"
     *          returns true on success, false otherwise
     *      boolean setChecksum(int n)
     *          sets the Packet's checksum to "n"
     *          returns true on success, false otherwise
     *      boolean setPayload(String newPayload)
     *          sets the Packet's payload to "newPayload"
     *          returns true on success, false otherwise
     *      int getSeqnum()
     *          returns the contents of the Packet's sequence field
     *      int getAcknum()
     *          returns the contents of the Packet's ack field
     *      int getChecksum()
     *          returns the checksum of the Packet
     *      String getPayload()
     *          returns the Packet's payload
     *
     */

    // Add any necessary class variables here. They can hold
    // state information for the sender. 
	private int local_seqnum=1;
	private int local_acknum=1;
	private int local_checksum=0;
	
	private Packet crt_packet;

    // Also add any necessary methods (e.g. checksum of a String)
	public int reset_num(int num) {
		if(num==1) {
			num=0;
		}
		else if(num==0) {
			num=1;
		}
		return num;
	}
	
	public int generate_Checksum(String payload, int seqnum,int acknum) {
		int res=0;
		for(char c:payload.toCharArray()) {
			res+=c-'a';
		}
		res=res+seqnum+acknum;
		return res;
	}

    // This is the constructor.  Don't touch!
    public Sender(int entityName,
                       EventList events,
                       double pLoss,
                       double pCorrupt,
                       int trace,
                       Random random)
    {
        super(entityName, events, pLoss, pCorrupt, trace, random);
    }

    // This routine will be called whenever the application layer at the sender
    // has a message to  send.  The job of your protocol is to insure that
    // the data in such a message is delivered in-order, and correctly, to
    // the receiving application layer.
    protected void Output(Message message)
    {
    	String payload=message.getData();
    	local_seqnum=reset_num(local_seqnum);
    	local_acknum=reset_num(local_acknum);
    	local_checksum=generate_Checksum(payload,local_seqnum,local_acknum);
    	crt_packet=new Packet(local_seqnum,local_acknum,local_checksum,payload);
    	udtSend(crt_packet);
    	
    	startTimer(40);
    }
    
    // This routine will be called whenever a packet sent from the receiver 
    // (i.e. as a result of udtSend() being done by a receiver procedure)
    // arrives at the sender.  "packet" is the (possibly corrupted) packet
    // that was sent from the receiver.
    protected void Input(Packet packet)
    {
    	int seqnum=packet.getSeqnum();
    	int acknum=packet.getAcknum();
    	int checksum=packet.getChecksum();
    	System.out.println("Receive: seqnum:"+seqnum+" acknum:"+acknum+" checksum:"+checksum);
    	if(local_checksum==checksum&&local_seqnum==seqnum&&local_acknum==acknum) {
    		stopTimer();
    		System.out.println("checksum right!!");
    	}
    	else {
    		udtSend(crt_packet);
    	}

    }
    
    // This routine will be called when the senders's timer expires (thus 
    // generating a timer interrupt). You'll probably want to use this routine 
    // to control the retransmission of packets. See startTimer() and 
    // stopTimer(), above, for how the timer is started and stopped. 
    protected void TimerInterrupt()
    {
    	udtSend(crt_packet);
    	startTimer(40);
    }
    
    // This routine will be called once, before any of your other sender-side 
    // routines are called. It should be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of the sender).
    protected void Init()
    {
    	
    }

}

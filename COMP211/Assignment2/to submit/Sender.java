/*************************************
 * Filename:  Sender.java
 * Names:		Minhao Jin		Shuheng Mo
 * Student-IDs:	201447766		201448174
 * Date:	Oct.25th
 *************************************/
import java.util.ArrayList;
import java.util.List;
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
	private int nextseqnum;				//next sequence number from application layer
	private int seqnum=0;				//the sequence number for all the packet
	private double wait_time;			//time for timeout
	private int acknum;					//acknowledge number
	private int base;					//sender window base
	private final int WINDOW_SIZE=8;	//sender window size
	
	private List <Packet> buffer;		//buffer used for storing all packet (extracted from message) from application layer

    // Also add any necessary methods (e.g. checksum of a String)
	
	
	//generate checksum according to the packet's 'payload', 'seqnum', 'acknum'
	public int generate_Checksum(String payload, int seqnum,int acknum) {
		int res=0;
		for(char c:payload.toCharArray()) {
			res+=c-'a';
		}
		res=res+seqnum+acknum;
		return res;
	}
	
	//judge whether one packet is corrupted or not
	public boolean iscorrupted(Packet p) {
		int seqnum=p.getSeqnum();
		int acknum=p.getAcknum();
		int chknum=p.getChecksum();
		String payload=p.getPayload();
		
		if(generate_Checksum(payload, seqnum,acknum)==chknum) {
			return false;
		}
		
		return true;
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
    	String payload=message.getData();								//extract data string from 'message'
    	int chksum=generate_Checksum(payload,seqnum,acknum);		//generate checksum
    	Packet p=new Packet(seqnum,acknum,chksum,payload);			//make one packet for this message
    	seqnum++;
    	acknum++;
    	if(nextseqnum<base+WINDOW_SIZE) {								//if the packet is in the sender window
    		udtSend(p);													//send this packet through udt
    		buffer.add(p);												//add this sent packet to buffer
    		if(base==nextseqnum) {										//if base equals to next sequence number
    			startTimer(wait_time);											//start timer
    		}
    		nextseqnum++;												
    	}
    	else {
    		buffer.add(p);												//refuse data
    	}
    }
    
    // This routine will be called whenever a packet sent from the receiver 
    // (i.e. as a result of udtSend() being done by a receiver procedure)
    // arrives at the sender.  "packet" is the (possibly corrupted) packet
    // that was sent from the receiver.
    protected void Input(Packet packet)
    {
    	//stopTimer();
    	int acknum=packet.getAcknum();									//get the acknowledge number
    	int checksum=packet.getChecksum();								//get the checksum 
    	
    	//System.out.println("Sender receives:"+packet.toString());		
    	
    	if(iscorrupted(packet)) {										//judge whether the received packet from receiver is corrupted or not
    		return;
    	}
    	
    	if(buffer.get(acknum).getChecksum()==checksum&&base<=acknum) {	//if the packet is not corrupted, judge whether its checksum is valid
    		base=acknum+1;												//if the checksum is valid, 'base' equals to 'acknum'+1
    	}
    	
    	if(base==buffer.size()) {											//If it is the last packet
    		stopTimer();												//stop timer
    	}
    	else {
    		startTimer(wait_time);												//else restart the timer
    	}
    	
    	//System.out.println("Sender base:"+base);
    	
    }
    
    // This routine will be called when the senders's timer expires (thus 
    // generating a timer interrupt). You'll probably want to use this routine 
    // to control the retransmission of packets. See startTimer() and 
    // stopTimer(), above, for how the timer is started and stopped. 
    protected void TimerInterrupt()
    {
    	startTimer(wait_time);													//if time expires, restart timer
    	
    		int tail=base+WINDOW_SIZE;							//the index of the last packet in the window
    		if(tail>buffer.size()) {							//if the value of 'tail' is greater than the buffer size
    			tail=buffer.size();								//set the value of 'tail' to buffer size
    		}
    	
    	
    	for(int i=base;i<tail;i++) {								//re-send packets in the buffer without acknowledgement from 'base' to 'nextseqnum'
    		udtSend(buffer.get(i));
    	}
    }
    
    // This routine will be called once, before any of your other sender-side 
    // routines are called. It should be used to do any required
    // initialization (e.g. of member variables you add to control the state
    // of the sender).
    protected void Init()
    {
    	nextseqnum=0;
    	wait_time=40;
    	acknum=0;
    	base=0;
    	buffer =new ArrayList();
    }

}

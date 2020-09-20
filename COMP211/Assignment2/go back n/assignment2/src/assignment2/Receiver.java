/*************************************
 * Filename:  Receiver.java
 * Names:		Minhao Jin		Shuheng Mo
 * Student-IDs:	201447766		201448174
 * Date:	Oct.25th
 *************************************/
package assignment2;

import java.util.Random;

public class Receiver extends NetworkHost

{
	/*
	 * Predefined Constants (static member variables):
	 *
	 * int MAXDATASIZE : the maximum size of the Message data and Packet payload
	 *
	 *
	 * Predefined Member Methods:
	 *
	 * void startTimer(double increment): Starts a timer, which will expire in
	 * "increment" time units, causing the interrupt handler to be called. You
	 * should only call this in the Sender class. void stopTimer(): Stops the timer.
	 * You should only call this in the Sender class. void udtSend(Packet p) Sends
	 * the packet "p" into the network to arrive at other host void
	 * deliverData(String dataSent) Passes "dataSent" up to application layer. Only
	 * call this in the Receiver class. double getTime() Returns the current time of
	 * the simulator. Might be useful for debugging. String getReceivedData()
	 * Returns a String with all data delivered to receiving process. Might be
	 * useful for debugging. You should only call this in the Sender class. void
	 * printEventList() Prints the current event list to stdout. Might be useful for
	 * debugging, but probably not.
	 *
	 *
	 * Predefined Classes:
	 *
	 * Message: Used to encapsulate a message coming from application layer
	 * Constructor: Message(String inputData): creates a new Message containing
	 * "inputData" Methods: boolean setData(String inputData): sets an existing
	 * Message's data to "inputData" returns true on success, false otherwise String
	 * getData(): returns the data contained in the message Packet: Used to
	 * encapsulate a packet Constructors: Packet (Packet p): creates a new Packet,
	 * which is a copy of "p" Packet (int seq, int ack, int check, String
	 * newPayload) creates a new Packet with a sequence field of "seq", an ack field
	 * of "ack", a checksum field of "check", and a payload of "newPayload" Packet
	 * (int seq, int ack, int check) chreate a new Packet with a sequence field of
	 * "seq", an ack field of "ack", a checksum field of "check", and an empty
	 * payload Methods: boolean setSeqnum(int n) sets the Packet's sequence field to
	 * "n" returns true on success, false otherwise boolean setAcknum(int n) sets
	 * the Packet's ack field to "n" returns true on success, false otherwise
	 * boolean setChecksum(int n) sets the Packet's checksum to "n" returns true on
	 * success, false otherwise boolean setPayload(String newPayload) sets the
	 * Packet's payload to "newPayload" returns true on success, false otherwise int
	 * getSeqnum() returns the contents of the Packet's sequence field int
	 * getAcknum() returns the contents of the Packet's ack field int getChecksum()
	 * returns the checksum of the Packet String getPayload() returns the Packet's
	 * payload
	 *
	 */

	// Add any necessary class variables here. They can hold
	// state information for the receiver.

	private int expectedseqnum;													//expected sequence number of receiver
	private Packet latest_packet;												//Latest acknowledged packet

	// Also add any necessary methods (e.g. checksum of a String)
	
	//generate checksum according to the packet's 'payload', 'seqnum', 'acknum'
	public int generate_Checksum(String payload, int seqnum, int acknum) {
		int res = 0;
		for (char c : payload.toCharArray()) {
			res += c - 'a';
		}
		res = res + seqnum + acknum;
		return res;
	}

	//judge whether one packet is corrupted or not
	public boolean isCorrupted(int snd_checksum, int rcv_checksum) {
		return snd_checksum != rcv_checksum;
	}

	// This is the constructor. Don't touch!
	public Receiver(int entityName, EventList events, double pLoss, double pCorrupt, int trace, Random random) {
		super(entityName, events, pLoss, pCorrupt, trace, random);
	}

	// This routine will be called whenever a packet from the sender
	// (i.e. as a result of a udtSend() being done by a Sender procedure)
	// arrives at the receiver. Argument "packet" is the (possibly corrupted)
	// packet that was sent from the sender.
	protected void Input(Packet packet) {
		int seqnum = packet.getSeqnum();										//get the sequence number from sender
		int acknum = packet.getAcknum();										//get the acknowledge number from sender
		int checksum = packet.getChecksum();									//get the checksum from sender
		String payload = packet.getPayload();									//get the payload from sender
		

		System.out.println("Receiver receives: "+packet.toString());

		if (expectedseqnum == seqnum) {											//if the expected sequence number equals to received sequence number
			if (!isCorrupted(checksum, generate_Checksum(payload, seqnum, acknum))) {		//and if it is not corrupted 
				latest_packet=packet;											//update the latest acknowledged packet to this packet
				System.out.println("checksum right");
				deliverData(payload);											//pass data up to the application layer
				expectedseqnum++;												//expected sequence number incremented by 1
			}
			else {
				System.out.println("wrong checksum");							//if the checksum is wrong
			}

		} else {
			System.out.println("Discarded! Want:"+expectedseqnum);				//if received sequence number is not equal to expected sequence number
		}
		udtSend(latest_packet);													//send the latest ack packet anyway
	}

	// This routine will be called once, before any of your other receiver-side
	// routines are called. It should be used to do any required
	// initialization (e.g. of member variables you add to control the state
	// of the receiver).
	protected void Init() {
		expectedseqnum = 0;
		latest_packet=new Packet(0,0,-1);
	}

}

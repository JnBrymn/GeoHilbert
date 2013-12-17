package com.jb.hilbert;

import java.util.Iterator;

public class BitReaderWriter {

	int byteIndex = 0;//the byte which contains the next unread bit
	byte bitIndex = 0;//the index of the next unread bit
	int iterateBy;
	byte[] bytes;
	
	int lastByteIndex = -1;
	byte lastBitIndex = -1;
	byte lastBitMask = 0x0;
	byte hackSpecialCaseBitMask;//TODO this is a hack and needs to be removed (see note below)
	
	/**
	 * @param bytes
	 * @param iterateBy how many bits to return each time. Only option for now is 1,2,4
	 */
	public BitReaderWriter(byte[] bytes, int iterateBy) {
		this.bytes = bytes;
		this.iterateBy = iterateBy;
		
		//TODO the following is a hack and needs to be removed (see note below)
		switch (iterateBy) {
		case 2:
			hackSpecialCaseBitMask = 0x03;
			break;
		case 4:
			hackSpecialCaseBitMask = 0x0f;
			break;
		case 8:
			hackSpecialCaseBitMask = (byte) 0xff;
			break;
		default:
			break;
		}
	}
	
	private byte getBitMask() {
		byte bitMask = 0;
		for(int i = 0; i < iterateBy; i++) {
			bitMask <<= 1;
			bitMask |= 1;
		}
		bitMask <<= 8-bitIndex-iterateBy;
		return bitMask;
	}

	public boolean hasNext() { 
		if((byteIndex) < bytes.length) return true;
		else return false;
	}

	public byte next() {
		byte bitMask = getBitMask();
		byte b = (byte) (bytes[byteIndex] & bitMask);
		b >>= 8-bitIndex-iterateBy;
		
		//If your right shift and the most significant bit is 1
		//then the "new" bits that appear at the left will also 
		//be one. I think this is something due to two's compliment
		//behavior. The following is a special case fix for this situation.
		//TODO this is a hack and needs to be fixed!
		if(bitIndex == 0) {
			b &= hackSpecialCaseBitMask;
		}
		
		lastByteIndex = byteIndex;
		lastBitIndex = bitIndex;
		lastBitMask = bitMask;
		
		bitIndex += iterateBy;
		bitIndex %= 8;
		if(bitIndex == 0) {
			byteIndex++;
		}
		return b;
	}
	
	/**
	 * This writes the least significant bits of byte b to the positions of the bits
	 * returned in the previous call to next.
	 * 
	 * @param b
	 */
	public void write(byte b) {
		//shift b into correct position and mask
		b <<= 8-lastBitIndex-iterateBy;
		b &= lastBitMask;
		
		//delete corresponding values on current byte
		bytes[lastByteIndex] = (byte) ((bytes[lastByteIndex] & lastBitMask) ^ bytes[lastByteIndex]);
		
		//or b and bytes[lastByteIndex] together - bytes have now been overwritten
		bytes[lastByteIndex] |= b;
	}
	
	public static void main(String[] args) {
		byte[] bytes;
		BitReaderWriter brw;
		
		bytes = new byte[] {0,1,2};
		System.out.println(Utils.bytesToStr(bytes, 2));
		brw = new BitReaderWriter(bytes, 2);
		while(brw.hasNext()) {
			brw.next();
			brw.write((byte) 1);
		}
		System.out.println(Utils.bytesToStr(bytes, 2));
		
	}
}

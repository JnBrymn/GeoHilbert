package com.jb.hilbert;

import java.math.BigInteger;

public class Utils {

	public static String byteToStr(byte b,int base) {
		return bytesToStr(new byte[]{b}, base);
	}
	public static String bytesToStr(byte[] bytes,int base) {
		byte[] paddedBytes = new byte[bytes.length+1];
		for(int i = 0; i < bytes.length; i++) {
			paddedBytes[bytes.length-i] = bytes[bytes.length - 1 - i];
		}
		String val = (new BigInteger(paddedBytes)).toString(base);
		StringBuilder sb = new StringBuilder();
		int charPerByte = base==2?8: base==4?4: base==16?2:  0;
		for(int i = 0; i < (bytes.length*charPerByte-val.length());i++) {
			sb.append('0');
		}
		sb.append(val);
		String strVal = sb.toString();
		if(base == 2) {
			return strVal.replaceAll("(\\d{2})(\\d{2})(\\d{2})(\\d{2})", "$1$2:$3$4  ");
		} else if(base == 4) {
			return strVal.replaceAll("(\\d{2})(\\d{2})", "$1:$2  ");
		} else if(base == 16) {
			return strVal.replaceAll("(\\w{2})", "$1 ");
		} else {
			return "error";
		}
	}
	
	
	
	/**
	 * @param val
	 * @param min
	 * @param max
	 * @param numBytes
	 * @return
	 */
	public static byte[] convertValMinMaxToByteArray(double val, double min, double max, int numBytes) {
		
		System.out.println("Given initial value, "+ val +", in the domain of ["+ min +","+ max +"].");
		double newVal = (val - min) / (max - min);
		System.out.println("\tFirst scale the number to a value between 0 and 1. The value is "+ newVal +".");
		
		long longVal = (long) (newVal * Math.pow((double) 256, (double) numBytes));
		//TODO, if numBytes is 8, then all values for longVal >= 0.5 will be negated because Java num values are all signed
		System.out.println("\tThen scale the number to be a long value between 0 and 256^"+ numBytes +". This number is "+ longVal +".");
		
		byte[] bytesVal = new byte[numBytes];
		for (int i = 0; i < numBytes; ++i) {
			bytesVal[i] = (byte) (longVal >> (numBytes - i - 1 << 3));
		}
		System.out.println("\tThen convert this number into a byteArray of specified size: "+ Utils.bytesToStr(bytesVal,2) +"." );
		return bytesVal;
	}
	
}

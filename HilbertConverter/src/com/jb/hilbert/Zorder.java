package com.jb.hilbert;
import java.math.BigInteger;

public class Zorder {

	
	/**
	 * TODO this can be made much faster with methods such as this:
	 *  http://www-graphics.stanford.edu/~seander/bithacks.html#InterleaveBMN
	 *  and then convert bytes in machine word-sized chunks at a time
	 * @param numBytes
	 * @param x
	 * @param y
	 * @return
	 */
	public static byte[] zOrder(byte[] x, byte[] y) {
		int numBytes = x.length;
		byte[] z = new byte[numBytes*2];
		for(int i = 0; i < numBytes; i++) {
			byte x_ = (byte) (x[i] >> 4);//shift x to the right
			byte y_ = (byte) (y[i] >> 4);//shift y to the right
			for(int j = 0; j < 4; j++) {
				byte x_masked_j = (byte) (x[i] & (1 << j));
				byte y_masked_j = (byte) (y[i] & (1 << j));
				z[2*i+1] |= (y_masked_j << j) | (x_masked_j << (j + 1));
				
				x_masked_j = (byte) (x_ & (1 << j));
				y_masked_j = (byte) (y_ & (1 << j));
				z[2*i] |= (y_masked_j << j) | (x_masked_j << (j + 1));
			}
		}
		return z;
	}
	
	
	public static void main(String[] args)  {
		int numBytes = 2;
		
		double val1 = 0.19999;
		double min1 = 0.0;
		double max1 = 1.0;		
		byte[] val1bytes = Utils.convertValMinMaxToByteArray(val1, min1, max1, numBytes);
		
		double val2 = 0.34999;
		double min2 = 0.0;
		double max2 = 1.0;
		byte[] val2bytes = Utils.convertValMinMaxToByteArray(val2, min2, max2, numBytes);

		
		System.out.println("Then interleave these bitarrays:");
		System.out.println("\t"+ Utils.bytesToStr(val1bytes,2));
		System.out.println("\t"+ Utils.bytesToStr(val2bytes,2));
		byte[] z = zOrder(val1bytes, val2bytes);

		System.out.println("\tTo get this in \"z-order\":");//http://en.wikipedia.org/wiki/Z-order_curve
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		
		
		System.out.println("To test z order:");
		z = zOrder(new byte[]{0}, new byte[]{0});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{0}, new byte[]{1});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{1}, new byte[]{0});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{1}, new byte[]{1});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		
		z = zOrder(new byte[]{0}, new byte[]{2});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{0}, new byte[]{3});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{1}, new byte[]{2});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{1}, new byte[]{3});
		System.out.println("\t"+ Utils.bytesToStr(z,2));

		z = zOrder(new byte[]{2}, new byte[]{0});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{2}, new byte[]{1});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{3}, new byte[]{0});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{3}, new byte[]{1});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		
		z = zOrder(new byte[]{2}, new byte[]{2});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{2}, new byte[]{3});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{3}, new byte[]{2});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		z = zOrder(new byte[]{3}, new byte[]{3});
		System.out.println("\t"+ Utils.bytesToStr(z,2));
		
		
		
	}


}

package com.jb.hilbert;

import java.util.ArrayList;

public class HilbertOrder {

	private static final byte D = 0;
	private static final byte n = 1;
	private static final byte U = 2;
	private static final byte C = 3;
	
	private byte state = D;
	ArrayList<byte[]> hilbertMap;//TODO is ArrayList the best structure here?
	ArrayList<byte[]> nextMap;//TODO since this never changes, maybe Vector is better?
	
	public HilbertOrder() {
		byte[] D = new byte[]{3, 2, 0, 1};
		byte[] n = new byte[]{1, 2, 0, 3};
		byte[] U = new byte[]{3, 0, 2, 1};
		byte[] C = new byte[]{1, 0, 2, 3};
		
		hilbertMap = new ArrayList<byte[]>();
		hilbertMap.add(D);
		hilbertMap.add(n);
		hilbertMap.add(U);
		hilbertMap.add(C);
		
		byte[] nextFromD = new byte[]{2, 0, 1, 0};
		byte[] nextFromn = new byte[]{1, 1, 0, 3};
		byte[] nextFromU = new byte[]{0, 3, 2, 2};
		byte[] nextFromC = new byte[]{3, 2, 3, 1};
		
		nextMap = new ArrayList<byte[]>();
		nextMap.add(nextFromD);
		nextMap.add(nextFromn);
		nextMap.add(nextFromU);
		nextMap.add(nextFromC);
	}
	
	public void hilbertOrder(byte[] bytes) {
		state = D;
		
		BitReaderWriter brw = new BitReaderWriter(bytes, 2);
		while(brw.hasNext()) {
			byte z = brw.next();//next two bits in z ordering
			byte h = hilbertMap.get(state)[z];
			state = nextMap.get(state)[z];
			brw.write(h);
		}
	}
	
	public static void main(String[] args) {
		
		byte[] bytes;
		HilbertOrder ho = new HilbertOrder();
		int numBytes = 1;
			
		
		double val1 = 0.1232341;
		double min1 = 0.0;
		double max1 = 1.0;		
		byte[] val1bytes = Utils.convertValMinMaxToByteArray(val1, min1, max1, numBytes);
		
		double val2 = 0.7644444;
		double min2 = 0.0;
		double max2 = 1.0;
		byte[] val2bytes = Utils.convertValMinMaxToByteArray(val2, min2, max2, numBytes);

		
		System.out.println("Then interleave these bitarrays:");
		System.out.println("\t"+ Utils.bytesToStr(val1bytes,2));
		System.out.println("\t"+ Utils.bytesToStr(val2bytes,2));
		bytes = Zorder.zOrder(val1bytes, val2bytes);
		System.out.println("\tTo get this in \"z-order\":");//http://en.wikipedia.org/wiki/Z-order_curve
		System.out.println("\t"+ Utils.bytesToStr(bytes,2));

		System.out.println("Printing this in base 4 makes it easier to read:");
		System.out.println("\t"+ Utils.bytesToStr(bytes,4));
		
		ho.hilbertOrder(bytes);
		System.out.println("Reordering them into Hilbert Order:");
		System.out.println("\t"+ Utils.bytesToStr(bytes,4));
		System.out.println("I've checked this by hand and it should be 22:32  22:11");
		
		System.out.println();
		System.out.println();
		
		
		val1 = 0.632341;
		min1 = 0.0;
		max1 = 1.0;		
		val1bytes = Utils.convertValMinMaxToByteArray(val1, min1, max1, numBytes);
		
		val2 = 0.8645444;
		min2 = 0.0;
		max2 = 1.0;
		val2bytes = Utils.convertValMinMaxToByteArray(val2, min2, max2, numBytes);

		
		System.out.println("Then interleave these bitarrays:");
		System.out.println("\t"+ Utils.bytesToStr(val1bytes,2));
		System.out.println("\t"+ Utils.bytesToStr(val2bytes,2));
		bytes = Zorder.zOrder(val1bytes, val2bytes);
		System.out.println("\tTo get this in \"z-order\":");//http://en.wikipedia.org/wiki/Z-order_curve
		System.out.println("\t"+ Utils.bytesToStr(bytes,2));

		System.out.println("Printing this in base 4 makes it easier to read:");
		System.out.println("\t"+ Utils.bytesToStr(bytes,4));
		
		ho.hilbertOrder(bytes);
		System.out.println("Reordering them into Hilbert Order:");
		System.out.println("\t"+ Utils.bytesToStr(bytes,4));
		System.out.println("I've checked this by hand and it should be 12:02  22:13");
	}
	
}

package com.jb.hilbert;

import java.util.ArrayList;
import java.util.List;

public class HilbertConverter {

	public static byte[] convert(List<Double> vals, List<Double> mins, List<Double> maxs) {
		byte[] bytes;
		HilbertOrder ho = new HilbertOrder();
		int numBytes = 1;
			
		
		double val1 = vals.get(0);
		double min1 = mins.get(0);
		double max1 = maxs.get(0);		
		byte[] val1bytes = Utils.convertValMinMaxToByteArray(val1, min1, max1, numBytes);
		
		double val2 = vals.get(1);
		double min2 = mins.get(1);
		double max2 = maxs.get(1);
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
		
		return bytes;
		
	}
	
	public static void main(String[] args) {
		
		List<Double> vals = new ArrayList<Double>();
		vals.add(0.4);
		vals.add(0.6);
		
		List<Double> mins = new ArrayList<Double>();
		mins.add(0.0);
		mins.add(0.0);
		
		List<Double> maxs = new ArrayList<Double>();
		maxs.add(1.0);
		maxs.add(1.0);
		
		byte[] hilbertBytes = convert(vals, mins, maxs);
	}
	
}

package com.ibm.block.generator.impl.app;

import com.ibm.block.generator.impl.ThreeTierAppGen;
import com.ibm.block.model.core.impl.PlacementModel;

public class SmallThreeTierAppGen extends ThreeTierAppGen {

	private static int t1VmCount = 0;
	private static int t1VvPerVmCount = 0;
	private static int t2VmCount = 0;
	private static int t2VvPerVmCount = 0;
	private static int t3VmCount = 1;
	private static int t3VvPerVmCount = 1;
	
	private static int t1ReplicaCount = 1;
	private static int t2ReplicaCount = 1;
	private static int t3ReplicaCount = 1;
	
	private static boolean hasAntiColPath = false;
	private static boolean hasAntiColStorage = true;
	private static boolean hasColStorage = false;
	private static boolean hasReplica = false;
	private static boolean hasSameProperty =false;
	
	public SmallThreeTierAppGen(PlacementModel model) {
		super(model, t1VmCount, t2VmCount, t3VmCount, t1VvPerVmCount, t2VvPerVmCount,
				t3VvPerVmCount, hasAntiColPath, hasAntiColStorage, hasColStorage,
				hasReplica, hasSameProperty, t1ReplicaCount, t2ReplicaCount, t3ReplicaCount);
	}
	
	public SmallThreeTierAppGen(PlacementModel model, int t1VmCount, int t2VmCount, int t3VmCount, 
			int t1VvPerVmCount, int t2VvPerVmCount, int t3VvPerVmCount, 
			boolean hasAntiColPath, boolean hasAntiColStorage, boolean hasColStorage, boolean hasReplica, 
			boolean hasSameProperty, int t1ReplicaCount, int t2ReplicaCount, int t3ReplicaCount) {
		
		super(model, t1VmCount, t2VmCount, t3VmCount, t1VvPerVmCount, t2VvPerVmCount,
				t3VvPerVmCount, hasAntiColPath, hasAntiColStorage, hasColStorage,
				hasReplica, hasSameProperty, t1ReplicaCount, t2ReplicaCount, t3ReplicaCount);
	}
}

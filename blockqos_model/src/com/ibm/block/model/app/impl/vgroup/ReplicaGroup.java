package com.ibm.block.model.app.impl.vgroup;

import com.ibm.block.model.app.impl.VVolumeGroup;

public class ReplicaGroup extends VVolumeGroup {

	private static String namePrefix = "g_replica";
	
	public ReplicaGroup() {
		super(namePrefix);
	}
}

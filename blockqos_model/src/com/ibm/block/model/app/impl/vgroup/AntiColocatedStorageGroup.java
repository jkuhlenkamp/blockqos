package com.ibm.block.model.app.impl.vgroup;

import com.ibm.block.model.app.impl.VVolumeGroup;

public class AntiColocatedStorageGroup extends VVolumeGroup {

	private static String namePrefix = "g_anticolstorage";
	
	public AntiColocatedStorageGroup() {
		super(namePrefix);
	}
}

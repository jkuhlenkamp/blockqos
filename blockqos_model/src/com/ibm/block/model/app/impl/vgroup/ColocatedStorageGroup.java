package com.ibm.block.model.app.impl.vgroup;

import com.ibm.block.model.app.impl.VVolumeGroup;

public class ColocatedStorageGroup extends VVolumeGroup {

	private static String namePrefix = "g_colstorage";
	
	public ColocatedStorageGroup() {
		super(namePrefix);
	}
}

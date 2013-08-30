package com.ibm.block.model.app.impl.vgroup;

import com.ibm.block.model.app.impl.VVolumeGroup;
import com.ibm.block.model.app.intf.AntiColocatedPathGroupInterface;

public class AntiColocatedPathGroup extends VVolumeGroup implements AntiColocatedPathGroupInterface {

	private static String namePrefix = "g_anticolpath";
	private int limit;
	
	public AntiColocatedPathGroup(int limit) {
		super(namePrefix);
		this.limit = limit;
	}

	@Override
	public Integer getSharedSwitchLimit() {
		return limit;
	}
}

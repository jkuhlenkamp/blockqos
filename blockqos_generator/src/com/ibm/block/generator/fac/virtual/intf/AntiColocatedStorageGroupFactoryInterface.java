package com.ibm.block.generator.fac.virtual.intf;

import com.ibm.block.model.app.impl.vgroup.AntiColocatedStorageGroup;

public interface AntiColocatedStorageGroupFactoryInterface extends
		VVGroupFactoryInterface {

	public AntiColocatedStorageGroup create();
}

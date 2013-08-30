package com.ibm.block.generator.fac.virtual.intf;

import com.ibm.block.model.app.impl.vgroup.ReplicaGroup;

public interface ReplicaGroupFactoryInterface extends VVGroupFactoryInterface {

	public ReplicaGroup create();
	
}

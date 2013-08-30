package com.ibm.block.generator.fac.virtual.intf;

import com.ibm.block.generator.fac.core.intf.ResourceFactoryInterface;
import com.ibm.block.model.app.impl.ResourceDemand;

public interface RDemandFactoryInterface extends ResourceFactoryInterface {

	public ResourceDemand create();
	public Integer getAmount();
}

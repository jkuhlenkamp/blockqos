package com.ibm.block.generator.fac.virtual.intf;

import java.util.ArrayList;

import com.ibm.block.generator.fac.core.intf.EntityFactoryInterface;
import com.ibm.block.generator.fac.virtual.impl.RDemandFactory;

public interface VFactoryInterface extends EntityFactoryInterface {

	public ArrayList<RDemandFactory> getRDemandFactories();
	public void addRDemandFactory(RDemandFactory factory);
}

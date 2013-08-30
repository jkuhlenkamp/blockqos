package com.ibm.block.placement.var.intf;

import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.placement.var.impl.ConModel;
import com.ibm.block.placement.var.impl.VarModel;

public interface ConModelBuilderInterface {

	public ConModel build(PlacementModel p, VarModel v);
	
}

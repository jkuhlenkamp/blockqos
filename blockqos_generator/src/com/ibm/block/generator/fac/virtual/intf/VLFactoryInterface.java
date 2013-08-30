package com.ibm.block.generator.fac.virtual.intf;

import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;

public interface VLFactoryInterface extends VFactoryInterface {

	public VLink create(VMachine end1, VMachine end2);
	public VLink create(VMachine end1, VVolume end2);
	
}

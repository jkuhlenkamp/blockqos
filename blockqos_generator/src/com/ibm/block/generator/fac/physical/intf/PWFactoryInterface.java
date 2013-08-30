package com.ibm.block.generator.fac.physical.intf;

import com.ibm.block.model.dc.impl.PSwitch;

public interface PWFactoryInterface extends PFactoryInterface {

	public PSwitch create();
}

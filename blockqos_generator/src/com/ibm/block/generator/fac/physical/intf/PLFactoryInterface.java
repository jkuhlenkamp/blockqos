package com.ibm.block.generator.fac.physical.intf;

import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;

public interface PLFactoryInterface extends PFactoryInterface {

	public PLink create(PMachine end1, PStorage end2);
	public PLink create(PMachine end1, PSwitch end2);
	public PLink create(PStorage end1, PSwitch end2);
	public PLink create(PSwitch end1, PSwitch end2);
}

package com.ibm.block.model.app.intf;

import com.ibm.block.model.app.impl.VEntity;

public interface VLinkInterface extends VEntityInterface {

	// Getters
	public VEntity[] getEnds();
	public VEntity getOtherEnd(VEntity entity);
	public VEntity getEnd1();
	public VEntity getEnd2();
	
	
	// Util
	public boolean hasEnd(VEntity entity);
}

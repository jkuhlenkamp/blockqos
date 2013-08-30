package com.ibm.block.model.dc.intf;

import com.ibm.block.model.dc.impl.PEntity;

public interface PLinkInterface extends PEntityInterface {

	// Getters
	public PEntity[] getEnds();
	public PEntity getOtherEnd(PEntity entity);
		
	// Util
	public boolean hasEnd(PEntity entity);
}

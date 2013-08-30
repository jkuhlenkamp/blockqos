package com.ibm.block.model.dc.intf;

import com.ibm.block.model.core.intf.ResourceInterface;
import com.ibm.block.model.dc.impl.PEntity;

public interface ResourceOfferInterface extends ResourceInterface {

	// Getters
	public Integer getCapacity();
	public Integer getUsage();
	public Integer getUtilization();
	public Integer getLeftCapacity();
	public PEntity getPEntity();
	
	// Setters
	public void setUsage(Integer usage);
	public void setParent(PEntity parent);
}

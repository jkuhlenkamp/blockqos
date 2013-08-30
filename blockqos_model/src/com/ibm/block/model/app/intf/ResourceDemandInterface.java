package com.ibm.block.model.app.intf;

import com.ibm.block.model.app.impl.VEntity;
import com.ibm.block.model.core.intf.ResourceInterface;

public interface ResourceDemandInterface extends ResourceInterface {

	// Getters
	public Integer getAmount();
	public VEntity getPEntity();
	
	// Setters
	public void setVEntity(VEntity parent);
	
}

package com.ibm.block.model.core.impl;

import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.intf.ResourceInterface;

public abstract class Resource extends Entity implements ResourceInterface {

	private ResourceType type;								// The type of the resource according to an Enum
	
	public Resource(ResourceType type) {
		super(type.toString());
		this.type = type;
	}
	
	@Override
	public ResourceType getType() {
		return type;
	}

	@Override
	public boolean hasResourceType(ResourceType type) {
		if( this.type.equals(type) ) {
			return true;
		}
		else {
			return false;
		}
	}

}

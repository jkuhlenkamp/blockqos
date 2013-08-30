package com.ibm.block.model.app.impl;

import com.ibm.block.model.app.intf.VLinkInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.Resource;

public class VLink extends VEntity implements VLinkInterface {
	
	private final VMachine end1;									// First VEntity that is attached to the VLink
	private final VEntity end2;									// Second VEntity that is attached to the VLink
	private static String namePrefix = "vl";
	
	
	public VLink(VMachine end1, VMachine end2) {				// Constructor: Link between two VMachines
		super(namePrefix);
		if( end1 == end2 ) {
			throw new IllegalArgumentException("Error: The same VMachine cannot be" +
					"connected by a VLink! End1:" +end1.pretty()+ ", End2:" +end2.pretty());
		}
		this.end1 = end1;
		this.end2 = end2;
		end1.addVLink(this);									// Attach Link to relation of vm on end1
		end2.addVLink(this);									// Attach Link to relation of vm on end2
	}
	
	public VLink(VMachine end1, VVolume end2) {					// Constructor: Link between a VVolume and a VMachine
		super(namePrefix);
		this.end1 = end1;
		this.end2 = end2;
		end1.addVLink(this);									// Attach Link to relation of vm on end1
		end2.addVLink(this);									// Attach Link to relation of vv on end2
	}
	
	@Override
	public String pretty() {
		String s = super.getName()+"[";
		//s += "class:" +super.getClass()+ ", ";
		//s += "id:" +super.getId()+ ", ";
		s += "end1:" +end1.getName()+ ", ";
		s += "end2:" +end2.getName()+ ", ";
		s += "resources:[";
		for( Resource r : getResourceMap().values() ) {
			s += r.pretty()+ ", ";
		}
		s += "]";
		s += "properties:[";
		for( PropertyType p : getPropertyMap().values() ) {
			s += p+ ", ";
		}
		s += "]";
		s += "]";
		return s;
	}

	/**
	 * Returns an Array of size 2 that holds both <code>VEntity</code> objects that are 
	 * attached by the <code>VLink</code>.
	 * 
	 * @see com.ibm.block.model.app.intf.VLinkInterface#getEnds()
	 */
	@Override
	public VEntity[] getEnds() {
		return new VEntity[]{end1, end2};
	}

	/**
	 * Returns the second <code>VEntity</code> that is attached to the <code>VLink</code>,
	 * if the provided <code>VEntity</code> matches one of the two <code>VEntity</code> that 
	 * are attached to the <code>VLink</code>.
	 * 
	 * @param entity
	 * @see com.ibm.block.model.app.intf.VLinkInterface#getOtherEnd(com.ibm.block.model.app.impl.VEntity)
	 */
	@Override
	public VEntity getOtherEnd(VEntity entity) {
		if( end1.equals(entity) ) {
			return end2;
		}
		if( end2.equals(entity) ) {
			return end1;
		}
		return null;
	}

	/**
	 * Returns a <code>true</code> if the provided <code>VEntity</code> matches one of
	 * the <code>VEntity</code> objects that are attached to the <code>VLink</code>.
	 * 
	 * @param entity
	 * @see com.ibm.block.model.app.intf.VLinkInterface#hasEnd(com.ibm.block.model.app.impl.VEntity)
	 */
	@Override
	public boolean hasEnd(VEntity entity) {
		if( end1.equals(entity) || end2.equals(entity) ) {
			return true;
		}
		return false;
	}

	@Override
	public VMachine getEnd1() {
		return end1;
	}

	@Override
	public VEntity getEnd2() {
		return end2;
	}

}

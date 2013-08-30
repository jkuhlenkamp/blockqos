package com.ibm.block.model.dc.impl;

import com.ibm.block.model.dc.intf.PLinkInterface;

public class PLink extends PEntity implements PLinkInterface {

	private final PEntity end1;									// First PEntity that is attached to the PLink
	private final PEntity end2;									// Second PEntity that is attached to the pLink
	private static String namePrefix = "pl";
	
	public PLink(PMachine end1, PStorage end2) {
		super(namePrefix);
		this.end1 = end1;
		this.end2 = end2;
		end1.addPLink(this);									// Attach Link to relation of vm on end1
		end2.addPLink(this);
	}
	
	public PLink(PMachine end1, PSwitch end2) {
		super(namePrefix);
		this.end1 = end1;
		this.end2 = end2;
		end1.addPLink(this);									// Attach Link to relation of vm on end1
		end2.addPLink(this);
	}
	
	public PLink(PStorage end1, PSwitch end2) {
		super(namePrefix);
		this.end1 = end1;
		this.end2 = end2;
		end1.addPLink(this);									// Attach Link to relation of vm on end1
		end2.addPLink(this);
	}
	
	public PLink(PSwitch end1, PSwitch end2) {
		super(namePrefix);
		this.end1 = end1;
		this.end2 = end2;
		end1.addPLink(this);									// Attach Link to relation of vm on end1
		end2.addPLink(this);
	}

	@Override
	public String pretty() {
		String s = super.getName()+"[";
		//s += "class:" +super.getClass()+ ", ";
		//s += "id:" +super.getId()+ ", ";
		s += "end1:" +end1.getName()+ ", ";
		s += "end2:" + end2.getName();
		s += "]";
		return s;
	}

	@Override
	public PEntity[] getEnds() {
		return new PEntity[]{end1, end2};
	}

	@Override
	public PEntity getOtherEnd(PEntity entity) {
		if( end1.equals(entity) ) {
			return end2;
		}
		if( end2.equals(entity) ) {
			return end1;
		}
		return null;
	}

	@Override
	public boolean hasEnd(PEntity entity) {
		if( end1.equals(entity) || end2.equals(entity) ) {
			return true;
		}
		return false;
	}

}

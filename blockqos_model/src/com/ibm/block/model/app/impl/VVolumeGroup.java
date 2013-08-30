package com.ibm.block.model.app.impl;

import java.util.HashMap;

import com.ibm.block.model.app.intf.VVolumeGroupInterface;
import com.ibm.block.model.core.impl.Entity;

public abstract class VVolumeGroup extends Entity implements VVolumeGroupInterface {

	HashMap<Integer, VVolume> members;
	
	public VVolumeGroup(String name) {
		super(name);
		members = new HashMap<>();
	}

	@Override
	public HashMap<Integer, VVolume> getMembers() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VVolume> clone = (HashMap<Integer, 
				VVolume>) members.clone();
		return clone;
	}

	@Override
	public void addMember(VVolume vv) {
		members.put(vv.getId(), vv);
	}

	@Override
	public boolean hasMember(VVolume vv) {
		if( members.containsKey(vv.getId()) ) {
			return true;
		}
		return false;
	}
	
	@Override
	public String pretty() {
		String s = super.getName()+"[";
		//s += "class:" +super.getClass()+ ", ";
		//s += "id:" +super.getId()+ ", ";
		//s += "name:" +super.getName()+ ", ";
		s += "members:[";
		for( VVolume vv : members.values() ) {
			s += vv.pretty()+ ", ";
		}
		s += "]";
		s += "]";
		return s;
	}
}

package com.ibm.block.generator.fac.virtual.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.generator.fac.core.impl.EntityFactory;
import com.ibm.block.generator.fac.virtual.intf.VVGroupFactoryInterface;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class VVGroupFactory extends EntityFactory implements VVGroupFactoryInterface {

	private HashMap<Integer, VVolume> members;
	
	public VVGroupFactory(PlacementModel model) {
		super(model);
	}

	@Override
	public void addMember(VVolume newMember) {
		members.put(newMember.getId(), newMember);
	}

	@Override
	public ArrayList<VVolume> getMemberList() {
		ArrayList<VVolume> memberList = new ArrayList<>();
		for( VVolume vv : members.values() ) {
			memberList.add(vv);
		}
		return memberList;
	}

	@Override
	public HashMap<Integer, VVolume> getMemberMap() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VVolume> clone = (HashMap<Integer, 
				VVolume>) members.clone();
		return clone;
	}

}

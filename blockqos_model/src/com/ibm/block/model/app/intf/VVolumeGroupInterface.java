package com.ibm.block.model.app.intf;

import java.util.HashMap;

import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.intf.EntityInterface;

public interface VVolumeGroupInterface extends EntityInterface {

	public HashMap<Integer, VVolume> getMembers();

	public void addMember(VVolume vv);

	public boolean hasMember(VVolume vv);
	
}

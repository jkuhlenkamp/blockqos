package com.ibm.block.model.app.intf;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;

public interface VMachineInterface extends VEntityInterface {
	
	// Getters
	public HashMap<Integer, VMachine> getConnectedVMachines();
	public HashMap<Integer, VVolume> getConnectedVVolumes();
	public VLink getVLink(VMachine vm);
	public VLink getVLink(VVolume vv);
	public ArrayList<VLink> getVLinkList();
	
	// Setters
	public boolean addVLink(VLink vl);
	
	// Util
	public boolean isConnectedTo(VMachine vm);
	public boolean isConnectedTo(VVolume vv);
}
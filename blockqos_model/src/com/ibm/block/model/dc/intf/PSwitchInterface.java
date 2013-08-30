package com.ibm.block.model.dc.intf;

import java.util.HashMap;

import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;

public interface PSwitchInterface extends PEntityInterface {

	// Getters
	public HashMap<Integer, PMachine> getConnectedPMachines();
	public HashMap<Integer, PStorage> getConnectedPStorages();
	public HashMap<Integer, PSwitch> getConnectedPSwitches();
	public PLink getPLink(PMachine pm);
	public PLink getPLink(PStorage ps);
	public PLink getPLink(PSwitch pw);
	
	// Setters
	public boolean addPLink(PLink pl);
			
	// Util
	public boolean isConnectedTo(PMachine pm);
	public boolean isConnectedTo(PStorage ps);
	public boolean isConnectedTo(PSwitch pw);
	
	public boolean hasPMachine();
	public boolean hasPStorage();
	public boolean hasPSwitch();
}

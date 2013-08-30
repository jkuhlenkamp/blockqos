package com.ibm.block.model.dc.intf;

import java.util.HashMap;

import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;

public interface PMachineInterface extends PEntityInterface {

	// Getters
	public PSwitch getConnectedPSwitch();
	public HashMap<Integer, PStorage> getConnectedPStorages();
	public PLink getSwitchPLink();
	public PLink getPLink(PStorage ps);
	public HashMap<Integer, PLink> getPLinksOnPath(PMachine pm);
	public HashMap<Integer, PLink> getPLinksOnPath(PStorage pm);
	public HashMap<Integer, PSwitch> getPSwitchesOnPath(PMachine target);
	//public HashMap<Integer, PSwitch> getPswitchesOnPath(PStorage target);
	public HashMap<Integer, PSwitch> getPSwitchesOnPath(PStorage target);
	
	// Setters
	public boolean addPLink(PLink pl);
		
	// Util
	
	/**
	 * Returns true if the PMachine is connected to a PSwitch through a PLink.
	 * @return
	 */
	public boolean hasSwitch();
	
	/**
	 * Returns true if the PMachine is connected to a provided
	 * PStorage via a PLink.
	 * @param ps
	 * @return
	 */
	public boolean isConnectedTo(PStorage ps);
	
	/**
	 * Returns true if the PMachine is locally or through PSwitches connected 
	 * to a provided PStorage.
	 * @param target
	 * @return
	 */
	public boolean hasPathTo(PStorage target);
	public boolean hasPathTo(PMachine target);
}

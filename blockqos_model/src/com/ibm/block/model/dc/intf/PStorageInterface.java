package com.ibm.block.model.dc.intf;

import java.util.HashMap;

import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;

public interface PStorageInterface extends PEntityInterface {
	
	// Getters
		public PSwitch getConnectedPSwitch();
		public PMachine getConnectedPMachine();
		public PLink getSwitchPLink();
		public PLink getPMachineLink();
		public HashMap<Integer, PLink> getPLinksOnPath(PMachine target);
		public HashMap<Integer, PLink> getPLinksOnPath(PStorage target);
		public HashMap<Integer, PSwitch> getPSwitchesOnPath(PMachine target);
		public HashMap<Integer, PSwitch> getPSwitchesOnPath(PStorage target);
		
		// Setters
		public boolean addPLink(PLink pl);
			
		// Util
		
		/**
		 * Returns true if the PStorage is connected to a PSwitch through a PLink.
		 * @return
		 */
		public boolean hasPSwitch();
		
		/**
		 * Returns true if the PStorage is connected to a PMachine through a PLink.
		 * @return
		 */
		public boolean hasPMachine();
		
		/**
		 * Returns true if the provided PMachine is connected to the PStorage.
		 * 
		 * @param ps
		 * @return
		 */
		public boolean isConnectedTo(PMachine pm);
		
		/**
		 * Returns true if the provided PSwitch is connected to the PStorage.
		 * 
		 * @param ps
		 * @return
		 */
		public boolean isConnectedTo(PSwitch pw);
		
		/**
		 * Returns true if the PStorage is connected to a provided 
		 * PStorage through PSwitches.
		 * 
		 * @param target
		 * @return
		 */
		public boolean hasPathTo(PStorage target);
		
		/**
		 * Returns true if the PStorage is connected to a provided 
		 * PStorage through PSwitches .
		 * 
		 * @param target
		 * @return
		 */
		public boolean hasPathTo(PMachine target);
}

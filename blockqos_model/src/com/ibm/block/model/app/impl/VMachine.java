package com.ibm.block.model.app.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.model.app.intf.VMachineInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.Resource;

public class VMachine extends VEntity implements VMachineInterface {

	private static String namePrefix = "vm";
	private HashMap<Integer, VMachine> connectedVms;
	private HashMap<Integer, VVolume> connectedVvs;
	private HashMap<Integer, VLink> connectedVls;					// Key: Id of VE on the other side
	
	public VMachine() {
		super(namePrefix);
		connectedVms = new HashMap<>();
		connectedVvs = new HashMap<>();
		connectedVls = new HashMap<>();
	}

	@Override
	public String pretty() {
		String s = super.getName()+"[";
		//s += "class:" +super.getClass()+ ", ";
		//s += "id:" +super.getId()+ ", ";
		//s += "name:" +super.getName()+ ", ";
		s += "vls:[";
		for( VLink vl : connectedVls.values() ) {
			s += vl.pretty()+ ", ";
		}
		s += "]";
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

	@Override
	public HashMap<Integer, VMachine> getConnectedVMachines() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VMachine> clone = (HashMap<Integer, 
				VMachine>) connectedVms.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, VVolume> getConnectedVVolumes() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VVolume> clone = (HashMap<Integer, 
				VVolume>) connectedVvs.clone();
		return clone;
	}

	@Override
	public boolean isConnectedTo(VMachine vm) {
		if( connectedVms.containsKey(vm.getId()) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isConnectedTo(VVolume vv) {
		if( connectedVvs.containsKey(vv.getId()) ) {
			return true;
		}
		return false;
	}

	@Override
	public VLink getVLink(VMachine vm) {
		VLink r = connectedVls.get(vm.getId());
		if( r == null ) {
			throw new InvalidParameterException("Error: The provided VMachine object is" +
					"not registered with a VLink! VMachine:" +vm.pretty()+ ", this:" +pretty());
		}
		return r;
	}

	@Override
	public VLink getVLink(VVolume vv) {
		VLink r = connectedVls.get(vv.getId());
		if( r == null ) {
			throw new InvalidParameterException("Error: The provided VVolume object is" +
					"not registered with a VLink! VVolume:" +vv.pretty()+ ", this:" +pretty());
		}
		return r;
	}

	@Override
	public boolean addVLink(VLink vl) {
		if( vl.hasEnd(this) ) {
			VEntity ve = vl.getOtherEnd(this);
			connectedVls.put(ve.getId(), vl);				// Adds vl with the id of the connected ve
			if( ve != null && ve instanceof VMachine ) {
				connectedVms.put(ve.getId(), (VMachine) ve);
			}
			else {
				if( ve != null && ve instanceof VVolume ) {
					connectedVvs.put(ve.getId(), (VVolume) ve);
				}
				else{
					throw new InvalidParameterException("Error: The VLink object to connect " +
							"has no registered connected VMachine or VVolume on the other end! " +
							"VLink:" +vl.pretty()+ ", this:" +pretty()+ "End2:" +ve.pretty());
				}
			}
			return true;
		}
		else {
			throw new InvalidParameterException("Error: The VLink object to connect " +
					"has no registered attachment to the VMachine! VLink:" +vl.pretty()+ 
					", this: " +pretty());
		}
	}

	@Override
	public ArrayList<VLink> getVLinkList() {
		return new ArrayList<VLink>(connectedVls.values());
	}

}

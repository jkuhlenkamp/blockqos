package com.ibm.block.model.dc.impl;

import java.security.InvalidParameterException;
import java.util.HashMap;

import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.Resource;
import com.ibm.block.model.dc.intf.PSwitchInterface;

public class PSwitch extends PEntity implements PSwitchInterface {

	private static String namePrefix = "pw";
	private HashMap<Integer, PMachine> connectedPms;
	private HashMap<Integer, PStorage> connectedPss;
	private HashMap<Integer, PSwitch> connectedPws;
	private HashMap<Integer, PLink> connectedPls;
	
	public PSwitch() {
		super(namePrefix);
		connectedPms = new HashMap<>();
		connectedPss = new HashMap<>();
		connectedPws = new HashMap<>();
		connectedPls = new HashMap<>();
	}
	
	@Override
	public String pretty() {
		String s = super.getName()+"[";
		//s += "class:" +super.getClass()+ ", ";
		//s += "id:" +super.getId()+ ", ";
		//s += "name:" +super.getName()+ ", ";
		s += "pls:[";
		for( PLink pl : connectedPls.values() ) {
			s += pl.pretty()+ ", ";
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
	public HashMap<Integer, PMachine> getConnectedPMachines() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PMachine> clone = (HashMap<Integer, 
				PMachine>) connectedPms.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, PStorage> getConnectedPStorages() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PStorage> clone = (HashMap<Integer, 
				PStorage>) connectedPss.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, PSwitch> getConnectedPSwitches() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PSwitch> clone = (HashMap<Integer, 
				PSwitch>) connectedPws.clone();
		return clone;
	}

	@Override
	public PLink getPLink(PMachine pm) {
		if( pm != null && isConnectedTo(pm) ) {
			return connectedPls.get(pm.getId());
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"PMachine is not connected by a PLink! this:" +
					pretty()+ ", PMachine:" +pm.pretty());
		}
	}

	@Override
	public PLink getPLink(PStorage ps) {
		if( ps != null && isConnectedTo(ps) ) {
			return connectedPls.get(ps.getId());
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"PStorage is not connected by a PLink! this:" +
					pretty()+ ", PStoragee:" +ps.pretty());
		}
	}

	@Override
	public PLink getPLink(PSwitch pw) {
		if( pw != null && isConnectedTo(pw) ) {
			return connectedPls.get(pw.getId());
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"PSwitch is not connected by a PLink! this:" +
					pretty()+ ", PSwitch:" +pw.pretty());
		}
	}

	@Override
	public boolean addPLink(PLink pl) {
		if( pl.hasEnd(this) ) {					// Check if this PSwitch is registered @PLink
			PEntity pe = pl.getOtherEnd(this);
			if( pe != null ) {					// Check for PEntity @end2 of the PLink
				if( pe instanceof PSwitch ) {	// Check if end2 is PSwitch
					connectedPws.put(pe.getId(), (PSwitch) pe);
					connectedPls.put(pe.getId(), pl);
				}
				else {
					if( pe instanceof PMachine ) {
						connectedPms.put(pe.getId(), (PMachine) pe);
						connectedPls.put(pe.getId(), pl);
					}
					else {
						if( pe instanceof PStorage ) {
							connectedPss.put(pe.getId(), (PStorage) pe);
							connectedPls.put(pe.getId(), pl);
						}
						else {
							throw new InvalidParameterException("Error: The PLink object to connect " +
									"has no registered connected PSwitch, PMachine or PStoarge on the other end! " +
									"PLink:" +pl.pretty()+ ", this:" +pretty()+ "End2:" +pe.pretty());
						}
					}
				}
			}
			else {
				throw new InvalidParameterException("Error: The PLink object to connect " +
						"has no registered PEntity on the other end! " +
						"PLink:" +pl.pretty()+ ", this:" +pretty() );
			}
		}
		else {
			throw new InvalidParameterException("Error: The PLink object to connect " +
					"has no reference to this PSwitch!" +
					"PLink:" +pl.pretty()+ ", this:" +pretty() );
		}
		return false;
	}

	@Override
	public boolean isConnectedTo(PMachine pm) {
		if( connectedPms.containsKey(pm.getId()) &&
				connectedPls.containsKey(pm.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isConnectedTo(PStorage ps) {
		boolean isConnected = false;
		if( connectedPss.containsKey(ps.getId()) &&
				connectedPls.containsKey(ps.getId())) {
			isConnected = true;
		}
		return isConnected;
	}

	@Override
	public boolean isConnectedTo(PSwitch pw) {
		if( connectedPws.containsKey(pw.getId()) &&
				connectedPls.containsKey(pw.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPMachine() {
		if( connectedPms.size() > 0 ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPStorage() {
		if( connectedPss.size() > 0 ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPSwitch() {
		if( connectedPws.size() > 0 ) {
			return true;
		}
		return false;
	}

}

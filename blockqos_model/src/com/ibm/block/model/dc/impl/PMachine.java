package com.ibm.block.model.dc.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.Resource;
import com.ibm.block.model.dc.intf.PMachineInterface;

public class PMachine extends PEntity implements PMachineInterface {

	private static String namePrefix = "pm";
	private PSwitch connectedPw;
	private HashMap<Integer, PStorage> connectedPss;
	private HashMap<Integer, PLink> connectedPls;
	
	private boolean recPathCheck(PEntity target, PSwitch sw, 
			ArrayList<PSwitch> visited) {
		
		boolean isOnPath = false;
		
		if( target instanceof PMachine ) {
			if( sw.isConnectedTo((PMachine) target) ) {
				isOnPath = true;
			}
		}
		if( target instanceof PStorage ) {
			if(sw.isConnectedTo((PStorage) target) ) {
				isOnPath = true;
			}
		}
		
		visited.add(sw);
		ArrayList<PSwitch> candidates = new ArrayList<>();
		candidates.addAll( sw.getConnectedPSwitches().values() );
		for( int i = 0; isOnPath == false && i < candidates.size(); i++ ) {
			if( !visited.contains(candidates.get(i)) ) {
				isOnPath = recPathCheck(target, candidates.get(i), visited);
			}
		}
		return isOnPath;
	}
	
	private ArrayList<PSwitch> recGetSwitchesOnPath(PEntity target, 
			PSwitch sw, ArrayList<PSwitch> visited) {
		ArrayList<PSwitch> onPath = new ArrayList<>();
		
		if( target instanceof PMachine ) {
			if( sw.isConnectedTo((PMachine) target) ) {
				onPath.add(sw);
				return onPath;
			}
		}
		if( target instanceof PStorage ) {
			if(sw.isConnectedTo((PStorage) target) ) {
				onPath.add(sw);
				return onPath;
			}
		}
		
		visited.add(sw);
		ArrayList<PSwitch> candidates = new ArrayList<>();
		candidates.addAll( sw.getConnectedPSwitches().values() );
		for( int i = 0; onPath.isEmpty() && i < candidates.size(); i++ ) {
			if( !visited.contains(candidates.get(i)) ) {
				onPath.addAll( recGetSwitchesOnPath( target, 
						candidates.get(i), visited) );
			}
		}
		if( !onPath.isEmpty() ) {
			onPath.add(sw);
		}
		return onPath;
	}
	/*
	private ArrayList<PLink> recGetLinksOnPath(PEntity origin, PEntity target, 
			PSwitch sw, ArrayList<PSwitch> visited) {
		ArrayList<PLink> onPath = new ArrayList<>();
		
		if( target instanceof PMachine ) {
			if( sw.isConnectedTo((PMachine) target) ) {
				onPath.add(sw.getPLink((PMachine) target));
				if( origin instanceof PMachine ) {
					onPath.add(sw.getPLink((PMachine) origin) );
				}
				if( origin instanceof PStorage ) {
					onPath.add(sw.getPLink((PStorage) origin) );
				}
				if( origin instanceof PSwitch ) {
					onPath.add(sw.getPLink((PSwitch) origin) );
				}
				return onPath;
			}
		}
		if( target instanceof PStorage ) {
			if(sw.isConnectedTo((PStorage) target) ) {
				onPath.add(sw.getPLink((PStorage) target));
				if( origin instanceof PMachine ) {
					onPath.add(sw.getPLink((PMachine) origin) );
				}
				if( origin instanceof PStorage ) {
					onPath.add(sw.getPLink((PStorage) origin) );
				}
				if( origin instanceof PSwitch ) {
					onPath.add(sw.getPLink((PSwitch) origin) );
				}
				return onPath;
			}
		}
		
		visited.add(sw);
		ArrayList<PSwitch> candidates = new ArrayList<>();
		candidates.addAll( sw.getConnectedPSwitches().values() );
		for( int i = 0; onPath.isEmpty() && i < candidates.size(); i++ ) {
			if( !visited.contains(candidates.get(i)) ) {
				onPath.addAll( recGetLinksOnPath( sw, target, 
						candidates.get(i), visited) );
			}
		}
		if( !onPath.isEmpty() ) {
			if( origin instanceof PMachine ) {
				onPath.add(sw.getPLink((PMachine) origin) );
			}
			if( origin instanceof PStorage ) {
				onPath.add(sw.getPLink((PStorage) origin) );
			}
			if( origin instanceof PSwitch ) {
				onPath.add(sw.getPLink((PSwitch) origin) );
			}
		}
		return onPath;
	}
	*/
	public PMachine() {
		super(namePrefix);
		connectedPw = null;
		connectedPls = new HashMap<>();
		connectedPss = new HashMap<>();
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
	public PSwitch getConnectedPSwitch() {
		return connectedPw;
	}
	
	/**
	 * Returns PStorages that are directly attached to the PMachine.
	 */
	@Override
	public HashMap<Integer, PStorage> getConnectedPStorages() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PStorage> clone = (HashMap<Integer, 
				PStorage>) connectedPss.clone();
		return clone;
	}

	@Override
	public PLink getSwitchPLink() {
		if( connectedPls.containsKey(connectedPw.getId()) ) {
			return connectedPls.get(connectedPw.getId());
		}
		else {
			throw new IllegalArgumentException("Error: No PSwitch " +
					"is connected by a PLink! this:" + pretty() );
		}
	}

	@Override
	public PLink getPLink(PStorage ps) {
		if( connectedPls.containsKey(ps.getId()) ) {
			return connectedPls.get(ps.getId());
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"PStorage is not connected by a PLink! this:" +
					pretty()+ ", PStorage:" +ps.pretty());
		}
	}
	
	/*
	@Override
	public HashMap<Integer, PLink> getPLinksOnPath(PMachine target) {
		HashMap<Integer, PLink> result = new HashMap<>();
		
		ArrayList<PLink> onPath = new ArrayList<>();
		ArrayList<PSwitch> visited;
		if( connectedPw != null ) {
			visited = new ArrayList<>();
			onPath.addAll( recGetLinksOnPath(this, target, connectedPw, visited) );
		}
			
		for(PLink pl : onPath) {
			result.put(pl.getId(), pl);
		}
		return result;
	}
	*/
	/*
	@Override
	public HashMap<Integer, PLink> getPLinksOnPath(PStorage target) {
		HashMap<Integer, PLink> result = new HashMap<>();
		// Get PLinks to locally connected PSs
		if( connectedPss.containsKey(target.getId()) && 
				connectedPss.get(target.getId()).equals(target) ) {
			result.put(getPLink(target).getId(), getPLink(target));
			return result;
		}
		//System.out.println("Found local PLs: " +result.keySet()+ ", stored connected PSs: "+connectedPss.keySet());
		
		ArrayList<PLink> onPath = new ArrayList<>();
		ArrayList<PSwitch> visited;
		if( connectedPw != null ) {
			visited = new ArrayList<>();
			onPath.addAll( recGetLinksOnPath(this, target, connectedPw, visited) );
		}
		
		for(PLink pl : onPath) {
			result.put(pl.getId(), pl);
		}
		return result;
	}
	*/
	/*
	@Override
	public HashMap<Integer, PSwitch> getPSwitchesOnPath(PMachine target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		ArrayList<PSwitch> onPath = new ArrayList<>();
		ArrayList<PSwitch> visited;
		
		if(this == target) {
			if( hasSwitch() ) {
				visited = new ArrayList<>();
				onPath.addAll( recGetSwitchesOnPath(target, connectedPw, visited) );
			}
			
			for(PSwitch sw : onPath) {
				result.put(sw.getId(), sw);
			}
		}
		return result;
	}
	*/
	/*
	@Override
	public HashMap<Integer, PSwitch> getPswitchesOnPath(PStorage target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		ArrayList<PSwitch> onPath = new ArrayList<>();
		ArrayList<PSwitch> visited;
		
		if( hasSwitch() ) {
			visited = new ArrayList<>();
			onPath.addAll( recGetSwitchesOnPath(target, connectedPw, visited) );
		}
		
		for(PSwitch sw : onPath) {
			result.put(sw.getId(), sw);
		}
		
		return result;
	}
	*/
	@Override
	public boolean addPLink(PLink pl) {
		
		if( pl.hasEnd(this) ) {					// Check if this PMachine is registered @PLink
			PEntity pe = pl.getOtherEnd(this);
			if( pe != null ) {					// Check for PEntity @end2 of the PLink
				if( pe instanceof PSwitch ) {	// Check if end2 is PSwitch
					connectedPw = (PSwitch) pe;
					connectedPls.put(pe.getId(), pl);
				}
				else {
					if( pe instanceof PStorage ) {
						connectedPss.put(pe.getId(), (PStorage) pe);
						connectedPls.put(pe.getId(), pl);
					}
					else {
						throw new InvalidParameterException("Error: The PLink object to connect " +
								"has no registered connected PStorage or PSwitch on the other end! " +
								"PLink:" +pl.pretty()+ ", this:" +pretty()+ "End2:" +pe.pretty());
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
					"has no reference to this PMachine!" +
					"PLink:" +pl.pretty()+ ", this:" +pretty() );
		}
		return false;
	}

	/**
	 * Returns true if the PMachine has a PSwitch and the PSwitch is connected
	 * via a PLink.
	 * 
	 * @see com.ibm.block.model.dc.intf.PMachineInterface#hasSwitch()
	 */
	@Override
	public boolean hasSwitch() {
		if( connectedPw != null && connectedPls.containsKey(
				connectedPw.getId()) && connectedPls.get(connectedPw.getId()
				).getOtherEnd(this).equals(connectedPw) ) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the provided PStorage is connected to the PMachine via
	 * a single PLink. Represents directly attached PStorage to a PMachine.
	 * 
	 * @see com.ibm.block.model.dc.intf.PMachineInterface#isConnectedTo(com.ibm.block.model.dc.impl.PStorage)
	 */
	@Override
	public boolean isConnectedTo(PStorage ps) {
		if( connectedPss.containsKey(ps.getId()) && 
				connectedPss.containsValue(ps) &&
				connectedPls.get(ps.getId()).getOtherEnd(this).equals(ps)) {
			return true;
		}
		return false;
	}

	
	
	@Override
	public boolean hasPathTo(PStorage target) {
		if( isConnectedTo(target) ) {				// Check if target is local Storage
			return true;
		}
		if( hasSwitch() && connectedPw.isConnectedTo(target) ) {
			return true;
		}
		ArrayList<PSwitch> visited = new ArrayList<>();
		visited.add(connectedPw);
		return recPathCheck(target, connectedPw, visited);
	}

	@Override
	public boolean hasPathTo(PMachine target) {
		if ( hasSwitch() ) {
			if( connectedPw.isConnectedTo(target) ) {
				return true;
			}
			ArrayList<PSwitch> visited = new ArrayList<>();
			visited.add(connectedPw);
			return recPathCheck(target, connectedPw, visited);
		}
		return false;
	}

	// Reimplementation of method
	@Override
	public HashMap<Integer, PSwitch> getPSwitchesOnPath(PStorage target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		if(! (connectedPss.containsValue(target))) {
			result.putAll( recGetPSwitchesOnPath(connectedPw, target) );
		}
		// Change for PLinks
		return result;
	}
	
	private HashMap<Integer, PSwitch> recGetPSwitchesOnPath(PSwitch origin, PStorage target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		if(! (origin.getConnectedPStorages().containsValue(target))) {
			for(PSwitch pw : origin.getConnectedPSwitches().values()) {
				if( pw != origin ) {
					result.putAll(recGetPSwitchesOnPath(pw, target));
					if(result.size() > 0) {
						result.put(origin.getId(), origin);
					}
				}
			}
		}
		else{
			result.put(origin.getId(), origin);
		}
		return result;
	}
	
	@Override
	public HashMap<Integer, PSwitch> getPSwitchesOnPath(PMachine target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		if(!(this == target) ) {
			result.putAll( recGetPSwitchesOnPath(connectedPw, target) );
		}
		return result;
	}
	
	private HashMap<Integer, PSwitch> recGetPSwitchesOnPath(PSwitch origin, PMachine target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		if(! (origin.getConnectedPMachines().containsValue(target))) {
			for(PSwitch pw : origin.getConnectedPSwitches().values()) {
				if( pw != origin ) {
					result.putAll(recGetPSwitchesOnPath(pw, target));
					if(result.size() > 0) {
						result.put(origin.getId(), origin);
					}
				}
			}
		}
		else{
			result.put(origin.getId(), origin);
		}
		return result;
	}
	
	@Override
	public HashMap<Integer, PLink> getPLinksOnPath(PMachine target) {
		HashMap<Integer, PLink> result = new HashMap<>();
		if( this.getId() != target.getId() ) {
			result.putAll( recGetPLinksOnPath(connectedPw, target, this) );
			if(result.size() > 0) {
				result.put(connectedPw.getPLink(this).getId(), connectedPw.getPLink(this));
			}
		}
		return result;
	}
	
	private HashMap<Integer, PLink> recGetPLinksOnPath(PSwitch origin, PMachine target, PEntity last) {
		HashMap<Integer, PLink> result = new HashMap<>();
		if(! (origin.getConnectedPMachines().containsValue(target))) {
			for(PSwitch pw : origin.getConnectedPSwitches().values()) {
				if( pw != last ) {
					result.putAll(recGetPLinksOnPath(pw, target, origin));
					if(result.size() > 0) {
						result.put(origin.getPLink(pw).getId(), origin.getPLink(pw));
					}
				}
			}
		}
		else{
			result.put(origin.getPLink(target).getId(), origin.getPLink(target));
		}
		return result;
	}
	
	@Override
	public HashMap<Integer, PLink> getPLinksOnPath(PStorage target) {
		HashMap<Integer, PLink> result = new HashMap<>();
		if(! (connectedPss.containsValue(target))) {
			result.putAll( recGetPLinksOnPath(connectedPw, target, this) );
			if(result.size() > 0) {
				result.put(connectedPw.getPLink(this).getId(), connectedPw.getPLink(this));
			}
		}
		else{
			result.put(this.getPLink(target).getId(), this.getPLink(target));
		}
		return result;
	}
	
	private HashMap<Integer, PLink> recGetPLinksOnPath(PSwitch origin, PStorage target, PEntity last) {
		HashMap<Integer, PLink> result = new HashMap<>();
		if(! (origin.getConnectedPStorages().containsValue(target))) {
			for(PSwitch pw : origin.getConnectedPSwitches().values()) {
				if( pw.getId() != last.getId() ) {
					result.putAll(recGetPLinksOnPath(pw, target, origin));
					if(result.size() > 0) {
						result.put(origin.getPLink(pw).getId(), origin.getPLink(pw));
					}
				}
			}
		}
		else{
			result.put(origin.getPLink(target).getId(), origin.getPLink(target));
		}
		return result;
	}

}

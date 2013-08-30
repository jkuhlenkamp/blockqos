package com.ibm.block.model.dc.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.Resource;
import com.ibm.block.model.dc.intf.PStorageInterface;

public class PStorage extends PEntity implements PStorageInterface {

	private static String namePrefix = "ps";
	private PMachine connectedPm;						// PStorage can only be directly connected to a single PMachine
	private PSwitch connectedPw;						// PStorage can only be connected to a single PSwitch	
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
	
	public PStorage() {
		super(namePrefix);
		connectedPm = null;
		connectedPw = null;
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
	public PSwitch getConnectedPSwitch() {
		return connectedPw;
	}

	@Override
	public PMachine getConnectedPMachine() {
		return connectedPm;
	}

	@Override
	public PLink getSwitchPLink() {
		return connectedPls.get( connectedPw.getId() );
	}

	@Override
	public PLink getPMachineLink() {
		return connectedPls.get( connectedPm.getId() );
	}

	@Override
	public HashMap<Integer, PLink> getPLinksOnPath(PMachine target) {
		HashMap<Integer, PLink> result = new HashMap<>();
		if( hasPMachine() && connectedPm.equals(target) ) {
			result.put(getPMachineLink().getId(), getPMachineLink());
			return result;
		}
		
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

	@Override
	public HashMap<Integer, PLink> getPLinksOnPath(PStorage target) {
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

	@Override
	public HashMap<Integer, PSwitch> getPSwitchesOnPath(PMachine target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		ArrayList<PSwitch> onPath = new ArrayList<>();
		ArrayList<PSwitch> visited;
		
		if( connectedPw != null ) {
			visited = new ArrayList<>();
			onPath.addAll( recGetSwitchesOnPath(target, connectedPw, visited) );
		}
		
		for(PSwitch sw : onPath) {
			result.put(sw.getId(), sw);
		}
		
		return result;
	}

	@Override
	public HashMap<Integer, PSwitch> getPSwitchesOnPath(PStorage target) {
		HashMap<Integer, PSwitch> result = new HashMap<>();
		ArrayList<PSwitch> onPath = new ArrayList<>();
		ArrayList<PSwitch> visited;
		
		if( connectedPw != null ) {
			visited = new ArrayList<>();
			onPath.addAll( recGetSwitchesOnPath(target, connectedPw, visited) );
		}
		
		for(PSwitch sw : onPath) {
			result.put(sw.getId(), sw);
		}
		
		return result;
	}

	@Override
	public boolean addPLink(PLink pl) {
		if( pl.hasEnd(this) ) {					// Check if this PStorage is registered @PLink
			PEntity pe = pl.getOtherEnd(this);
			if( pe != null ) {					// Check for PEntity @end2 of the PLink
				if( pe instanceof PSwitch ) {	// Check if end2 is PSwitch
					connectedPw = (PSwitch) pe;
					connectedPls.put(pe.getId(), pl);
				}
				else {
					if( pe instanceof PMachine ) {
						connectedPm = (PMachine) pe;
						connectedPls.put(pe.getId(), pl);
					}
					else {
						throw new InvalidParameterException("Error: The PLink object to connect " +
								"has no registered connected PMachine or PSwitch on the other end! " +
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
					"has no reference to this PStorage!" +
					"PLink:" +pl.pretty()+ ", this:" +pretty() );
		}
		return false;
	}

	@Override
	public boolean hasPSwitch() {
		if( connectedPw != null ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPMachine() {
		if( connectedPm != null ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isConnectedTo(PMachine pm) {
		if( hasPMachine() && connectedPm.equals(pm) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isConnectedTo(PSwitch pw) {
		if( hasPSwitch() && connectedPw.equals(pw) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPathTo(PStorage target) {
		if ( connectedPw != null ) {
			if( connectedPw.isConnectedTo(target) ) {
				return true;
			}
			ArrayList<PSwitch> visited = new ArrayList<>();
			visited.add(connectedPw);
			return recPathCheck(target, connectedPw, visited);
		}
		return false;
	}

	@Override
	public boolean hasPathTo(PMachine target) {
		if( isConnectedTo(target) ) {				// Check if target is local Storage
			return true;
		}
		if ( connectedPw != null ) {
			if( connectedPw.isConnectedTo(target) ) {
				return true;
			}
			ArrayList<PSwitch> visited = new ArrayList<>();
			visited.add(connectedPw);
			return recPathCheck(target, connectedPw, visited);
		}
		return false;
	}

}

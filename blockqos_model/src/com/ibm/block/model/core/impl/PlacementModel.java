package com.ibm.block.model.core.impl;
import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VEntity;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedPathGroup;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedStorageGroup;
import com.ibm.block.model.app.impl.vgroup.ColocatedStorageGroup;
import com.ibm.block.model.app.impl.vgroup.ReplicaGroup;
import com.ibm.block.model.app.impl.vgroup.SamePropertyGroup;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.intf.PlacementModelInterface;
import com.ibm.block.model.dc.impl.PEntity;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;


public class PlacementModel implements PlacementModelInterface {

	private HashMap<Integer, Entity> entities;
	private HashMap<Integer, PEntity> pentities;
	private HashMap<Integer, VEntity> ventities;
	
	private HashMap<Integer, ResourceDemand> rdemands;
	private HashMap<ResourceType, HashMap<Integer, ResourceDemand>> rdemandbytype;
	private HashMap<Integer, VMachine> vmachines;
	private HashMap<Integer, VVolume> vvolumes;
	private HashMap<Integer, VLink> vlinks;
	
	private HashMap<Integer, ReplicaGroup> replicagroups;
	private HashMap<Integer, SamePropertyGroup> samepropgroups;
	private HashMap<Integer, ColocatedStorageGroup> colstoragegroups;
	private HashMap<Integer, AntiColocatedStorageGroup> anticolstoragegroups;
	private HashMap<Integer, AntiColocatedPathGroup> anticolpathgroups;
	
	private HashMap<Integer, ResourceOffer> roffers;
	private HashMap<Integer, PMachine> pmachines;
	private HashMap<Integer, PStorage> pstorages;
	private HashMap<Integer, PSwitch> pswitches;
	private HashMap<Integer, PLink> plinks;
	
	public PlacementModel(){
		entities = new HashMap<>();
		pentities = new HashMap<>();
		ventities = new HashMap<>();
		
		rdemands = new HashMap<>();
		rdemandbytype = new HashMap<>();
		vmachines = new HashMap<>();
		vvolumes = new HashMap<>();
		vlinks = new HashMap<>();
		
		replicagroups = new HashMap<>();
		samepropgroups = new HashMap<>();
		colstoragegroups = new HashMap<>();
		anticolstoragegroups = new HashMap<>();
		anticolpathgroups = new HashMap<>();
		
		roffers = new HashMap<>();
		pmachines = new HashMap<>();
		pstorages = new HashMap<>();
		pswitches = new HashMap<>();
		plinks = new HashMap<>();
	}
	
	@Override
	public Entity getEntity(Integer id) {
		if( hasEntity(id) ) {
			return entities.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"id is not registered as an Entity object!: id:" +id);
		}
	}

	@Override
	public ResourceDemand getResourceDemand(Integer id) {
		if( hasResourceDemand(id) ) {
			return rdemands.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"id is not registered as an ResourceDemand object!: id:" +id);
		}
	}

	@Override
	public VMachine getVMachine(Integer id) {
		if( hasVMachine(id) ) {
			return vmachines.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a VMachine object!: id:" +id);
		}
	}

	@Override
	public VVolume getVVolume(Integer id) {
		if( hasVVolume(id) ) {
			return vvolumes.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as an VVolume object!: id:" +id);
		}
	}

	@Override
	public VLink getVLink(Integer id) {
		if( hasVLink(id) ) {
			return vlinks.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a VLink object!: id:" +id);
		}
	}

	@Override
	public ResourceOffer getResourceOffer(Integer id) {
		if( hasResourceOffer(id) ) {
			return roffers.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a ResourceOffer object!: id:" +id);
		}
	}

	@Override
	public PMachine getPMachine(Integer id) {
		if( hasPMachine(id) ) {
			return pmachines.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a PMachine object!: id:" +id);
		}
	}

	@Override
	public PStorage getPStorage(Integer id) {
		if( hasPStorage(id) ) {
			return pstorages.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a PStorage object!: id:" +id);
		}
	}

	@Override
	public PSwitch getPSwitch(Integer id) {
		if( hasPSwitch(id) ) {
			return pswitches.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"id is not registered as a PSwitch object!: id:" +id);
		}
	}

	@Override
	public PLink getPLink(Integer id) {
		if( hasPLink(id) ) {
			return plinks.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
					"id is not registered as a PLink object!: id:" +id);
		}
	}

	@Override
	public HashMap<Integer, Entity> getEntities() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, Entity> clone = (HashMap<Integer, 
				Entity>) entities.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, ResourceDemand> getResourceDemands() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, ResourceDemand> clone = (HashMap<Integer, 
				ResourceDemand>) rdemands.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, VMachine> getVMachines() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VMachine> clone = (HashMap<Integer, 
				VMachine>) vmachines.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, VVolume> getVVolumes() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VVolume> clone = (HashMap<Integer, 
				VVolume>) vvolumes.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, VLink> getVLinks() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VLink> clone = (HashMap<Integer, 
				VLink>) vlinks.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, ResourceOffer> getResourceOffers() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, ResourceOffer> clone = (HashMap<Integer, 
				ResourceOffer>) roffers.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, PMachine> getPMachines() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PMachine> clone = (HashMap<Integer, 
				PMachine>) pmachines.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, PStorage> getPStorages() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PStorage> clone = (HashMap<Integer, 
				PStorage>) pstorages.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, PSwitch> getPSwitches() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PSwitch> clone = (HashMap<Integer, 
				PSwitch>) pswitches.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, PLink> getPLinks() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PLink> clone = (HashMap<Integer, 
				PLink>) plinks.clone();
		return clone;
	}

	@Override
	public void addEntity(Entity e) {
		entities.put(e.getId(), e);
		if( e instanceof PEntity ) {
			pentities.put(e.getId(), (PEntity) e);
		}
		if( e instanceof VEntity ) {
			ventities.put(e.getId(), (VEntity) e);
		}
		if( e instanceof PLink ) {
			plinks.put(e.getId(), (PLink) e);
		}
		if( e instanceof PMachine ) {
			pmachines.put(e.getId(), (PMachine) e);
		}
		if( e instanceof PStorage) {
			pstorages.put(e.getId(), (PStorage) e);
		}
		if( e instanceof PSwitch) {
			pswitches.put(e.getId(), (PSwitch) e);
		}
		if( e instanceof ResourceDemand) {
			rdemands.put(e.getId(), (ResourceDemand) e);
			if( !rdemandbytype.containsKey(((ResourceDemand) e).getType()) ) {
				rdemandbytype.put( ((ResourceDemand) e).getType(), 
						new HashMap<Integer, ResourceDemand>() );
			}
			rdemandbytype.get(((ResourceDemand) e).getType()).put(
					e.getId(), (ResourceDemand) e);
		}
		if( e instanceof ResourceOffer) {
			roffers.put(e.getId(), (ResourceOffer) e);
		}
		if( e instanceof VLink) {
			vlinks.put(e.getId(), (VLink) e);
		}
		if( e instanceof VMachine) {
			vmachines.put(e.getId(), (VMachine) e);
		}
		if( e instanceof VVolume) {
			vvolumes.put(e.getId(), (VVolume) e);
		}
		if(e instanceof ReplicaGroup) {
			replicagroups.put(e.getId(), (ReplicaGroup) e);
		}
		if(e instanceof SamePropertyGroup) {
			samepropgroups.put(e.getId(), (SamePropertyGroup) e);
		}
		if(e instanceof ColocatedStorageGroup) {
			colstoragegroups.put(e.getId(), (ColocatedStorageGroup) e);
		}
		if(e instanceof AntiColocatedStorageGroup) {
			anticolstoragegroups.put(e.getId(), (AntiColocatedStorageGroup) e);
		}
		if(e instanceof AntiColocatedPathGroup) {
			anticolpathgroups.put(e.getId(), (AntiColocatedPathGroup) e);
		}
	}

	@Override
	public boolean hasEntity(Integer id) {
		if( entities.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasResourceDemand(Integer id) {
		if( rdemands.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasVMachine(Integer id) {
		if( vmachines.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasVVolume(Integer id) {
		if( vvolumes.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasVLink(Integer id) {
		if( vlinks.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPMachine(Integer id) {
		if( pmachines.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPStorage(Integer id) {
		if( pstorages.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPSwitch(Integer id) {
		if( pswitches.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasPLink(Integer id) {
		if( plinks.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasResourceOffer(Integer id) {
		if( roffers.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public Integer getRDemandSum(ResourceType type) {
		Integer result = 0;
		for( ResourceDemand rd : rdemands.values() ) {
			if( rd.hasResourceType(type) ) {
				result += rd.getAmount();
			}
		}
		return result;
	}

	@Override
	public AntiColocatedPathGroup getAntiColocatedPathGroup(Integer id) {
		if( hasAntiColocatedPathGroup(id) ) {
			return anticolpathgroups.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a AntiColocatedPathGroup object!: id:" +id);
		}
	}

	@Override
	public AntiColocatedStorageGroup getAntiColocatedStorageGroup(Integer id) {
		if( hasAntiColocatedStorageGroup(id) ) {
			return anticolstoragegroups.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a AntiColocatedStorageGroup object!: id:" +id);
		}
	}

	@Override
	public ColocatedStorageGroup getColocatedStorageGroup(Integer id) {
		if( hasColocatedStorageGroup(id) ) {
			return colstoragegroups.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a ColocatedStorageGroup object!: id:" +id);
		}
	}

	@Override
	public ReplicaGroup getReplicaGroup(Integer id) {
		if( hasReplicaGroup(id) ) {
			return replicagroups.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a ReplicaGroup object!: id:" +id);
		}
	}

	@Override
	public SamePropertyGroup getSamePropertygroup(Integer id) {
		if( hasSamePropertygroup(id) ) {
			return samepropgroups.get(id);
		}
		else {
			throw new IllegalArgumentException("Error: The provided " +
				"id is not registered as a SamePropertyGroup object!: id:" +id);
		}
	}

	@Override
	public boolean hasAntiColocatedPathGroup(Integer id) {
		if( anticolpathgroups.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasAntiColocatedStorageGroup(Integer id) {
		if( anticolstoragegroups.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasColocatedStorageGroup(Integer id) {
		if( colstoragegroups.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasReplicaGroup(Integer id) {
		if( replicagroups.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasSamePropertygroup(Integer id) {
		if( samepropgroups.containsKey(id) ) {
			return true;
		}
		return false;
	}

	@Override
	public HashMap<Integer, AntiColocatedPathGroup> getAntiColocatedPathGroups() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, AntiColocatedPathGroup> clone = (HashMap<Integer, 
				AntiColocatedPathGroup>) anticolpathgroups.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, AntiColocatedStorageGroup> getAntiColocatedStorageGroups() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, AntiColocatedStorageGroup> clone = (HashMap<Integer, 
				AntiColocatedStorageGroup>) anticolstoragegroups.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, ColocatedStorageGroup> getColocatedStorageGroups() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, ColocatedStorageGroup> clone = (HashMap<Integer, 
				ColocatedStorageGroup>) colstoragegroups.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, ReplicaGroup> getReplicaGroups() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, ReplicaGroup> clone = (HashMap<Integer, 
				ReplicaGroup>) replicagroups.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, SamePropertyGroup> getSamePropertygroups() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, SamePropertyGroup> clone = (HashMap<Integer, 
				SamePropertyGroup>) samepropgroups.clone();
		return clone;
	}

	@Override
	public int[] getPropertyIds() {
		int[] a = new int[PropertyType.values().length];
		int i = 0;
		for(PropertyType t : PropertyType.values()) {
			a[i] = t.getId();
			i++;
		}
		return a;
	}

	@Override
	public HashMap<Integer, ResourceDemand> getResourceDemands(ResourceType type) {
		if( rdemandbytype.containsKey(type) ) {
			@SuppressWarnings("unchecked")
			HashMap<Integer, ResourceDemand> clone = (HashMap<Integer, 
					ResourceDemand>) rdemandbytype.get(type).clone();
			return clone;
		}
		else {
			return new HashMap<Integer, ResourceDemand>();
		}
	}

	@Override
	public int[] getPSwitchIntList() {
		int[] result = new int[pswitches.size()];
		int i = 0;
		for(Integer pw : pswitches.keySet()){
			result[i] = pw;
			i++;
		}
		return result;
	}

	@Override
	public int[] getPLinkIntList() {
		int[] result = new int[plinks.size()];
		int i = 0;
		for(Integer pl : plinks.keySet()){
			result[i] = pl;
			System.out.print(result[i]+ " ");
			i++;
		}
		System.out.println("result length" +result.length);
		return result;
	}

	@Override
	public ArrayList<Integer> getPEntityIntList() {
		return new ArrayList<Integer>( pentities.keySet() );
	}

	@Override
	public HashMap<Integer, PEntity> getPEntities() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, PEntity> clone = (HashMap<
				Integer, PEntity>) pentities.clone();
		return clone;
	}

	@Override
	public HashMap<Integer, VEntity> getVEntities() {
		@SuppressWarnings("unchecked")
		HashMap<Integer, VEntity> clone = (HashMap<
				Integer, VEntity>) ventities.clone();
		return clone;
	}
}

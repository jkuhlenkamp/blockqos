package com.ibm.block.model.core.intf;

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
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.Entity;
import com.ibm.block.model.dc.impl.PEntity;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;

public interface PlacementModelInterface {

	// Getters
	
	public Entity getEntity(Integer id);
	
	public ResourceDemand getResourceDemand(Integer id);
	public HashMap<Integer, ResourceDemand> getResourceDemands(ResourceType type);
	public VMachine getVMachine(Integer id);
	public VVolume getVVolume(Integer id);
	public VLink getVLink(Integer id);
	public AntiColocatedPathGroup getAntiColocatedPathGroup(Integer id);
	public AntiColocatedStorageGroup getAntiColocatedStorageGroup(Integer id);
	public ColocatedStorageGroup getColocatedStorageGroup(Integer id);
	public ReplicaGroup getReplicaGroup(Integer id);
	public SamePropertyGroup getSamePropertygroup(Integer id);
	
	public ResourceOffer getResourceOffer(Integer id);
	//public HashMap<Integer, ResourceOffer> getResourceOffers(ResourceType type);
	public PMachine getPMachine(Integer id);
	public PStorage getPStorage(Integer id);
	public PSwitch getPSwitch(Integer id);
	public PLink getPLink(Integer id);
	
	public HashMap<Integer, Entity> getEntities();
	public HashMap<Integer, PEntity> getPEntities();
	public HashMap<Integer, VEntity> getVEntities();
	
	public HashMap<Integer, ResourceDemand> getResourceDemands();
	public HashMap<Integer, VMachine> getVMachines();
	public HashMap<Integer, VVolume> getVVolumes();
	public HashMap<Integer, VLink> getVLinks();
	public HashMap<Integer, AntiColocatedPathGroup> getAntiColocatedPathGroups();
	public HashMap<Integer, AntiColocatedStorageGroup> getAntiColocatedStorageGroups();
	public HashMap<Integer, ColocatedStorageGroup> getColocatedStorageGroups();
	public HashMap<Integer, ReplicaGroup> getReplicaGroups();
	public HashMap<Integer, SamePropertyGroup> getSamePropertygroups();
	
	public HashMap<Integer, ResourceOffer> getResourceOffers();
	public HashMap<Integer, PMachine> getPMachines();
	public HashMap<Integer, PStorage> getPStorages();
	public HashMap<Integer, PSwitch> getPSwitches();
	public int[] getPSwitchIntList();
	public HashMap<Integer, PLink> getPLinks();
	public int[] getPLinkIntList();
	public ArrayList<Integer> getPEntityIntList();
	
	// Setters
	public void addEntity(Entity e);
	
	// Util
	public Integer getRDemandSum(ResourceType type);
	public int[] getPropertyIds();
	
	public boolean hasEntity(Integer id);
	
	public boolean hasResourceDemand(Integer id);
	public boolean hasVMachine(Integer id);
	public boolean hasVVolume(Integer id);
	public boolean hasVLink(Integer id);
	public boolean hasAntiColocatedPathGroup(Integer id);
	public boolean hasAntiColocatedStorageGroup(Integer id);
	public boolean hasColocatedStorageGroup(Integer id);
	public boolean hasReplicaGroup(Integer id);
	public boolean hasSamePropertygroup(Integer id);
	
	public boolean hasResourceOffer(Integer id);
	public boolean hasPMachine(Integer id);
	public boolean hasPStorage(Integer id);
	public boolean hasPSwitch(Integer id);
	public boolean hasPLink(Integer id);
}

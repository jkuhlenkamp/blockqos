package com.ibm.block.placement.var.impl;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VEntity;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PEntity;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;
import com.ibm.block.placement.var.intf.VarModelBuilderInterface;

public class VarModelBuilder implements VarModelBuilderInterface {

	private VarModel v;
	private PlacementModel p;
	
	@Override
	public VarModel build(PlacementModel p) {
		this.v = new VarModel();
		this.p = p;
		
		for(PMachine e : p.getPMachines().values()) {
			v.addEntity(e);
			addResourceOffer(e);
		}
		for(PStorage e : p.getPStorages().values()) {
			v.addEntity(e);
			addResourceOffer(e);
		}
		for(PSwitch e : p.getPSwitches().values()) {
			v.addEntity(e);
			addResourceOffer(e);
		}
		for(PLink e : p.getPLinks().values()) {
			v.addEntity(e);
			addResourceOffer(e);
		}
		
		for(VMachine e : p.getVMachines().values()) {
			v.addEntity(e, p.getPMachines().keySet());
			addResourceDemand(e);
		}
		for(VVolume e : p.getVVolumes().values()) {
			v.addEntity(e, p.getPStorages().keySet());
			addResourceDemand(e);
		}
		for(VLink e : p.getVLinks().values()) {
			/*
			v.addEntity(e, 
					p.getPSwitches().keySet(), 
					p.getPLinks().keySet());
			*/
			addResourceDemand(e);
		}
		
		for(ResourceType r : ResourceType.values()) {
			int max_capacity = 0;
			for(ResourceOffer ro : p.getResourceOffers().values()) {
				if(ro.getType().equals(r) && ro.getCapacity() > max_capacity) {
					max_capacity = ro.getCapacity();
				}
			}
			v.addRType(r, max_capacity);
		}
		
		return v;
	}
	
	private void addResourceOffer(PEntity e) {
		for(ResourceOffer r : e.getResourceMap().values()) {
			v.addROffer(r, p.getResourceDemands(r.getType()).values(), getMaxAggUtilization(r.getType()));
		}
	}
	
	private void addResourceDemand(VEntity e) {
		for(ResourceDemand r : e.getResourceMap().values()) {
			v.addRDemand(r);
		}
	}

	private int getMaxAggUtilization(ResourceType t) {
		int i = 0;
		for(ResourceDemand r : p.getResourceDemands(t).values()) {
			i += r.getAmount();
		}
		return i;
	}
}

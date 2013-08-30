package com.ibm.block.generator.impl;

import java.util.ArrayList;

import com.ibm.block.generator.fac.virtual.impl.vg.AntiColocatedPathGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.AntiColocatedStorageGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.ColocatedStorageGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.ReplicaGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vg.SamePropertyGroupFactory;
import com.ibm.block.generator.fac.virtual.impl.vl.LargeVLFactory;
import com.ibm.block.generator.fac.virtual.impl.vl.SmallVLFactory;
import com.ibm.block.generator.fac.virtual.impl.vm.LargeVMFactory;
import com.ibm.block.generator.fac.virtual.impl.vm.SmallVMFactory;
import com.ibm.block.generator.fac.virtual.impl.vv.LargeVVFactory;
import com.ibm.block.generator.fac.virtual.impl.vv.SmallVVFactory;
import com.ibm.block.generator.intf.ThreeTierAppGenInterface;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedPathGroup;
import com.ibm.block.model.app.impl.vgroup.AntiColocatedStorageGroup;
import com.ibm.block.model.app.impl.vgroup.ReplicaGroup;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class ThreeTierAppGen extends Generator implements
		ThreeTierAppGenInterface {

	private int t1VmCount;
	private int t1VvPerVmCount;
	private int t2VmCount;
	private int t2VvPerVmCount;
	private int t3VmCount;
	private int t3VvPerVmCount;
	
	private boolean hasAntiColPath;
	private boolean hasAntiColStorage;
	private boolean hasColStorage;
	private boolean hasReplica;
	private boolean hasSameProperty;
	
	private int t1ReplicaCount;
	private int t2ReplicaCount;
	private int t3ReplicaCount;
	
	private LargeVMFactory largeVmFac;
	private SmallVMFactory smallVmFac;
	private LargeVVFactory largeVvFac;
	private SmallVVFactory smallVvFac;
	private LargeVLFactory largeVlFac;
	private SmallVLFactory smallVlFac;
	
	private ReplicaGroupFactory repGroupFac;
	private SamePropertyGroupFactory sameGroupFac;
	private ColocatedStorageGroupFactory colstorGroupFac;
	private AntiColocatedStorageGroupFactory anticolstoreGroupFac;
	private AntiColocatedPathGroupFactory anticolpathGroupFac;
	
	public ThreeTierAppGen(PlacementModel model, int t1VmCount, 
			int t2VmCount, int t3VmCount, int t1VvPerVmCount, 
			int t2VvPerVmCount, int t3VvPerVmCount, boolean hasAntiColPath,
			boolean hasAntiColStorage, boolean hasColStorage, 
			boolean hasReplica, boolean hasSameProperty, int t1ReplicaCount,
			int t2ReplicaCount, int t3ReplicaCount) {
		
		super(model);
		
		this.largeVmFac = new LargeVMFactory(model);
		this.smallVmFac = new SmallVMFactory(model);
		this.largeVvFac = new LargeVVFactory(model);
		this.smallVvFac = new SmallVVFactory(model);
		this.largeVlFac = new LargeVLFactory(model);
		this.smallVlFac = new SmallVLFactory(model);
		
		this.repGroupFac = new ReplicaGroupFactory(model);
		this.sameGroupFac = new SamePropertyGroupFactory(model);
		sameGroupFac.addProperty(PropertyType.STORAGE_SSD);
		sameGroupFac.addProperty(PropertyType.STORAGE_HDD);
		this.colstorGroupFac = new ColocatedStorageGroupFactory(model);
		this.anticolstoreGroupFac = new AntiColocatedStorageGroupFactory(model);
		this.anticolpathGroupFac = new AntiColocatedPathGroupFactory(model, 1);
		
		this.t1VmCount = t1VmCount;
		this.t1VvPerVmCount = t1VvPerVmCount;
		this.t2VmCount = t2VmCount;
		this.t2VvPerVmCount = t2VvPerVmCount;
		this.t3VmCount = t3VmCount;
		this.t3VvPerVmCount = t3VvPerVmCount;
		
		this.t1ReplicaCount = t1ReplicaCount;
		this.t2ReplicaCount = t2ReplicaCount;
		this.t3ReplicaCount = t3ReplicaCount;
		
		this.hasAntiColPath = hasAntiColPath;
		this.hasAntiColStorage = hasAntiColStorage;
		this.hasColStorage = hasColStorage;
		this.hasReplica = hasReplica;
		this.hasSameProperty = hasSameProperty;
	}

	@Override
	public int getT1VMCount() {
		return t1VmCount;
	}

	@Override
	public int getT1VVperVMCount() {
		return t1VvPerVmCount;
	}

	@Override
	public int getT2VMCount() {
		return t2VmCount;
	}

	@Override
	public int getT2VVperVMCount() {
		return t2VvPerVmCount;
	}

	@Override
	public int getT3VMCount() {
		return t3VmCount;
	}

	@Override
	public int getT3VVperVMCount() {
		return t3VvPerVmCount;
	}

	@Override
	public boolean hasAntiColocatedPath() {
		return hasAntiColPath;
	}

	@Override
	public boolean hasAntiColocatedStorage() {
		return hasAntiColStorage;
	}

	@Override
	public boolean hasColocatedStorage() {
		return hasColStorage;
	}

	@Override
	public boolean hasReplica() {
		return hasReplica;
	}

	@Override
	public boolean hasSameProperty() {
		return hasSameProperty;
	}

	@Override
	public LargeVMFactory getLVMFactory() {
		return largeVmFac;
	}

	@Override
	public SmallVMFactory getSVMFactory() {
		return smallVmFac;
	}

	@Override
	public LargeVVFactory getLVVFactory() {
		return largeVvFac;
	}

	@Override
	public SmallVVFactory getSVVFactory() {
		return smallVvFac;
	}

	@Override
	public LargeVLFactory getLVLFactory() {
		return largeVlFac;
	}

	@Override
	public SmallVLFactory getSVlFactory() {
		return smallVlFac;
	}

	@Override
	public ReplicaGroupFactory getRepGroupFactory() {
		return repGroupFac;
	}

	@Override
	public SamePropertyGroupFactory getSamepropGroupFactory() {
		return sameGroupFac;
	}

	@Override
	public ColocatedStorageGroupFactory getColstorageGroupFactory() {
		return colstorGroupFac;
	}

	@Override
	public AntiColocatedStorageGroupFactory getAnticolstorageGroupFactory() {
		return anticolstoreGroupFac;
	}

	@Override
	public AntiColocatedPathGroupFactory getAnticolpathGroupFactory() {
		return anticolpathGroupFac;
	}

	@Override
	public int getT1ReplicaCount() {
		return t1ReplicaCount;
	}

	@Override
	public int getT2ReplicaCount() {
		return t2ReplicaCount;
	}

	@Override
	public int getT3ReplicaCount() {
		return t3ReplicaCount;
	}
	
	@Override
	public PlacementModel build() {

		ArrayList<VMachine> t1Vms = new ArrayList<>();
		ArrayList<VMachine> t2Vms = new ArrayList<>();
		ArrayList<VMachine> t3Vms = new ArrayList<>();
		
		// Building tier1
		for(int i = 0; i<t1VmCount; i++) {
			VMachine vm = largeVmFac.create();
			t1Vms.add(vm);
			for(int j = 0; j<t1VvPerVmCount; j++) {
				ArrayList<VVolume> replicas = new ArrayList<>();
				for(int k = 0; k<t1ReplicaCount; k++) {
					VVolume vv = smallVvFac.create();
					replicas.add(vv);
					smallVlFac.create(vm, vv);
				}
				if(hasReplica) {
					ReplicaGroup repGroup = repGroupFac.create();
					for(VVolume vv : replicas) {
						repGroup.addMember(vv);
					}
				}
			}
		}
		
		//Building tier2
		for(int i = 0; i<t2VmCount; i++) {
			VMachine vm = largeVmFac.create();
			t2Vms.add(vm);
			for(int j = 0; j<t2VvPerVmCount; j++) {
				ArrayList<VVolume> replicas = new ArrayList<>();
				for(int k = 0; k<t2ReplicaCount; k++) {
					VVolume vv = smallVvFac.create();
					replicas.add(vv);
					smallVlFac.create(vm, vv);
				}
				if(hasReplica) {
					ReplicaGroup repGroup = repGroupFac.create();
					for(VVolume vv : replicas) {
						repGroup.addMember(vv);
					}
				}
			}
		}
		
		//Building tier3
		for(int i = 0; i<t3VmCount; i++) {
			VMachine vm = largeVmFac.create();
			t3Vms.add(vm);
			ArrayList<VVolume> antiCollocatedVolumes = new ArrayList<>();
			for(int j = 0; j<t3VvPerVmCount; j++) {
				ArrayList<VVolume> replicas = new ArrayList<>();
				for(int k = 0; k<t3ReplicaCount; k++) {
					VVolume vv = smallVvFac.create();
					antiCollocatedVolumes.add(vv);
					replicas.add(vv);
					largeVlFac.create(vm, vv);
				}
				if(hasReplica) {
					ReplicaGroup repGroup = repGroupFac.create();
					for(VVolume vv : replicas) {
						repGroup.addMember(vv);
					}
				}
				if( hasAntiColStorage ) {
					AntiColocatedStorageGroup antiColGroup = anticolstoreGroupFac.create();
					for( VVolume vv : antiCollocatedVolumes ) {
						antiColGroup.addMember(vv);
					}
				}
				if( hasAntiColPath ) {
					AntiColocatedPathGroup antiColPathGroup = anticolpathGroupFac.create();
					for( VVolume vv : antiCollocatedVolumes ) {
						antiColPathGroup.addMember(vv);
					}
				}
			}
		}
		
		// Building inter VM network
		// Connect tier1 and tier2
		for(VMachine t1vm : t1Vms) {
			for(VMachine t2vm : t2Vms) {
				smallVlFac.create(t1vm, t2vm);
			}
		}
		// Connect Vms in tier2
		@SuppressWarnings("unchecked")
		ArrayList<VMachine> l = (ArrayList<VMachine>) t2Vms.clone();
		for(VMachine end1 : t2Vms) {
			l.remove(end1);
			for(VMachine end2 : l) {
				smallVlFac.create(end1, end2);
			}
		}
		
		// Connect tier2 and tier3
		for(VMachine t2vm : t2Vms) {
			for(VMachine t3vm : t3Vms) {
				smallVlFac.create(t2vm, t3vm);
			}
		}

		// Connect VMs in tier3
		l = (ArrayList<VMachine>) t3Vms.clone();
		for(VMachine end1 : t3Vms) {
			l.remove(end1);
			for(VMachine end2 : l) {
				smallVlFac.create(end1, end2);
			}
		}
		
		return getModel();
	}
}

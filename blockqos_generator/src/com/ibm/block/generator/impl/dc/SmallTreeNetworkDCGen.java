package com.ibm.block.generator.impl.dc;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.block.generator.impl.TreeNetworkDCGen;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;

public class SmallTreeNetworkDCGen extends TreeNetworkDCGen {

	private int largePmsPerRack;
	private int smallPmsPerRack;
	private int largePssPerRack;
	private int smallPssPerPM;
	
	
	/**
	 * 
	 * @param model
	 */
	public SmallTreeNetworkDCGen(PlacementModel model, int treeDepth, int fanoutLvl1, 
			int fanoutLvl2, int fanoutLvl3, int fanoutLvl4, int largePmsPerRack, int smallPmsPerRack, 
			int largePssPerRack, int smallPssPerPM) {
		super(model, treeDepth, fanoutLvl1, fanoutLvl2, fanoutLvl3, fanoutLvl4);
		this.largePmsPerRack = largePmsPerRack;
		this.smallPmsPerRack = smallPmsPerRack;
		this.largePssPerRack = largePssPerRack;
		this.smallPssPerPM = smallPssPerPM;
	}

	@Override
	public PlacementModel build() {
		// Create root switch
		PSwitch root = super.getLargePWFactory().create();
		
		// Create network
		HashMap<Integer, ArrayList<PSwitch>> lvl = new HashMap<>();
		
		if( super.getTreeDepth() > 0 ) {
			lvl.put(1, new ArrayList<PSwitch>());
			for( int j = 0; j < super.getFanout1(); j++ ) {
				PSwitch s = super.getLargePWFactory().create(); 
				lvl.get(1).add(s);
				super.getLargePLFactory().create(root, s);
			}
		}
		if( super.getTreeDepth() > 1 ) {
			lvl.put(2, new ArrayList<PSwitch>());
			for( PSwitch parent : lvl.get(1) ) {
				for( int j = 0; j < super.getFanout2(); j++ ) {
					PSwitch s = super.getLargePWFactory().create(); 
					lvl.get(2).add(s);
					super.getLargePLFactory().create(parent, s);
				}
			}
		}
		if( super.getTreeDepth() > 2 ) {
			lvl.put(3, new ArrayList<PSwitch>());
			for( PSwitch parent : lvl.get(2) ) {
				for( int j = 0; j < super.getFanout3(); j++ ) {
					PSwitch s = super.getLargePWFactory().create(); 
					lvl.get(3).add(s);
					super.getLargePLFactory().create(parent, s);
				}
			}
		}
		
		for( PSwitch pw : lvl.get(super.getTreeDepth()) ) {
			ArrayList<PMachine> pms = new ArrayList<>();
			for( int i = 0; i < largePmsPerRack; i++ ) {
				PMachine pm = super.getLargePMFactory().create();
				pms.add(pm);
				super.getSmallPLFactory().create(pm, pw);
			}
			for( int i = 0; i < smallPmsPerRack; i++ ) {
				PMachine pm = super.getSmallPMFactory().create();
				pms.add(pm);
				super.getSmallPLFactory().create(pm, pw);
			}
			for( int i = 0; i < largePssPerRack; i++ ) {
				PStorage ps = super.getLargePSFactory().create();
				super.getSmallPLFactory().create(ps, pw);
			}
			for( PMachine pm : pms ) {
				for( int i = 0; i < smallPssPerPM; i++ ) {
					PStorage ps = super.getSmallPSFactory().create();
					super.getLargePLFactory().create(pm, ps);
				}
			}
		}
		return super.getModel();
	}

}

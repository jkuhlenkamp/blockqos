package com.ibm.block.generator.impl;

import com.ibm.block.generator.fac.physical.impl.pl.LargePLFactory;
import com.ibm.block.generator.fac.physical.impl.pl.SmallPLFactory;
import com.ibm.block.generator.fac.physical.impl.pm.LargePMFactory;
import com.ibm.block.generator.fac.physical.impl.pm.SmallPMFactory;
import com.ibm.block.generator.fac.physical.impl.ps.LargePSFactory;
import com.ibm.block.generator.fac.physical.impl.ps.SmallPSFactory;
import com.ibm.block.generator.fac.physical.impl.pw.LargePWFactory;
import com.ibm.block.generator.intf.TreeDCFactoryGenInterface;
import com.ibm.block.model.core.impl.PlacementModel;

public abstract class TreeNetworkDCGen extends Generator 
		implements TreeDCFactoryGenInterface{
	
	private int treeDepth;
	private int fanout1;
	private int fanout2;
	private int fanout3;
	private int fanout4;
	
	private LargePMFactory fpmlarge;
	private SmallPMFactory fpmsmall;
	private LargePSFactory fpslarge;
	private SmallPSFactory fpssmall;
	private LargePWFactory fpwlarge;
	private LargePLFactory fpllarge;
	private SmallPLFactory fplsmall;
	
	public TreeNetworkDCGen(PlacementModel model, int treeDepth, int fanout1, int fanout2, 
			int fanout3, int fanout4) {
		super(model);
		
		this.treeDepth = treeDepth;
		this.fanout1 = fanout1;
		this.fanout2 = fanout2;
		this.fanout3 = fanout3;
		this.fanout4 = fanout4;
		
		fpmlarge = new LargePMFactory(super.getModel());
		fpmsmall = new SmallPMFactory(super.getModel());
		fpslarge = new LargePSFactory(super.getModel());
		fpssmall = new SmallPSFactory(super.getModel());
		fpwlarge = new LargePWFactory(super.getModel());
		fpllarge = new LargePLFactory(super.getModel());
		fplsmall = new SmallPLFactory(super.getModel());
	}

	@Override
	public Integer getTreeDepth() {
		return treeDepth;
	}

	@Override
	public Integer getFanout1() {
		return fanout1;
	}

	@Override
	public Integer getFanout2() {
		return fanout2;
	}

	@Override
	public Integer getFanout3() {
		return fanout3;
	}

	@Override
	public Integer getFanout4() {
		return fanout4;
	}

	@Override
	public LargePMFactory getLargePMFactory() {
		return fpmlarge;
	}

	@Override
	public SmallPMFactory getSmallPMFactory() {
		return fpmsmall;
	}

	@Override
	public LargePSFactory getLargePSFactory() {
		return fpslarge;
	}

	@Override
	public SmallPSFactory getSmallPSFactory() {
		return fpssmall;
	}

	@Override
	public LargePWFactory getLargePWFactory() {
		return fpwlarge;
	}

	@Override
	public LargePLFactory getLargePLFactory() {
		return fpllarge;
	}

	@Override
	public SmallPLFactory getSmallPLFactory() {
		return fplsmall;
	}

}

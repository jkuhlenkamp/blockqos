package com.ibm.block.generator.intf;

import com.ibm.block.generator.fac.physical.impl.pl.LargePLFactory;
import com.ibm.block.generator.fac.physical.impl.pl.SmallPLFactory;
import com.ibm.block.generator.fac.physical.impl.pm.LargePMFactory;
import com.ibm.block.generator.fac.physical.impl.pm.SmallPMFactory;
import com.ibm.block.generator.fac.physical.impl.ps.LargePSFactory;
import com.ibm.block.generator.fac.physical.impl.ps.SmallPSFactory;
import com.ibm.block.generator.fac.physical.impl.pw.LargePWFactory;

public interface TreeDCFactoryGenInterface {

	public Integer getTreeDepth();
	public Integer getFanout1();
	public Integer getFanout2();
	public Integer getFanout3();
	public Integer getFanout4();
	
	public LargePMFactory getLargePMFactory();
	public SmallPMFactory getSmallPMFactory();
	public LargePSFactory getLargePSFactory();
	public SmallPSFactory getSmallPSFactory();
	public LargePWFactory getLargePWFactory();
	public LargePLFactory getLargePLFactory();
	public SmallPLFactory getSmallPLFactory();
	
}

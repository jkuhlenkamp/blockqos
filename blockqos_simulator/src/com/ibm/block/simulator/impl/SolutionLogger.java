package com.ibm.block.simulator.impl;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.app.impl.VLink;
import com.ibm.block.model.app.impl.VMachine;
import com.ibm.block.model.app.impl.VVolume;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.PLink;
import com.ibm.block.model.dc.impl.PMachine;
import com.ibm.block.model.dc.impl.PStorage;
import com.ibm.block.model.dc.impl.PSwitch;
import com.ibm.block.model.dc.impl.ResourceOffer;
import com.ibm.block.placement.var.impl.VarModel;
import com.ibm.block.placement.var.impl.VariableModel;
import com.ibm.block.simulator.intf.SolutionLoggerInterface;

public class SolutionLogger implements SolutionLoggerInterface {

	private static final Logger log = Logger.getLogger( SolutionLogger.class.getName() );
	
	private VariableModel vMod;
	private Solver s;
	private PlacementModel pMod;
	
	public SolutionLogger(PlacementModel pMod, VariableModel vMod, Solver s) {
		this.vMod = vMod;
		this.pMod = pMod;
		this.s = s;
	}
	
	private void logResourceOffer(ResourceOffer o){
		log.info("     ~ " +o.getName()+ "[" +
				"Pre capacity: " +s.getVar( vMod.getPreCapVar(o) ).getVal()  + ", " +
				"Aggregated post demand: " +s.getVar( vMod.getAggPotRdVar(o) ).getVal()+ "/" +s.getVar( vMod.getPreCapVar(o) ).getVal()
				//"Max capacity: " +s.getVar( vMod.getMaxCapVar(o) ).getVal() +
				//"Pre utilization: " +s.getVar( vMod.getPreUtilVar(o) ).getVal()
			);
	}
	
	public void logSolution(String fileName) {
		FileHandler logFile;
		try {
			logFile = new FileHandler(fileName);
			logFile.setFormatter(new SimpleFormatter());
		    log.addHandler(logFile);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Constants
		log.info("PLACEMENT SOLUTION");
		log.info("CONSTANTS");
		log.info(" + PMachines:");
		for( PMachine e : pMod.getPMachines().values() ) {
			log.info("   - " +e.getName()+ ": " +s.getVar( vMod.getPLocationVar(e) ).getVal() );
			for( ResourceOffer o : e.getResourceMap().values() ) {
				logResourceOffer(o);
			}
		}
		log.info(" + PStorages:");
		for( PStorage e : pMod.getPStorages().values() ) {
			log.info("   - " +e.getName()+ ": " +s.getVar( vMod.getPLocationVar(e) ).getVal() );
			for( ResourceOffer o : e.getResourceMap().values() ) {
				logResourceOffer(o);
			}
		}
		log.info(" + PSwitches:");
		for( PSwitch e : pMod.getPSwitches().values() ) {
			if( s.getVar( vMod.getPLocationVar(e) ) != null ) {
				log.info("   - " +e.getName()+ ": " +s.getVar( vMod.getPLocationVar(e) ).getVal() );
			}
			for( ResourceOffer o : e.getResourceMap().values() ) {
				logResourceOffer(o);
			}
		}
		log.info(" + PLinks:");
		for( PLink e : pMod.getPLinks().values() ) {
			log.info("   - " +e.getName()+ ": " +s.getVar( vMod.getPLocationVar(e) ).getVal() );
			for( ResourceOffer o : e.getResourceMap().values() ) {
				logResourceOffer(o);
			}
		}
		log.info(" + VMachines:");
		for( VMachine e : pMod.getVMachines().values() ) {
			log.info("   - " +e.getName()+ ": " +s.getVar( vMod.getLocationVar(e) ).getVal() );
			for( ResourceDemand r : e.getResourceMap().values() ) {
				logResourceDemand(r);
			}
		}
		log.info(" + VVolumes:");
		for( VVolume e : pMod.getVVolumes().values() ) {
			log.info("   - " +e.getName()+ ": " +s.getVar( vMod.getLocationVar(e) ).getVal() );
			for( ResourceDemand r : e.getResourceMap().values() ) {
				logResourceDemand(r);
			}
		}
		log.info( " + VLinks: #" +pMod.getVLinks().size() );
		for( VLink e : pMod.getVLinks().values() ) {
			log.info("   - " +e.getName()+ "(" +e.getEnd1().getName()+ ", " +e.getEnd2().getName()+ ") , PLinks: " +pretty( s.getVar(vMod.getPLinkLocationVar(e)).getValue() )+ ", " +" PSwitches: " +pretty( s.getVar(vMod.getPSwitchLocationVar(e)).getValue() ) );
			//log.info("   - " +e.getName()+ " PSwitches: " +pretty( s.getVar(vMod.getPSwitchLocationVar(e)).getValue() ) );
			for( ResourceDemand r : e.getResourceMap().values() ) {
				logResourceDemand(r);
			}
		}
		
		// Variables
		log.info("PLACMENT VARIABLES");
		// Objectives
		log.info("OBJECTIVE VARIABLES");
		log.info( " + Overall objective: " +s.getVar( vMod.getObjectiveVar() ).getVal() );
		log.info( " + Aggregated overall bandwidth: " +s.getVar( vMod.getAggBandwidthVar() ).getVal() );
		/*
		for( ResourceOffer offer : pMod.getResourceOffers().values() ) {
			if( offer.getType().equals(ResourceType.BANDWIDTH) ) {
				log.info( "   - " +offer.getName()+ ", Aggregated bandwidth per plink: (" +s.getVar( vMod.getAggPotRdVar(offer) ).getVal()+ "/"  +s.getVar( vMod.getPreCapVar(offer) ).getVal()+ ")" );
			}
		}
		*/
		log.info( " + Aggregated colocated switches: " +s.getVar( vMod.getAggColSwitchesVar() ).getVal() );
		
	}

	private void logResourceDemand(ResourceDemand r) {
		log.info("     ~ " +r.getName()+ "[" +
				"Pre amount: " +s.getVar( vMod.getAmountVar(r) ).getVal()  + ", "
			);
	}
	
	private String pretty(int[] a) {
		String s = "[";
		for(int i = 0; i < a.length; i++ ) {
			if(i != (a.length-1) ) {
				s += a[i] + ", ";
			}
			else{
				s += a[i];
			}
		}
		s += "]";
		return s;
	}

	@Override
	public void logResult(PlacementModel p, VarModel v, Solver s) {
		// TODO Auto-generated method stub
		
	}

}

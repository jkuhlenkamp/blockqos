package com.ibm.block.simulator.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

public class ResultLogger implements SolutionLoggerInterface {

	private static final Logger log = Logger.
			getLogger(ResultLogger.class.getName());

	private PlacementModel p;
	private VarModel v;
	private Solver s;
	
	private static String H1 = "______";
	private static String H2 = "   ===";
	private static String LVL1 = "+ ";
	private static String LVL2 = "   - ";
	private static String LVL3 = "       * ";
	private static String LVL4 = "          + ";
	
	private ArrayList<Integer> bw_list;
	private ArrayList<Integer> max_core_list;
	private ArrayList<Integer> max_ram_list;
	private ArrayList<Integer> max_storage_list;
	private ArrayList<Integer> max_bw_list;
	
	public ResultLogger(String fileName) {
		bw_list = new ArrayList<>();
		max_core_list = new ArrayList<>();
		max_ram_list = new ArrayList<>();
		max_storage_list = new ArrayList<>();
		max_bw_list = new ArrayList<>();
		
		FileHandler handler;
		try {
			handler = new FileHandler(fileName);
			handler.setFormatter(new SimpleFormatter());
			log.addHandler(handler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void logResult(PlacementModel p, VarModel v, Solver s) {
		this.p = p;
		this.v = v;
		this.s = s;
		
		log.info(H1+"Physical Machines");
		for(PMachine e : p.getPMachines().values()) {
			log.info(LVL1+e.getName()+"[loc_id_var:" +s.getVar(v.getIdVar(e)).getVal()+"]");
			for(ResourceOffer r : e.getResourceMap().values()) {
				prettyResourceOffer(r);
			}
		}
		log.info(H1+"Physical Storages");
		for(PStorage e : p.getPStorages().values()) {
			log.info(LVL1+e.getName()+"[loc_id_var:" +s.getVar(v.getIdVar(e)).getVal()+"]");
			for(ResourceOffer r : e.getResourceMap().values()) {
				prettyResourceOffer(r);
			}
		}
		log.info(H1+"Physical Switches");
		for(PSwitch e : p.getPSwitches().values()) {
			log.info(LVL1+e.getName()+"[loc_id_var:" +s.getVar(v.getIdVar(e)).getVal()+"]");
			for(ResourceOffer r : e.getResourceMap().values()) {
				prettyResourceOffer(r);
			}
		}
		log.info(H1+"Physical Links");
		for(PLink e : p.getPLinks().values()) {
			log.info(LVL1+e.getName()+"[connects: (" +e.getEnds()[0].getName()+ ", " +e.getEnds()[1].getName()+ "), loc_id_var:" +s.getVar(v.getIdVar(e)).getVal()+"]");
			for(ResourceOffer r : e.getResourceMap().values()) {
				prettyResourceOffer(r);
			}
		}
		log.info(H1+"Virtual Machines");
		for(VMachine e : p.getVMachines().values()) {
			log.info(LVL1+e.getName()+"[" +
					"loc_var:" +s.getVar(v.getLocVar(e)).getVal()+ ", " +
					"loc_var_domain: " +p.getPMachines().keySet()+ "]");
			for(ResourceDemand r : e.getResourceMap().values()) {
				prettyResourceDemand(r);
			}
		}
		log.info(H1+"Virtual Volumes");
		for(VVolume e : p.getVVolumes().values()) {
			log.info(LVL1+e.getName()+"[" +
					"loc_var:" +s.getVar(v.getLocVar(e)).getVal()+ ", " +
					"loc_var_domain: " +p.getPStorages().keySet()+ "]");
			for(ResourceDemand r : e.getResourceMap().values()) {
				prettyResourceDemand(r);
			}
		}
		log.info(H1+"Virtual Links");
		for(VLink e : p.getVLinks().values()) {
			log.info(LVL1+e.getName()+"[connects: (" +e.getEnds()[0].getName()+ ", " +e.getEnds()[1].getName()+ ")]");
			/**
			log.info(LVL2+"loc_on_pw_var:" +prettyArray(s.getVar(v.getLocVarOnPSwitches(e)).getValue())+ ", " +
					"domain:" +p.getPSwitches().keySet());
			log.info(LVL2+"loc_on_pl_var:" +prettyArray(s.getVar(v.getLocVarOnPLinks(e)).getValue())+ ", " +
					"domain:" +p.getPLinks().keySet());
			*/
			for(ResourceDemand r : e.getResourceMap().values()) {
				prettyResourceDemand(r);
			}
		}
		log.info(H1+"Objectives");
		log.info(LVL1+"Overall bandwidth="+s.getVar(v.getPerfBandwidthVar()).getVal());
		for(ResourceType r : ResourceType.values()) {
			log.info(LVL1+"Max res utilization" +r+ "="+s.getVar(v.getPerfMaxUtilizationVar(r)).getVal());
		}
		bw_list.add(s.getVar(v.getPerfBandwidthVar()).getVal());
		max_core_list.add(s.getVar(v.getPerfMaxUtilizationVar(ResourceType.CORES)).getVal());
		max_ram_list.add(s.getVar(v.getPerfMaxUtilizationVar(ResourceType.RAM)).getVal());
		max_storage_list.add(s.getVar(v.getPerfMaxUtilizationVar(ResourceType.STORAGE)).getVal());
		max_bw_list.add(s.getVar(v.getPerfMaxUtilizationVar(ResourceType.BANDWIDTH)).getVal());
	}

	private void prettyResourceOffer(ResourceOffer r) {
		log.info(LVL2+r.getName()+ "[" +
				"type:" +r.getType()+ ", " +
				"max capacity:" +s.getVar(v.getResCapacityVar(r)).getVal()+ ", " +
				"pre left capacity:" +s.getVar(v.getResPreUtilizationVar(r)).getVal()+ ", " +
				"post usage:" +s.getVar(v.getResPostAggUtilizationVar(r)).getVal()+
				"]");
		/*
		for(ResourceDemand rd : p.getResourceDemands(r.getType()).values()) {
			log.info(LVL3+r.getName()+rd.getName()+ "[" +
				"post usage:" +s.getVar(v.getResPostUtilizationOfRDemand(r, rd)).getVal()+
				"]");
		}
		*/
	}
	
	private void prettyResourceDemand(ResourceDemand r) {
		log.info(LVL2+r.getName()+"[" +
				"type:" +r.getType()+ ", " +
				"amount:" +s.getVar(v.getResAmountVar(r)).getVal()+
				"]");
		
	}

	static private String prettyArray(int[] a) {
		String s = "[";
		for(int i=0; i<a.length; i++) {
			s += a[i];
			if(! (i == (a.length-1))) {
				s+=" ";
			}
		}
		s+="]";
		return s;
	}
	
	public void logPerformanceMetrics() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("perf_metrics.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( writer != null ) {
			writer.println("Agg. bandwidth usage, max #cores, max RAM, max Storage, max bandwidth");
			log.info("Agg. bandwidth usage, max #cores, max RAM, max Storage, max bandwidth");
			int i=0;
			for(Integer val : bw_list) {
				writer.println(bw_list.get(i)+ ", " +max_core_list.get(i)+ ", " +max_ram_list.get(i)+ ", " +max_storage_list.get(i)+ ", " +max_bw_list.get(i) );
				i++;
			}
			writer.close();
		}
	}
}

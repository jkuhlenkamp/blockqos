package com.ibm.block.simulator.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.Variable;
import choco.kernel.model.variables.integer.IntegerConstantVariable;
import choco.kernel.model.variables.set.SetConstantVariable;
import choco.kernel.model.variables.set.SetVariable;

import com.ibm.block.generator.impl.app.SmallThreeTierAppGen;
import com.ibm.block.generator.impl.dc.SmallTreeNetworkDCGen;
import com.ibm.block.model.core.enums.ResourceType;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.ResourceOffer;
import com.ibm.block.placement.var.impl.ConModel;
import com.ibm.block.placement.var.impl.ConModelBuilder;
import com.ibm.block.placement.var.impl.VarModel;
import com.ibm.block.placement.var.impl.VarModelBuilder;

public class SimulationRunner {

	private static final Logger log = Logger.
			getLogger(SimulationRunner.class.getName());
	
	private static final ResultLogger solutionLog = new 
			ResultLogger("result_log.txt");
	
	SimulationRunner() {
		FileHandler handler;
		try {
			handler = new FileHandler("execution_log.txt");
			handler.setFormatter(new SimpleFormatter());
			log.addHandler(handler);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Creating a Solution Log
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("Start of simulation...");
		
		log.info("Start of PlacementModel generation...");
		PlacementModel p = new PlacementModel();
		p = new SmallTreeNetworkDCGen(p, 2, 2,2,2,2, 2,0,2,2).build();
		p = new SmallThreeTierAppGen(p, 0,0,1, 0,0,1, false,true,false,false,false, 1,1,1).build();
		log.info("End of PlacementModel generation.");
		
		int n = 20;
		for(int j=0; j<n; j++) {
			log.info("Start of CHOCO Iteration:" +j+ "...");
			log.info("Start of CHOCO VarModel generation...");
			VarModelBuilder var_model_builder = new VarModelBuilder();
			VarModel v = var_model_builder.build(p);
			log.info("End of CHOCO VarModel generation.");
			
			log.info("Start of reading VarModel variables to CHOCO solver...");
			CPModel m = new CPModel();
			m.addVariables(v.getVariableArray());
			
			log.info("End of CHOCO reading VarModel variables to CHOCO solver.");
			
			log.info("Start of CHOCO ConstraintModel generation...");
			ConModelBuilder con_model_builder = new ConModelBuilder(true, true, false);
			ConModel c = con_model_builder.build(p, v);
			log.info("End of CHOCO ConstraintModel generation.");
			
			log.info("Start of adding ConstraintModel constraints to CHOCO model...");
			for(Constraint e : c.getAllConstraints()) {
				if(e != null) {
					m.addConstraint(e);
					log.info("Constraint:" +e.pretty());
				}
				else{
					throw new IllegalArgumentException();
				}
			}
			log.info("End of adding ConstraintModel constraints to CHOCO model.");
			
			log.info("Start of reading CHOCO model to CHOCO solver...");
			CPSolver s = new CPSolver();
			s.read(m);
			//s.solve();
			s.minimize(true);
			log.info("End of reading CHOCO model to CHOCO solver.");
			
			log.info("Start of solving CHOCO model with CHOCO solver...");
			log.info("Overall bandwidth="+s.getVar(v.getPerfBandwidthVar()).getVal());
			log.info("Max Core util="+s.getVar(v.getPerfMaxUtilizationVar(ResourceType.CORES)).getVal());
			for(ResourceType r : ResourceType.values()) {
				log.info("Max res utilization" +r+ "="+s.getVar(v.getPerfMaxUtilizationVar(r)).getVal());
			}
			
			//s.solve();
			/*
			int i = 0;							// Solution counter
			do {
				
				if(s.checkSolution()) {
					log.info("Found solution #" +i+ ", isCorrect:" +s.checkSolution());
					*/
					solutionLog.logResult(p, v, s);
					/*
					System.out.println("Overall bandwidth="+s.getVar(v.getPerfBandwidthVar()).getVal());
					System.out.println("Max Core util="+s.getVar(v.getPerfMaxUtilizationVar(ResourceType.CORES)).getVal());
					i++;
				}
				else {
					log.info("Incorrect solution:");
				}
			} while (s.nextSolution());
			*/
			//log.info("End of solving CHOCO model with CHOCO solver.");
			
			for(ResourceOffer ro : p.getResourceOffers().values()) {
				int usage = ro.getUsage();
				ro.setUsage(usage+s.getVar(v.getResPostAggUtilizationVar(ro)).getVal());
				//log.info(ro.getName()+": old_usage:" +usage+ ", new_usage:" +ro.getUsage());
			}
		}
		solutionLog.logPerformanceMetrics();
	}

	static private String prettyArray(int[] a) {
		String s = "";
		for(int i=0; i<a.length; i++) {
			s += a[i]+ " ";
		}
		return s;
	}
}

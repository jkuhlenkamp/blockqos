package com.ibm.block.simulator.impl;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.model.variables.set.SetVariable;

import com.ibm.block.generator.impl.app.SmallThreeTierAppGen;
import com.ibm.block.generator.impl.dc.SmallTreeNetworkDCGen;
import com.ibm.block.model.app.impl.ResourceDemand;
import com.ibm.block.model.core.impl.PlacementModel;
import com.ibm.block.model.dc.impl.ResourceOffer;
import com.ibm.block.placement.constraint.impl.ConstraintModel;
import com.ibm.block.placement.constraint.impl.ConstraintModelGen;
import com.ibm.block.placement.var.impl.VariableModel;

public class SimulationCoordinator {

	private static final Logger executionLog = Logger.getLogger( SimulationCoordinator.class.getName() );
	private static final Logger constraintLog = Logger.getLogger( SimulationCoordinator.class.getName() );
	private static final Logger resultLog = Logger.getLogger( SimulationCoordinator.class.getName() );
	private Handler handler;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FileHandler executionLogFile;
		FileHandler constraintLogFile;
		FileHandler resultLogFile;
		try {
			executionLogFile = new FileHandler("execution_log.txt");
			executionLogFile.setFormatter(new SimpleFormatter());
		    executionLog.addHandler(executionLogFile);
		    constraintLogFile = new FileHandler("constraint_log.txt");
		    constraintLogFile.setFormatter(new SimpleFormatter());
		    constraintLog.addHandler(constraintLogFile);
		    resultLogFile = new FileHandler("result_log.txt");
		    resultLogFile.setFormatter(new SimpleFormatter());
		    resultLog.addHandler(resultLogFile);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executionLog.info("Start of simulation...");
		
		PlacementModel pmodel = new PlacementModel();
		executionLog.info("Generating data center model...");
		//pmodel = new SmallTreeNetworkDCGen(pmodel).build();
		executionLog.info("Generating application model...");
		pmodel = new SmallThreeTierAppGen(pmodel).build();
		executionLog.info("Extracting variable model...");
		VariableModel vmodel = new VariableModel(pmodel);
		executionLog.info("Extracting constraint model...");
		ConstraintModel cmodel = new ConstraintModelGen(pmodel, vmodel).build();
		
		executionLog.info("Creating CHOCO model...");
		// Build the Choco constraint model
		CPModel m = new CPModel();
		/*
		for( IntegerVariable v : vmodel.getVmLocationVarMap().values() ) {
			m.addVariable(v);
		}
		for( IntegerVariable v : vmodel.getVvLocationVarMap().values() ) {
			m.addVariable(v);
		}
		*/
		executionLog.info("Propagating constaints to CHOCO model...");
		// System.out.println("Extracting constraints!");
		for( Constraint c : cmodel.getAllConstraints() ) {
			m.addConstraint(c);
			constraintLog.info(c.pretty());
		}
		
		executionLog.info("Building CHOCO solver...");
		// Build the solver
		CPSolver s = new CPSolver();
		// Read the model
		executionLog.info("Propagating CHOCO model to CHOCO solver...");
		//s.setTimeLimit(600000); // time limit 10min
		s.read(m);
		//s.minimize(true);
		
		// Solve the model
		executionLog.info("Solving the model with CHOCO solver...");
		s.solve();

		//System.out.println(vmodel.pretty());
		SolutionLogger solutionLogger = new SolutionLogger(pmodel, vmodel, s);
		int solutionCount = 0;
		boolean isCorrectSolution = true; // [2Do: check for correct solutions]
		//if( s.isFeasible() ) {
			do {
				if(s.checkSolution()) {
					solutionCount++;
					solutionLogger.logSolution("placement_result.txt");
				}
			} while ( isCorrectSolution && s.nextSolution() && solutionCount < 10 );
		//}
		//else{
			//System.out.println( "Placement problem is not feasable." );
		//}
	}

}

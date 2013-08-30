package com.ibm.block.model.util.impl;

import com.ibm.block.model.util.intf.IdInterface;

public class Id implements IdInterface {

	private static Id instance = null;					// Singelton Pattern: holds only instance of class 
	private Integer counter;							// Next id that is provided. Number of provided ids
	
	private Id() {
		counter = 0;
	}
	
	/**
	 * Returns the only single instance of the class Id. Implements the Singelton pattern. 
	 * 
	 * @return the only single instance of class Id
	 * @see com.ibm.block.model.util.intf.IdInterface#getInstance()
	 */
	public static Id getInstance() {					// Singelton Pattern: returns only instance of class
		if (instance == null) {							// Checks if first and only instance of class exists
            instance = new Id();						// Creates first and only instance of class
        }
        return instance;
	}

	/**
	 * Returns an <code>Integer</code> object that serves as an Id. Each call
	 * of the method increments the returned value of the <code>Integer</code>
	 * object by 1.
	 * 
	 * @return returns an integer counter
	 * @see com.ibm.block.model.util.intf.IdInterface#getId()
	 */
	@Override
	public Integer getId() {
		Integer i = new Integer(counter);				// Create Integer i from counter
		counter++;										// Increment counter
		return i;										// Return Integer i
	}

}

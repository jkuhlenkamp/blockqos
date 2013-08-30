package com.ibm.block.model.app.impl;

import java.security.InvalidParameterException;

import com.ibm.block.model.app.intf.VVolumeInterface;
import com.ibm.block.model.core.enums.PropertyType;
import com.ibm.block.model.core.impl.Resource;

public class VVolume extends VEntity implements VVolumeInterface {

	private VLink link;
	private VMachine vm;
	private static String namePrefix = "vv";
	
	public VVolume() {
		super(namePrefix);
	}

	@Override
	public String pretty() {
		String s = super.getName()+"[";
		//s += "class:" +super.getClass()+ ", ";
		//s += "id:" +super.getId()+ ", ";
		//s += "name:" +super.getName()+ ", ";
		s += "vl:" +link;
		s += "resources:[";
		for( Resource r : getResourceMap().values() ) {
			s += r.pretty()+ ", ";
		}
		s += "]";
		s += "properties:[";
		for( PropertyType p : getPropertyMap().values() ) {
			s += p+ ", ";
		}
		s += "]";
		s += "]";
		return s;
	}

	@Override
	public VLink getVLink() {
		return link;
	}

	@Override
	public VMachine getVMachine() {
		return vm;
	}

	@Override
	public boolean addVLink(VLink vl) {
		if( vl.hasEnd(this) ) {
			vm = (VMachine) vl.getOtherEnd(this);
			link = vl;
			if( vm != null ) {
				return true;
			}
			else {
				throw new InvalidParameterException("Error: The VLink object to connect " +
						"has no registered connected VMachine on the other end! " +
						"VLink:" +vl.pretty()+ ", this:" +pretty());
			}
		}
		else {
			throw new InvalidParameterException("Error: The VLink object to connect " +
					"has no registered attachment to this VVolume! VLink:" +vl.pretty()+ 
					", this: " +pretty());
		}
	}

	@Override
	public boolean isConnectedTo(VMachine vm) {
		if( this.vm.equals(vm) ) {
			return true;
		}
		return false;
	}

}

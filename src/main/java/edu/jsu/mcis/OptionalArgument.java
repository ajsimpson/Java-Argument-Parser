package edu.jsu.mcis;

public class OptionalArgument extends Argument {

	public boolean present = false;
	
	public OptionalArgument (String n) {
		name = n;
	}
	
	void setFlag(boolean f) {
		present = f;
	}
	
	boolean getFlag() {
		return present;
	}
}
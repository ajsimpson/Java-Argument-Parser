package edu.jsu.mcis;

public class NamedArgument extends Argument {

	public boolean present = false;
	
	public NamedArgument (String n) {
		name = n;
	}
	
	void setFlag(boolean f) {
		present = f;
	}
	
	boolean getFlag() {
		return present;
	}
}
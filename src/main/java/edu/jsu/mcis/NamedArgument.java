package edu.jsu.mcis;

public class NamedArgument extends Argument {

	private boolean present = false;
	private boolean grouped = false;
	private String groupName = "";
	
	public NamedArgument(String n) {
		name = n;
	}
	
	public void setFlag(boolean f) {
		present = f;
	}
	
	public boolean getFlag() {
		return present;
	}
	
	public void setGrouped(boolean g) {
		grouped = g;
	}
	
	public boolean isGrouped() {
		return grouped;
	}
	
	public void setGroup(String g) {
		groupName = g;
	}
	
	public String getGroup() {
		return groupName;
	}
}
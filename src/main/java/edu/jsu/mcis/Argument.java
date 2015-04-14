package edu.jsu.mcis;

import java.util.ArrayList;

public class Argument {

	public enum Datatype {STRING, FLOAT, INTEGER, BOOLEAN};
	
	protected String name = "";
	protected String info = "";
	protected Datatype type = Datatype.STRING;
	protected boolean containsRestrictedValues = false;
	protected ArrayList<String> restrictedValues = new ArrayList<>();
	protected boolean required = false;
	protected ArrayList<String> values = new ArrayList<>();
	
	public Argument() {
		values.add("0");
	}
	
	public String getName(){
		return name;
	}
	
	public void addValue(String v) {
		if(values.get(0) == "0") {
			values.set(0, v);
		}
		else if(!values.contains(v)) {
			values.add(v);
		}
	}
	
	public String getValue(int v){
		return values.get(v);
	}
	
	public void replaceValue(String v, int i) {
		if(values.size() >= i) {
			values.set(i, v);
		}
	}
	
	public ArrayList<String> getValues() {
		return values;
	}
	
	public void setInfo(String i){
		info = i;
	}
	
	public String getInfo(){
		return info;
	}
	
	public void setDatatype(Datatype d) {
		type = d;
	}
	
	public Datatype getDatatype() {
		return type;
	}
	
	public void addRestrictedValue(String c) {
		restrictedValues.add(c);
	}
	
	public ArrayList<String> getRestrictedValues() {
		return restrictedValues;
	}

	public void setRestrictedValues(boolean r) {
		containsRestrictedValues = r;
	}
	
	public boolean hasRestrictedValues() {
		return containsRestrictedValues;
	}
	
	public void isRestrictedValue(String v) {
		if(containsRestrictedValues) {
		boolean found = false;
		if(restrictedValues.contains(v))
			found = true;
		else
			throw new RestrictedValueException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: Invalid restricted value: " + v);
		}
	}
	
	public void setRequired(boolean r) {
		required = r;
	}
	
	public boolean isRequired() {
		return required;
	}
}
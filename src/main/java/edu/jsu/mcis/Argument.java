package edu.jsu.mcis;

public class Argument {

	private enum DataType {STRING, FLOAT, INT, BOOLEAN};

	public String name = "";
	public String value = "";
	public String info = "";
	public String type = "";
	
	String getName(){
		return name;
	}
	
	void setValue(String v){
		value = v;
	}
	
	String getValue(){
		return value;
	}
	
	void setInfo(String i){
		info = i;
	}
	
	String getInfo(){
		return info;
	}
	
	void setDataType(String d) {
		type = d;
	}
	
	String getDataType() {
		return type;
	}
}
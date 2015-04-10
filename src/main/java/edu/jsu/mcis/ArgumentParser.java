package edu.jsu.mcis;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class ArgumentParser {
	
    protected Map<String, PositionalArgument> positionalArguments;
    protected Map<String, NamedArgument> namedArguments;
	private Map<String, String> namedArgumentShortNames;
	private Map<String, ArrayList<String>> groups;
	private ArrayList<String> requiredArguments;
    
	public ArgumentParser() {
		positionalArguments = new HashMap<>();
		namedArguments = new HashMap<>();
		namedArgumentShortNames = new HashMap<>();
		groups = new HashMap<>();
		requiredArguments = new ArrayList<>();
	}
	
    public void addPositionalArgument(String name) {
        positionalArguments.put(name, new PositionalArgument(name));
    }
    
    public void addNamedArgument(String name) {
        namedArguments.put(name, new NamedArgument(name));
		String shortName = "" + name.charAt(0);
		namedArgumentShortNames.put(shortName, name);
    }
	
	public ArrayList<String> getPositionalArgumentNames() {
		ArrayList<String> positionalArgumentNames = new ArrayList<>();
		for (Map.Entry<String, PositionalArgument> entry : positionalArguments.entrySet()) {
			positionalArgumentNames.add(entry.getKey());
		}
		return positionalArgumentNames;
	}
	
	public ArrayList<String> getNamedArgumentNames() {
		ArrayList<String> namedArgumentNames = new ArrayList<>();
		for (Map.Entry<String, NamedArgument> entry : namedArguments.entrySet()) {
			namedArgumentNames.add(entry.getKey());
		}
		return namedArgumentNames;
	}
	
    public void addPositionalArgumentValue(String name, String value, Argument.Datatype type) {
        PositionalArgument temp = new PositionalArgument(name);
        temp.setValue(value);
        temp.setDatatype(type);
        positionalArguments.put(name,temp);
    }
    
    public void addNamedArgumentValue(String name, String value, Argument.Datatype type) {
        NamedArgument temp = new NamedArgument(name);
		temp.addRestrictedValue(value);
        temp.setValue(value);
        temp.setDatatype(type);
		if(type != Argument.Datatype.STRING) {
			checkForInvalidArguments(value);
		}
        namedArguments.put(name, temp);
    }
	
	@SuppressWarnings("unchecked")
    public <T> T getValue(String name) {
		if(positionalArguments.get(name) != null) {
			PositionalArgument temp = new PositionalArgument(name);
			temp = positionalArguments.get(name);
			if(temp.getDatatype() == Argument.Datatype.BOOLEAN) {
				return (T) Boolean.valueOf(temp.getValue());
			} 
			else if(temp.getDatatype() == Argument.Datatype.INTEGER) {
				return (T) new Integer(temp.getValue());
			} 
			else if(temp.getDatatype() == Argument.Datatype.FLOAT) {
				return (T) new Float(temp.getValue());
			} 
			else {
				return (T) temp.getValue();
			}
        }
		else if(namedArguments.get(name) != null) {
			NamedArgument temp = new NamedArgument(name);
			temp = namedArguments.get(name);
			if(temp.getDatatype() == Argument.Datatype.BOOLEAN) {
				return (T) Boolean.valueOf(temp.getValue());
			} 
			else if(temp.getDatatype() == Argument.Datatype.INTEGER) {
				return (T) new Integer(temp.getValue());
			} 
			else if(temp.getDatatype() == Argument.Datatype.FLOAT) {
				return (T) new Float(temp.getValue());
			} 
			else {
				return (T) temp.getValue();
			}
        }
		else {
            throw new UnknownArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unknown argument(s): " + name);
        }
    }
	
	public String getPositionalArgumentValue(String name) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return temp.getValue();
	}
	
	public String getNamedArgumentValue(String name) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		return temp.getValue();
	}
	
	public void setPositionalArgumentType(String name, Argument.Datatype type) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		temp.setDatatype(type);
		positionalArguments.put(name, temp);
	}
	
	public void setNamedArgumentType(String name, Argument.Datatype type) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		temp.setDatatype(type);
		namedArguments.put(name, temp);
	}
	
	public Argument.Datatype getPositionalArgumentType(String name) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return temp.getDatatype();
	}
	
	public Argument.Datatype getNamedArgumentType(String name) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		return temp.getDatatype();
	}
	
    public void addPositionalArgumentDescription(String name, String info) {
        PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
        temp.setInfo(info);
        positionalArguments.put(name, temp);
    }
	
    public String getPositionalArgumentDescription(String name) {
        PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
        return temp.getInfo();
    }
	
    public void addNamedArgumentDescription(String name, String info) {
        NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
        temp.setInfo(info);
        namedArguments.put(name, temp);
    }
	
    public String getNamedArgumentDescription(String name) {
        NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
        return temp.getInfo();
    }
	
	public void setFlag(String name, boolean flag) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		temp.setFlag(flag);
		namedArguments.put(name, temp);
	}
	
	public boolean getFlag(String name) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		return temp.getFlag();
	}

	public void addNamedRestrictedValue(String name, String value) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		temp.setRestrictedValues(true);
		temp.addRestrictedValue(value);
		namedArguments.put(name, temp);
	}
	
	public void addPositionalRestrictedValue(String name, String value) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		temp.setRestrictedValues(true);
		temp.addRestrictedValue(value);
		positionalArguments.put(name, temp);
	}
	
	public ArrayList<String> getNamedRestrictedValues(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.getRestrictedValues();
	}
	
	public ArrayList<String> getPositionalRestrictedValues(String name) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		return temp.getRestrictedValues();
	}
	
	public boolean hasNamedRestrictedValues(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.hasRestrictedValues();
	}
	
	public boolean hasPositionalRestrictedValues(String name) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		return temp.hasRestrictedValues();
	}
	
	public int getNumberOfNamedRestrictedValues(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.restrictedValues.size();
	}
	
	public int getNumberOfPositionalRestrictedValues(String name) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		return temp.restrictedValues.size();
	}
	
	public void setRequired(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		temp.setRequired(true);
		requiredArguments.add(name);
		namedArguments.put(name, temp);
	}

	public boolean isRequired(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.isRequired();
	}
	
	public ArrayList<String> getRequiredArguments() {
		return requiredArguments;
	}
	
	public void createGroup(String name) {
		groups.put(name, null);
	}
	
	public void addToGroup(String group, String name) {
		ArrayList<String> names = new ArrayList<>();
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		if(temp != null) {
			if(!temp.isGrouped()) {
				names.add(name);
				temp.setGrouped(true);
				temp.setGroup(group);
			}
			else {
				throw new MutualExclusionException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: Argument '" + name + "' already exists in group '" + temp.getGroup() + "'");
			}
		}
		else {
			throw new UnknownArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unknown argument(s): " + name);
		}
		groups.put(group, names);
	}
	
	public boolean isGrouped(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.isGrouped();
	}

	public ArrayList<String> getGroupValues(String group) {
		return groups.get(group);
	}
	
	public String getGroupName(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.getGroup();
	}

	public void parse(ArrayList<String> input) {
		String argument = "";
		String name = "";
		String index = "";
		float value;
		checkForRequiredArguments(input);
        for(int i=0; i<input.size(); i++) {
            if(input.get(i).contains("--help") || input.get(i).contains("-h")) {
                System.out.println(showHelp());
				System.exit(0);
			}
			else if(input.get(i).startsWith("--")) {
				argument = input.get(i).replace("--", "");
				NamedArgument temp = new NamedArgument(argument);
				temp = namedArguments.get(argument);
				if(temp == null) {
					throw new UnknownArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unknown argument(s): " + argument);
				}
				temp.setFlag(true);
				temp.setValue(input.get(i+1));
				temp.isRestrictedValue(input.get(i+1));
				namedArguments.put(argument, temp);
				input.set(i, "");
				input.set(i+1, "");
			}
			else if(input.get(i).contains("-") && !input.get(i).contains("--")) {
				argument = input.get(i).replace("-", "");
				if(namedArgumentShortNames.get(argument) != null) {
					name = namedArgumentShortNames.get(argument);
					NamedArgument temp = new NamedArgument(name);
					temp = namedArguments.get(name);
					temp.setFlag(true);
					temp.setValue(input.get(i+1));
					temp.isRestrictedValue(input.get(i+1));
					namedArguments.put(name, temp);
					input.set(i, "");
					input.set(i+1, "");
					}
				else {
					throw new UnknownArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unknown argument(s): " + argument);
				}
			}
		}
		int count = 0;
		ArrayList<String> names = new ArrayList<>();
		for(String arguments : positionalArguments.keySet()) {
			names.add(arguments);
		}
		for(int i=0; i<input.size(); i++) {
			if (input.get(i) != "" && count<names.size()) {
				try {
					value = Float.parseFloat(input.get(i));
					argument = names.get(count);
					PositionalArgument temp = new PositionalArgument(argument);
					temp = positionalArguments.get(argument);
					temp.setValue(input.get(i));
					positionalArguments.put(argument, temp);
					input.set(i, "");
					count++;
				}
				catch(Exception e) {
					checkForInvalidInput(input);
				}
			}
		}
		checkForMissingArguments(input);
		checkForUnrecognisedArguments(input);
	}
	
    public void checkForMissingArguments(ArrayList<String> s) {
        String missingArguments = "";
		for(String argument : positionalArguments.keySet()) {
			PositionalArgument temp = new PositionalArgument(argument);
			temp = positionalArguments.get(argument);
			if(temp.getValue() == "") {
				missingArguments += temp.getName() + " ";
			}
		}
		if(missingArguments != "") {
			throw new MissingArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: missing argument(s): " + missingArguments);
		}
    }
    
    public void checkForUnrecognisedArguments(ArrayList<String> s) {
        String unrecognisedArguments = "";
		float value;
		for(int i=0; i<s.size(); i++) {
			if(s.get(i) != "") {
				try {
					value = Float.parseFloat(s.get(i));
					unrecognisedArguments += s.get(i) + " ";
				} catch(Exception e) {}
			}
		}
		if(unrecognisedArguments != "") {
			throw new UnrecognisedArgumentException ("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unrecognised argument(s): " + unrecognisedArguments);
		}
    }
	
	public void checkForInvalidInput(ArrayList<String> s) {
		String invalidArguments = "";
		float value;
		for(int i=0; i<s.size(); i++) {
			try {
				value = Float.parseFloat(s.get(i));
			} catch(Exception e) {
				invalidArguments += s.get(i) + " ";
			}
		}
		if(invalidArguments != "") {
			throw new InvalidArgumentException("\nUsage: Java VolumeCalculator length width height\nVolumeCalculator.Java: error: argument width: invalid float value: " + invalidArguments + "\nThe following datatypes should be supported: int, float, boolean, and String, which is the default value if type is left unspecified.");
		}
	}
	
	public void checkForInvalidArguments(String s) {
		boolean valid = true;
		while(true) {
			try {
				Integer.parseInt(s);
				valid = true;
				break;
			} catch (Exception e) {
				valid = false;
				try {
					Float.parseFloat(s);
					valid = true;
					break;
				} catch (Exception f) {
					valid = false;
				}  
			}
		}
		if(!valid) {
			throw new InvalidArgumentException("\nUsage: Java VolumeCalculator length width height\nVolumeCalculator.Java: error: argument width: invalid float value: " + s + "\nThe following datatypes should be supported: int, float, boolean, and String, which is the default value if type is left unspecified.");
		}
	}
	
	public void checkForRequiredArguments(ArrayList<String> in) {
		String index = "";
		String index2 = "";
		String missingRequiredArguments = "";
		ArrayList<String> temp = new ArrayList<>();
		for(int i=0; i<in.size(); i++) {
			if(in.get(i).startsWith("--")) {
				index = in.get(i).replace("--", "");
				temp.add(index);
			}
			else if(in.get(i).contains("-") && !in.get(i).contains("--")) {
				index = in.get(i).replace("-", "");
				for(int j=0; j<requiredArguments.size(); j++) {
					if(requiredArguments.get(j).startsWith(index)) {
						index2 = requiredArguments.get(j);
						temp.add(index2);
					}
				}
			}
		}
		for(int i=0; i<requiredArguments.size(); i++) {
			if(!temp.contains(requiredArguments.get(i))) {
				missingRequiredArguments += (requiredArguments.get(i) + " ");
			}
		}
		if(missingRequiredArguments != "") {
			throw new InvalidArgumentException("\nUsage: Java VolumeCalculator length width height\nVolumeCalculator.Java: error: Missing required argument(s): " + missingRequiredArguments);
		}
	}

	public String typeToString(Argument.Datatype type) {
		if (type == Argument.Datatype.FLOAT) {
			return "float";
		}
		else if (type == Argument.Datatype.INTEGER) {
			return "integer";
		}
		else if (type == Argument.Datatype.BOOLEAN) {
			return "boolean";
		}
		else {
			return "String";
		}
	}
	
    public String showHelp() {
        return("\nUsage: Java VolumeCalculator length width height\nCalculate the volume of a box.\n\nPositional arguments:\nlength: the length of the box\nwidth: the width of the box\nheight: the height of the box");
    }
}
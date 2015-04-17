package edu.jsu.mcis;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
*	Used to parse arguments into useful, storable information
*/
public class ArgumentParser {
	
    protected Map<String, PositionalArgument> positionalArguments;
    protected Map<String, NamedArgument> namedArguments;
	private Map<String, String> namedArgumentShortNames;
	private Map<String, ArrayList<String>> groups;
	private ArrayList<String> requiredArguments;
    
	/**
	* Creates an ArgumentParser object used to parse arguments into useful, storable information
	*/
	public ArgumentParser() {
		positionalArguments = new HashMap<>();
		namedArguments = new HashMap<>();
		namedArgumentShortNames = new HashMap<>();
		groups = new HashMap<>();
		requiredArguments = new ArrayList<>();
	}
	
	/**
	* add a positional argument
	*
	* @param name - The name of the positional argument being added
	*/
    public void addPositionalArgument(String name) {
        positionalArguments.put(name, new PositionalArgument(name));
    }
    
	/**
	* add a named argument
	*
	* @param name - The name of the named argument being added
	*/
    public void addNamedArgument(String name) {
        namedArguments.put(name, new NamedArgument(name));
		String shortName = "" + name.charAt(0);
		namedArgumentShortNames.put(shortName, name);
    }
	
	/**
	* get the names of all positional arguments
	*
	* @return ArrayList<String> - The list of all positional arguments
	*/
	public ArrayList<String> getPositionalArgumentNames() {
		ArrayList<String> positionalArgumentNames = new ArrayList<>();
		for (Map.Entry<String, PositionalArgument> entry : positionalArguments.entrySet()) {
			positionalArgumentNames.add(entry.getKey());
		}
		return positionalArgumentNames;
	}
	
	/**
	* get the names of all named arguments
	*
	* @return ArrayList<String> - The list of all name arguments
	*/
	public ArrayList<String> getNamedArgumentNames() {
		ArrayList<String> namedArgumentNames = new ArrayList<>();
		for (Map.Entry<String, NamedArgument> entry : namedArguments.entrySet()) {
			namedArgumentNames.add(entry.getKey());
		}
		return namedArgumentNames;
	}
	
	/**
	* add a positional argument value with its datatype
	*
	* @param name - The name of the positional argument to by changed
	* @param value - The value to be added to the positional argument
	* @param type - The data type of the value being added
	*/
    public void addPositionalArgumentValue(String name, String value, Argument.Datatype type) {
        PositionalArgument temp = new PositionalArgument(name);
		temp.addValue(value);
        temp.setDatatype(type);
        if(type != Argument.Datatype.STRING) {
            checkForInvalidArguments(value);
        }
        positionalArguments.put(name,temp);
    }
	
	/**
	* add a named argument value with its datatype
	*
	* @param name - The name of the named argument to by changed
	* @param value - The value to be added to the positional argument
	* @param type - The data type of the value being added
	*/
    public void addNamedArgumentValue(String name, String value, Argument.Datatype type) {
        NamedArgument temp = new NamedArgument(name);
		temp.addRestrictedValue(value);
		temp.addValue(value);
        temp.setDatatype(type);
		if(type != Argument.Datatype.STRING) {
			checkForInvalidArguments(value);
		}
        namedArguments.put(name, temp);
    }
	
	/**
	*get the required type value of the argument
	*
	*@param name the name of the argument
	*@param <T> the type of the argument
	*@param index the value's position
	*@return the value in the required type of the argument
	*/
	@SuppressWarnings("unchecked")
    public <T> T getValue(String name, int index) {
		if(positionalArguments.get(name) != null) {
			PositionalArgument temp = new PositionalArgument(name);
			temp = positionalArguments.get(name);
			if(temp.getDatatype() == Argument.Datatype.BOOLEAN) {
				return (T) Boolean.valueOf(temp.getValue(index));
			} 
			else if(temp.getDatatype() == Argument.Datatype.INTEGER) {
				return (T) new Integer(temp.getValue(index));
			} 
			else if(temp.getDatatype() == Argument.Datatype.FLOAT) {
				return (T) new Float(temp.getValue(index));
			} 
			else {
				return (T) temp.getValue(index);
			}
        }
		else if(namedArguments.get(name) != null) {
			NamedArgument temp = new NamedArgument(name);
			temp = namedArguments.get(name);
			if(temp.getDatatype() == Argument.Datatype.BOOLEAN) {
				return (T) Boolean.valueOf(temp.getValue(index));
			} 
			else if(temp.getDatatype() == Argument.Datatype.INTEGER) {
				return (T) new Integer(temp.getValue(index));
			} 
			else if(temp.getDatatype() == Argument.Datatype.FLOAT) {
				return (T) new Float(temp.getValue(index));
			} 
			else {
				return (T) temp.getValue(index);
			}
        }
		else {
            throw new UnknownArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unknown argument(s): " + name);
        }
    }
	
	/**
	* replace a value in PositionalArgument
	*
	* @param name - The name of the positional argument being edited
	* @param index - The index value to be changed
	* @param value - The value being placed in the index
	*/
	public void replacePositionalValue(String name, int index, String value) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		temp.replaceValue(value, index);
	}
	
	/**
	* replace a value in NamedArgument
	*
	* @param name - The name of the named argument being edited
	* @param index - The index value to be changed
	* @param value - The value being placed in the index
	*/
	public void replaceNamedValue(String name, int index, String value) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		temp.replaceValue(value, index);
	}
	
	/**
	*get the value of the positional argument
	*
	*@param name the name of positional argument
	*@param index the position of the value
	*@return the value of the positional argument
	*/
	public String getPositionalArgumentValue(String name, int index) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return temp.getValue(index);
	}
	
	/**
	*get the value of the named argument
	*
	*@param name the name of named argument
	*@param index the position of the value
	*@return the value of the named argument
	*/
	public String getNamedArgumentValue(String name, int index) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		return temp.getValue(index);
	}
	
	/**
	*set the data type of the positional argument
	*
	*@param name the name of the positional argument
	*@param type the data type of the positional argument
	*/
	public void setPositionalArgumentType(String name, Argument.Datatype type) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		temp.setDatatype(type);
		positionalArguments.put(name, temp);
	}
	
	/**
	*set the data type of the named argument
	*
	*@param name the name of the named argument
	*@param type the data type of the named argument
	*/
	public void setNamedArgumentType(String name, Argument.Datatype type) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		temp.setDatatype(type);
		namedArguments.put(name, temp);
	}
	
	/**
	*get the value's type of the positional argument
	*
	*@param name the name of the positional argument
	*@return the data type of the positional argument
	*/
	public Argument.Datatype getPositionalArgumentType(String name) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return temp.getDatatype();
	}
	
	/**
	*get the value's type of the named argument
	*
	*@param name the name of the named argument
	*@return the data type of the named argument
	*/
	public Argument.Datatype getNamedArgumentType(String name) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		return temp.getDatatype();
	}
	
	/**
	*set a description for positional argument
	*
	*@param name the name of the positional argument
	*@param info the description of the positional argument
	*/
    public void addPositionalArgumentDescription(String name, String info) {
        PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
        temp.setInfo(info);
        positionalArguments.put(name, temp);
    }
	
	/**
	*get the description of the positional argument
	*
	*@param name the name of the positional argument
	*@return the description of the positional argument
	*/
    public String getPositionalArgumentDescription(String name) {
        PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
        return temp.getInfo();
    }
	
	/**
	*set a description for named argument
	*
	*@param name the name of the named argument
	*@param info the description of the named argument
	*/
    public void addNamedArgumentDescription(String name, String info) {
        NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
        temp.setInfo(info);
        namedArguments.put(name, temp);
    }
	
	/**
	*get the description of the named argument
	*
	*@param name the name of the named argument
	*@return the description of the named argument
	*/
    public String getNamedArgumentDescription(String name) {
        NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
        return temp.getInfo();
    }
	
	/**
	*set the flag for the argument
	*
	*@param name the name of the argument
	*@param flag the flag to this argument
	*/
	public void setFlag(String name, boolean flag) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		temp.setFlag(flag);
		namedArguments.put(name, temp);
	}
	
	/**
	*get the flag of the argument
	*
	*@param name the name of the argument
	*@return the flag of this argument
	*/
	public boolean getFlag(String name) {
        NamedArgument temp = new NamedArgument(name);
        temp = namedArguments.get(name);
		return temp.getFlag();
	}

	/**
	*set restricted value for the named argument
	*
	*@param name the name of the named argument
	*@param value the value need to be set as restricted
	*/
	public void addNamedRestrictedValue(String name, String value) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		temp.setRestrictedValues(true);
		temp.addRestrictedValue(value);
		temp.addValue(value);
		namedArguments.put(name, temp);
	}
	
	/**
	*set restricted value for the positional argument
	*
	*@param name the name of the positional argument
	*@param value the value need to be set as restricted
	*/
	public void addPositionalRestrictedValue(String name, String value) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		temp.setRestrictedValues(true);
		temp.addRestrictedValue(value);
		temp.addValue(value);
		positionalArguments.put(name, temp);
	}
	
	/**
	*get the restricted values of the named argument
	*
	*@param name the name of the named argument
	*@return the restricted values of named argument
	*/
	public ArrayList<String> getNamedRestrictedValues(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.getRestrictedValues();
	}
	
	/**
	*get the restricted values of the positional argument
	*
	*@param name the name of the positional argument
	*@return the restricted values of positional argument
	*/
	public ArrayList<String> getPositionalRestrictedValues(String name) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		return temp.getRestrictedValues();
	}
	
	/**
	*check if the named argument has restricted values
	*
	*@param name the name of the named argument
	*@return if the named argument has restricted values 
	*/
	public boolean hasNamedRestrictedValues(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.hasRestrictedValues();
	}
	
	/**
	*check if the positional argument has restricted values
	*
	*@param name the name of the positional argument
	*@return if the positional argument has restricted values
	*/
	public boolean hasPositionalRestrictedValues(String name) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		return temp.hasRestrictedValues();
	}
	
	/**
	*check how many restricted values are there in one named argument
	*
	*@param name the name of the named argument
	*@return the numbers of restricted values of this named argument
	*/
	public int getNumberOfNamedRestrictedValues(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.restrictedValues.size();
	}
	
	/**
	*check how many restricted values are there in one positional argument
	*
	*@param name the name of the positional argument
	*@return the numbers of restricted values of this positional argument
	*/
	public int getNumberOfPositionalRestrictedValues(String name) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		return temp.restrictedValues.size();
	}
	
	/**
	*set a required argument
	*
	*@param name the name of required argument
	*/
	public void setRequired(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		temp.setRequired(true);
		requiredArguments.add(name);
		namedArguments.put(name, temp);
	}

	/**
	*check if the argument is required 
	*
	*@param name is the name of the argument that we need to check
	*@return if the argument is required
	*/
	public boolean isRequired(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.isRequired();
	}
	
	/**
	*get the all required arguments
	*
	*@return the required arguments
	*/
	public ArrayList<String> getRequiredArguments() {	//not working
		return requiredArguments;
	}
	
	/**
	*create a group
	*
	*@param name the name of the group
	*/
	public void createGroup(String name) {
		groups.put(name, null);
	}
	
	/**
	*@param group the name of the mutual group the argument is to be placed in
	*@param name the value(s) to be put into the mutual group
	*@throws MutualExclusionException when the value is existing in other group.
	*@throws UnknownArgumentException when there is no such group find.
	*/
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
	
	/**
	*return if the element is in one of the group
	*
	*@param name the name of the element
	*@return if the element is in one of the group
	*/
	public boolean isGrouped(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.isGrouped();
	}

	/**
	*return the elements from the group we provided
	*
	*@param group name
	*@return elements in group map
	*/
	public ArrayList<String> getGroupValues(String group) {
		return groups.get(group);
	}
	
	/**
	*return the specific group name
	*
	*@param name the name of the group
	*@return the name of the specific group's name
	*/
	public String getGroupName(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.getGroup();
	}
	
	/**
	*return the group names
	*
	*@return the group names
	*/
	public ArrayList<String> getGroups() {
		ArrayList<String> groupNames = new ArrayList<>();
		for (Map.Entry<String, ArrayList<String>> entry : groups.entrySet()) {
			groupNames.add(entry.getKey());
		}
		return groupNames;
	}
	
	/**
	*return the list values of the positional Argument
	*
	*@param name the string name is the argument, we are trying to get the values from
	*@return the list of values
	*/
	public ArrayList<String> getPositionalValues(String name) {
		PositionalArgument temp = new PositionalArgument(name);
		temp = positionalArguments.get(name);
		return temp.getValues();
	}
	
	/**
	*return the list values of the named argument
	*
	*@param name is the optional argument, which we are trying to get the values from
	*@return the values of the argument we provided
	*/
	public ArrayList<String> getNamedValues(String name) {
		NamedArgument temp = new NamedArgument(name);
		temp = namedArguments.get(name);
		return temp.getValues();
	}

	/**
	*Parses the string passed in through the Command Line.
	*
	*
	*@param input the string Arraylist to be parsed
	*@throws MissingArgumentException if the amount of values parsed does not match the number of required arguments
	*@throws InvalidArgumentException if the amount of values parsed exceeds the number of required arguments
	*@throws UnknownArgumentException if the data type of a value does not match the data type of the argument
	*/
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
				temp.addValue(input.get(i+1));
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
					temp.addValue(input.get(i+1));
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
					temp.addValue(input.get(i));
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

	/**
	*Check if there is missing some values
	*
	*@param s is the values that user inputed
	*@throws MissingArgumentException if there is missing some values
	*/
    public void checkForMissingArguments(ArrayList<String> s) {
        String missingArguments = "";
		for(String argument : positionalArguments.keySet()) {
			PositionalArgument temp = new PositionalArgument(argument);
			temp = positionalArguments.get(argument);
			if(temp.getValue(0) == "0") {
				missingArguments += temp.getName() + " ";
			}
		}
		if(missingArguments != "") {
			throw new MissingArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: missing argument(s): " + missingArguments);
		}
    }
    
	/**
	*Check if the user input too many argument
	*
	*@param s the extra values read from the commend line
	*@throws UnrecognisedArgumentException if there has the extra values
	*/
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
	
	/**
	*Check if the user input too many argument
	*
	*@param s the extra values read from the commend line
	*@throws InvalidArgumentException if there is any invalid argument
	*/
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
	
	/**
	*Check if the product owner input the legal values.
	*
	*@param s is the argument, which user input.
	*@throws InvalidArgumentException if the argument is found illegal
	*/
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
				try {
					Boolean.parseBoolean(s);
					valid = true;
					break;
					} catch (Exception g) {
						valid = false;
					}
			}
		}
		if(!valid) {
			throw new InvalidArgumentException("\nUsage: Java VolumeCalculator length width height\nVolumeCalculator.Java: error: argument width: invalid float value: " + s + "\nThe following datatypes should be supported: int, float, boolean, and String, which is the default value if type is left unspecified.");
		}
	}
	
	/**
	*Check if this argument is required or not
	*
	*@param in the user input from commend line.
	*@throws InvalidArgumentException if the argument is required but not find in commend line.
	*/
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
			throw new RequiredArgumentException("\nUsage: Java VolumeCalculator length width height\nVolumeCalculator.Java: error: Missing required argument(s): " + missingRequiredArguments);
		}
	}

	/**
	*return the value, which shows the argument type.
	*
	*@param type the data type of value
	*@return get String type datatype instead of enum type of data type
	*/
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
	
	/**
	*return the help text when find -h or --help in commend line.
	*
	*@return the text of showHelp().
	*/
    public String showHelp() {
        return("\nUsage: Java VolumeCalculator length width height\nCalculate the volume of a box.\n\nPositional arguments:\nlength: the length of the box\nwidth: the width of the box\nheight: the height of the box");
    }
}
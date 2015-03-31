package edu.jsu.mcis;

import java.util.ArrayList;
import java.util.HashMap;

public class ArgumentParser {
	
    private HashMap<String, PositionalArgument> positionalArguments;
    private HashMap<String, OptionalArgument> optionalArguments;
	private HashMap<String, String> optionalArgumentShortNames;
    
	public ArgumentParser() {
		positionalArguments = new HashMap<>();
		optionalArguments = new HashMap<>();
		optionalArgumentShortNames = new HashMap<>();
	}
	
    public void addPositionalArgument(String name) {
        positionalArguments.put(name, new PositionalArgument(name));
    }
    
    public void addOptionalArgument(String name) {
        optionalArguments.put(name, new OptionalArgument(name));
		String shortName = "" + name.charAt(0);
		optionalArgumentShortNames.put(shortName, name);
    }
	
    public void addPositionalArgumentValue(String name, String value, Argument.Datatype type) {
        PositionalArgument temp = new PositionalArgument(name);
        temp.setValue(value);
        temp.setDatatype(type);
        positionalArguments.put(name,temp);
    }
    
    public void addOptionalArgumentValue(String name, String value, Argument.Datatype type) {
        OptionalArgument temp = new OptionalArgument(name);
        temp.setValue(value);
        temp.setDatatype(type);
		if(type != Argument.Datatype.STRING) {
			checkForInvalidArguments(value);
		}
        optionalArguments.put(name, temp);
    }
	
	public String getPositionalArgumentValue(String name) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return temp.getValue();
	}
	
	public String getOptionalArgumentValue(String name) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		return temp.getValue();
	}
	
    public void addOptionalArgumentDescription(String name, String info) {
        OptionalArgument temp = new OptionalArgument(name);
		temp = optionalArguments.get(name);
        temp.setInfo(info);
        optionalArguments.put(name, temp);
    }
	
    public String getOptionalArgumentDescription(String name) {
        OptionalArgument temp = new OptionalArgument(name);
		temp = optionalArguments.get(name);
        return temp.getInfo();
    }
	
	public void setFlag(String name, boolean flag) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		temp.setFlag(flag);
		optionalArguments.put(name, temp);
	}
	
	public boolean getFlag(String name) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		return temp.getFlag();
	}
	
	public void setOptionalArgumentType(String name, Argument.Datatype type) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		temp.setDatatype(type);
		optionalArguments.put(name, temp);
	}
	
	public Argument.Datatype getOptionalArgumentType(String name) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		return temp.getDatatype();
	}
	
	//CREATE METHOD TO GET AND SET RESTRICTED CHOICES FOR ARGUMENT
	public void setChoice(String n, String c) {
		OptionalArgument temp = new OptionalArgument(n);
		temp = optionalArguments.get(n);
		temp.setChoice(c);
		optionalArguments.put(n, temp);
	}
	
	public void getChoices(String name) {
		OptionalArgument temp = new OptionalArgument(name);
		temp = optionalArguments.get(name);
		temp.getChoices();
	}
		
		
	@SuppressWarnings("unchecked")
    public <T> T getPositionalArgument(String name) {
		Object v = 0f;
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
        if(null != temp.type) switch (temp.type) {
            case INTEGER:
                v = Integer.parseInt(temp.value);
                parsePositionalArgumentInt(name);
            case FLOAT:
                v = Float.parseFloat(temp.value);
                parsePositionalArgumentFloat(name);
            case BOOLEAN:
                v = Boolean.parseBoolean(temp.value);
                parsePositionalArgumentBoolean(name);
            case STRING:
                v = temp.value;
        }
        return (T) v;
    }
    
	@SuppressWarnings("unchecked")
    public <T> T getOptionalArgument(String name) {
		Object v = null;
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
        if(null != temp.type) switch (temp.type) {
            case INTEGER:
                v = Integer.parseInt(temp.value);
                parseOptionalArgumentInt(name);
            case FLOAT:
                v = Float.parseFloat(temp.value);
                parseOptionalArgumentFloat(name);
            case BOOLEAN:
                v = Boolean.parseBoolean(temp.value);
                parseOptionalArgumentBoolean(name);
            case STRING:
                v = temp.value;
        }
        return (T) v;
    }
	
	public int parsePositionalArgumentInt(String name) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return Integer.parseInt(temp.value);
	}
	
	public float parsePositionalArgumentFloat(String name) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return Float.parseFloat(temp.value);
	}
	
	public boolean parsePositionalArgumentBoolean(String name) {
        PositionalArgument temp = new PositionalArgument(name);
        temp = positionalArguments.get(name);
		return Boolean.parseBoolean(temp.value);
	}
	
	public int parseOptionalArgumentInt(String name) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		return Integer.parseInt(temp.value);
	}
	
	public float parseOptionalArgumentFloat(String name) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		return Float.parseFloat(temp.value);
	}
	
	public boolean parseOptionalArgumentBoolean(String name) {
        OptionalArgument temp = new OptionalArgument(name);
        temp = optionalArguments.get(name);
		return Boolean.parseBoolean(temp.value);
	}
	
	public void parse(ArrayList<String> input) {
		String argument = "";
		String name = "";
		String index = "";
		float value;
        for(int i=0; i<input.size(); i++) {
            if(input.get(i).contains("--help") || input.get(i).contains("-h")) {
                System.out.println(showHelp());
				System.exit(0);
			}
			else if(input.get(i).startsWith("--")) {
				argument = input.get(i).replace("--", "");
				OptionalArgument temp = new OptionalArgument(argument);
				temp = optionalArguments.get(argument);
				if(temp == null) {
					throw new UnknownArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unknown arguments: " + argument);
				}
				temp.setFlag(true);
				temp.setValue(input.get(i+1));
				optionalArguments.put(argument, temp);
				input.set(i, "");
				input.set(i+1, "");
			}
			else if(input.get(i).contains("-") && !input.get(i).contains("--")) {
				argument = input.get(i).replace("-", "");
				if(optionalArgumentShortNames.get(argument) != null) {
					name = optionalArgumentShortNames.get(argument);
					OptionalArgument temp = new OptionalArgument(name);
					temp = optionalArguments.get(name);
					temp.setFlag(true);
					temp.setValue(input.get(i+1));
					optionalArguments.put(name, temp);
					input.set(i, "");
					input.set(i+1, "");
					}
				else {
					throw new UnknownArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unknown arguments: " + argument);
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
			throw new MissingArgumentException("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: missing arguments: " + missingArguments);
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
			throw new UnrecognisedArgumentException ("\nUsage: Java VolumeCalculator length width height \nVolumeCalculator.Java: error: unrecognised arguments: " + unrecognisedArguments);
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
					try {
						Boolean.parseBoolean(s);
						valid = true;
						break;
					} catch (Exception h) {
						valid = false;
					}
				}  
			}
		}
		if(!valid) {
			throw new InvalidArgumentException("\nUsage: Java VolumeCalculator length width height\nVolumeCalculator.Java: error: argument width: invalid float value: " + s + "\nThe following datatypes should be supported: int, float, boolean, and String, which is the default value if type is left unspecified.");
		}
	}

    public String showHelp() {
        return("\nUsage: Java VolumeCalculator length width height\nCalculate the volume of a box.\n\nPositional arguments:\nlength: the length of the box\nwidth: the width of the box\nheight: the height of the box");
    }
}
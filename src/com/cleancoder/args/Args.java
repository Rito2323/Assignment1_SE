package com.cleancoder.args;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

public class Args {
  private Map<Character, ArgumentMarshaler> marshalers;
  private Set<Character> argsFound;
  private ListIterator<String> currentArgument;
  public Boolean isSchema = false;
  public Boolean isArgumentPassed = false;
  

  public Args(String schema, String[] args) throws ArgsException {
    
	/*
	 * Defining the HashMap that will store the key value pair of Character in schema 
	 * and its corresponding type  
	 */
	marshalers = new HashMap<Character, ArgumentMarshaler>();
    /*
     * The arguments given as input will be stored in this HashSet
     * 
     */
	argsFound = new HashSet<Character>();

    /*
     *If schema sent from main is not null
     *Call parseSchema()
     */
	if(schema!="") {
	 isSchema=true;
     parseSchema(schema);}
    /*
     * If schema sent from main is null
     * Prompt the user so that he doesn't pass null schema 
     */
	else {
     isSchema = false;
     System.out.println("You cannot pass null schema");}
    
	/*
	 * The input arguments cannot be NULL
	 * If its NULL , prompt user to send proper arguments
	 */
	if(isSchema.equals(true) && args!=null)
	{   isArgumentPassed=true;
		parseArgumentStrings(Arrays.asList(args));}
	else
	System.out.println("You cannot pass null array of inputs");
  }

  /*
   * ParseSchema() parses the schema and get hold of each character in the schema's string to put in HashMap
   */
  private void parseSchema(String schema) throws ArgsException {
      /*
	   * As null check for Schema's string is already done we don't need any other validation in this method
	   * Directly for loop can start and schema will at least be length of 1
	   * We are getting hold of each character in the schema
	   */
	 for (String element : schema.split(","))
     parseSchemaElement(element.trim());
     }

  /*
   * This method parses each schema element(the letter) getting hold of the letter(elementId) from schema String
   * after seeing the subsequent character(tail) we determine what type ;the elemntId should represent
   * and store as key value pair in HashMap  [ElementId and its type]
   */
  private void parseSchemaElement(String element) throws ArgsException {
     /*
      * Get hold of elementId being passed ; which is basically the passed element's String's first character 
      */
	char elementId = element.charAt(0);
    /*
     * Get hold of stream of characters from second character onwards in element String to determine the type of the element (inside the passed schema)
     */
	String elementTail = element.substring(1);
    /*
     * Before putting into HashMap first validate the elementId
     * The elementId should be a letter 
     */
	validateSchemaElementId(elementId);
    /*
     * Once validation done we can put in HashMap the elementId and its corresponding type
     */
	PutInHashMap(elementTail,elementId);
    
  }

  /*
   * This below method checks for validation of Schema's each ElementId
   * It should be letter otherwise throw the exception
   */
  private void validateSchemaElementId(char elementId) throws ArgsException {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
  }
  
  /*
   * This method puts in HashMap the key-value pair of elementId and its type checking elementTail
   */
  private void PutInHashMap(String elementTail,char elementId) throws ArgsException {
	  
	    if (elementTail.length() == 0) 
	      marshalers.put(elementId, new BooleanArgumentMarshaler());
	    else if (elementTail.equals("*"))
	      marshalers.put(elementId, new StringArgumentMarshaler());
	    else if (elementTail.equals("#"))
	      marshalers.put(elementId, new IntegerArgumentMarshaler());
	    else if (elementTail.equals("##"))
	      marshalers.put(elementId, new DoubleArgumentMarshaler());
	    else if (elementTail.equals("[*]"))
	      marshalers.put(elementId, new StringArrayArgumentMarshaler());
	    else if (elementTail.equals("&"))
	      marshalers.put(elementId, new MapArgumentMarshaler());
	    else
	     throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
  }

  /*
   * This method parses arguments passed to the java class
   * for every argument string that is preceded by -
   * it takes the argument character and puts in the set argsFound 
   */
  private void parseArgumentStrings(List<String> argsList) throws ArgsException {
    for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
      String argString = currentArgument.next();
      if(argString != null)
      { if (argString.startsWith("-")) {
        parseArgumentCharacters(argString.substring(1));
      } else {
        currentArgument.previous();
        break;
      }
     }
    }
  }
  
  /*
   * This method takes in the next character after -
   * which will be actually elementId of the passed arguments
   */
  private void parseArgumentCharacters(String argChars) throws ArgsException {
      for (int i = 0; i < argChars.length(); i++)
      parseArgumentCharacter(argChars.charAt(i));
  }

  /*
   * After we get the argument elementId it looks at HashMap m
   * If we get from the HashMap that means its valid argument passed in sync with the schema
   */
  private void parseArgumentCharacter(char argChar) throws ArgsException {
    ArgumentMarshaler m = marshalers.get(argChar);
    if (m == null) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    } else {
      argsFound.add(argChar);
      try {
        m.set(currentArgument);
      } catch (ArgsException e) {
        e.setErrorArgumentId(argChar);
        throw e;
      }
    }
  }

  public boolean has(char arg) {
    return argsFound.contains(arg);
  }

  public int nextArgument() {
    return currentArgument.nextIndex();
  }
/*
 * The below codes gets the values for the passes arguments calling the data type class
 */
  public boolean getBoolean(char arg) {
    return BooleanArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String getString(char arg) {
    return StringArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public int getInt(char arg) {
    return IntegerArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public double getDouble(char arg) {
    return DoubleArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public String[] getStringArray(char arg) {
    return StringArrayArgumentMarshaler.getValue(marshalers.get(arg));
  }

  public Map<String, String> getMap(char arg) {
    return MapArgumentMarshaler.getValue(marshalers.get(arg));
  }
}
package com.cleancoder.args;

public class ArgsMain {
	public static Boolean isExeceutionDone= false;
    
	public static void main(String[] args) {
    
    try {
      
      /*
       * The arguments passed to code
       */
      String argumengts1[]= {"-l","-p","1345","-d","Rito","-s","4.567","-f","r","-f","r1","-f","r2"};
      /*
       * The schema is set and object of Args class is created calling constructor
       */
      Args arg = new Args("l,p#,d*,s##,f[*]", argumengts1);
      
     /*
      * Call the below method and set all the passed arguments to appropriate variables inside the method
      */
      setOfvaluesPassedAsArguments(arg);
      
    } catch (ArgsException e) {
      System.out.printf("Argument error: %s\n", e.errorMessage());
    }
  }
    
    public static void  setOfvaluesPassedAsArguments(Args arg)
    {
    	
    	
    	
    	boolean boolean_value = arg.getBoolean('l');
        int integer_value = arg.getInt('p');
        String string_value = arg.getString('d');
        String array[]=arg.getStringArray('f');
        Double doubleValue=arg.getDouble('s');
        executeApplication(boolean_value, integer_value, string_value,array,doubleValue);
    }
  
    public static void executeApplication(boolean boolean_value, int integer_value, String string_value,String[] array,Double doubleValue ) {
	System.out.println(" ");
	System.out.println("THE VALUES PASSED AS ARGUMENTS AS " );
    System.out.println(" ");
    System.out.println("Boolean Value as " + boolean_value);
    System.out.println(" ");
    System.out.println("Integer as "+ integer_value);
    System.out.println(" ");
    System.out.println("String as "+ string_value);
    System.out.println(" ");
    if(array.length!=0)
     System.out.println("Array as " + array[0] +" " + array[1]+" " + array[2] );
    else
     System.out.println("Array as " + null );	
    System.out.println(" ");
    System.out.println("Double Value as " + doubleValue);
    /*
     * Set of true means completion of execution
     */
    isExeceutionDone= true;
    }
   
}
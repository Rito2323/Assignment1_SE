
## 1)CODE RESTRUCTURING DONE AS FOLLOWS:

### Code Changes I incorporated in Args.java:

Using 2 class variables: Boolean isSchema and Boolean isArgumemtPassed.
 -Earlier code was throwing exception on either of them null or both null. 
 
 
 -Now I will first check for null schema and null arguments passed and set the 2 variables to true/false.


-Depending on false value set, I will show appropriate message to prompt user what is expected to be passed to code as arguments/schema.


-Used proper commenting and methods keeping in mind of clean code practices.

### Code Changes I incorporated in Argsmain.java:

Completely restructured it
-Created a new method setOfvaluesPassedAsArguments(arg);
-Created a static public Boolean variable which will be set to true once the complete execution of flow is completed 
-Inside that method setting passed arguments to appropriate class variables
-Then calling executeApplication method from setOfvaluesPassedAsArguments
-Printing all the values passed as arguments with its data type in console

### Code changes I incorporated in ArgsTest.java:

Modified 3 test cases 
1) testCreateWithNoSchemaOrArguments , testWithNoSchemaButWithOneArgument
 , testWithNoSchemaButWithMultipleArgument
My new code has 2 variables isArgumentPassed and isSchema which gets set to false when either of them null .So assert should be on them for the above 3 test scenarios

No unit testing was there to test out Main class:
2)Added new test cases to test the ArgsMain class
  testMainClass_setOfvaluesPassedAsArguments_arrayPassedAsNull()
  testMainClass_setOfvaluesPassedAsArguments_doublePassedAsNull()
  testMainClass_setOfvaluesPassedAsArguments_inetegerPassedAsNull()

######################################

## 2)LINK TO VIEW THE EXECUTION RESULTS:
https://drive.google.com/file/d/1TKza3eJEMZF5su2VJhaMCsS5Jd_BUD8L/view?usp=sharing

#######################################

## 3)WHY MY CODE IS CLEAN:

1)Gave appropriate comments.
2)Gave null handlings.
   Code was missing null handlings in many areas.
3)When there is lot of lines of code in method, grouped those lines into another method so that’s its readable.
4)Ensured method names are good and they convey about the central logic of what the method does.
5)Tested the code with more inputs and scenarios to see if there is any loophole or not.
6)Changed the main.java completely restructuring it and to display more proper output statements in the console, so that the user understands what is expected output when main.java is run.
7)Changed the ArgTest.java to accommodate my changes in Args.java
8)ArgTest.java is changed too accommodate test cases to test ArgMain.java's code

########################################


This is the java version of the Args program 
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
      setOfvaluesPassedAsArguements(arg);
      
    } catch (ArgsException e) {
      System.out.printf("Argument error: %s\n", e.errorMessage());
    }
  }
    
    public static void  setOfvaluesPassedAsArguements(Args arg)
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

Schema:
 - char    - Boolean arg.
 - char*   - String arg.
 - char#   - Integer arg.
 - char##  - double arg.
 - char[*] - one element of a string array.

Example schema: (l,p#,d*,s##,f[*])
Coresponding command line: -l,-p,1345,-d,Rito,-s,4.567,-f,r,-f,r1,-f,r2


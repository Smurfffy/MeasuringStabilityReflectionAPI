// Alan Murphy G00312295
//This class will calculate the Positional Stability of each class in jar file.
package ie.gmit.sw;


import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * A class which reads the jar file, adds its class names to a map and calculates the Efferent and Afferent Couplings
 * 
 * @author Alan Murphy
 * @version 1.0
 *
 */
public class PositionalStability {
	
	// The map is used to store the names of classes as keys
	private HashMap<String, Couplings> map = new HashMap<>();
    private String jar;
    
    // Constructor to be called in the Runner class
    /**
     * Constructor to be called in the runner class
     * 
     * @param path a string for the path name
     */
    public PositionalStability(String path){

        this.jar = path;
        storeClassNames();
        calculatePositionalStability();
        for(Couplings c : map.values()){
        	//For this output I tried to print the names of the classes to the screen without the package info. I know there is a getSimpleName(); in reflection
        	// but i could not get it to work here.
        	System.out.printf("\nPositional Stability is %.2f for Class: %s", c.positionalStability(), c.getClassName());
        }
    }
    
    /**
     * Calculates Efferent and Affernet Couplings of each class, slightly missleading name
     */
    public void calculatePositionalStability(){

        try {
            File file = new File(jar);
            //I was unsure of loading the classes from the jar file from a URLClassLoader. Originaly I wanted to load the jar file from the project solution 
            //Which is what I assume you wanted for this project. I found tutorials and help for URL class loaders on stack overflow and this way worked for me
            //and loading it through the solution did not.
            URL clsurl = file.toURI().toURL();
            URL[] clsArray = new URL[]{clsurl};
            ClassLoader cl = new URLClassLoader(clsArray);
            // loop for each key in the  map so the positional stability of each class can be calculated
            for (String className : map.keySet()) {
                // The Class loader gets the name of the class, checks if its initilzed and gets our URLClassLoader
                Class<?> cls = Class.forName(className, false, cl);
                // this is the variable for the number of edges emanating from a type. Important for calculating Positional Stability
                int EffentCoupling = 0;
                //------Get the set of interface it implements...Code from project specification.------
                @SuppressWarnings("rawtypes")
				Class[] interfaces = cls.getInterfaces();
                for(Class<?> i : interfaces){
                	EffentCoupling++;
                	if(map.containsKey(i.getName())){
                		EffentCoupling++;
                		Couplings c = map.get(i.getName());
                		c.setAfferentCoup(c.getAfferentCoup() + 1);    		
                	}
                }
                //------Get the set of constructors...Code from project specification.------
                @SuppressWarnings("rawtypes")
				Constructor[] cons = cls.getConstructors();
                @SuppressWarnings("rawtypes")
				Class[] constructorParams;
                // Gets the parameters of each constructor
                for(Constructor<?> c : cons){
                    constructorParams = c.getParameterTypes();
                    for(Class<?> param : constructorParams){
                        if(map.containsKey(param.getName())){
                        	EffentCoupling++;
                        	Couplings coup = map.get(param.getName());
                        	coup.setAfferentCoup(coup.getAfferentCoup() + 1);
                        }
                    }
                }
                //------Get the fields / attributes...Code from project specification.------
                Field[] fields = cls.getFields();
                for(Field f : fields){
                    if(map.containsKey(f.getName())){
                    	EffentCoupling++;
                    	Couplings coup = map.get(f.getName());
                    	coup.setAfferentCoup(coup.getAfferentCoup() + 1);
                    }
                }
                //------Get the set of methods...Code from project specification.------
                Method[] methods = cls.getMethods();
                @SuppressWarnings("rawtypes")
				Class[] methodParams;
                for(Method m : methods){
                    Class<?> methodReturnType = m.getReturnType();
                    if(map.containsKey(methodReturnType.getName())){
                    	EffentCoupling++;
                    	Couplings coup = map.get(methodReturnType.getName());
                    	coup.setAfferentCoup(coup.getAfferentCoup() + 1);
                    }
                    methodParams = m.getParameterTypes();
                    for(Class<?> mp : methodParams){
                        if(map.containsKey(mp.getName())){
                        	EffentCoupling++;
                        	Couplings coup = map.get(mp.getName());
                        	coup.setAfferentCoup(coup.getAfferentCoup() + 1);
                        }
                    }
                }
                // sets the value of EfferentCouplings for the class
                map.get(cls.getName()).setEfferentCoup(EffentCoupling);
                //Loops through for each class in the jar file
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    
    // Method for storing the class names in the jar file to a map.
    // This is mostly code given in the specification for this project
    /**
     * Method for storing the names of class to a hash map as keys.
     */
    public void storeClassNames(){

        try {
            File file  = new File(jar); 
            //The below code reads the classes in the jar file
            //As given in the specification for the project
            @SuppressWarnings("resource")
			JarInputStream JARin = new JarInputStream(new FileInputStream(file));
            JarEntry next = JARin.getNextJarEntry();
            while (next != null) {
                if (next.getName().endsWith(".class")) {
                    String name = next.getName().replaceAll("/", "\\.");
                    name = name.replaceAll(".class", "");
                    if (!name.contains("$")) name.substring(0, name.length() - ".class".length());
                    // Adds the classes to a map so its name can be stored as keys
                    // Keeps a count of how many classes are loaded in
                    map.put(name, new Couplings());
                    map.get(name).setClassName(name);
                }
                next = JARin.getNextJarEntry();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

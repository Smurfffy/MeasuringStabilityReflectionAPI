//Alan Murphy G00312295
//Class for getting Afferent couplings and Efferent Couplings and calculating positional stability.
package ie.gmit.sw;

/**
 * Gets the values of the Efferent and Afferent Couplings of the classes in the jar file and Calculated the Positional Stability.
 * 
 * @author Alan Murphy
 * @version 1.0
 *
 */
public class Couplings {
	
	private int AfferentCoup; //The number of edges incident on a type.
	private int EfferentCoup; //the number of edges emanating from a type
	private String className; // The name of the class in the JAR file
	
	
	/**
	 * Returns the name of the class
	 * 
	 * @return a String
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * Sets the class name 
	 * 
	 * @param className
	 * the name of the class
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * Gets the value for the Afferent Couplings
	 * 
	 * @return an int
	 */
	public int getAfferentCoup() {
		return AfferentCoup;
	}
	/**
	 * Sets the value of the Afferent Couplings of a class
	 * 
	 * @param afferentCoup
	 * An integer which stores the value of the classes Affernet Coupling
	 */
	public void setAfferentCoup(int afferentCoup) {
		AfferentCoup = afferentCoup;
	}
	/**
	 * Gets the value for the Efferent Couplings
	 * 
	 * @return an int
	 */
	public int getEfferentCoup() {
		return EfferentCoup;
	}
	/**
	 * Sets the value of the Efferent Couplings of a class
	 * 
	 * @param efferentCoup
	 * An integer which stores the value of the classes Efferent Coupling
	 */
	public void setEfferentCoup(int efferentCoup) {
		EfferentCoup = efferentCoup;
	}
	
	// The positional stability is calculated for the classes here
	// positional stability is a metric range [0...1] with zero indicating a maximally stable class.
	// Positional stability (I) calculated with Afferent Couplings(Ca) and Efferent Couplings(Ce) with formula....
	// I = Ce / Ca + Ce.
	
	/**
	 * 
	 * @return the Positional Stability
	 * 
	 * The Positional stability is calculated and value i is returned
	 */
	public float positionalStability(){
		float i = 1f;
		try{
			if(getEfferentCoup() > 0){
				i = ((float) getEfferentCoup() / ((float) getAfferentCoup() + (float) getEfferentCoup()));
			}else{
				i = 0f;
			}
		}catch (Exception e){
			i = 0f;
		}
		return i;
	}
}

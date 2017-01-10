//Alan Murphy G00312295
//Main class for running the application.
package ie.gmit.sw;

/**
 * The runner class for running the application
 * 
 * @author Alan Murphy
 * @version 1.0
 */

public class Runner {
	/**
	 * Main method creates and instance of the Positional Stability Class and starts the swing GUI
	 * @param args
	 */
	public static void main(String[] args){
		
		@SuppressWarnings("unused")
		PositionalStability I = new PositionalStability("Enter path for Jar file here"); // <---- Must manually be entered before running
		
		//Example(Jar file included in submission folder)
		//PositionalStability I = new PositionalStability("C:/Users/User/Desktop/Sort/G00312295/servicestring.jar");
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new AppWindow();
			}
		});
	}
}


public class Main
{
	public static CarVector carLoc = new CarVector(0,0,0);
	public static SerialIOController VCM_Communicator;
	public static CSV_Controller Data_Controller;
	public static PyScriptRunner ScriptRunner;
	public static GUIController Application;
	private static int GUIWidth = 925;
	private static int GUIHeight = 720;
	static final int X = 0;
	static final int Y = 1;
	static final int Z = 2;
	
	public static void main(String args[])
	{
		// create a Communication controller
		VCM_Communicator = new SerialIOController();
		// create a GUI controller (Automatically opens itself
		Application = new GUIController(GUIWidth,GUIHeight);
		// create a CSV controller
		Data_Controller = new CSV_Controller();
		// create a new Python Script Runner
		ScriptRunner = new PyScriptRunner();
		
		
		
		System.out.println("Started");
	}
	
}

/*
// the following thread creation is only need while we don't also have a GUI or some other thread
		// running and keeping this process alive.
		Thread t=new Thread()
		{
			public void run() 
			{
				// lets just keep this app live for 1,000 seconds to try and read and
				// write some data
				try {Thread.sleep(1000000);} 
				catch (InterruptedException ie) 
				{
					// no need to really worry if this is interuptted.
				}
			}
		};
		t.start();
		System.out.println("Started");
*/

public class PyScriptRunner implements Runnable
{
private String nameOfScript;
public PyScriptRunner()
{

}

public synchronized void runPyScript(String s)// pulls out the 
{
	try
	{
		Runtime.getRuntime().exec(s);
	}
	catch(Exception e)
	{
		System.err.println("Hey your python script didn't run");
	}
}

public void run()
{
	runPyScript(nameOfScript);
}

}

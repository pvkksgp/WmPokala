package WxDevMotivation;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import com.softwareag.util.IDataMap;
// --- <<IS-END-IMPORTS>> ---

public final class util

{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void generateRandomInt (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(generateRandomInt)>> ---
		// @sigtype java 3.5
		// [i] field:0:optional min
		// [i] field:0:required max
		// [o] field:0:required random
		IDataMap pipe = new IDataMap(pipeline);
		int min = pipe.getAsInteger("min", 0);
		int max = pipe.getAsInteger("max");
		Random r = new Random();
		pipe.put("random", String.valueOf(r.nextInt(max - min) + min));			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static int startingPoNumber = 10000;
	public static final String[] customers = {"Google","AusPost"};
	public static final String[] products = {"iPhone7","Samsung Galaxy S7","iPad Pro","HTC Desire"};
	// --- <<IS-END-SHARED>> ---
}

